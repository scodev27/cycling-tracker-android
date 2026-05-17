package cat.deim.asm01.pedalean2.data.datasource.remote

import cat.deim.asm01.pedalean2.data.datasource.remote.model.LoginRequest
import cat.deim.asm01.pedalean2.data.datasource.remote.model.RentRequest
import cat.deim.asm01.pedalean2.data.datasource.remote.model.TokenResponse
import cat.deim.asm01.pedalean2.data.datasource.remote.model.BikeResponse
import cat.deim.asm01.pedalean2.data.datasource.remote.model.UserResponse
import cat.deim.asm01.pedalean2.data.datasource.remote.model.RentHistoryResponse
import cat.deim.asm01.pedalean2.data.datasource.remote.model.SingleRentResponse
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

    @GET("endpoints/v2/user")
    suspend fun getUser(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authToken: String
    ): UserResponse

    @GET("endpoints/v2/bike")
    suspend fun getBikes(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authToken: String
    ): BikeResponse

    @GET("endpoints/v2/rent")
    suspend fun getRents(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authToken: String
    ): RentHistoryResponse

    @POST("endpoints/v2/rent/start")
    suspend fun startRent(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authToken: String,
        @Body request: RentRequest
    ): SingleRentResponse

    @POST("endpoints/v2/rent/stop")
    suspend fun stopRent(
        @Header("server-token") serverToken: String,
        @Header("Authorization") authToken: String,
        @Body request: RentRequest
    ): SingleRentResponse
}