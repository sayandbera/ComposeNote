package com.sayanx.composenote.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sayanx.composenote.R
import com.sayanx.composenote.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    darkThemeMode: MutableState<Boolean>,
    navController: NavController,
    note: Note,
    onUpdateNote: (Note) -> Unit = {}
) {
    val newNoteTitle = remember {
        mutableStateOf("")
    }
    val newNoteDescription = remember {
        mutableStateOf("")
    }

    newNoteTitle.value = note.title ?: ""
    newNoteDescription.value = note.description ?: ""

    Scaffold(
        topBar = {
            Surface(shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp, bottomStart = 35.dp, bottomEnd = 35.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp).heightIn(max = 48.dp)
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
                    navigationIcon = {
                        Surface(
                            color = Color.Transparent,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(50.dp)
                                .clickable { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier.padding(12.7.dp)
                            )
                        }
                    },
                    title = {},
                    actions = {
                        val themeModeIcon = remember {
                            mutableIntStateOf(R.drawable.light_mode)
                        }
                        themeModeIcon.intValue = if (darkThemeMode.value) R.drawable.light_mode else R.drawable.dark_mode
                        Surface(
                            color = Color.Transparent,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(CircleShape)
                                .size(40.dp)
                                .clickable { darkThemeMode.value = !darkThemeMode.value }
                        ) {
                            Icon(
                                painter = painterResource(id = themeModeIcon.intValue),
                                contentDescription = "Theme Mode",
                                modifier = Modifier
                                    .size(43.5.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxWidth()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .padding(top = 3.dp),
                textStyle = TextStyle(fontSize = 22.5.sp),
                value = newNoteTitle.value,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                label = { Text("Title") },
                onValueChange = { newTitle ->
                    newNoteTitle.value = newTitle
                    onUpdateNote(Note(id = note.id, title = newNoteTitle.value, description = newNoteDescription.value))
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .background(color = Color.Transparent),
                textStyle = TextStyle(
                    fontSize = 16.5.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.3.sp
                ),
                value = newNoteDescription.value,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.None),
                label = { Text("Note") },
                onValueChange = { newDesc ->
                    newNoteDescription.value = newDesc
                    onUpdateNote(Note(id = note.id, title = newNoteTitle.value, description = newNoteDescription.value))
                }
            )
        }
    }
}

