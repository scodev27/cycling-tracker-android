package cat.deim.asm01.pedalean2.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm01.pedalean2.domain.models.Bike
import cat.deim.asm01.pedalean2.domain.models.BikeRent
import cat.deim.asm01.pedalean2.domain.models.Rent
import cat.deim.asm01.pedalean2.domain.models.UserRent
import cat.deim.asm01.pedalean2.domain.repository.IBikeRepository
import cat.deim.asm01.pedalean2.domain.repository.IRentRepository
import cat.deim.asm01.pedalean2.domain.repository.IUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import kotlin.random.Random

sealed class BikeDetailState {
    object Loading : BikeDetailState()
    data class Success(val bike: Bike) : BikeDetailState()
    data class Error(val message: String) : BikeDetailState()
}

class BikeDetailViewModel(
    private val bikeRepository: IBikeRepository,
    private val rentRepository: IRentRepository,
    private val userRepository: IUserRepository,
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
                bikeRepository.getAll()

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
            val bike = currentState.bike
            val isNowRented = !bike.isRented

            val newLat = if (isNowRented) bike.latitude + Random.nextDouble(-0.005, 0.005).toFloat() else bike.latitude
            val newLon = if (isNowRented) bike.longitude + Random.nextDouble(-0.005, 0.005).toFloat() else bike.longitude

            viewModelScope.launch {
                try {
                    val activeUser = userRepository.getActiveUser()

                    val dummyRent = Rent(
                        uuid = UUID.randomUUID().toString(),
                        timeStart = Date(), timeEnd = Date(), isRented = isNowRented, rentTime = 0, rentMeters = 0,
                        rentStartLatitude = newLat, rentStartLongitude = newLon,
                        rents = BikeRent(uuid = bike.uuid, name = bike.name),
                        rented_by = UserRent(username = activeUser.username, email = activeUser.email, firstName = activeUser.name, lastName = "")
                    )

                    val success = if (isNowRented) {
                        rentRepository.createRent(dummyRent)
                    } else {
                        rentRepository.updateRent(dummyRent)
                    }

                    if (success) {
                        val updatedBike = bike.copy(
                            isRented = isNowRented, latitude = newLat, longitude = newLon
                        )
                        bikeRepository.update(updatedBike)
                        _state.value = BikeDetailState.Success(updatedBike)
                    } else {
                        loadBikeDetails()
                    }
                } catch (e: Exception) {
                    _state.value = BikeDetailState.Error("Error de connexió.")
                    loadBikeDetails()
                }
            }
        }
    }
}