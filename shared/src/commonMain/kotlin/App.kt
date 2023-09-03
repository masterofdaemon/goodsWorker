import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi



@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: MainViewModel, ctx: Any?) {
    MaterialTheme {
        val uiState by viewModel.uiState.collectAsState()
        var isAddOpVisible by remember { mutableStateOf(false) }
        var isViewGoodsVisible by remember { mutableStateOf(false) }

        val opLambda = {op: Operation ->
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
