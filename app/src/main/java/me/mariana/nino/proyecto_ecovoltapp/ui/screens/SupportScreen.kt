package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import me.mariana.nino.proyecto_ecovoltapp.R

@Composable
fun SupportScreen(
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    onReportSent: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    val green = Color(0xFF007A3D)
    val darkText = Color(0xFF172033)
    val softGreen = Color(0xFFEAF7EF)

    val problemOptions = listOf(
        "Problema con la reserva",
        "Vehículo en mal estado",
        "Problema con el pago",
        "No puedo finalizar el viaje",
        "Objeto olvidado",
        "Otro problema"
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedProblem by remember { mutableStateOf("Selecciona una opción") }
    var description by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var showPhotoOptions by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var cameraBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var photoSource by remember { mutableStateOf("") }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            cameraBitmap = null
            photoSource = "galeria"
            Toast.makeText(context, "Imagen seleccionada desde galería", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            cameraBitmap = bitmap
            selectedImageUri = null
            photoSource = "camara"
            Toast.makeText(context, "Foto tomada correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    val hasPhoto = selectedImageUri != null || cameraBitmap != null

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FBF7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 18.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SupportTopBar(
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            SupportHeader(
                green = green,
                darkText = darkText
            )

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 22.dp)
                ) {
                    Text(
                        text = "1. Selecciona el tipo de problema",
                        color = darkText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFFF7FAF7))
                                .clickable { expanded = true }
                                .padding(horizontal = 14.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ReportProblem,
                                contentDescription = null,
                                tint = green,
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                text = selectedProblem,
                                color = if (selectedProblem == "Selecciona una opción") {
                                    Color(0xFF7B837F)
                                } else {
                                    Color(0xFF303A35)
                                },
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f)
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xFF6B746F)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            problemOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = option,
                                            fontSize = 13.sp,
                                            color = Color(0xFF303A35)
                                        )
                                    },
                                    onClick = {
                                        selectedProblem = option
                                        expanded = false
                                        errorMessage = ""
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "2. Describe el problema",
                        color = darkText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { newText ->
                            if (newText.length <= 300) {
                                description = newText
                                errorMessage = ""
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Escribe los detalles del problema...",
                                fontSize = 13.sp,
                                color = Color(0xFF8B928F)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(135.dp),
                        shape = RoundedCornerShape(16.dp),
                        maxLines = 6,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = green,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )

                    Text(
                        text = "${description.length}/300",
                        color = Color(0xFF8B928F),
                        fontSize = 11.sp,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "3. Adjunta una foto opcional",
                        color = darkText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PhotoAttachBox(
                        hasPhoto = hasPhoto,
                        selectedImageUri = selectedImageUri,
                        cameraBitmap = cameraBitmap,
                        green = green,
                        softGreen = softGreen,
                        onClick = {
                            showPhotoOptions = true
                        }
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = errorMessage,
                            color = Color(0xFFE53935),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    Button(
                        onClick = {
                            when {
                                selectedProblem == "Selecciona una opción" -> {
                                    errorMessage = "Selecciona el tipo de problema."
                                }

                                description.trim().length < 10 -> {
                                    errorMessage = "Describe el problema con un poco más de detalle."
                                }

                                else -> {
                                    isSending = true
                                    errorMessage = ""

                                    val reportData = hashMapOf(
                                        "userId" to (currentUser?.uid ?: ""),
                                        "userEmail" to (currentUser?.email ?: "Sin correo"),
                                        "tipoProblema" to selectedProblem,
                                        "descripcion" to description.trim(),
                                        "fotoAdjunta" to hasPhoto,
                                        "origenFoto" to photoSource,
                                        "estado" to "pendiente",
                                        "fechaCreacion" to FieldValue.serverTimestamp()
                                    )

                                    db.collection("reportes")
                                        .add(reportData)
                                        .addOnSuccessListener {
                                            isSending = false
                                            Toast.makeText(
                                                context,
                                                "Reporte enviado correctamente.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            onReportSent()
                                        }
                                        .addOnFailureListener {
                                            isSending = false
                                            errorMessage =
                                                "No se pudo enviar el reporte. Inténtalo nuevamente."
                                        }
                                }
                            }
                        },
                        enabled = !isSending,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = green,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = if (isSending) "ENVIANDO..." else "ENVIAR REPORTE",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onCancelClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = green
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "CANCELAR",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (showPhotoOptions) {
            AlertDialog(
                onDismissRequest = {
                    showPhotoOptions = false
                },
                title = {
                    Text(
                        text = "Adjuntar foto",
                        color = darkText,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "Selecciona cómo quieres adjuntar la evidencia del reporte.",
                            color = Color(0xFF6B746F),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        ListItem(
                            headlineContent = {
                                Text(
                                    text = "Tomar foto",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            supportingContent = {
                                Text("Abrir cámara del dispositivo")
                            },
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = green
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.White),
                            modifier = Modifier.clickable {
                                showPhotoOptions = false
                                cameraLauncher.launch(null)
                            }
                        )

                        HorizontalDivider(color = Color(0xFFE0E0E0))

                        ListItem(
                            headlineContent = {
                                Text(
                                    text = "Seleccionar de galería",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            supportingContent = {
                                Text("Elegir una imagen guardada")
                            },
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = null,
                                    tint = green
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.White),
                            modifier = Modifier.clickable {
                                showPhotoOptions = false
                                galleryLauncher.launch("image/*")
                            }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showPhotoOptions = false
                        }
                    ) {
                        Text(
                            text = "Cerrar",
                            color = green,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun SupportTopBar(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.82f))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF1E1E1E)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ecosin),
            contentDescription = "Logo Ecovolt",
            modifier = Modifier
                .align(Alignment.Center)
                .height(54.dp)
                .fillMaxWidth(0.58f),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun SupportHeader(
    green: Color,
    darkText: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Reporte / Soporte",
                color = darkText,
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Cuéntanos qué ocurrió para poder ayudarte.",
                color = Color(0xFF6B746F),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAF7EF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.TwoWheeler,
                contentDescription = null,
                tint = green,
                modifier = Modifier.size(38.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(14.dp))

    HorizontalDivider(
        color = Color(0xFFE2E8E3),
        thickness = 1.dp
    )
}

@Composable
private fun PhotoAttachBox(
    hasPhoto: Boolean,
    selectedImageUri: Uri?,
    cameraBitmap: Bitmap?,
    green: Color,
    softGreen: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (hasPhoto) 170.dp else 118.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(softGreen)
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            cameraBitmap != null -> {
                Image(
                    bitmap = cameraBitmap.asImageBitmap(),
                    contentDescription = "Foto adjunta",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.Crop
                )

                PhotoSelectedBadge()
            }

            selectedImageUri != null -> {
                AndroidView(
                    factory = { context ->
                        ImageView(context).apply {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }
                    },
                    update = { imageView ->
                        imageView.setImageURI(selectedImageUri)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(14.dp))
                )

                PhotoSelectedBadge()
            }

            else -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = null,
                            tint = green,
                            modifier = Modifier.size(23.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Adjuntar foto",
                        color = green,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Toma una foto o selecciona una imagen.",
                        color = Color(0xFF65716A),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotoSelectedBadge() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White.copy(alpha = 0.92f))
                .padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF007A3D),
                modifier = Modifier.size(17.dp)
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = "Foto adjuntada",
                color = Color(0xFF007A3D),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
