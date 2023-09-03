import androidx.compose.ui.window.ComposeUIViewController
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController {
    val mainViewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })

    App(mainViewModel, ctx)
}