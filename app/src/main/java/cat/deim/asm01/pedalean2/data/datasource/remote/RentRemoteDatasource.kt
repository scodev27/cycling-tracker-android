package cat.deim.asm01.pedalean2.data.datasource.remote

import com.pedalean2.common.datasource.local.model.RentModel
import com.pedalean2.common.interfaces.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class RentRemoteDatasource : IDatasource<RentModel> {

    override fun getAll(): List<RentModel> = try {
        runBlocking(Dispatchers.IO) {
            RetrofitClient.apiService.getRents(
                serverToken = RetrofitClient.SERVER_TOKEN,
                authToken = "Bearer ${RetrofitClient.accessToken}"
            )
        }
    } catch (e: Exception) {
        android.util.Log.e("API_ERROR", "Error descarregant l'historial de lloguers remotament", e)
        emptyList()
    }

    override fun getById(uuid: String): RentModel? {
        return getAll().find { it.uuid == uuid }
    }

    override fun insert(dataModel: RentModel): Boolean = false
    override fun update(dataModel: RentModel): Boolean = false
    override fun delete(uuid: String): Boolean = false
}