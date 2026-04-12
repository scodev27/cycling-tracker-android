package cat.deim.asm01.pedalean2.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.deim.asm01.pedalean2.R
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeDetailScreen(viewModel: BikeDetailViewModel, onBackClick: () -> Unit) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Pedalean2", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Place, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (state) {
                is BikeDetailState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is BikeDetailState.Error -> {
                    Text(
                        text = (state as BikeDetailState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is BikeDetailState.Success -> {
                    val bike = (state as BikeDetailState.Success).bike

                    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                        Text(text = bike.type, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.bike),
                                    contentDescription = null,
                                    modifier = Modifier.size(56.dp).clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    val status = if (bike.isRented) "Rented" else "Available"
                                    Text(text = status, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = bike.name, fontSize = 14.sp, color = Color.Gray)
                                }
                            }

                            val buttonColor = if (bike.isRented) Color(0xFFD81B60) else Color(0xFFE2F0D9)
                            val buttonTextColor = if (bike.isRented) Color.White else Color.Black
                            val buttonText = if (bike.isRented) "Stop rent" else "Rent"

                            Button(
                                onClick = { viewModel.toggleRentStatus() },
                                colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = buttonTextColor),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(buttonText, fontWeight = FontWeight.Medium)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(24.dp))

                        val batteryText = if (bike.batteryLevel > 70) "High" else if (bike.batteryLevel > 30) "Medium" else "Low"

                        DetailRow(icon = Icons.Default.Info, text = "Battery: $batteryText (${bike.batteryLevel}%)")
                        DetailRow(icon = Icons.Default.LocationOn, text = "${bike.meters} meters away")

                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        DetailRow(icon = Icons.Default.CheckCircle, text = "Last maintenance: ${dateFormat.format(bike.lastMaintenance)}")

                        Spacer(modifier = Modifier.height(32.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mapa_reus),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset(x = bike.longitude.toInt().dp, y = bike.latitude.toInt().dp)
                                    .size(48.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp)
    }
}