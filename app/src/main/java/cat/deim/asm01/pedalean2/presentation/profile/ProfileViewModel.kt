package cat.deim.asm01.pedalean2.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm01.pedalean2.domain.models.Rent
import cat.deim.asm01.pedalean2.domain.models.User
import cat.deim.asm01.pedalean2.domain.repository.IRentRepository
import cat.deim.asm01.pedalean2.domain.repository.IUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: User, val rents: List<Rent>) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

class ProfileViewModel(
    private val userRepository: IUserRepository,
    private val rentRepository: IRentRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            try {
                val currentUser = userRepository.getActiveUser()
                val allRents = rentRepository.getAllRents()

                _state.value = ProfileState.Success(user = currentUser, rents = allRents)
            } catch (e: Exception) {
                _state.value = ProfileState.Error("Error al carregar el perfil: ${e.message}")
            }
        }
    }
}