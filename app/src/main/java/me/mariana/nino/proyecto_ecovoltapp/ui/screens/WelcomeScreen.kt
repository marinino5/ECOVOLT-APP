package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import me.mariana.nino.proyecto_ecovoltapp.R
import androidx.compose.foundation.clickable
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val pages = 3
    val pagerState = rememberPagerState(pageCount = { pages })

    LaunchedEffect(Unit) {
        while (true) {
            delay(8500)
            val nextPage = (pagerState.currentPage + 1) % pages
            pagerState.animateScrollToPage(nextPage)
        }
    }

    val currentPage by remember {
        derivedStateOf { pagerState.currentPage }
    }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> EcovoltCoverSlide()
                1 -> EcovoltAboutSlide()
                2 -> EcovoltHowItWorksSlide()
            }
        }

        EcovoltBottomAccessPanel(
            currentPage = currentPage,
            totalPages = pages,
            onPageClick = { page ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(page)
                }
            },
            onLoginClick = onLoginClick,
            onRegisterClick = onRegisterClick
        )
    }
}

@Composable
fun EcovoltCoverSlide() {
    Image(
        painter = painterResource(id = R.drawable.fondo_inicio),
        contentDescription = "Portada Ecovolt",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun EcovoltAboutSlide() {
    EcovoltBackgroundInfoSlide {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EcovoltSlideHeader(
                label = "SOBRE ECOVOLT",
                title = "QUIÉNES SOMOS",
                subtitle = "Movilidad eléctrica para una ciudad verde."
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Ecovolt es una empresa de movilidad eléctrica en Bucaramanga enfocada en ofrecer una alternativa práctica, sostenible y moderna para los desplazamientos urbanos.",
                color = Color(0xFF31433A),
                fontSize = 15.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(3.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF08752F))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nuestro propósito es facilitar trayectos cortos mediante vehículos eléctricos disponibles en estaciones estratégicas, promoviendo una movilidad más limpia, ordenada y accesible para la ciudad.",
                color = Color(0xFF31433A),
                fontSize = 15.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EcovoltHowItWorksSlide() {
    EcovoltBackgroundInfoSlide {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EcovoltSlideHeader(
                label = "GUÍA RÁPIDA",
                title = "¿CÓMO FUNCIONA?",
                subtitle = "Alquila tu transporte eléctrico en pocos pasos."
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                EcovoltStepItem(
                    number = "1",
                    title = "ENCUENTRA",
                    text = "Ubica patinetes disponibles en estaciones cercanas."
                )

                EcovoltStepItem(
                    number = "2",
                    title = "RESERVA",
                    text = "Selecciona el vehículo que más te convenga."
                )

                EcovoltStepItem(
                    number = "3",
                    title = "VIAJA",
                    text = "Activa tu recorrido y muévete de forma sostenible."
                )

                EcovoltStepItem(
                    number = "4",
                    title = "FINALIZA",
                    text = "Termina tu viaje en una estación permitida."
                )
            }
        }
    }
}

@Composable
fun EcovoltBackgroundInfoSlide(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo_1),
            contentDescription = "Fondo Ecovolt",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x35FFFFFF),
                            Color(0xBBFFFFFF),
                            Color(0xF2FFFFFF)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 104.dp, bottom = 210.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun EcovoltSlideHeader(
    label: String,
    title: String,
    subtitle: String
) {
    val darkBlue = Color(0xFF0B2740)
    val ecoGreen = Color(0xFF08752F)
    val accentBlue = Color(0xFF1C4D73)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = ecoGreen,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.6.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = title,
            color = darkBlue,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .width(42.dp)
                    .height(2.dp)
                    .clip(CircleShape)
                    .background(ecoGreen)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(accentBlue)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .width(42.dp)
                    .height(2.dp)
                    .clip(CircleShape)
                    .background(ecoGreen)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = subtitle,
            color = ecoGreen,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            lineHeight = 21.sp
        )
    }
}

@Composable
fun EcovoltStepItem(
    number: String,
    title: String,
    text: String
) {
    val darkBlue = Color(0xFF0B2740)
    val ecoGreen = Color(0xFF08752F)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(ecoGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = text,
                color = Color(0xFF47584F),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun EcovoltBottomAccessPanel(
    currentPage: Int,
    totalPages: Int,
    onPageClick: (Int) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xEFFFFFFF),
                            Color.White
                        )
                    )
                )
                .navigationBarsPadding()
                .padding(horizontal = 28.dp)
                .padding(top = 42.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EcovoltPageIndicator(
                totalPages = totalPages,
                selectedPage = currentPage,
                onPageClick = onPageClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF08752F),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Registrarse",
                    color = Color(0xFF08752F),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun EcovoltPageIndicator(
    totalPages: Int,
    selectedPage: Int,
    onPageClick: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            val selected = index == selectedPage

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(8.dp)
                    .width(if (selected) 26.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (selected) Color(0xFF08752F)
                        else Color(0xFFD5DDCE)
                    )
                    .clickable {
                        onPageClick(index)
                    }
            )
        }
    }
}