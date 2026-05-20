package me.mariana.nino.proyecto_ecovoltapp.data

data class Trip(
    val id: String,
    val vehicleModel: String,
    val vehicleCode: String,
    val vehicleType: String,
    val location: String,
    val minutes: Int,
    val pricePerMinute: Int,
    val reservationFee: Int,
    val totalPaid: Int,
    val status: TripStatus,
    val dateText: String,
    val paymentMethod: String = "Visa demo · 4242"
)

enum class TripStatus(val label: String) {
    PROGRAMADO("Programado"),
    FINALIZADO("Finalizado")
}