package com.dave.shareride.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dave.shareride.R
import com.dave.shareride.network_requests.classes.RoutesHistory


class RoutesHistoryAdapter(
    private var RoutesHistoryList1: List<RoutesHistory>,
    private val context: Context
) :
    RecyclerView.Adapter<RoutesHistoryAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val tvDestination : TextView = itemView.findViewById(R.id.tvDestination)
        val tvRouteStatus : TextView = itemView.findViewById(R.id.tvRouteStatus)
        val tvDepartureTime : TextView = itemView.findViewById(R.id.tvDepartureTime)
        val tvPickUpNumber : TextView = itemView.findViewById(R.id.tvPickUpNumber)

        val tvVehicleModel : TextView = itemView.findViewById(R.id.tvVehicleModel)
        val tvRegistrationNumber : TextView = itemView.findViewById(R.id.tvRegistrationNumber)
        val tvNumberSlots : TextView = itemView.findViewById(R.id.tvNumberSlots)

        init {

            itemView.setOnClickListener(this)


        }

        override fun onClick(v: View?) {



        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_view_history_routes,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {

        val destinationName = RoutesHistoryList1[position].destinationName
        val routeStatus = RoutesHistoryList1[position].routeStatus
        val departureTime = RoutesHistoryList1[position].departureTime
        val pickPointsNumber = "${RoutesHistoryList1[position].pickPointsNumber} pick points"
        val vehicleModel = RoutesHistoryList1[position].vehicleModel
        val vehicleRegistration = RoutesHistoryList1[position].vehicleRegistration
        val vehicleSlots = "${RoutesHistoryList1[position].vehicleSlots} Slots"

        holder.tvDestination.text = destinationName
        holder.tvRouteStatus.text = routeStatus.toLowerCase()
        holder.tvDepartureTime.text = departureTime
        holder.tvPickUpNumber.text = pickPointsNumber
        holder.tvVehicleModel.text = vehicleModel
        holder.tvRegistrationNumber.text = vehicleRegistration
        holder.tvNumberSlots.text = vehicleSlots



    }

    override fun getItemCount(): Int {
        return RoutesHistoryList1.size
    }

}