package cat.deim.asm01.pedalean2.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm01.pedalean2.domain.models.Bike
import cat.deim.asm01.pedalean2.domain.models.User
import cat.deim.asm01.pedalean2.domain.repository.IBikeRepository
import cat.deim.asm01.pedalean2.domain.repository.IUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MainState {
    object Loading : MainState()
    data class Success(val user: User, val bikes: List<Bike>) : MainState()
    data class Error(val message: String) : MainState()
}

class MainViewModel(
    private val userRepository: IUserRepository,
    private val bikeRepository: IBikeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val currentUser = userRepository.getActiveUser()

                val allBikes = bikeRepository.getAll()

                _state.value = MainState.Success(user = currentUser, bikes = allBikes)
            } catch (e: Exception) {
                _state.value = MainState.Error("Error al carregar les dades: ${e.message}")
            }
        }
    }
}