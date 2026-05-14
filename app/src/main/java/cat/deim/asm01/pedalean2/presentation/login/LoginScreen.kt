package cat.deim.asm01.pedalean2.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import cat.deim.asm01.pedalean2.R
import cat.deim.asm01.pedalean2.presentation.ui.theme.RentAvailableColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.loginState.collectAsState()

    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            onLoginSuccess()
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dp_24)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_48)))

            Image(
                painter = painterResource(id = R.drawable.logo_pedalean),
                contentDescription = stringResource(id = R.string.logo_desc),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.email_label),
                    fontSize = dimensionResource(id = R.dimen.sp_14).value.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp_8), bottom = dimensionResource(id = R.dimen.dp_4))
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(stringResource(id = R.string.enter_email), color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(50)
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

                Text(
                    text = stringResource(id = R.string.password_label),
                    fontSize = dimensionResource(id = R.dimen.sp_14).value.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp_8), bottom = dimensionResource(id = R.dimen.dp_4))
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(stringResource(id = R.string.enter_password), color = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(50)
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_8)))

                Text(
                    text = stringResource(id = R.string.forgot_password_no),
                    color = Color.Gray,
                    fontSize = dimensionResource(id = R.dimen.sp_14).value.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = dimensionResource(id = R.dimen.dp_8))
                )
            }

            if (state is LoginState.Error) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_8)))
                Text(
                    text = (state as LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_32)))

            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dp_56))
                    .clip(CircleShape)
                    .background(RentAvailableColor)
                    .align(Alignment.End)
            ) {
                if (state is LoginState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(dimensionResource(id = R.dimen.dp_12)),
                        color = Color.Black,
                        strokeWidth = dimensionResource(id = R.dimen.dp_3)
                    )
                } else {
                    IconButton(
                        onClick = { viewModel.login(email, password) },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = stringResource(id = R.string.login_desc),
                            tint = Color.Black,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dp_24))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_32)))
        }
    }
}