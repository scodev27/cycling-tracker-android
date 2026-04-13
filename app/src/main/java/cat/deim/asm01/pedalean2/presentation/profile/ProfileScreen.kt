package cat.deim.asm01.pedalean2.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.deim.asm01.pedalean2.R
import cat.deim.asm01.pedalean2.domain.models.Rent
import cat.deim.asm01.pedalean2.domain.models.User
import cat.deim.asm01.pedalean2.presentation.ui.theme.RentActiveColor
import cat.deim.asm01.pedalean2.presentation.ui.theme.RentAvailableColor
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel, onBackClick: () -> Unit) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = dimensionResource(id = R.dimen.sp_20).value.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.dp_48))
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_desc))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (state) {
                is ProfileState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is ProfileState.Error -> Text((state as ProfileState.Error).message, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                is ProfileState.Success -> {
                    val data = state as ProfileState.Success

                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = dimensionResource(id = R.dimen.dp_24)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_24))
                    ) {
                        item { Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_8))) }

                        item {
                            Text(
                                text = stringResource(id = R.string.profile_title),
                                fontSize = dimensionResource(id = R.dimen.sp_22).value.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))
                            ProfileCard(user = data.user)
                        }

                        item {
                            Text(
                                text = stringResource(id = R.string.rental_history_title),
                                fontSize = dimensionResource(id = R.dimen.sp_22).value.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_8)))
                        }

                        items(data.rents) { rent ->
                            RentHistoryCard(rent = rent)
                        }

                        item { Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_24))) }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileCard(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.dp_2)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_16))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_20))) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dp_64)).clip(CircleShape).background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = stringResource(id = R.string.avatar_desc), tint = Color.Gray, modifier = Modifier.size(dimensionResource(id = R.dimen.dp_40)))
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_16)))
                Column {
                    Text(text = user.name, fontSize = dimensionResource(id = R.dimen.sp_18).value.sp, fontWeight = FontWeight.Medium)
                    Text(text = user.email, fontSize = dimensionResource(id = R.dimen.sp_14).value.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth().height(dimensionResource(id = R.dimen.dp_48)),
                colors = ButtonDefaults.buttonColors(containerColor = RentAvailableColor, contentColor = Color.Black),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_12))
            ) {
                Text(stringResource(id = R.string.edit_profile), fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun RentHistoryCard(rent: Rent) {
    val dateFormat = SimpleDateFormat(stringResource(id = R.string.format_date), Locale.getDefault())
    val dateString = dateFormat.format(rent.timeStart)

    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = dimensionResource(id = R.dimen.dp_8)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.dp_2)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_16))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_16))) {
            Text(text = "${rent.rents.name} - $dateString", fontWeight = FontWeight.Medium, fontSize = dimensionResource(id = R.dimen.sp_16).value.sp)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_8)))

            if (rent.isRented) {
                Text(
                    text = stringResource(id = R.string.rent_in_progress),
                    fontSize = dimensionResource(id = R.dimen.sp_15).value.sp,
                    color = RentActiveColor,
                    fontWeight = FontWeight.Bold
                )
            } else {
                val hours = rent.rentTime / 60
                val mins = rent.rentTime % 60
                val timeString = if (hours > 0) "${hours}h ${mins}m" else "${mins}m"
                val km = rent.rentMeters / 1000.0
                val price = 5.58 + (rent.rentTime * 0.05) + (km * 0.60)

                val kmString = String.format(Locale.US, "%.1f", km)
                val priceString = String.format(Locale.US, "%.2f", price)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = stringResource(id = R.string.travel_time, timeString), fontSize = dimensionResource(id = R.dimen.sp_14).value.sp, color = Color.Gray)
                    Text(text = stringResource(id = R.string.distance_km, kmString), fontSize = dimensionResource(id = R.dimen.sp_14).value.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_4)))
                Text(text = stringResource(id = R.string.price_euros, priceString), fontSize = dimensionResource(id = R.dimen.sp_14).value.sp, color = Color.Gray)
            }
        }
    }
}