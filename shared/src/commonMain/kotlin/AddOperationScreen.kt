import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddOperationScreen(
    viewModel: MainViewModel,
    onAddOperation: (Operation) -> Unit
) {
    var newOperationName by remember { mutableStateOf("") }
    var newOperationStatus by remember { mutableStateOf(Status.waiting) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = newOperationName,
            onValueChange = { newOperationName = it },
            label = { Text("Operation Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

//        DropdownMenu(
//            expanded = false, // Set this to true when you want to show the dropdown
//            onDismissRequest = { /* Handle dismiss here */ }
//        ) {
//            Status.values().forEach { status ->
//                DropdownMenuItem(onClick = { newOperationStatus = status }) {
//                    Text(text = status.name)
//                }
//            }
//        }

        Button(
            onClick = {
                val newOperation = Operation(
                    id = "",
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
