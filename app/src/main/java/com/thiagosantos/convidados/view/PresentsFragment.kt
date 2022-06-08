package com.thiagosantos.convidados.view
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thiagosantos.convidados.databinding.FragmentPresentsBinding
import com.thiagosantos.convidados.service.constants.GuestConstants
import com.thiagosantos.convidados.view.adapter.GuestAdapter
import com.thiagosantos.convidados.view.listener.GuestListener
import com.thiagosantos.convidados.viewmodel.GuestsViewModel

class PresentsFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private var _binding: FragmentPresentsBinding? = null
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         mViewModel=
             ViewModelProvider(this)[GuestsViewModel::class.java]

        _binding = FragmentPresentsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //RecyclerView
        //1 - Obter a recylcler
        val recycler =  binding.recyclerPresents

        //2 - Definir um layout
        recycler.layoutManager


        //3 - Definir um adapter
        recycler.adapter = mAdapter

        mListener = object : GuestListener{
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestsFormActivity::class.java)

                val bundle =Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)

                intent.putExtras(bundle)
                startActivity(intent)

            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.PRESENT)
            }
        }

        mAdapter.attachListeners(mListener)

        observer()



        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume(){
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.PRESENT)

    }


    private fun observer(){
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)

        })
    }
}