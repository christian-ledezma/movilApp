package com.example.myapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun GithubScreen(modifier: Modifier) {

    var nickname by remember { mutableStateOf(value="") }

    Column {
        Text( text = "")
        OutlinedTextField(
            value = nickname,
            onValueChange = {
                it -> nickname = it
            }
        )
        OutlinedButton( onClick = {

        }) {
            Text( text = "")
        }
    }

}

fun remenber(function: () -> MutableState<String>) {}
