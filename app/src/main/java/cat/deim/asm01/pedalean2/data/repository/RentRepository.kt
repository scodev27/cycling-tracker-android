package cat.deim.asm01.pedalean2.data.repository

import cat.deim.asm01.pedalean2.domain.models.Rent
import cat.deim.asm01.pedalean2.domain.models.BikeRent
import cat.deim.asm01.pedalean2.domain.models.UserRent
import cat.deim.asm01.pedalean2.domain.repository.IRentRepository
import cat.deim.asm01.pedalean2.utils.toDate
import com.pedalean2.common.datasource.local.model.BikeRentModel
import com.pedalean2.common.datasource.local.model.RentModel
import com.pedalean2.common.datasource.local.model.UserRentModel
import com.pedalean2.common.interfaces.IDatasource

class RentRepository(
    private val localDatasource: IDatasource<RentModel>,
    private val remoteDatasource: IDatasource<RentModel>
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

            rents = BikeRent(
                uuid = this.bike.uuid,
                name = this.bike.name
            ),

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

            bike = BikeRentModel(
                uuid = this.rents.uuid,
                name = this.rents.name
            ),

            user = UserRentModel(
                username = this.rented_by.username,
                email = this.rented_by.email,
                firstName = this.rented_by.firstName,
                lastName = this.rented_by.lastName
            )
        )
    }

    override fun getAllRents(): List<Rent> {
        var localRents = localDatasource.getAll()

        // Sincronització inicial dels lloguers
        if (localRents.isEmpty()) {
            val remoteRents = remoteDatasource.getAll()
            remoteRents.forEach { localDatasource.insert(it) }
            localRents = localDatasource.getAll()
        }

        return localRents.map { it.toDomain() }
    }

    override fun createAllRents(rents: List<Rent>): Int {
        var count = 0
        rents.forEach { rent ->
            if (localDatasource.insert(rent.toModel())) {
                count++
            }
        }
        return count
    }

    override fun updateAllRents(rents: List<Rent>): Int {
        var count = 0
        rents.forEach { rent ->
            if (localDatasource.update(rent.toModel())) {
                count++
            }
        }
        return count
    }

    override fun deleteAllRents(): Int {
        val allRents = localDatasource.getAll()
        var count = 0
        allRents.forEach { rentModel ->
            if (localDatasource.delete(rentModel.uuid)) {
                count++
            }
        }
        return count
    }

    override fun getRentByUuid(uuid: String): Rent? {
        var rentModel = localDatasource.getById(uuid)

        if (rentModel == null) {
            rentModel = remoteDatasource.getById(uuid)
            rentModel?.let { localDatasource.insert(it) }
        }

        return rentModel?.toDomain()
    }

    override fun createRent(rent: Rent): Boolean {
        return localDatasource.insert(rent.toModel())
    }

    override fun updateRent(rent: Rent): Boolean {
        return localDatasource.update(rent.toModel())
    }

    override fun deleteRent(uuid: String): Boolean {
        return localDatasource.delete(uuid)
    }

    override fun isRentActive(uuid: String): Boolean {
        val rent = getRentByUuid(uuid)
        return rent?.isRented ?: false
    }
}