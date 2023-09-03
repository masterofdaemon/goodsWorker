import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GoodsFromOperationScreen( viewModel: MainViewModel,
                              isVisible : Boolean, goods: List<Good>) {
    val modifier = Modifier
    BottomSheetFromWish(visible = isVisible, modifier = modifier) {

    }
}