package com.thiagosantos.convidados.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thiagosantos.convidados.service.model.GuestModel
import com.thiagosantos.convidados.service.repository.GuestRepository
// view model não tem context, tem que usar o application
class GuestFormViewModel(application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private  val mContext = application.applicationContext
    private val mGuestRepository: GuestRepository = GuestRepository.getInstance(mContext)
    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest

    private var mGuest = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = mGuest


    fun save(id: Int, name: String, presence: Boolean){
        val guest = GuestModel(id, name, presence)
       // mSaveGuest.value = mGuestRepository.save(guest)

        if (id == 0){
            mSaveGuest.value = mGuestRepository.save(guest)
        }else{
            mSaveGuest.value = mGuestRepository.update(guest)
        }
    }

    fun load(id: Int){
        mGuest.value = mGuestRepository.get(id)
    }
}