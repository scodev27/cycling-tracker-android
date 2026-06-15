package cat.deim.asm01.pedalean2.data.repository

import cat.deim.asm01.pedalean2.domain.models.User
import cat.deim.asm01.pedalean2.domain.repository.IUserRepository
import cat.deim.asm01.pedalean2.common.models.UserModel
import cat.deim.asm01.pedalean2.common.IDatasource
import cat.deim.asm01.pedalean2.utils.toDate

class UserRepository(
    private val localDatasource: IDatasource<UserModel>,
    private val remoteDatasource: IDatasource<UserModel>
) : IUserRepository {

    private fun UserModel.toDomain(): User {
        return User(
            uuid = this.uuid,
            name = this.name,
            username = this.userName,
            email = this.email,
            courseGroup = this.courseGroup,
            phoneNumber = this.phoneNumber,
            birthDate = this.birthDate.toDate(),
            isInRenting = this.isInRenting,
            totalRentingTime = this.totalRentingTime,
            totalRents = this.totalRents,
            creditCardNumber = this.creditCardNumber,
            creditCardCvv = this.creditCardCvv,
            creditCardExpirationDateMonth = this.creditCardExpirationDateMonth,
            creditCardExpirationDateYear = this.creditCardExpirationDateYear
        )
    }

    private fun User.toModel(): UserModel {
        return UserModel(
            uuid = this.uuid,
            name = this.name,
            userName = this.username,
            email = this.email,
            courseGroup = this.courseGroup,
            phoneNumber = this.phoneNumber,
            birthDate = this.birthDate.toString(),
            isInRenting = this.isInRenting,
            totalRentingTime = this.totalRentingTime,
            totalRents = this.totalRents,
            creditCardNumber = this.creditCardNumber,
            creditCardCvv = this.creditCardCvv,
            creditCardExpirationDateMonth = this.creditCardExpirationDateMonth,
            creditCardExpirationDateYear = this.creditCardExpirationDateYear
        )
    }

    override fun getUserById(uuid: String): User {
        var userModel = localDatasource.getById(uuid)

        if (userModel == null) {
            userModel = remoteDatasource.getById(uuid)
            userModel?.let { localDatasource.insert(it) }
        }

        return userModel!!.toDomain()
    }

    override fun getActiveUser(): User {
        var allUsers = localDatasource.getAll()

        if (allUsers.isEmpty()) {
            val remoteUsers = remoteDatasource.getAll()
            remoteUsers.forEach { localDatasource.insert(it) }
            allUsers = localDatasource.getAll()
        }

        return allUsers.firstOrNull()?.toDomain()
            ?: throw Exception("No s'ha pogut descarregar el perfil del servidor.")
    }

    override fun setActiveUser(user: User) {
        localDatasource.insert(user.toModel())
    }

    override fun updateActiveUser(user: User) {
        localDatasource.update(user.toModel())
    }

    override fun deleteActiveUser(user: User) {
        localDatasource.delete(user.uuid)
    }
}