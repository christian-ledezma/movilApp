package com.example.myapp.features.github.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.features.github.domain.model.UserModel
import com.example.myapp.features.github.domain.usecase.FindByNicknameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GithubViewModel (
    val useCase: FindByNicknameUseCase
) : ViewModel() {
    sealed class GithubStateUI {
        object Init: GithubStateUI()
        object Loading: GithubStateUI()
        class Error(val message: String): GithubStateUI()
        class Success(val github: UserModel): GithubStateUI()
    }
    private val _state = MutableStateFlow<GithubStateUI>(GithubStateUI.Init)

    val state : StateFlow<GithubStateUI> = _state.asStateFlow()

    fun fetchAlias(nickname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("GithubViewModel", "Iniciando búsqueda para: $nickname")//
            _state.value = GithubStateUI.Loading
            val result = useCase.invoke(nickname)
            Log.d("GithubViewModel", "Resultado obtenido: ${result.isSuccess}")//

            result.fold(
                onSuccess = {
                    user -> _state.value = GithubStateUI.Success (user)
                    Log.d("GithubViewModel", "Usuario encontrado: ${user.nickname}")
                },
                onFailure = {error->
                    Log.e("GithubViewModel", "Error encontrado: ${error.message}", error)
                    Log.e("GithubViewModel", "Tipo de error: ${error::class.java.simpleName}")
                    _state.value = GithubStateUI.Error(message = error.message ?: "Error desconocido")
                }
            )
        }
    }
}
