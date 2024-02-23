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
import java.util.Date

class EditableIndividualEventViewModel(application: Application) : AndroidViewModel(application) {
    private val _etGames = MutableLiveData<MutableMap<String,List<MatchData>>>()
    private val _loading = MutableLiveData<Boolean>()
    private val _event = MutableLiveData<String>()
    val etGames : LiveData<MutableMap<String, List<MatchData>>> get() = _etGames
    val event : LiveData<String> get() = _event
    val loading : LiveData<Boolean> get() = _loading
    private lateinit var database: DatabaseReference

    fun fetchData(){
        viewModelScope.launch {
            try {
                _loading.value = true
                Log.i("currentFragment", currentFragment)
                database = Firebase.database.reference
                database.child(currentFragment).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val newData = mutableListOf<MatchData>()
                            for (k in snapshot.children) {
                                try {
                                    val data = k.getValue(MatchData::class.java) ?: MatchData()
                                    newData.add(data)
                                    Log.i("data",k.value.toString())
                                }catch (e :Error  ){
                                    Log.i("error",e.message.toString())
                                }

                            }
                            addGameData(currentFragment,newData)
                        }
                        _loading.value = false
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.i("error",error.message)
                        _loading.value = false
                    }
                })


            } catch (e: IOException) {
                Log.d("IndividualEventsViewModel", e.toString())
                _loading.value = false
            }
        }
    }

    fun addNewGame(){
        val currentDate = Date()
        val newmatchdata = MatchData(key = currentDate.toString())
        database.child(currentFragment).child(currentDate.toString()).setValue(newmatchdata)
    }

    fun addGameData(gameKey: String, list : MutableList<MatchData>) {
        val currentMap = _etGames.value ?: mutableMapOf()
        currentMap[gameKey] = list
        _etGames.value = currentMap
    }

    fun update(fragment : String, cardkey : String , fieldUpdated : String , updatedValue : String){
        database.child(fragment).child(cardkey).child(fieldUpdated).setValue(updatedValue)
    }
}