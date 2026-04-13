package cat.deim.asm01.pedalean2.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.sp
import cat.deim.asm01.pedalean2.R
import cat.deim.asm01.pedalean2.domain.models.Bike
import cat.deim.asm01.pedalean2.presentation.ui.theme.RentAvailableColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel, onProfileClick: () -> Unit, onBikeClick: (String) -> Unit) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = dimensionResource(id = R.dimen.sp_24).value.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (state) {
                is MainState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is MainState.Error -> Text(text = (state as MainState.Error).message, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                is MainState.Success -> {
                    val data = state as MainState.Success

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = dimensionResource(id = R.dimen.dp_16))
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_8)))

                        UserHeader(
                            userName = stringResource(id = R.string.account_name_format, data.user.username),
                            onProfileClick = onProfileClick
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_16)),
                            contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.dp_16))
                        ) {
                            items(data.bikes) { bike ->
                                BikeCard(bike = bike, onBikeClick = onBikeClick)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserHeader(userName: String, onProfileClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProfileClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.dp_2))
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_16)).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dp_64))
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = stringResource(id = R.string.avatar_desc), tint = Color.Gray)
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_16)))
            Text(text = userName, fontSize = dimensionResource(id = R.dimen.sp_18).value.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BikeCard(bike: Bike, onBikeClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.dp_4)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_16))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_16))) {
            Image(
                painter = painterResource(id = R.drawable.bike),
                contentDescription = stringResource(id = R.string.bike_desc),
                modifier = Modifier.fillMaxWidth().height(dimensionResource(id = R.dimen.dp_150)).clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp_12))),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_8)))
            Text(
                text = stringResource(id = R.string.meters_away, bike.meters.toString()),
                color = Color.Gray,
                fontSize = dimensionResource(id = R.dimen.sp_14).value.sp
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text(text = bike.type, fontWeight = FontWeight.Bold, fontSize = dimensionResource(id = R.dimen.sp_16).value.sp)
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(id = R.string.battery_level, bike.batteryLevel.toString()),
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(id = R.dimen.sp_14).value.sp
                    )
                    Text(
                        text = stringResource(id = R.string.battery_label),
                        fontSize = dimensionResource(id = R.dimen.sp_14).value.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))
            Button(
                onClick = { onBikeClick(bike.uuid) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = RentAvailableColor, contentColor = Color.Black)
            ) { Text(stringResource(id = R.string.view_details)) }
        }
    }
}