package com.explore.parakram24.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import androidx.lifecycle.viewModelScope
import com.explore.parakram24.SponsorData
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream

class SponsorsViewModel(application: Application) : AndroidViewModel(application) {
    private val _sponsorData = MutableLiveData<List<SponsorData>>()
    val sponsorData : LiveData<List<SponsorData>> get() = _sponsorData

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    fun fetchData(){
        viewModelScope.launch {
            try {
                _loading.value = true

                val assetManager = getApplication<Application>().assets
                val inputStream: InputStream = assetManager.open("sponsordata.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val json = String(buffer, Charsets.UTF_8)
                val gson = Gson()
                val sponsors = gson.fromJson(json, Array<SponsorData>::class.java)
                _sponsorData.value = sponsors.toList()
                _loading.value = false
            } catch (e: IOException) {
                Log.d("SponsorViewModel", e.toString())
                _loading.value = false
            }
        }
    }
}