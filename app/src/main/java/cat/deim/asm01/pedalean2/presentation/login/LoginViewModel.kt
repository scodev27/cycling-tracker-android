package cat.deim.asm01.pedalean2.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm01.pedalean2.domain.repository.IUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
            _loginState.value = LoginState.Error("Siusplau, omple tots els camps.")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                val activeUser = userRepository.getActiveUser()

                if (activeUser.email == emailInput) {
                    if (passwordInput == "1234") {
                        _loginState.value = LoginState.Success
                    } else {
                        _loginState.value = LoginState.Error("Contrasenya incorrecta (Pista: és 1234)")
                    }
                } else {
                    _loginState.value = LoginState.Error("No s'ha trobat cap usuari amb aquest email.")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error: ${e.message}")
            }
        }
    }
}