package com.sayanx.composenote.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sayanx.composenote.R
import com.sayanx.composenote.model.Note
import com.sayanx.composenote.navigations.EnumScreens
import com.sayanx.composenote.viewmodel.NoteViewModel

@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavController,
    multiColumnView: MutableState<Boolean>,
    darkThemeMode: MutableState<Boolean>,
    notes: List<Note>,
    onRemoveData: (Note) -> Unit = {}
) {
    Scaffold(
        topBar = {
            Surface(shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp, bottomStart = 35.dp, bottomEnd = 35.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
                    title = {
                        Text(
                            text = "Take Note",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    },
                    actions = {
                        val layoutViewIcon = remember {
                            mutableIntStateOf(R.drawable.grid_view)
                        }
                        layoutViewIcon.intValue = if (multiColumnView.value) R.drawable.row_view else R.drawable.grid_view

                        Surface(
                            color = Color.Transparent,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clip(CircleShape)
                                .size(40.dp)
                                .clickable { multiColumnView.value = !multiColumnView.value }
                        ) {
                            Icon(painter = painterResource(id = layoutViewIcon.intValue),
                                contentDescription = "Layout View",
                                modifier = Modifier
                                    .size(43.8.dp)
                                    .padding(8.3.dp)
                            )
                        }

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
                    },
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(EnumScreens.AddScreen.name) }, //noteAddingState.value = !noteAddingState.value
                content = { Icon(modifier = Modifier.size(35.dp), imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,

            ) {

            Column(modifier = Modifier.padding(10.dp)) {

                if (multiColumnView.value) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = 180.dp), modifier = Modifier.fillMaxWidth(220f)
                    ) {
                        items(notes) { note ->
                            NoteRow(note = note, onLongClickedNote = onRemoveData, navController = navController)
                        }
                    }
                }
                else {
                    LazyColumn {
                        items(notes) { note ->
                            NoteRow(note = note, onLongClickedNote = onRemoveData, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteRow(
    note: Note,
    navController: NavController,
    onLongClickedNote: (Note) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, color = Color.Gray),
        shape = RoundedCornerShape( topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 1.dp)
    ) {
        Column(modifier = Modifier
            .combinedClickable(
                onClick = {
                    navController.navigate(
                        route = EnumScreens.DetailScreen.name + "/${(note.id)}/${note.title}/${note.description}"
                    )
                },
                onLongClick = { onLongClickedNote(note) })
            .padding(horizontal = 15.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.Start) {

            Text(
                text = note.title ?: "",
                fontSize = 15.7.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 6.5.dp)
            )

            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = note.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 0.3.sp
            )
        }
    }
}

@Composable
fun ScreenPreview(
    darkThemeMode: MutableState<Boolean>,
    multiColumnView: MutableState<Boolean>,
    noteViewModel: NoteViewModel,
    navController: NavController
) {
    val noteList = noteViewModel.noteList.collectAsState().value

    // Deleting the empty notes:
    noteList.filter {
        it.title?.isEmpty() == true && it.description?.isEmpty() == true
    }.forEach { emptyNote ->
        noteViewModel.deleteNote(emptyNote)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {

        NoteScreen(
            multiColumnView = multiColumnView,
            darkThemeMode = darkThemeMode,
            notes = noteList.reversed(),
            onRemoveData = { noteViewModel.deleteNote(it) },
            navController = navController
        )
    }
}