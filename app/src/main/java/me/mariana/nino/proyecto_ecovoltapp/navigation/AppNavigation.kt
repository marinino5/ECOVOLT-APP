package me.mariana.nino.proyecto_ecovoltapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.mariana.nino.proyecto_ecovoltapp.auth.LoginScreen
import me.mariana.nino.proyecto_ecovoltapp.auth.RegisterScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.PlaceholderScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.WelcomeScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.MapScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.HomeScreen

@Composable
fun EcovoltNavigation() {
    val navController = rememberNavController()

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
                    }
                },
                onGoToRegister = {
                    navController.navigate(AppRoutes.REGISTER) {
                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onLoginSuccess = {
                    navController.navigate(route = AppRoutes.HOME) {
                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
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
                    }
                },
                onGoToLogin = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.REGISTER) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AppRoutes.HOME) {
            HomeScreen(
                onMapClick = {
                    navController.navigate(route = AppRoutes.MAP)
                },
                onFleetClick = {
                    navController.navigate(route = AppRoutes.SCOOTER_LIST)
                },
                onHistoryClick = {
                    navController.navigate(route = AppRoutes.HISTORY)
                },
                onProfileClick = {
                    navController.navigate(route = AppRoutes.PROFILE)
                },
                onSupportClick = {
                    navController.navigate(route = AppRoutes.SUPPORT)
                }
            )
        }
        composable(route = AppRoutes.MAP) {
            MapScreen(
                onViewFleetClick = {
                    navController.navigate(route = AppRoutes.SCOOTER_LIST)
                },
                onHomeClick = {
                    navController.navigate(route = AppRoutes.HOME) {
                        popUpTo(AppRoutes.HOME) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable(AppRoutes.SCOOTER_LIST) {
            PlaceholderScreen(
                title = "Flota de patinetes",
                subtitle = "Aquí se mostrará la lista de patinetes disponibles.",
                primaryButtonText = "Ver detalle del patinete",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.SCOOTER_DETAIL)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.SCOOTER_DETAIL) {
            PlaceholderScreen(
                title = "Detalle del patinete",
                subtitle = "Información del vehículo, batería, tarifa y estado.",
                primaryButtonText = "Reservar ahora",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.RESERVATION)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.RESERVATION) {
            PlaceholderScreen(
                title = "Confirmar reserva",
                subtitle = "Revisa el patinete seleccionado antes del pago.",
                primaryButtonText = "Confirmar reserva",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.PAYMENT)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.PAYMENT) {
            PlaceholderScreen(
                title = "Pago simulado",
                subtitle = "Resumen del viaje y método de pago ficticio.",
                primaryButtonText = "Pagar y activar",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.ACTIVE_TRIP)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.ACTIVE_TRIP) {
            PlaceholderScreen(
                title = "Viaje en curso",
                subtitle = "Tiempo, distancia, costo acumulado y estado del viaje.",
                primaryButtonText = "Finalizar viaje",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.FINISH_TRIP)
                },
                secondaryButtonText = "Reportar problema",
                onSecondaryClick = {
                    navController.navigate(AppRoutes.SUPPORT)
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
                    navController.navigate(AppRoutes.HISTORY)
                },
                secondaryButtonText = "Volver al mapa",
                onSecondaryClick = {
                    navController.navigate(AppRoutes.MAP)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.HISTORY) {
            PlaceholderScreen(
                title = "Historial de viajes",
                subtitle = "Consulta tus recorridos anteriores.",
                primaryButtonText = "Ir a perfil",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.PROFILE)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.PROFILE) {
            PlaceholderScreen(
                title = "Mi perfil",
                subtitle = "Información básica del usuario y accesos principales.",
                primaryButtonText = "Soporte",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.SUPPORT)
                },
                secondaryButtonText = "Cerrar sesión",
                onSecondaryClick = {
                    navController.navigate(AppRoutes.WELCOME) {
                        popUpTo(AppRoutes.WELCOME) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.SUPPORT) {
            PlaceholderScreen(
                title = "Reporte / Soporte",
                subtitle = "Formulario para reportar problemas durante el uso del servicio.",
                primaryButtonText = "Enviar reporte demo",
                onPrimaryClick = {
                    navController.navigate(AppRoutes.MAP)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}