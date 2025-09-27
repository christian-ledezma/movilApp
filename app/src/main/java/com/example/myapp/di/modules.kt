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
import com.example.myapp.features.movie.data.database.MovieDatabase
import com.example.myapp.features.movie.data.datasource.MovieRemoteDataSource
import com.example.myapp.features.movie.data.repository.MovieRepository
import com.example.myapp.features.movie.domain.repository.IMovieRepository
import com.example.myapp.features.movie.domain.usecase.FetchPopularMoviesUseCase
import com.example.myapp.features.movie.domain.usecase.GetLikedMoviesUseCase
import com.example.myapp.features.movie.domain.usecase.ToggleLikeUseCase
import com.example.myapp.features.movie.presentation.PopularMoviesViewModel
import com.example.myapp.features.profile.data.ProfileRepository
import com.example.myapp.features.profile.domain.repository.IProfileRepository
import com.example.myapp.features.profile.domain.usecase.GetProfileUseCase
import com.example.myapp.features.profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import java.util.concurrent.TimeUnit
import com.example.myapp.features.movie.data.repository.LikedMovieRepository
import com.example.myapp.features.movie.data.repository.LikedMovieRepositoryImpl

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

    // ===== GITHUB =====
    single<GithubService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_GITHUB)).create(GithubService::class.java)
    }
    single { GithubRemoteDataSource(get()) }
    single<IGithubRepository> { GithubRepository(get()) }
    factory { FindByNicknameUseCase(get()) }
    viewModel { GithubViewModel(get()) }

    // ===== DOLLAR =====
    single { RealTimeRemoteDataSource() }
    single<IDollarRepository> { DollarRepository(get()) }
    factory { CambioTipoDollarUseCase(get()) }
    viewModel { DollarViewModel(get()) }

    // ===== MOVIES =====
    // API Key
    single(named("apiKey")) {
        androidApplication().getString(R.string.api_key)
    }

    // Network
    single<MovieService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_MOVIE)).create(MovieService::class.java)
    }
    single { MovieRemoteDataSource(get(), get(named("apiKey"))) }

    // Remote Repository (para películas populares)
    single<IMovieRepository> { MovieRepository(get()) }

    // Room Database (para likes)
    single {
        MovieDatabase.getDatabase(androidContext())
    }
    single {
        get<MovieDatabase>().likedMovieDao()
    }

    // Local Repository (para likes)
    single<LikedMovieRepository> {
        LikedMovieRepositoryImpl(get())
    }

    // Use Cases
    factory { FetchPopularMoviesUseCase(get()) }
    factory { ToggleLikeUseCase(get()) }
    factory { GetLikedMoviesUseCase(get()) }

    // ViewModel
    viewModel {
        PopularMoviesViewModel(
            fetchPopularMovies = get(),
            toggleLikeUseCase = get(),
            getLikedMoviesUseCase = get()
        )
    }

    // ===== PROFILE =====
    single<IProfileRepository> { ProfileRepository() }
    factory { GetProfileUseCase(get()) }
    viewModel { ProfileViewModel(get()) }
}