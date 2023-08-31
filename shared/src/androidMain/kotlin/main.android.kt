import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() {
    val mainViewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })
    App(mainViewModel)
}
