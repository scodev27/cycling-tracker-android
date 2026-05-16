package cat.deim.asm01.pedalean2.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm01.pedalean2.data.datasource.remote.RetrofitClient
import cat.deim.asm01.pedalean2.data.datasource.remote.model.LoginRequest
import cat.deim.asm01.pedalean2.domain.repository.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(emailInput: String, passwordInput: String) {
        if (emailInput.isBlank() || passwordInput.isBlank()) {
            _loginState.value = LoginState.Error("Si us plau, omple tots els camps.")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.login(
                        serverToken = RetrofitClient.SERVER_TOKEN,
                        request = LoginRequest(emailInput, passwordInput)
                    )
                }

                RetrofitClient.accessToken = response.access

                withContext(Dispatchers.IO) {
                    userRepository.getActiveUser()
                }

                _loginState.value = LoginState.Success

            } catch (e: retrofit2.HttpException) {
                _loginState.value = LoginState.Error("Correu o contrasenya incorrectes.")
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de xarxa: ${e.message}")
            }
        }
    }
}