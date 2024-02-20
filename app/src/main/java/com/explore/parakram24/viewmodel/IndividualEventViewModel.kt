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
    private val _gamesData = MutableLiveData<List<MatchData>>()
    val gamesData : LiveData<List<MatchData>> get() = _gamesData

    private val _loading = MutableLiveData<Boolean>()
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
//                            for (k in snapshot.children) {
//                                //Log.i("key->match",k.key.toString())
//                                val data = snapshot.child(k.key.toString()).getValue(MatchData::class.java)
////                                _gamesData.value?.plus(data)
//
//                                //Log.i("key",snapshot.child(k.key.toString()).value.toString())
//                                Log.i("matchdata",data.toString())
//                            }
                            val newData = mutableListOf<MatchData>()
                            for (k in snapshot.children) {
                                val data = k.getValue(MatchData::class.java)
                                data?.let { newData.add(it) }
                            }
                            _gamesData.value = _gamesData.value.orEmpty() + newData
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.i("error",error.message)
                    }

                })

                Log.i("ref", database.toString())
                Log.i("gameslistin",gamesData.value.toString())
                _loading.value = false
            } catch (e: IOException) {
                Log.d("IndividualEventsViewModel", e.toString())
                _loading.value = false
            }
        }
        Log.i("gameslistout",gamesData.value.toString())
    }
}