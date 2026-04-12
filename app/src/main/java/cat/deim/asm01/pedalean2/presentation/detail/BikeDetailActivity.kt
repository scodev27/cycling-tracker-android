package cat.deim.asm01.pedalean2.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm01.pedalean2.data.repository.BikeRepository
import cat.deim.asm01.pedalean2.presentation.ui.theme.Pedalean2Theme
import com.pedalean2.common.factory.DatasourceFactory

class BikeDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bikeUuid = intent.getStringExtra("BIKE_UUID") ?: ""

        setContent {
            Pedalean2Theme {
                val factory = DatasourceFactory.getInstance()
                val bikeRepository = BikeRepository(factory.createBikeDatasource())

                val viewModel: BikeDetailViewModel = ViewModelProvider(
                    this,
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return BikeDetailViewModel(bikeRepository, bikeUuid) as T
                        }
                    }
                ).get(BikeDetailViewModel::class.java)

                BikeDetailScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() }
                )
            }
        }
    }
}