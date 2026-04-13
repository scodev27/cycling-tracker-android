package cat.deim.asm01.pedalean2.presentation.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm01.pedalean2.data.repository.RentRepository
import cat.deim.asm01.pedalean2.data.repository.UserRepository
import cat.deim.asm01.pedalean2.presentation.ui.theme.Pedalean2Theme
import com.pedalean2.common.factory.DatasourceFactory

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pedalean2Theme {
                val factory = DatasourceFactory.getInstance()
                val userDatasource = factory.createUserDatasource()
                val rentDatasource = factory.createRentDatasource()

                val userRepository = UserRepository(userDatasource)
                val rentRepository = RentRepository(rentDatasource)

                val viewModel: ProfileViewModel = ViewModelProvider(
                    this,
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ProfileViewModel(userRepository, rentRepository) as T
                        }
                    }
                )[ProfileViewModel::class.java]

                ProfileScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() }
                )
            }
        }
    }
}