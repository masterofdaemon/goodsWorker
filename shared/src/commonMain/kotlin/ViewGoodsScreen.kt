import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun ViewGoodsScreen(
    viewModel: MainViewModel,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    ctx: Any?
) {
    val uiState by viewModel.uiState.collectAsState()

    val modifier = Modifier
    BottomSheetFromWish(visible = isVisible, modifier= modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                )
                // .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            // Display goods associated with the operation
            uiState.goods.forEach { good ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Описание: ${good.description}")
                        Text(text = "Цена: ${good.price}р")
                        Text(text = "Приедет: ${good.date}")
                        // Display other properties of the Good
                        Button(
                            onClick = {
                                // Handle the "Look on the Internet" action here
                                // You can launch a web browser or perform any other action
                                      browseUrl(url = good.url, ctx)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text("прейти на сайт")
                        }
                    }
                }
            }

            // Button to dismiss the screen
            Button(
                onClick = {
                    onDismiss()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Dismiss")
            }
        }
    }

}
