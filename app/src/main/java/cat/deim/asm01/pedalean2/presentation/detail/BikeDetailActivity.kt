package cat.deim.asm01.pedalean2.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm01.pedalean2.data.datasource.BikeLocalDatasource
import cat.deim.asm01.pedalean2.data.datasource.RentLocalDatasource
import cat.deim.asm01.pedalean2.data.datasource.UserLocalDatasource
import cat.deim.asm01.pedalean2.data.datasource.database.Pedalean2AppDatabase
import cat.deim.asm01.pedalean2.data.repository.BikeRepository
import cat.deim.asm01.pedalean2.data.repository.RentRepository
import cat.deim.asm01.pedalean2.data.repository.UserRepository
import cat.deim.asm01.pedalean2.presentation.ui.theme.Pedalean2Theme
import com.pedalean2.common.factory.DatasourceFactory

class BikeDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bikeUuid = intent.getStringExtra("BIKE_UUID") ?: ""

        setContent {
            Pedalean2Theme {
                val factory = DatasourceFactory.getInstance()
                val database = Pedalean2AppDatabase.getDatabase(this@BikeDetailActivity)

                val bikeRepository = BikeRepository(
                    localDatasource = BikeLocalDatasource(database.bikeDao()),
                    remoteDatasource = factory.createBikeDatasource()
                )

                val rentRepository = RentRepository(
                    localDatasource = RentLocalDatasource(database.rentDao()),
                    remoteDatasource = factory.createRentDatasource()
                )

                val userRepository = UserRepository(
                    localDatasource = UserLocalDatasource(database.userDao()),
                    remoteDatasource = factory.createUserDatasource()
                )

                val viewModel: BikeDetailViewModel = ViewModelProvider(
                    this,
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return BikeDetailViewModel(bikeRepository, rentRepository, userRepository, bikeUuid) as T
                        }
                    }
                )[BikeDetailViewModel::class.java]

                BikeDetailScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() }
                )
            }
        }
    }
}