package me.mariana.nino.proyecto_ecovoltapp.navigation

object AppRoutes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val MAP = "map"

    const val FLEET = "fleet"
    const val FLEET_WITH_STATION = "fleet/{stationName}"

    const val SCOOTER_LIST = "scooter_list"
    const val SCOOTER_DETAIL = "scooter_detail"
    const val RESERVATION = "reservation"
    const val PAYMENT = "payment"
    const val ACTIVE_TRIP = "active_trip"
    const val FINISH_TRIP = "finish_trip"
    const val HISTORY = "history"
    const val PROFILE = "profile"
    const val SUPPORT = "support"

    fun fleetRoute(stationName: String): String {
        return "fleet/$stationName"
    }
}