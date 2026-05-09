package me.mariana.nino.proyecto_ecovoltapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.PlaceholderScreen
import me.mariana.nino.proyecto_ecovoltapp.ui.screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.WELCOME
    ) {
        composable(AppRoutes.WELCOME) {
            WelcomeScreen(
                onLoginClick = { navController.navigate(AppRoutes.LOGIN) },
                onRegisterClick = { navController.navigate(AppRoutes.REGISTER) }
            )
        }

        composable(AppRoutes.LOGIN) {
            PlaceholderScreen(
                title = "Iniciar sesión",
                subtitle = "Aquí irá la pantalla de login con correo y contraseña.",
                primaryButtonText = "Ingresar demo",
                onPrimaryClick = { navController.navigate(AppRoutes.MAP) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(AppRoutes.REGISTER) {
            PlaceholderScreen(
                title = "Crear cuenta",
                subtitle = "Aquí irá el formulario de registro del usuario.",
                primaryButtonText = "Crear cuenta demo",
                onPrimaryClick = { navController.navigate(AppRoutes.MAP) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.HOW_IT_WORKS) {
            PlaceholderScreen(
                title = "¿Cómo funciona?",
                subtitle = "1. Elige una estación\n2. Reserva un patinete\n3. Inicia tu viaje\n4. Finaliza y paga de forma simulada",
                primaryButtonText = "Entendido",
                onPrimaryClick = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.ABOUT) {
            PlaceholderScreen(
                title = "Quiénes somos",
                subtitle = "Ecovolt es una propuesta académica de movilidad eléctrica sostenible para Bucaramanga.",
                primaryButtonText = "Volver",
                onPrimaryClick = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }


        composable(AppRoutes.MAP) {
            PlaceholderScreen(
                title = "Mapa de parques",
                subtitle = "Selecciona una estación disponible en Bucaramanga.",
                primaryButtonText = "Ver flota disponible",
                onPrimaryClick = { navController.navigate(AppRoutes.SCOOTER_LIST) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.SCOOTER_LIST) {
            PlaceholderScreen(
                title = "Flota de patinetes",
                subtitle = "Aquí se mostrará la lista de patinetes disponibles.",
                primaryButtonText = "Ver detalle del patinete",
                onPrimaryClick = { navController.navigate(AppRoutes.SCOOTER_DETAIL) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.SCOOTER_DETAIL) {
            PlaceholderScreen(
                title = "Detalle del patinete",
                subtitle = "Información del vehículo, batería, tarifa y estado.",
                primaryButtonText = "Reservar ahora",
                onPrimaryClick = { navController.navigate(AppRoutes.RESERVATION) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.RESERVATION) {
            PlaceholderScreen(
                title = "Confirmar reserva",
                subtitle = "Revisa el patinete seleccionado antes del pago.",
                primaryButtonText = "Confirmar reserva",
                onPrimaryClick = { navController.navigate(AppRoutes.PAYMENT) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.PAYMENT) {
            PlaceholderScreen(
                title = "Pago simulado",
                subtitle = "Resumen del viaje y método de pago ficticio.",
                primaryButtonText = "Pagar y activar",
                onPrimaryClick = { navController.navigate(AppRoutes.ACTIVE_TRIP) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.ACTIVE_TRIP) {
            PlaceholderScreen(
                title = "Viaje en curso",
                subtitle = "Tiempo, distancia, costo acumulado y estado del viaje.",
                primaryButtonText = "Finalizar viaje",
                onPrimaryClick = { navController.navigate(AppRoutes.FINISH_TRIP) },
                secondaryButtonText = "Reportar problema",
                onSecondaryClick = { navController.navigate(AppRoutes.SUPPORT) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.FINISH_TRIP) {
            PlaceholderScreen(
                title = "Viaje finalizado",
                subtitle = "Resumen final del recorrido realizado.",
                primaryButtonText = "Ver historial",
                onPrimaryClick = { navController.navigate(AppRoutes.HISTORY) },
                secondaryButtonText = "Volver al mapa",
                onSecondaryClick = { navController.navigate(AppRoutes.MAP) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.HISTORY) {
            PlaceholderScreen(
                title = "Historial de viajes",
                subtitle = "Consulta tus recorridos anteriores.",
                primaryButtonText = "Ir a perfil",
                onPrimaryClick = { navController.navigate(AppRoutes.PROFILE) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.PROFILE) {
            PlaceholderScreen(
                title = "Mi perfil",
                subtitle = "Información básica del usuario y accesos principales.",
                primaryButtonText = "Soporte",
                onPrimaryClick = { navController.navigate(AppRoutes.SUPPORT) },
                secondaryButtonText = "Cerrar sesión",
                onSecondaryClick = { navController.navigate(AppRoutes.WELCOME) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.SUPPORT) {
            PlaceholderScreen(
                title = "Reporte / Soporte",
                subtitle = "Formulario para reportar problemas durante el uso del servicio.",
                primaryButtonText = "Enviar reporte demo",
                onPrimaryClick = { navController.navigate(AppRoutes.MAP) },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}