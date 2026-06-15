package cat.deim.asm01.pedalean2.data.repository

import cat.deim.asm01.pedalean2.domain.models.Bike
import cat.deim.asm01.pedalean2.domain.repository.IBikeRepository
import cat.deim.asm01.pedalean2.utils.toDate
import cat.deim.asm01.pedalean2.common.models.BikeModel
import cat.deim.asm01.pedalean2.common.IDatasource

class BikeRepository(
    private val localDatasource: IDatasource<BikeModel>,
    private val remoteDatasource: IDatasource<BikeModel>
) : IBikeRepository {

    private fun BikeModel.toDomain(): Bike {
        return Bike(
            uuid = this.uuid,
            name = this.name,
            latitude = this.latitude.toFloat(),
            longitude = this.longitude.toFloat(),
            type = this.type,
            meters = this.meters,
            lastUse = this.lastUse.toDate(),
            lastMaintenance = this.lastMaintenance.toDate(),
            batteryLevel = this.batteryLevel,
            isRented = this.isRented,
            isReserved = this.isReserved
        )
    }

    private fun Bike.toModel(): BikeModel {
        return BikeModel(
            uuid = this.uuid,
            id = this.uuid,
            name = this.name,
            latitude = this.latitude.toDouble(),
            longitude = this.longitude.toDouble(),
            type = this.type,
            meters = this.meters,
            lastUse = this.lastUse.toString(),
            lastMaintenance = this.lastMaintenance.toString(),
            batteryLevel = this.batteryLevel,
            isRented = this.isRented,
            isReserved = this.isReserved
        )
    }

    override fun getAll(): List<Bike> {
        try {
            val remoteBikes = remoteDatasource.getAll()
            if (remoteBikes.isNotEmpty()) {
                remoteBikes.forEach { bikeModel ->
                    localDatasource.insert(bikeModel)
                }
                return remoteBikes.mapNotNull {
                    try { it.toDomain() } catch (e: Exception) { null }
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("REPO", "Error", e)
        }
        return localDatasource.getAll().map { it.toDomain() }
    }

    override fun insertAll(bikes: List<Bike>): Int {
        var count = 0
        bikes.forEach { bike ->
            if (localDatasource.insert(bike.toModel())) {
                count++
            }
        }
        return count
    }

    override fun updateAll(bikes: List<Bike>): Int {
        var count = 0
        bikes.forEach { bike ->
            if (localDatasource.update(bike.toModel())) {
                count++
            }
        }
        return count
    }

    override fun deleteAll(): Int {
        val allBikes = localDatasource.getAll()
        var count = 0
        allBikes.forEach { bikeModel ->
            if (localDatasource.delete(bikeModel.uuid)) {
                count++
            }
        }
        return count
    }

    override fun insert(bike: Bike): Boolean {
        return localDatasource.insert(bike.toModel())
    }

    override fun getByUuid(uuid: String): Bike? {
        var bikeModel = localDatasource.getById(uuid)
        if (bikeModel == null) {
            bikeModel = remoteDatasource.getById(uuid)
            bikeModel?.let { localDatasource.insert(it) }
        }
        return bikeModel?.toDomain()
    }

    override fun update(bike: Bike): Boolean {
        return localDatasource.update(bike.toModel())
    }

    override fun delete(uuid: String): Boolean {
        return localDatasource.delete(uuid)
    }
}