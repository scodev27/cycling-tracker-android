package cat.deim.asm01.pedalean2.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm01.pedalean2.data.repository.UserRepository
import cat.deim.asm01.pedalean2.presentation.main.MainActivity
import cat.deim.asm01.pedalean2.presentation.ui.theme.Pedalean2Theme
import com.pedalean2.common.factory.DatasourceFactory

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pedalean2Theme {
                val userDatasource = DatasourceFactory.getInstance().createUserDatasource()

                val userRepository = UserRepository(userDatasource)

                val loginViewModel: LoginViewModel = ViewModelProvider(
                    this,
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return LoginViewModel(userRepository) as T
                        }
                    }
                )[LoginViewModel::class.java]

                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}