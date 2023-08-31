import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

actual fun getPlatformName(): String = "Desktop"

@Composable fun MainView() {

    val mainViewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })
    App(mainViewModel)
}

@Preview
@Composable
fun AppPreview() {
    val mainViewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })

    App(mainViewModel)
}