package cat.deim.asm01.pedalean2.data.datasource

import cat.deim.asm01.pedalean2.data.datasource.database.dao.UserDao
import cat.deim.asm01.pedalean2.data.datasource.database.model.UserEntity
import cat.deim.asm01.pedalean2.common.models.UserModel
import cat.deim.asm01.pedalean2.common.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class UserLocalDatasource(private val userDao: UserDao) : IDatasource<UserModel> {

    private fun UserEntity.toUserModel(): UserModel {
        return UserModel(
            uuid = this.uuid, name = this.name, userName = this.username, email = this.email,
            courseGroup = this.courseGroup, phoneNumber = this.phoneNumber, birthDate = this.birthDate,
            isInRenting = this.isInRenting, totalRentingTime = this.totalRentingTime, totalRents = this.totalRents,
            creditCardNumber = this.creditCardNumber, creditCardCvv = this.creditCardCvv,
            creditCardExpirationDateMonth = this.creditCardExpirationDateMonth, creditCardExpirationDateYear = this.creditCardExpirationDateYear
        )
    }

    private fun UserModel.toUserEntity(): UserEntity {
        return UserEntity(
            uuid = this.uuid, name = this.name, username = this.userName, email = this.email,
            courseGroup = this.courseGroup, phoneNumber = this.phoneNumber, birthDate = this.birthDate,
            isInRenting = this.isInRenting, totalRentingTime = this.totalRentingTime, totalRents = this.totalRents,
            creditCardNumber = this.creditCardNumber, creditCardCvv = this.creditCardCvv,
            creditCardExpirationDateMonth = this.creditCardExpirationDateMonth, creditCardExpirationDateYear = this.creditCardExpirationDateYear
        )
    }

    override fun getAll(): List<UserModel> = runBlocking(Dispatchers.IO) { userDao.getAll().map { it.toUserModel() } }

    override fun getById(uuid: String): UserModel? = runBlocking(Dispatchers.IO) { userDao.getById(uuid)?.toUserModel() }

    override fun insert(dataModel: UserModel): Boolean = try { runBlocking(Dispatchers.IO) { userDao.insert(dataModel.toUserEntity()) }; true } catch (e: Exception) { false }

    override fun update(dataModel: UserModel): Boolean = try { runBlocking(Dispatchers.IO) { userDao.update(dataModel.toUserEntity()) }; true } catch (e: Exception) { false }

    override fun delete(uuid: String): Boolean = try { runBlocking(Dispatchers.IO) { userDao.delete(uuid) }; true } catch (e: Exception) { false }
}