package com.thiagosantos.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thiagosantos.convidados.viewmodel.GuestFormViewModel
import com.thiagosantos.convidados.R
import com.thiagosantos.convidados.databinding.ActivityGuestsFormBinding
import com.thiagosantos.convidados.service.constants.GuestConstants


class GuestsFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGuestsFormBinding

    private lateinit var mViewModel: GuestFormViewModel

    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestsFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]

        setListeners()
        observe()
        loadData()

        binding.radioPresence.isChecked = true
    }


    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_save){

            val name = binding.editName.text.toString()
            val presence = binding.radioPresence.isChecked

            mViewModel.save(mGuestId, name, presence)



        }
    }

    private  fun setListeners(){
        binding.buttonSave.setOnClickListener(this)
    }

    private fun loadData(){
        val bundle = intent.extras
        if (bundle != null){
            mGuestId = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(mGuestId)
        }
    }

    private fun observe(){
        mViewModel.saveGuest.observe(this, Observer {
            if (it){
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence){
                binding.radioPresence.isChecked = true
            }else {
                binding.radioAbsent.isChecked = true
            }
        })
    }


}