package me.mariana.nino.proyecto_ecovoltapp.data

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TripRepository {

    private val _trips = mutableStateListOf<Trip>()

    val trips: List<Trip>
        get() = _trips

    private val db = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null

    /**
     * Inicia un snapshot listener en Firestore para escuchar los viajes
     * del usuario actual en tiempo real. Cualquier cambio (alta, modificación,
     * eliminación) actualiza automáticamente la lista local y dispara la
     * recomposición de Compose en TripsScreen.
     */
    fun observeTrips(userId: String) {
        // Cancela cualquier listener anterior para evitar fugas de memoria
        listener?.remove()

        if (userId.isBlank()) {
            _trips.clear()
            return
        }

        listener = db.collection("reservas")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    return@addSnapshotListener
                }

                val incoming = snapshot.documents.mapNotNull { doc ->
                    try {
                        val statusName = doc.getString("status") ?: "PROGRAMADO"
                        val status = if (statusName == "FINALIZADO") {
                            TripStatus.FINALIZADO
                        } else {
                            TripStatus.PROGRAMADO
                        }

                        val createdAtMillis = doc.getTimestamp("fechaCreacion")
                            ?.toDate()
                            ?.time
                            ?: 0L

                        Trip(
                            id = doc.id,
                            vehicleModel = doc.getString("vehicleModel").orEmpty(),
                            vehicleCode = doc.getString("vehicleCode").orEmpty(),
                            vehicleType = doc.getString("vehicleType").orEmpty(),
                            location = doc.getString("location").orEmpty(),
                            minutes = (doc.getLong("minutes") ?: 0L).toInt(),
                            pricePerMinute = (doc.getLong("pricePerMinute") ?: 0L).toInt(),
                            reservationFee = (doc.getLong("reservationFee") ?: 0L).toInt(),
                            totalPaid = (doc.getLong("totalPaid") ?: 0L).toInt(),
                            status = status,
                            dateText = doc.getString("dateText").orEmpty(),
                            paymentMethod = doc.getString("paymentMethod") ?: "Visa demo · 4242",
                            createdAt = createdAtMillis
                        )
                    } catch (e: Exception) {
                        null
                    }
                }.sortedByDescending { it.createdAt }

                _trips.clear()
                _trips.addAll(incoming)
            }
    }

    /**
     * Detiene la escucha de Firestore. Llamar al salir de TripsScreen
     * o cuando el usuario hace logout para liberar recursos.
     */
    fun stopObserving() {
        listener?.remove()
        listener = null
    }

    /**
     * Crea una reserva nueva en Firestore. El snapshot listener
     * actualizará automáticamente la lista local cuando Firestore
     * confirme la escritura, por lo que el UI se refresca solo.
     */
    fun addScheduledTrip(
        userId: String,
        vehicle: Vehicle,
        minutes: Int,
        reservationFee: Int,
        totalPaid: Int
    ) {
        if (userId.isBlank()) return

        val tripData = hashMapOf(
            "userId" to userId,
            "vehicleModel" to vehicle.model,
            "vehicleCode" to vehicle.code,
            "vehicleType" to vehicle.type.label,
            "location" to vehicle.location,
            "minutes" to minutes,
            "pricePerMinute" to vehicle.pricePerMinute,
            "reservationFee" to reservationFee,
            "totalPaid" to totalPaid,
            "status" to TripStatus.PROGRAMADO.name,
            "dateText" to currentDateText(),
            "paymentMethod" to "Visa demo · 4242",
            "fechaCreacion" to FieldValue.serverTimestamp()
        )

        db.collection("reservas")
            .add(tripData)
    }

    private fun currentDateText(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy · hh:mm a", Locale("es", "CO"))
        return formatter.format(Date())
    }
}
