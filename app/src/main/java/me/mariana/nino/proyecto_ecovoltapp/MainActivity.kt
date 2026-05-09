package me.mariana.nino.proyecto_ecovoltapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.mariana.nino.proyecto_ecovoltapp.navigation.EcovoltNavigation
import me.mariana.nino.proyecto_ecovoltapp.ui.theme.Proyecto_ecovoltappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Proyecto_ecovoltappTheme {
                EcovoltNavigation()
            }
        }
    }
}