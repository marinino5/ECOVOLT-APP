package me.mariana.nino.proyecto_ecovoltapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.mariana.nino.proyecto_ecovoltapp.navigation.EcovoltNavigation
import me.mariana.nino.proyecto_ecovoltapp.ui.theme.Proyecto_ecovoltappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.WHITE,
                android.graphics.Color.WHITE
            )
        )

        setContent {
            Proyecto_ecovoltappTheme {
                EcovoltNavigation()
            }
        }
    }
}