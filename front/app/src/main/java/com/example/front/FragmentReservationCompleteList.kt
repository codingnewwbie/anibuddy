package com.example.front

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.front.adapter.ReservationAdapter
import com.example.front.retrofit.Reservation
import com.example.front.retrofit.RetrofitService
import kotlinx.coroutines.launch
import java.util.Calendar

class FragmentReservationCompleteList : Fragment() {
    private var reservationList: List<Reservation> = listOf()
    private lateinit var reservationAdapter: ReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reservation_complete_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // 클릭 리스너를 포함한 어댑터 초기화
        reservationAdapter = ReservationAdapter(reservationList.toMutableList()) { reservation ->
            // 클릭 시 ReservationCompleteActivity로 이동
            val intent = Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra("reservationId", reservation.id) // 예약 ID 전달
            }
            startActivity(intent)
        }

        recyclerView.adapter = reservationAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 완료된 예약 목록을 가져오는 로직 추가
        fetchCompletedReservations()

        return view
    }

    private fun fetchCompletedReservations() {
        lifecycleScope.launch {
            try {
                val response = RetrofitService.reservationService.getReservations() // 예약 목록 가져오기
                if (response.isSuccessful) {
                    response.body()?.let { allReservations ->
                        val currentTime = Calendar.getInstance() // 현재 시간 가져오기

                        // 예약일자가 지난 예약만 필터링
                        reservationList = allReservations.filter { reservation ->
                            val reservationTime = Calendar.getInstance().apply {
                                set(reservation.year, reservation.month - 1, reservation.day, reservation.hour, reservation.minute) // 월은 0부터 시작
                            }
                            // 예약 시간이 현재 시간보다 지났는지 비교
                            reservationTime.before(currentTime) || reservationTime.equals(currentTime)
                        }
                        reservationAdapter.updateReservations(reservationList) // 어댑터에 데이터 업데이트
                    } ?: Log.e("FragmentReservationCompleteList", "Response body is null")
                } else {
                    Log.e("FragmentReservationCompleteList", "Error fetching reservations: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("FragmentReservationCompleteList", "Exception while fetching reservations", e)
            }
        }
    }
}
