import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun AddOperationScreen(
    viewModel: MainViewModel,
    onAddOperation: (Operation) -> Unit,
    isVisible : Boolean
) {
    var newOperationName by remember { mutableStateOf("") }
    val newOperationStatus by remember { mutableStateOf(Status.waiting) }
    val modifier = Modifier
    val snackbarHostState = remember { SnackbarHostState() }
    val channel = remember { Channel<Int>(Channel.CONFLATED) }
    LaunchedEffect(channel) {
        channel.receiveAsFlow().collect { index ->
            val result = snackbarHostState.showSnackbar(
                message = "Snackbar # $index",
                actionLabel = "Action on $index"
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    /* action has been performed */
                }
                SnackbarResult.Dismissed -> {
                    /* dismissed, no action needed */
                }
            }
        }
    }
    BottomSheetFromWish(visible = isVisible, modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = newOperationName,
                onValueChange = { newOperationName = it },
                label = { Text("Operation Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Button(
                onClick = {
                    if (newOperationName.isEmpty()) {
                        channel.trySend(222)
                        return@Button
                    }
                    newOperationName = replaceSymbols(newOperationName)
                    val newOperation = Operation(
                        id = null,
                        name = newOperationName,
                        status = newOperationStatus
                    )
                    viewModel.addOperation(newOperation)
                    onAddOperation(newOperation)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Add Operation")
            }

        }
    }
}

fun replaceSymbols (input:String) : String {
    val symbol = """""""
    return when {
        input.contains("'") -> input.replaceFirst("'"," дюймов")
        input.contains('"') -> input.replaceFirst(symbol, " дюймов")
        else -> input
    }
}
