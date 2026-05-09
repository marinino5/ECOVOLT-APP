package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlaceholderScreen(
    title: String,
    subtitle: String,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
    onBackClick: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null
) {
    val darkBlue = Color(0xFF001F3F)
    val ecoGreen = Color(0xFF0B7A34)
    val softGreen = Color(0xFFEAF7EC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(softGreen)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ECOVOLT",
                    color = ecoGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = title,
                    color = darkBlue,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = subtitle,
                    color = Color.DarkGray,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onPrimaryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ecoGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = primaryButtonText,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (secondaryButtonText != null && onSecondaryClick != null) {
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onSecondaryClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = secondaryButtonText,
                            color = ecoGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onBackClick) {
                    Text(
                        text = "Volver",
                        color = Color.Gray
                    )
                }
            }
        }
    }
}