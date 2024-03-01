package com.explore.parakram24.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.explore.parakram24.fragments.MatchData
import com.explore.parakram24.fragments.currentFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.io.IOException

class IndividualEventViewModel(application: Application) : AndroidViewModel(application) {
    private val _games = MutableLiveData<MutableMap<String,List<MatchData>>>()
    val games : MutableLiveData<MutableMap<String,List<MatchData>>> get() = _games

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading
    private lateinit var database: DatabaseReference

    fun fetchData(current : String){
        viewModelScope.launch {
            try {
                _loading.value = true
                Log.i("current", current)
                database = Firebase.database.reference
                database.child(current).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val newData = mutableListOf<MatchData>()
                            for (k in snapshot.children) {
                                try {
                                    val data = k.getValue(MatchData::class.java) ?: MatchData()
                                    newData.add(data)
                                }catch (e :Error  ){
                                    Log.i("error",e.message.toString())
                                }

                            }
                            addGameData(currentFragment,newData)
                        }
                        else{
                            addGameData(current,null)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.i("error",error.message)
                    }

                })

                _loading.value = false
            } catch (e: IOException) {
                Log.d("IndividualEventsViewModel", e.toString())
                _loading.value = false
            }
        }
    }

    fun addGameData(gameKey: String, list : MutableList<MatchData>?) {
        val currentMap = _games.value ?: mutableMapOf()
        currentMap[gameKey] = list ?: listOf()
        _games.value = currentMap
    }
}
