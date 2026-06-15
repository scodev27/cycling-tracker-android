package cat.deim.asm01.pedalean2.data.datasource.remote

import cat.deim.asm01.pedalean2.common.models.BikeModel
import cat.deim.asm01.pedalean2.common.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class BikeRemoteDatasource : IDatasource<BikeModel> {

    override fun getAll(): List<BikeModel> = try {
        runBlocking(Dispatchers.IO) {
            val response = RetrofitClient.apiService.getBikes(
                serverToken = RetrofitClient.SERVER_TOKEN,
                authToken = "Bearer ${RetrofitClient.accessToken}"
            )
            response.bike ?: emptyList()
        }
    } catch (e: Exception) {
        android.util.Log.e("API_ERROR", "Error descarregant les bicicletes remotament", e)
        emptyList()
    }

    override fun getById(uuid: String): BikeModel? {
        return getAll().find { it.uuid == uuid }
    }

    override fun insert(dataModel: BikeModel): Boolean = false
    override fun update(dataModel: BikeModel): Boolean = false
    override fun delete(uuid: String): Boolean = false
}