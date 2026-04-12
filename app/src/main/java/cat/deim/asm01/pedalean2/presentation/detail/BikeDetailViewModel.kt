package cat.deim.asm01.pedalean2.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm01.pedalean2.domain.models.Bike
import cat.deim.asm01.pedalean2.domain.repository.IBikeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class BikeDetailState {
    object Loading : BikeDetailState()
    data class Success(val bike: Bike) : BikeDetailState()
    data class Error(val message: String) : BikeDetailState()
}

class BikeDetailViewModel(
    private val bikeRepository: IBikeRepository,
    private val bikeUuid: String
) : ViewModel() {

    private val _state = MutableStateFlow<BikeDetailState>(BikeDetailState.Loading)
    val state: StateFlow<BikeDetailState> = _state.asStateFlow()

    init {
        loadBikeDetails()
    }

    private fun loadBikeDetails() {
        viewModelScope.launch {
            try {
                val bike = bikeRepository.getByUuid(bikeUuid)
                if (bike != null) {
                    _state.value = BikeDetailState.Success(bike)
                } else {
                    _state.value = BikeDetailState.Error("Bicicleta no trobada")
                }
            } catch (e: Exception) {
                _state.value = BikeDetailState.Error("Error: ${e.message}")
            }
        }
    }

    fun toggleRentStatus() {
        val currentState = _state.value
        if (currentState is BikeDetailState.Success) {
            val currentBike = currentState.bike
            val updatedBike = currentBike.copy(isRented = !currentBike.isRented)
            _state.value = BikeDetailState.Success(updatedBike)
        }
    }
}