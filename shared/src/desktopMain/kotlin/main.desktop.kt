import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import java.awt.Desktop
import java.net.URI

actual fun getPlatformName(): String = "Desktop"

@Composable fun MainView() {

    val mainViewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })
    App(mainViewModel, null)
}

@Preview
@Composable
fun AppPreview() {
    val mainViewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })

    App(mainViewModel, null)
}

actual fun browseUrl(url: String, ctx: Any?) {
    val desktop = Desktop.getDesktop()
    desktop.browse(URI.create(url))
}