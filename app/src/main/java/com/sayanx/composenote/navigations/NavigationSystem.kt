package com.sayanx.composenote.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sayanx.composenote.model.Note
import com.sayanx.composenote.screen.DetailScreen
import com.sayanx.composenote.screen.NoteAddScreen
import com.sayanx.composenote.screen.ScreenPreview
import com.sayanx.composenote.ui.theme.ComposeNoteTheme
import com.sayanx.composenote.viewmodel.NoteViewModel
import java.util.UUID

@Composable
fun NavigationSystem(viewModel: NoteViewModel) {
    val multiColumnView = remember { mutableStateOf(false) }
    val darkThemeMode = remember { mutableStateOf(true) }

    val navController = rememberNavController()

    ComposeNoteTheme(darkTheme = darkThemeMode.value) {
        NavHost(navController = navController, startDestination = EnumScreens.HomeScreen.name) {

            composable(route = EnumScreens.HomeScreen.name) {
                ScreenPreview(
                    darkThemeMode = darkThemeMode,
                    multiColumnView = multiColumnView,
                    noteViewModel = viewModel,
                    navController = navController
                )
            }

            composable(route = EnumScreens.AddScreen.name) {
                NoteAddScreen(
                    darkThemeMode = darkThemeMode,
                    navController = navController,
                    onAddNote = { note ->
                        viewModel.upsertNote(note)
                    }
                )
            }

            composable(
                route = EnumScreens.DetailScreen.name+"/{note.id}/{note.title}/{note.description}",
                arguments = listOf(
                    navArgument("note.id") {
                        type = NavType.StringType
                    },
                    navArgument("note.title") {
                        type = NavType.StringType
                    },
                    navArgument("note.description") {
                        type = NavType.StringType
                    }
                )
            ) {
                val noteId = it.arguments?.getString("note.id")
                val noteTitle = it.arguments?.getString("note.title")
                val noteDescription = it.arguments?.getString("note.description")
                DetailScreen(
                    note = Note(id = UUID.fromString(noteId),title = noteTitle, description = noteDescription),
                    darkThemeMode = darkThemeMode,
                    navController = navController,
                    onUpdateNote = { note ->
                        viewModel.updateNote(note)
                    }
                )
            }
        }
    }
}