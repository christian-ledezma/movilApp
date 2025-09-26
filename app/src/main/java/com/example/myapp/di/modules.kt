package com.example.myapp.di

import com.example.myapp.R
import com.example.myapp.features.dollar.data.Respository.DollarRepository
import com.example.myapp.features.dollar.datasource.RealTimeRemoteDataSource
import com.example.myapp.features.dollar.domain.repository.IDollarRepository
import com.example.myapp.features.dollar.domain.usecase.CambioTipoDollarUseCase
import com.example.myapp.features.dollar.presentation.DollarViewModel
import com.example.myapp.features.github.data.api.GithubService
import com.example.myapp.features.github.data.datasource.GithubRemoteDataSource
import com.example.myapp.features.github.data.repository.GithubRepository
import com.example.myapp.features.github.domain.repository.IGithubRepository
import com.example.myapp.features.github.domain.usecase.FindByNicknameUseCase
import com.example.myapp.features.github.presentation.GithubViewModel
import com.example.myapp.features.movie.data.api.MovieService
import com.example.myapp.features.movie.data.datasource.MovieRemoteDataSource
import com.example.myapp.features.movie.data.repository.MovieRepository
import com.example.myapp.features.movie.domain.repository.IMovieRepository
import com.example.myapp.features.movie.domain.usecase.FetchPopularMoviesUseCase
import com.example.myapp.features.movie.presentation.PopularMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import java.util.concurrent.TimeUnit


object NetworkConstants {
    const val RETROFIT_GITHUB = "RetrofitGithub"
    const val GITHUB_BASE_URL = "https://api.github.com/"
    const val RETROFIT_MOVIE = "RetrofitMovie"
    const val MOVIE_BASE_URL = "https://api.themoviedb.org/"
}

val appModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single(named(NetworkConstants.RETROFIT_GITHUB)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.GITHUB_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named(NetworkConstants.RETROFIT_MOVIE)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.MOVIE_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    single<GithubService> {
        get<Retrofit>( named(NetworkConstants.RETROFIT_GITHUB)).create(GithubService::class.java)
    }
    single{ GithubRemoteDataSource(get()) }
    single<IGithubRepository>{ GithubRepository(get()) }

    factory { FindByNicknameUseCase(get()) }
    viewModel { GithubViewModel(get()) }



    single { RealTimeRemoteDataSource() }
    single<IDollarRepository>{ DollarRepository(get()) }
    factory { CambioTipoDollarUseCase(get()) }
    viewModel{ DollarViewModel(get()) }


    single(named("apiKey")) {
        androidApplication().getString(R.string.api_key)
    }
    single<MovieService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_MOVIE)).create(MovieService::class.java)
    }
    single { MovieRemoteDataSource(get(), get(named("apiKey"))) }
    single<IMovieRepository> { MovieRepository(get()) }
    factory { FetchPopularMoviesUseCase(get()) }
    viewModel{ PopularMoviesViewModel(get()) }

}