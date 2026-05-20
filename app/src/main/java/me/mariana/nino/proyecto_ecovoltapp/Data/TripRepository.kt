package me.mariana.nino.proyecto_ecovoltapp.data

import androidx.compose.runtime.mutableStateListOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TripRepository {

    private val _trips = mutableStateListOf<Trip>()

    val trips: List<Trip>
        get() = _trips

    init {
        if (_trips.isEmpty()) {
            _trips.addAll(
                listOf(
                    Trip(
                        id = "demo-1",
                        vehicleModel = "Patineta Eco Urbana",
                        vehicleCode = "ECO-1258",
                        vehicleType = "Patineta",
                        location = "Cra. 27 #45-33, Bucaramanga",
                        minutes = 30,
                        pricePerMinute = 450,
                        reservationFee = 1200,
                        totalPaid = 14700,
                        status = TripStatus.FINALIZADO,
                        dateText = "Viaje anterior"
                    ),
                    Trip(
                        id = "demo-2",
                        vehicleModel = "Moto Volt City",
                        vehicleCode = "MOTO-5643",
                        vehicleType = "Moto",
                        location = "Parque de los Niños, Bucaramanga",
                        minutes = 15,
                        pricePerMinute = 700,
                        reservationFee = 1200,
                        totalPaid = 11700,
                        status = TripStatus.FINALIZADO,
                        dateText = "Viaje anterior"
                    )
                )
            )
        }
    }

    fun addScheduledTrip(
        vehicle: Vehicle,
        minutes: Int,
        reservationFee: Int,
        totalPaid: Int
    ) {
        val trip = Trip(
            id = System.currentTimeMillis().toString(),
            vehicleModel = vehicle.model,
            vehicleCode = vehicle.code,
            vehicleType = vehicle.type.label,
            location = vehicle.location,
            minutes = minutes,
            pricePerMinute = vehicle.pricePerMinute,
            reservationFee = reservationFee,
            totalPaid = totalPaid,
            status = TripStatus.PROGRAMADO,
            dateText = currentDateText()
        )

        _trips.add(0, trip)
    }

    private fun currentDateText(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy · hh:mm a", Locale("es", "CO"))
        return formatter.format(Date())
    }
}
