package cat.deim.asm01.pedalean2.data.datasource.remote

import cat.deim.asm01.pedalean2.common.models.UserModel
import cat.deim.asm01.pedalean2.common.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class UserRemoteDatasource : IDatasource<UserModel> {

    override fun getAll(): List<UserModel> = try {
        runBlocking(Dispatchers.IO) {
            val response = RetrofitClient.apiService.getUser(
                serverToken = RetrofitClient.SERVER_TOKEN,
                authToken = "Bearer ${RetrofitClient.accessToken}"
            )
            listOf(response.user)
        }
    } catch (e: Exception) {
        android.util.Log.e("API_ERROR", "Error baixant usuari: ${e.message}", e)
        emptyList()
    }

    override fun getById(uuid: String): UserModel? {
        return getAll().firstOrNull()
    }

    override fun insert(dataModel: UserModel): Boolean = false
    override fun update(dataModel: UserModel): Boolean = false
    override fun delete(uuid: String): Boolean = false
}