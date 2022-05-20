package com.fiona.trafficnews.ui


import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.fiona.trafficnews.R
import com.fiona.trafficnews.viewmodel.UserViewModel
import com.fiona.trafficnews.data.Status
import com.fiona.trafficnews.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_update.*


class UpdateFragment : Fragment() {


    private lateinit var dialog: ProgressDialog
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }


    override fun onStart() {
        super.onStart()
        userViewModel = ViewModelProviders.of(requireActivity())[UserViewModel::class.java]

        initUI()
        setupObservers()

    }


    private fun initUI() {
        dialog = ProgressDialog.show(
            activity, "",
            "Loading. Please wait...", true
        )
        dialog.hide()
        tv_username.text = userViewModel.userInfoLiveData.value?.data?.username ?: String()
        val myResArray = resources.getStringArray(R.array.timezone_array)
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, myResArray)

        spinner_timezone.adapter = adapter
        userViewModel.userInfoLiveData.value?.data?.timezone?.let { spinner_timezone.setSelection(it - 1) }

        spinner_timezone.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                userViewModel.userUpdate(position + 1)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        })
        btn_back.setOnClickListener(View.OnClickListener {
            Log.d("btn_back", "btn_back")
            Navigation.findNavController(spinner_timezone)
                .navigate(R.id.action_update_fragment_to_new_fragment)
        })
    }

    private fun setupObservers() {

        userViewModel.userInfoUpdateLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                when (it.status) {
                    Status.SUCCESS -> {
                        dialog.hide()
                    }
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.ERROR -> {
                        dialog.hide()
                        AppUtils().showSnack(spinner_timezone, it.message.toString())
                    }
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Navigation.findNavController(spinner_timezone)
                        .navigate(R.id.action_update_fragment_to_new_fragment)

                }
            }
        )
    }


}