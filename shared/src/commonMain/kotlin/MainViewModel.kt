import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

@Serializable
enum class Status {
    waiting,
    processed,
    sold,
    buyed
}

@Serializable
data class Operation(val id: String?, val name: String, val status: Status)

data class OperationsUiState(
    val operations: List<Operation> = emptyList()
)

class MainViewModel : ViewModel() {

    private val httpClient = HttpClient(CIO) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val _uiState = MutableStateFlow(OperationsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateOperations()
    }

    private fun updateOperations() {
        runBlocking {
            launch(Dispatchers.Default) {
                val operations = getOperations()
                _uiState.update {
                    it.copy(operations = operations)
                }
            }
        }
    }

    private suspend fun getOperations(): List<Operation> {
        val url = "http://192.168.0.246/api/operations"
        val response: HttpResponse = httpClient.get(url)
        return response.body()
    }

//    fun addOperation(operation: Operation) {
//        val updatedOperations = _uiState.value.operations.toMutableList()
//        updatedOperations.add(operation)
//        _uiState.value = _uiState.value.copy(operations = updatedOperations)
//    }

    fun addOperation(operation: Operation) {
        viewModelScope.launch {
            try {
                val response: HttpResponse = httpClient.post("http://your-server-url/api/operations") {
                    contentType(ContentType.Application.Json)
                    setBody(operation)
                }

                if (response.status.isSuccess()) {
                    // Successfully added the operation to the server
                    val updatedOperations = _uiState.value.operations.toMutableList()
                    updatedOperations.add(operation)
                    _uiState.value = _uiState.value.copy(operations = updatedOperations)
                } else {
                    // Handle the case when adding to the server was not successful
                }
            } catch (e: Exception) {
                // Handle network or other errors
            }
        }
    }
}
