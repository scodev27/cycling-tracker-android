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

            val updatedBike = bike.copy(
                isRented = isNowRented,
                latitude = newLat,
                longitude = newLon
            )

            viewModelScope.launch {
                try {
                    bikeRepository.update(updatedBike)

                    if (isNowRented) {
                        val activeUser = userRepository.getActiveUser()
                        val newRent = Rent(
                            uuid = UUID.randomUUID().toString(),
                            timeStart = Date(),
                            timeEnd = Date(),
                            isRented = true,
                            rentTime = 0,
                            rentMeters = 0,
                            rentStartLatitude = bike.latitude,
                            rentStartLongitude = bike.longitude,
                            rents = BikeRent(uuid = bike.uuid, name = bike.name),
                            rented_by = UserRent(
                                username = activeUser.username,
                                email = activeUser.email,
                                firstName = activeUser.name,
                                lastName = ""
                            )
                        )
                        rentRepository.createRent(newRent)
                    } else {
                        val activeRents = rentRepository.getAllRents()
                        val rentToStop = activeRents.find { it.rents.uuid == bike.uuid && it.isRented }

                        if (rentToStop != null) {
                            val now = Date()
                            val timeDiffMins = ((now.time - rentToStop.timeStart.time) / 60000).toInt()
                            val finalMins = if (timeDiffMins > 0) timeDiffMins else 1

                            val updatedRent = rentToStop.copy(
                                isRented = false,
                                timeEnd = now,
                                rentTime = finalMins,
                                rentMeters = Random.nextInt(800, 2500)
                            )
                            rentRepository.updateRent(updatedRent)
                        }
                    }
                    _state.value = BikeDetailState.Success(updatedBike)
                } catch (e: Exception) {
                    _state.value = BikeDetailState.Error("Error: ${e.message}")
                }
            }
        }
    }
}