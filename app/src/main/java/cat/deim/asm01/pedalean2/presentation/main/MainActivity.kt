package cat.deim.asm01.pedalean2 // Ajusta el paquet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm01.pedalean2.data.repository.BikeRepository
import cat.deim.asm01.pedalean2.data.repository.UserRepository
import cat.deim.asm01.pedalean2.presentation.ui.theme.Pedalean2Theme
import cat.deim.asm01.pedalean2.presentation.main.MainScreen
import cat.deim.asm01.pedalean2.presentation.main.MainViewModel
import com.pedalean2.common.factory.DatasourceFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pedalean2Theme {
                val factory = DatasourceFactory.getInstance()

                val userDatasource = factory.createUserDatasource()
                val bikeDatasource = factory.createBikeDatasource()

                val userRepository = UserRepository(userDatasource)
                val bikeRepository = BikeRepository(bikeDatasource)

                val viewModel: MainViewModel = ViewModelProvider(
                    this,
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return MainViewModel(userRepository, bikeRepository) as T
                        }
                    }
                ).get(MainViewModel::class.java)

                MainScreen(viewModel = viewModel)
            }
        }
    }
}