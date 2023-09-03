import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory


actual fun getPlatformName(): String = "Android"

@Composable fun MainView(ctx: Context?) {
    val mainViewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })
    App(mainViewModel, ctx)
}

actual fun browseUrl(url: String, ctx: Any?) {
    val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK) // Add the FLAG_ACTIVITY_NEW_TASK flag

    ContextCompat.startActivity(ctx as Context, intent, null)

}