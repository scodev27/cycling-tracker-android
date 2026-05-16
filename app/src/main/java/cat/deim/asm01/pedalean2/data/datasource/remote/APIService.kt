package cat.deim.asm01.pedalean2.data.datasource.remote

import cat.deim.asm01.pedalean2.data.datasource.remote.model.LoginRequest
import cat.deim.asm01.pedalean2.data.datasource.remote.model.TokenResponse
import com.pedalean2.common.datasource.local.model.BikeModel
import com.pedalean2.common.datasource.local.model.RentModel
import com.pedalean2.common.datasource.local.model.UserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {

    @POST("endpoints/v2/token/")
    suspend fun login(
        @Header("server-token") serverToken: String,
        @Body request: LoginRequest
    ): TokenResponse

    @GET("endpoints/v2/user/")
    suspend fun getUser(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authToken: String
    ): List<UserModel>

    @GET("endpoints/v2/bike/")
    suspend fun getBikes(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authToken: String
    ): List<BikeModel>

    @GET("endpoints/v2/rent/")
    suspend fun getRents(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authToken: String
    ): List<RentModel>

}