package cat.deim.asm01.pedalean2.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cat.deim.asm01.pedalean2.R
import cat.deim.asm01.pedalean2.presentation.ui.theme.RentAvailableColor

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dp_24)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_32)))

            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = dimensionResource(id = R.dimen.sp_32).value.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

            HorizontalDivider(
                color = Color.LightGray,
                thickness = dimensionResource(id = R.dimen.dp_1),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dp_16))
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

            Text(
                text = stringResource(id = R.string.splash_subtitle),
                fontSize = dimensionResource(id = R.dimen.sp_14).value.sp,
                color = Color.DarkGray
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_pedalean),
                    contentDescription = stringResource(id = R.string.logo_desc),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
            }

            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dp_56)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RentAvailableColor,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_16))
            ) {
                Text(
                    text = stringResource(id = R.string.get_started),
                    fontSize = dimensionResource(id = R.dimen.sp_16).value.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))
        }
    }
}