package cat.deim.asm01.pedalean2.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.deim.asm01.pedalean2.R
import cat.deim.asm01.pedalean2.presentation.ui.theme.RentActiveColor
import cat.deim.asm01.pedalean2.presentation.ui.theme.RentAvailableColor
import cat.deim.asm01.pedalean2.presentation.ui.theme.SuccessGreen
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
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = dimensionResource(id = R.dimen.sp_20).value.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_desc))
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Place, contentDescription = stringResource(id = R.string.map_desc))
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

                    Column(modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.dp_24))) {
                        Text(text = bike.type, fontSize = dimensionResource(id = R.dimen.sp_24).value.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.bike),
                                    contentDescription = stringResource(id = R.string.bike_desc),
                                    modifier = Modifier.size(dimensionResource(id = R.dimen.dp_56)).clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_12)))
                                Column {
                                    val statusStrId = if (bike.isRented) R.string.status_rented else R.string.status_available
                                    Text(
                                        text = stringResource(id = statusStrId),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = dimensionResource(id = R.dimen.sp_14).value.sp
                                    )
                                    Text(
                                        text = bike.name,
                                        fontSize = dimensionResource(id = R.dimen.sp_14).value.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            val buttonColor = if (bike.isRented) RentActiveColor else RentAvailableColor
                            val buttonTextColor = if (bike.isRented) Color.White else Color.Black
                            val buttonStrId = if (bike.isRented) R.string.btn_stop_rent else R.string.btn_rent

                            Button(
                                onClick = { viewModel.toggleRentStatus() },
                                colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = buttonTextColor),
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_12))
                            ) {
                                Text(stringResource(id = buttonStrId), fontWeight = FontWeight.Medium)
                            }
                        }

                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_24)))
                        HorizontalDivider(color = Color.LightGray, thickness = dimensionResource(id = R.dimen.dp_1))
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_24)))

                        val batteryStateStrId = if (bike.batteryLevel > 70) R.string.battery_high else if (bike.batteryLevel > 30) R.string.battery_medium else R.string.battery_low
                        val batteryStateStr = stringResource(id = batteryStateStrId)

                        DetailRow(
                            icon = Icons.Default.Info,
                            text = stringResource(id = R.string.battery_detail, batteryStateStr, bike.batteryLevel)
                        )
                        DetailRow(
                            icon = Icons.Default.LocationOn,
                            text = stringResource(id = R.string.meters_away_detail, bike.meters)
                        )

                        val dateFormat = SimpleDateFormat(stringResource(id = R.string.format_date_short), Locale.getDefault())
                        DetailRow(
                            icon = Icons.Default.CheckCircle,
                            text = stringResource(id = R.string.last_maintenance, dateFormat.format(bike.lastMaintenance))
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_32)))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp_16)))
                                .background(Color.LightGray)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mapa_reus),
                                contentDescription = stringResource(id = R.string.map_desc),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            val refLat = 41.132f
                            val refLon = 1.245f
                            val multiplier = 8000f
                            val calcX = ((bike.longitude - refLon) * multiplier).dp
                            val calcY = ((refLat - bike.latitude) * multiplier).dp

                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = stringResource(id = R.string.pin_desc),
                                tint = SuccessGreen,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset(x = calcX, y = calcY)
                                    .size(dimensionResource(id = R.dimen.dp_48))
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
    Row(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dp_8)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(dimensionResource(id = R.dimen.dp_24)))
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_16)))
        Text(text = text, fontSize = dimensionResource(id = R.dimen.sp_16).value.sp)
    }
}