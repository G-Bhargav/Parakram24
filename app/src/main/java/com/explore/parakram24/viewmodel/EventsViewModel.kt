package com.explore.parakram24.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.explore.parakram24.adapters.EventData

class EventsViewModel(application: Application) : AndroidViewModel(application) {
    private val _eventData = MutableLiveData<List<EventData>>()
    val eventData: LiveData<List<EventData>> get() = _eventData


}