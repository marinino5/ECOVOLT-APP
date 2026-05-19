package me.mariana.nino.proyecto_ecovoltapp.data

enum class VehicleType(val label: String) {
    PATINETA("Patineta"),
    MOTO("Moto")
}

enum class VehicleStatus(val label: String) {
    DISPONIBLE("Disponible"),
    EN_USO("En uso")
}

data class Vehicle(
    val code: String,
    val model: String,
    val type: VehicleType,
    val battery: Int,
    val pricePerMinute: Int,
    val distanceMeters: Int,
    val autonomyKm: Int,
    val location: String,
    val status: VehicleStatus
) {
    val isAvailable: Boolean
        get() = status == VehicleStatus.DISPONIBLE
}

object VehicleRepository {

    val vehicles = listOf(
        Vehicle(
            code = "ECO-1258",
            model = "Patineta Eco Urbana",
            type = VehicleType.PATINETA,
            battery = 78,
            pricePerMinute = 450,
            distanceMeters = 120,
            autonomyKm = 25,
            location = "Cra. 27 #45-33, Bucaramanga",
            status = VehicleStatus.DISPONIBLE
        ),
        Vehicle(
            code = "ECO-0987",
            model = "Patineta Volt Lite",
            type = VehicleType.PATINETA,
            battery = 45,
            pricePerMinute = 480,
            distanceMeters = 250,
            autonomyKm = 18,
            location = "Parque San Pío, Bucaramanga",
            status = VehicleStatus.DISPONIBLE
        ),
        Vehicle(
            code = "MOTO-5643",
            model = "Moto Volt City",
            type = VehicleType.MOTO,
            battery = 67,
            pricePerMinute = 700,
            distanceMeters = 300,
            autonomyKm = 35,
            location = "Parque de los Niños, Bucaramanga",
            status = VehicleStatus.DISPONIBLE
        ),
        Vehicle(
            code = "MOTO-7712",
            model = "Moto Eco Ride",
            type = VehicleType.MOTO,
            battery = 30,
            pricePerMinute = 750,
            distanceMeters = 500,
            autonomyKm = 20,
            location = "Parque Santander, Bucaramanga",
            status = VehicleStatus.EN_USO
        )
    )

    fun getVehicleByCode(code: String): Vehicle? {
        return vehicles.find { it.code == code }
    }

    fun getVehiclesByType(type: VehicleType): List<Vehicle> {
        return vehicles.filter { it.type == type }
    }
}