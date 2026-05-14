package cat.deim.asm01.pedalean2.presentation.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm01.pedalean2.data.datasource.RentLocalDatasource
import cat.deim.asm01.pedalean2.data.datasource.UserLocalDatasource
import cat.deim.asm01.pedalean2.data.datasource.database.Pedalean2AppDatabase
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
                val database = Pedalean2AppDatabase.getDatabase(this@ProfileActivity)

                val userRepository = UserRepository(
                    localDatasource = UserLocalDatasource(database.userDao()),
                    remoteDatasource = factory.createUserDatasource()
                )

                val rentRepository = RentRepository(
                    localDatasource = RentLocalDatasource(database.rentDao()),
                    remoteDatasource = factory.createRentDatasource()
                )

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