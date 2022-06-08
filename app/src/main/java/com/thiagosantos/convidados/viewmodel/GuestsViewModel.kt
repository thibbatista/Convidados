package com.thiagosantos.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thiagosantos.convidados.service.constants.GuestConstants
import com.thiagosantos.convidados.service.model.GuestModel
import com.thiagosantos.convidados.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {


    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)
    private val mGuestList = MutableLiveData<List<GuestModel>>()

    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int) {

        if (filter == GuestConstants.FILTER.Empty) {
            mGuestList.value = mGuestRepository.getAll()

        } else if (filter == GuestConstants.FILTER.ABSENT) {
            mGuestList.value = mGuestRepository.getAbsent()

        } else if (filter == GuestConstants.FILTER.PRESENT) {
            mGuestList.value = mGuestRepository.getPresent()
        }


    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }
}