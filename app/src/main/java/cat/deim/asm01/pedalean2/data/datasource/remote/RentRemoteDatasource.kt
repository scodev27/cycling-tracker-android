package cat.deim.asm01.pedalean2.data.datasource.remote

import cat.deim.asm01.pedalean2.data.datasource.remote.model.RentRequest
import cat.deim.asm01.pedalean2.common.models.RentModel
import cat.deim.asm01.pedalean2.common.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class RentRemoteDatasource : IDatasource<RentModel> {

    override fun getAll(): List<RentModel> = try {
        runBlocking(Dispatchers.IO) {
            val response = RetrofitClient.apiService.getRents(
                serverToken = RetrofitClient.SERVER_TOKEN,
                authToken = "Bearer ${RetrofitClient.accessToken}"
            )
            response.rents ?: emptyList()
        }
    } catch (e: Exception) {
        emptyList()
    }

    override fun getById(uuid: String): RentModel? {
        return getAll().find { it.uuid == uuid }
    }

    override fun insert(dataModel: RentModel): Boolean = false

    override fun update(dataModel: RentModel): Boolean = false

    override fun delete(uuid: String): Boolean = false

    fun startRentRemote(bikeUuid: String, lat: Double, lon: Double): RentModel? = try {
        runBlocking(Dispatchers.IO) {
            val response = RetrofitClient.apiService.startRent(
                serverToken = RetrofitClient.SERVER_TOKEN,
                authToken = "Bearer ${RetrofitClient.accessToken}",
                request = RentRequest(bikeUuid, lat, lon)
            )
            response.rent
        }
    } catch (e: Exception) {
        null
    }

    fun stopRentRemote(bikeUuid: String, lat: Double, lon: Double): RentModel? = try {
        runBlocking(Dispatchers.IO) {
            val response = RetrofitClient.apiService.stopRent(
                serverToken = RetrofitClient.SERVER_TOKEN,
                authToken = "Bearer ${RetrofitClient.accessToken}",
                request = RentRequest(bikeUuid, lat, lon)
            )
            response.rent
        }
    } catch (e: Exception) {
        null
    }
}