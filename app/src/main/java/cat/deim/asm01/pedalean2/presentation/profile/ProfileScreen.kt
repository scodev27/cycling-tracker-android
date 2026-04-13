package cat.deim.asm01.pedalean2.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.deim.asm01.pedalean2.domain.models.Rent
import cat.deim.asm01.pedalean2.domain.models.User
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
                        Text("Pedalean2", fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(end = 48.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item { Spacer(modifier = Modifier.height(8.dp)) }

                        item {
                            Text("Profile", fontSize = 22.sp, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(16.dp))
                            ProfileCard(user = data.user)
                        }

                        item {
                            Text("Rental History", fontSize = 22.sp, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(data.rents) { rent ->
                            RentHistoryCard(rent = rent)
                        }

                        item { Spacer(modifier = Modifier.height(24.dp)) }
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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Avatar", tint = Color.Gray, modifier = Modifier.size(40.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "${user.name}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    Text(text = user.email, fontSize = 14.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2F0D9), contentColor = Color.Black),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Edit Profile", fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun RentHistoryCard(rent: Rent) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dateString = dateFormat.format(rent.timeStart)

    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${rent.rents.name} - $dateString", fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            if (rent.isRented) {
                Text(
                    text = "Lloguer en curs...",
                    fontSize = 15.sp,
                    color = Color(0xFFD81B60),
                    fontWeight = FontWeight.Bold
                )
            } else {
                val hours = rent.rentTime / 60
                val mins = rent.rentTime % 60
                val timeString = if (hours > 0) "$hours hour $mins min." else "$mins min."
                val km = String.format(Locale.US, "%.1f", rent.rentMeters / 1000f)
                val price = String.format(Locale.US, "%.2f", rent.rentTime * 0.20)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Travel time: $timeString", fontSize = 14.sp, color = Color.Gray)
                    Text(text = "$km km", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$price euros", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}