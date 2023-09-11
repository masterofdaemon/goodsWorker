
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi



@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: MainViewModel, ctx: Any?) {
    MaterialTheme {
        val uiState by viewModel.uiState.collectAsState()
        var isAddOpVisible by remember { mutableStateOf(false) }
        var isViewGoodsVisible by remember { mutableStateOf(false) }

        val opLambda = {_: Operation ->
            isAddOpVisible = !isAddOpVisible
        }

        val onGoodsViewDismiss = {
            isViewGoodsVisible = false
        }
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {isAddOpVisible = !isAddOpVisible }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            },
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),

                    title = {
                        Text("Ваши операции")
                    }
                )
//                IconButton(onClick = {}) {
//                    Icon(Icons.Outlined.Add, contentDescription = "Localized description")
//                }
            }
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AnimatedVisibility(visible = true) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(80.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(uiState.operations) { operation ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .padding(5.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(text = operation.name)
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(text = operation.status.toString())
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Button(onClick = {

                                        viewModel.updateGoodsByOperationId(operation.id!!)

                                        isViewGoodsVisible = true

                                                     },
                                        enabled = (operation.status== Status.processed)) {
                                        Text("Посмотреть")
                                    }
                                }
                            }
                        }

                    }
                    AddOperationScreen(viewModel, opLambda, isAddOpVisible)
                    ViewGoodsScreen(viewModel, isVisible = isViewGoodsVisible, onGoodsViewDismiss, ctx)
                }
            }
        }
    }
}

expect fun getPlatformName(): String
expect fun browseUrl(url: String, ctx: Any?)
