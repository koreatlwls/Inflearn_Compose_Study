package com.kimsijin.todolist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kimsijin.todolist.R
import com.kimsijin.todolist.domain.util.ToDoAndroidViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel(
                factory = ToDoAndroidViewModelFactory(application)
            )

            MainScreen(viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    var text by rememberSaveable { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("오늘 할 일") }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    modifier = Modifier.weight(weight = 1f),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 1,
                    placeholder = { Text("할 일") },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_add_24),
                            contentDescription = null,
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.addToDo(text)
                        text = ""
                        keyboardController?.hide()
                    }),
                )
            }

            Divider()

            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),   // 상하 좌우 패딩
                verticalArrangement = Arrangement.spacedBy(16.dp),  // 아이템간의 패딩
            ) {
                items(viewModel.items.value) { todoItem ->
                    Column {
                        ToDoItem(
                            toDo = todoItem,
                            onClick = {
                                viewModel.toggle(it.uid)
                            },
                            onDelete = {
                                viewModel.delete(it.uid)
                                scope.launch {
                                    val result = snackBarHostState.showSnackbar(
                                        message = "할 일 삭제됨",
                                        actionLabel = "취소",
                                    )

                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.restoreToDo()
                                    }
                                }
                            },
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color.Black, thickness = 1.dp)
                    }
                }
            }

        }
    }
}
