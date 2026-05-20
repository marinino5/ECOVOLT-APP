package me.mariana.nino.proyecto_ecovoltapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.mariana.nino.proyecto_ecovoltapp.auth.LoginScreen
import me.mariana.nino.proyecto_ecovoltapp.auth.RegisterScreen
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleRepository
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.FleetScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.HomeScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.MapScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.PaymentSimulatedScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.PlaceholderScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.ProfileScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.ReservationScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.SupportScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.TripsScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.VehicleDetailScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.WelcomeScreen

@Composable
fun EcovoltNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    fun navigateToReservationIfAvailable(vehicleCode: String) {
        val vehicle = VehicleRepository.getVehicleByCode(vehicleCode)

        if (vehicle?.isAvailable == true) {
            navController.navigate(AppRoutes.reservationRoute(vehicleCode))
        } else {
            Toast.makeText(
                context,
                "Este vehículo está en uso. No es posible reservarlo en este momento.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.WELCOME
    ) {
        composable(AppRoutes.WELCOME) {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(AppRoutes.LOGIN)
                },
                onRegisterClick = {
                    navController.navigate(AppRoutes.REGISTER)
                }
            )
        }

        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onBackToWelcome = {
                    navController.navigate(AppRoutes.WELCOME) {
                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onGoToRegister = {
                    navController.navigate(AppRoutes.REGISTER) {
                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onLoginSuccess = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AppRoutes.REGISTER) {
            RegisterScreen(
                onBackToWelcome = {
                    navController.navigate(AppRoutes.WELCOME) {
                        popUpTo(AppRoutes.REGISTER) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onGoToLogin = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.REGISTER) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AppRoutes.HOME) {
            HomeScreen(
                onMapClick = {
                    navController.navigate(AppRoutes.MAP) {
                        launchSingleTop = true
                    }
                },
                onFleetClick = {
                    navController.navigate(AppRoutes.FLEET) {
                        launchSingleTop = true
                    }
                },
                onHistoryClick = {
                    navController.navigate(AppRoutes.HISTORY) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(AppRoutes.PROFILE) {
                        launchSingleTop = true
                    }
                },
                onSupportClick = {
                    navController.navigate(AppRoutes.SUPPORT) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AppRoutes.MAP) {
            MapScreen(
                onFleetClick = { stationName ->
                    navController.navigate(AppRoutes.fleetRoute(stationName)) {
                        launchSingleTop = true
                    }
                },
                onHomeClick = {
                    navController.navigate(AppRoutes.HOME) {
                        launchSingleTop = true
                    }
                },
                onHistoryClick = {
                    navController.navigate(AppRoutes.HISTORY) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(AppRoutes.PROFILE) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AppRoutes.FLEET) {
            FleetScreen(
                stationName = "Estación Ecovolt",
                onMapClick = {
                    navController.navigate(AppRoutes.MAP) {
                        launchSingleTop = true
                    }
                },
                onFleetClick = {
                    navController.navigate(AppRoutes.FLEET) {
                        launchSingleTop = true
                    }
                },
                onHomeClick = {
                    navController.navigate(AppRoutes.HOME) {
                        launchSingleTop = true
                    }
                },
                onHistoryClick = {
                    navController.navigate(AppRoutes.HISTORY) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(AppRoutes.PROFILE) {
                        launchSingleTop = true
                    }
                },
                onDetailClick = { vehicleCode ->
                    navController.navigate(AppRoutes.vehicleDetailRoute(vehicleCode))
                },
                onReserveClick = { vehicleCode ->
                    navigateToReservationIfAvailable(vehicleCode)
                }
            )
        }

        composable(
            route = AppRoutes.FLEET_WITH_STATION,
            arguments = listOf(
                navArgument("stationName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val stationName = backStackEntry.arguments?.getString("stationName")
                ?: "Estación Ecovolt"

            FleetScreen(
                stationName = stationName,
                onMapClick = {
                    navController.navigate(AppRoutes.MAP) {
                        launchSingleTop = true
                    }
                },
                onFleetClick = {
                    navController.navigate(AppRoutes.FLEET) {
                        launchSingleTop = true
                    }
                },
                onHomeClick = {
                    navController.navigate(AppRoutes.HOME) {
                        launchSingleTop = true
                    }
                },
                onHistoryClick = {
                    navController.navigate(AppRoutes.HISTORY) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(AppRoutes.PROFILE) {
                        launchSingleTop = true
                    }
                },
                onDetailClick = { vehicleCode ->
                    navController.navigate(AppRoutes.vehicleDetailRoute(vehicleCode))
                },
                onReserveClick = { vehicleCode ->
                    navigateToReservationIfAvailable(vehicleCode)
                }
            )
        }

        composable(AppRoutes.SCOOTER_LIST) {
            FleetScreen(
                stationName = "Estación Ecovolt",
                onMapClick = {
                    navController.navigate(AppRoutes.MAP) {
                        launchSingleTop = true
                    }
                },
                onFleetClick = {
                    navController.navigate(AppRoutes.FLEET) {
                        launchSingleTop = true
                    }
                },
                onHomeClick = {
                    navController.navigate(AppRoutes.HOME) {
                        launchSingleTop = true
                    }
                },
                onHistoryClick = {
                    navController.navigate(AppRoutes.HISTORY) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(AppRoutes.PROFILE) {
                        launchSingleTop = true
                    }
                },
                onDetailClick = { vehicleCode ->
                    navController.navigate(AppRoutes.vehicleDetailRoute(vehicleCode))
                },
                onReserveClick = { vehicleCode ->
                    navigateToReservationIfAvailable(vehicleCode)
                }
            )
        }

        composable(
            route = AppRoutes.VEHICLE_DETAIL,
            arguments = listOf(
                navArgument("vehicleCode") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val vehicleCode = backStackEntry.arguments?.getString("vehicleCode") ?: ""
            val vehicle = VehicleRepository.getVehicleByCode(vehicleCode)

            if (vehicle != null) {
                VehicleDetailScreen(
                    vehicle = vehicle,
                    onBack = {
                        navController.popBackStack()
                    },
                    onReserveClick = {
                        navigateToReservationIfAvailable(vehicle.code)
                    },
                    onRouteClick = {
                        navController.navigate(AppRoutes.MAP) {
                            launchSingleTop = true
                        }
                    }
                )
            } else {
                PlaceholderScreen(
                    title = "Vehículo no encontrado",
                    subtitle = "No fue posible cargar la información del vehículo seleccionado.",
                    primaryButtonText = "Volver",
                    onPrimaryClick = {
                        navController.popBackStack()
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            route = AppRoutes.RESERVATION,
            arguments = listOf(
                navArgument("vehicleCode") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val vehicleCode = backStackEntry.arguments?.getString("vehicleCode") ?: ""
            val vehicle = VehicleRepository.getVehicleByCode(vehicleCode)

            when {
                vehicle == null -> {
                    PlaceholderScreen(
                        title = "Vehículo no encontrado",
                        subtitle = "No fue posible cargar la reserva porque el vehículo no existe.",
                        primaryButtonText = "Volver",
                        onPrimaryClick = {
                            navController.popBackStack()
                        },
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

                !vehicle.isAvailable -> {
                    PlaceholderScreen(
                        title = "Reserva no disponible",
                        subtitle = "Este vehículo está en uso. No es posible reservarlo en este momento.",
                        primaryButtonText = "Volver a la flota",
                        onPrimaryClick = {
                            navController.popBackStack()
                        },
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

                else -> {
                    ReservationScreen(
                        vehicle = vehicle,
                        onBack = {
                            navController.popBackStack()
                        },
                        onConfirmReservation = { selectedMinutes ->
                            navController.navigate(
                                AppRoutes.paymentRoute(
                                    vehicleCode = vehicle.code,
                                    minutes = selectedMinutes
                                )
                            )
                        },
                        onCancel = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }

        composable(
            route = AppRoutes.PAYMENT,
            arguments = listOf(
                navArgument("vehicleCode") {
                    type = NavType.StringType
                },
                navArgument("minutes") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val vehicleCode = backStackEntry.arguments?.getString("vehicleCode") ?: ""
            val selectedMinutes = backStackEntry.arguments?.getInt("minutes") ?: 15
            val vehicle = VehicleRepository.getVehicleByCode(vehicleCode)

            if (vehicle == null) {
                PlaceholderScreen(
                    title = "Pago no disponible",
                    subtitle = "No fue posible cargar la información del vehículo seleccionado.",
                    primaryButtonText = "Volver",
                    onPrimaryClick = {
                        navController.popBackStack()
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            } else {
                PaymentSimulatedScreen(
                    vehicle = vehicle,
                    selectedMinutes = selectedMinutes,
                    onBack = {
                        navController.popBackStack()
                    },
                    onCancel = {
                        navController.popBackStack()
                    },
                    onPaymentApproved = {
                        navController.navigate(AppRoutes.HISTORY) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        composable(AppRoutes.ACTIVE_TRIP) {
            PlaceholderScreen(
                title = "Viaje en curso",
                subtitle = "Tiempo, distancia, costo acumulado y estado del viaje.",
                primaryButtonText = "Finalizar viaje",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.FINISH_TRIP) {
                        launchSingleTop = true
                    }
                },
                secondaryButtonText = "Reportar problema",
                onSecondaryClick = {
                    navController.navigate(AppRoutes.SUPPORT) {
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.FINISH_TRIP) {
            PlaceholderScreen(
                title = "Viaje finalizado",
                subtitle = "Resumen final del recorrido realizado.",
                primaryButtonText = "Ver historial",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.HISTORY) {
                        launchSingleTop = true
                    }
                },
                secondaryButtonText = "Volver al mapa",
                onSecondaryClick = {
                    navController.navigate(AppRoutes.MAP) {
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.HISTORY) {
            TripsScreen(
                onMapClick = {
                    navController.navigate(AppRoutes.MAP) {
                        launchSingleTop = true
                    }
                },
                onFleetClick = {
                    navController.navigate(AppRoutes.FLEET) {
                        launchSingleTop = true
                    }
                },
                onHomeClick = {
                    navController.navigate(AppRoutes.HOME) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(AppRoutes.PROFILE) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AppRoutes.PROFILE) {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onHistoryClick = {
                    navController.navigate(AppRoutes.HISTORY) {
                        launchSingleTop = true
                    }
                },
                onSupportClick = {
                    navController.navigate(AppRoutes.SUPPORT) {
                        launchSingleTop = true
                    }
                },
                onLogoutClick = {
                    navController.navigate(AppRoutes.WELCOME) {
                        popUpTo(AppRoutes.WELCOME) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AppRoutes.SUPPORT) {
            SupportScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onCancelClick = {
                    navController.popBackStack()
                },
                onReportSent = {
                    navController.popBackStack()
                }
            )
        }
    }
}