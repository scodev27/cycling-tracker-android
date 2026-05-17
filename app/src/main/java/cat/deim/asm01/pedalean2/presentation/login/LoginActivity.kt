package cat.deim.asm01.pedalean2.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm01.pedalean2.data.datasource.UserLocalDatasource
import cat.deim.asm01.pedalean2.data.datasource.remote.UserRemoteDatasource
import cat.deim.asm01.pedalean2.data.datasource.database.Pedalean2AppDatabase
import cat.deim.asm01.pedalean2.data.repository.UserRepository
import cat.deim.asm01.pedalean2.presentation.main.MainActivity
import cat.deim.asm01.pedalean2.presentation.ui.theme.Pedalean2Theme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pedalean2Theme {
                val database = Pedalean2AppDatabase.getDatabase(this@LoginActivity)

                val userRepository = UserRepository(
                    localDatasource = UserLocalDatasource(database.userDao()),
                    remoteDatasource = UserRemoteDatasource()
                )

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
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}