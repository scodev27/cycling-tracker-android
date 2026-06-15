package cat.deim.asm01.pedalean2.data.datasource

import cat.deim.asm01.pedalean2.data.datasource.database.dao.RentDao
import cat.deim.asm01.pedalean2.data.datasource.database.model.RentEntity
import cat.deim.asm01.pedalean2.common.models.BikeRentModel
import cat.deim.asm01.pedalean2.common.models.RentModel
import cat.deim.asm01.pedalean2.common.models.UserRentModel
import cat.deim.asm01.pedalean2.common.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class RentLocalDatasource(private val rentDao: RentDao) : IDatasource<RentModel> {

    private fun RentEntity.toRentModel(): RentModel {
        return RentModel(
            uuid = this.uuid, timeStart = this.timeStart, timeEnd = this.timeEnd, isRented = this.isRented,
            rentTime = this.rentTime, rentMeters = this.rentMeters, rentStartLatitude = this.rentStartLatitude, rentStartLongitude = this.rentStartLongitude,
            bike = BikeRentModel(uuid = this.bikeUuid, name = this.bikeName),
            user = UserRentModel(username = this.userUsername, email = this.userEmail, firstName = this.userFirstName, lastName = this.userLastName)
        )
    }

    private fun RentModel.toRentEntity(): RentEntity {
        return RentEntity(
            uuid = this.uuid, timeStart = this.timeStart, timeEnd = this.timeEnd ?: "", isRented = this.isRented,
            rentTime = this.rentTime, rentMeters = this.rentMeters, rentStartLatitude = this.rentStartLatitude, rentStartLongitude = this.rentStartLongitude,
            bikeUuid = this.bike.uuid, bikeName = this.bike.name,
            userUsername = this.user.username, userEmail = this.user.email, userFirstName = this.user.firstName, userLastName = this.user.lastName
        )
    }

    override fun getAll(): List<RentModel> = runBlocking(Dispatchers.IO) { rentDao.getAll().map { it.toRentModel() } }

    override fun getById(uuid: String): RentModel? = runBlocking(Dispatchers.IO) { rentDao.getById(uuid)?.toRentModel() }

    override fun insert(dataModel: RentModel): Boolean = try { runBlocking(Dispatchers.IO) { rentDao.insert(dataModel.toRentEntity()) }; true } catch (e: Exception) { false }

    override fun update(dataModel: RentModel): Boolean = try { runBlocking(Dispatchers.IO) { rentDao.update(dataModel.toRentEntity()) }; true } catch (e: Exception) { false }

    override fun delete(uuid: String): Boolean = try { runBlocking(Dispatchers.IO) { rentDao.delete(uuid) }; true } catch (e: Exception) { false }
}