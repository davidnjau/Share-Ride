package com.dave.shareride.shared.bottom_navigation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dave.shareride.R
import com.dave.shareride.adapter.RoutesHistoryAdapter
import com.dave.shareride.network_requests.calls.RetrofitCallsAccount
import com.dave.shareride.network_requests.calls.RetrofitCallsAuthentication
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Fragment_History : Fragment() {

    lateinit var radioButton: RadioButton
    private var retrofitCalls: RetrofitCallsAccount = RetrofitCallsAccount()
    private lateinit var recyclerView : RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val rootView = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        rootView.radioHistory.setOnCheckedChangeListener { group, checkedId ->
            radioButton = rootView.findViewById(checkedId)
            val historyType = radioButton.text.toString()
            if (historyType == resources.getString(R.string.my_routes)){

                GlobalScope.launch(Dispatchers.IO){
                    val routeList =  retrofitCalls.getMyRoutes(requireActivity())
                    CoroutineScope(Dispatchers.Main).launch {
                        val routesHistoryAdapter = RoutesHistoryAdapter(routeList, requireActivity())
                        recyclerView.adapter = routesHistoryAdapter
                    }

                }

            }else{

            }

        }


        return rootView
    }




}