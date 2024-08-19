package com.example.front.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReservationService {
    @POST("/api/reservation")
    suspend fun createReservation(@Body reservationRequest: ReservationRequest): Response<ReservationResponse>

    @GET("/api/reservation")
    suspend fun getReservations(): Response<List<Reservation>>
}

data class ReservationRequest(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val phoneNumber: String,
    val storeId: String
)

data class ReservationResponse(
    val success: Boolean,
    val message: String
)

data class Reservation(
    val id: Long, // 데이터베이스에 맞춰 Long 타입으로 수정
    val phoneNumber: String, // 전화번호
    val details: String?, // 세부사항 (nullable로 변경)
    val reservationTime: Long, // 예약 시간 (타임스탬프)
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val storeId: String, // 매장 ID
    val storeName: String, // 매장 이름
    val storeAddress: String // 매장 주소
)
