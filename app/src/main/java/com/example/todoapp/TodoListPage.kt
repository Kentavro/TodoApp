package com.example.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.Bruh
import com.example.todoapp.ui.theme.FocusedTextFieldColor
import com.example.todoapp.ui.theme.RedForCircle
import com.example.todoapp.ui.theme.TodoColor
import com.example.todoapp.ui.theme.YellowForCircle
import com.example.todoapp.ui.theme.montserratFontFamily
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun TodoListPage(viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FocusedTextFieldColor,
                    unfocusedBorderColor = Bruh,
                    focusedLabelColor = FocusedTextFieldColor,
                    focusedTextColor = Color.White,
                    unfocusedLabelColor = Bruh
                ),
                label = { Text(text = "New mission", fontFamily = montserratFontFamily) },
                modifier = Modifier.fillMaxWidth(0.75f),
                textStyle = TextStyle(fontFamily = montserratFontFamily)
            )
            Button(
                onClick = {
                    viewModel.addTodo(inputText)
                    inputText = ""
                },
                colors = ButtonDefaults.buttonColors(Bruh),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "ADD", fontFamily = montserratFontFamily, fontSize = 15.sp)
            }
        }
        todoList?.let {
            LazyColumn(content = {
                itemsIndexed(it) { index, item ->
                    TodoItem(item = item, onDelete = {
                        viewModel.deleteTodo(item.id,)
                    }, RedForCircle)
                }
            }
            )
        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            text = "No items yet",
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit, circleColor: Color) {

    fun toggleCircleColor() {
            when (circleColor) {
            RedForCircle -> YellowForCircle
            YellowForCircle -> FocusedTextFieldColor
            else -> RedForCircle
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(TodoColor)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = SimpleDateFormat(
                        "HH:mm aa, dd/MM/y",
                        Locale.getDefault()
                    ).format(item.createdAt.time),
                    fontSize = 10.sp,
                    color = Bruh
                )
                            }
            Text(text = item.title, fontSize = 20.sp, color = Color.White, fontFamily = montserratFontFamily)
        }
        Column {

        }
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "Delete",
                tint = Bruh
            )
        }
    }
}