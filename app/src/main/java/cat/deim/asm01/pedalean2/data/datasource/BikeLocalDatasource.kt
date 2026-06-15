package cat.deim.asm01.pedalean2.data.datasource

import cat.deim.asm01.pedalean2.data.datasource.database.dao.BikeDao
import cat.deim.asm01.pedalean2.data.datasource.database.model.BikeEntity
import cat.deim.asm01.pedalean2.common.models.BikeModel
import cat.deim.asm01.pedalean2.common.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class BikeLocalDatasource(private val bikeDao: BikeDao) : IDatasource<BikeModel> {

    private fun BikeEntity.toBikeModel(): BikeModel {
        return BikeModel(
            uuid = this.uuid, id = this.uuid, name = this.name, latitude = this.latitude, longitude = this.longitude,
            type = this.type, meters = this.meters, lastUse = this.lastUse, lastMaintenance = this.lastMaintenance,
            batteryLevel = this.batteryLevel, isRented = this.isRented, isReserved = this.isReserved
        )
    }

    private fun BikeModel.toBikeEntity(): BikeEntity {
        return BikeEntity(
            uuid = this.uuid, name = this.name, latitude = this.latitude, longitude = this.longitude,
            type = this.type, meters = this.meters, lastUse = this.lastUse ?: "", lastMaintenance = this.lastMaintenance ?: "",
            batteryLevel = this.batteryLevel, isRented = this.isRented, isReserved = this.isReserved
        )
    }

    override fun getAll(): List<BikeModel> = runBlocking(Dispatchers.IO) { bikeDao.getAll().map { it.toBikeModel() } }

    override fun getById(uuid: String): BikeModel? = runBlocking(Dispatchers.IO) { bikeDao.getById(uuid)?.toBikeModel() }

    override fun insert(dataModel: BikeModel): Boolean = try { runBlocking(Dispatchers.IO) { bikeDao.insert(dataModel.toBikeEntity()) }; true } catch (e: Exception) { false }

    override fun update(dataModel: BikeModel): Boolean = try { runBlocking(Dispatchers.IO) { bikeDao.update(dataModel.toBikeEntity()) }; true } catch (e: Exception) { false }

    override fun delete(uuid: String): Boolean = try { runBlocking(Dispatchers.IO) { bikeDao.delete(uuid) }; true } catch (e: Exception) { false }
}