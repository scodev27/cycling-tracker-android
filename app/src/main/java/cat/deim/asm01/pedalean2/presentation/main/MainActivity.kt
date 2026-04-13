package cat.deim.asm01.pedalean2.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm01.pedalean2.data.repository.BikeRepository
import cat.deim.asm01.pedalean2.data.repository.UserRepository
import cat.deim.asm01.pedalean2.presentation.detail.BikeDetailActivity
import cat.deim.asm01.pedalean2.presentation.ui.theme.Pedalean2Theme
import cat.deim.asm01.pedalean2.presentation.profile.ProfileActivity
import com.pedalean2.common.factory.DatasourceFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pedalean2Theme {
                val factory = DatasourceFactory.getInstance()
                val userRepository = UserRepository(factory.createUserDatasource())
                val bikeRepository = BikeRepository(factory.createBikeDatasource())

                val viewModel: MainViewModel = ViewModelProvider(
                    this,
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return MainViewModel(userRepository, bikeRepository) as T
                        }
                    }
                )[MainViewModel::class.java]

                MainScreen(
                    viewModel = viewModel,
                    onProfileClick = {
                        startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                    },
                    onBikeClick = { bikeUuid ->
                        val intent = Intent(this@MainActivity, BikeDetailActivity::class.java)
                        intent.putExtra("BIKE_UUID", bikeUuid)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}