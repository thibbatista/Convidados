package com.thiagosantos.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thiagosantos.convidados.databinding.FragmentAllGuestsBinding
import com.thiagosantos.convidados.service.constants.GuestConstants
import com.thiagosantos.convidados.view.adapter.GuestAdapter
import com.thiagosantos.convidados.view.listener.GuestListener
import com.thiagosantos.convidados.viewmodel.GuestsViewModel


class AllGuestsFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()
    private var _binding: FragmentAllGuestsBinding? = null
    private lateinit var mListener: GuestListener

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel =
            ViewModelProvider(this)[GuestsViewModel::class.java]

        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)


        //RecyclerView
        //1 - Obter a recylcler
        val recycler =  binding.recyclerAllGuests

        //2 - Definir um layout
        recycler.layoutManager = LinearLayoutManager(context)

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
                mViewModel.load(GuestConstants.FILTER.Empty)
            }
        }

        mAdapter.attachListeners(mListener)

        observer()

        mViewModel.load(GuestConstants.FILTER.Empty)

        return binding.root
    }

    override fun onResume(){
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.Empty)

    }


    private fun observer(){
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)

        })
    }
}