package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.mariana.nino.proyecto_ecovoltapp.R
import me.mariana.nino.proyecto_ecovoltapp.data.Vehicle
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleType

@DrawableRes
fun getVehicleImageRes(vehicle: Vehicle): Int {
    return when (vehicle.code) {
        "ECO-1258" -> R.drawable.patineta1
        "ECO-0987" -> R.drawable.patineta2
        "MOTO-5643" -> R.drawable.moto1
        "MOTO-7712" -> R.drawable.moto2
        else -> {
            when (vehicle.type) {
                VehicleType.PATINETA -> R.drawable.patineta1
                VehicleType.MOTO -> R.drawable.moto1
            }
        }
    }
}

@Composable
fun VehicleThumbnail(
    vehicle: Vehicle,
    containerSize: Dp = 82.dp,
    imageSize: Dp = 74.dp
) {
    Box(
        modifier = Modifier.size(containerSize),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = getVehicleImageRes(vehicle)),
            contentDescription = vehicle.model,
            modifier = Modifier.size(imageSize),
            contentScale = ContentScale.Fit
        )
    }
}