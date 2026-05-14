package cat.deim.asm01.pedalean2.data.datasource

import cat.deim.asm01.pedalean2.data.datasource.database.dao.BikeDao
import cat.deim.asm01.pedalean2.data.datasource.database.model.BikeEntity
import com.pedalean2.common.datasource.local.model.BikeModel
import com.pedalean2.common.interfaces.IDatasource

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
            type = this.type, meters = this.meters, lastUse = this.lastUse.toString(), lastMaintenance = this.lastMaintenance.toString(),
            batteryLevel = this.batteryLevel, isRented = this.isRented, isReserved = this.isReserved
        )
    }

    override fun getAll(): List<BikeModel> = bikeDao.getAll().map { it.toBikeModel() }

    override fun getById(uuid: String): BikeModel? = bikeDao.getById(uuid)?.toBikeModel()

    override fun insert(dataModel: BikeModel): Boolean = try { bikeDao.insert(dataModel.toBikeEntity()); true } catch (e: Exception) { false }

    override fun update(dataModel: BikeModel): Boolean = try { bikeDao.update(dataModel.toBikeEntity()); true } catch (e: Exception) { false }

    override fun delete(uuid: String): Boolean = try { bikeDao.delete(uuid); true } catch (e: Exception) { false }
}