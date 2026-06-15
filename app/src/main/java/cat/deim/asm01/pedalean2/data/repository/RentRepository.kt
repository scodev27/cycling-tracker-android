package cat.deim.asm01.pedalean2.data.repository

import cat.deim.asm01.pedalean2.data.datasource.remote.RentRemoteDatasource
import cat.deim.asm01.pedalean2.domain.models.Rent
import cat.deim.asm01.pedalean2.domain.models.BikeRent
import cat.deim.asm01.pedalean2.domain.models.UserRent
import cat.deim.asm01.pedalean2.domain.repository.IRentRepository
import cat.deim.asm01.pedalean2.utils.toDate
import cat.deim.asm01.pedalean2.common.models.BikeRentModel
import cat.deim.asm01.pedalean2.common.models.RentModel
import cat.deim.asm01.pedalean2.common.models.UserRentModel
import cat.deim.asm01.pedalean2.common.IDatasource

class RentRepository(
    private val localDatasource: IDatasource<RentModel>,
    private val remoteDatasource: RentRemoteDatasource
) : IRentRepository {

    private fun RentModel.toDomain(): Rent {
        return Rent(
            uuid = this.uuid,
            timeStart = this.timeStart.toDate(),
            timeEnd = this.timeEnd.toDate(),
            isRented = this.isRented,
            rentTime = this.rentTime,
            rentMeters = this.rentMeters,
            rentStartLatitude = this.rentStartLatitude.toFloat(),
            rentStartLongitude = this.rentStartLongitude.toFloat(),
            rents = BikeRent(uuid = this.bike.uuid, name = this.bike.name),
            rented_by = UserRent(
                username = this.user.username,
                email = this.user.email,
                firstName = this.user.firstName,
                lastName = this.user.lastName
            )
        )
    }

    private fun Rent.toModel(): RentModel {
        return RentModel(
            uuid = this.uuid,
            timeStart = this.timeStart.toString(),
            timeEnd = this.timeEnd.toString(),
            isRented = this.isRented,
            rentTime = this.rentTime,
            rentMeters = this.rentMeters,
            rentStartLatitude = this.rentStartLatitude.toDouble(),
            rentStartLongitude = this.rentStartLongitude.toDouble(),
            bike = BikeRentModel(uuid = this.rents.uuid, name = this.rents.name),
            user = UserRentModel(
                username = this.rented_by.username,
                email = this.rented_by.email,
                firstName = this.rented_by.firstName,
                lastName = this.rented_by.lastName
            )
        )
    }

    override fun getAllRents(): List<Rent> {
        try {
            val remoteRents = remoteDatasource.getAll()
            if (remoteRents.isNotEmpty()) {
                remoteRents.forEach { localDatasource.insert(it) }
            }
        } catch (e: Exception) {}

        return localDatasource.getAll().map { it.toDomain() }
    }

    override fun createAllRents(rents: List<Rent>): Int = 0
    override fun updateAllRents(rents: List<Rent>): Int = 0
    override fun deleteAllRents(): Int = 0

    override fun getRentByUuid(uuid: String): Rent? {
        var rentModel = localDatasource.getById(uuid)
        if (rentModel == null) {
            rentModel = remoteDatasource.getById(uuid)
            rentModel?.let { localDatasource.insert(it) }
        }
        return rentModel?.toDomain()
    }

    override fun createRent(rent: Rent): Boolean {
        val remoteRentModel = remoteDatasource.startRentRemote(
            bikeUuid = rent.rents.uuid,
            lat = rent.rentStartLatitude.toDouble(),
            lon = rent.rentStartLongitude.toDouble()
        )
        return if (remoteRentModel != null) {
            localDatasource.insert(remoteRentModel)
            true
        } else {
            false
        }
    }

    override fun updateRent(rent: Rent): Boolean {
        val remoteRentModel = remoteDatasource.stopRentRemote(
            bikeUuid = rent.rents.uuid,
            lat = rent.rentStartLatitude.toDouble(),
            lon = rent.rentStartLongitude.toDouble()
        )
        return if (remoteRentModel != null) {
            localDatasource.update(remoteRentModel)
            true
        } else {
            false
        }
    }

    override fun deleteRent(uuid: String): Boolean = localDatasource.delete(uuid)

    override fun isRentActive(uuid: String): Boolean {
        return getRentByUuid(uuid)?.isRented ?: false
    }
}