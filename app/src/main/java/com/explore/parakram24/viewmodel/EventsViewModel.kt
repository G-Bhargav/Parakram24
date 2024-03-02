package com.explore.parakram24.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.explore.parakram24.EventData
import kotlinx.coroutines.launch

class EventsViewModel(application: Application) : AndroidViewModel(application) {
    private val _eventData = MutableLiveData<List<EventData>>()
    val eventData: LiveData<List<EventData>> get() = _eventData
    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    fun fetchData(){
        viewModelScope.launch {
            _loading.value=true
            _eventData.value = listOf(
                EventData("cricket"),
                EventData("Badminton"),
                EventData("Football"),
                EventData("Hockey"),
                EventData("Volleyball"),
                EventData("Basketball"),
                EventData("Kabaddi"),
                EventData("Athletics"),
                EventData("Table tennis"),
                EventData("Squash"),
                EventData("Chess"),
                EventData("Tennis"),
                EventData("Power lifting"),
                EventData("Boxing"),
                EventData("Karate"),
            )
            _loading.value=false
        }
    }

}