package com.sayanx.composenote.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayanx.composenote.model.Note
import com.sayanx.composenote.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository): ViewModel() {

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes().distinctUntilChanged()
                .collect { listOfNotes ->
                    if (listOfNotes.isEmpty()) {
                        Log.d("Empty", ": Empty list")
                    } else {
                        _noteList.value = listOfNotes
                    }
                }
        }
    }

    fun upsertNote(note: Note) = viewModelScope.launch { noteRepository.upsertNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch { noteRepository.updateNote(note) }
    fun deleteNote(note: Note) = viewModelScope.launch { noteRepository.deleteNote(note) }

}