package com.dave.shareride.drivers

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dave.shareride.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_create_pick_ups.*
import org.osmdroid.views.MapView
import java.text.DateFormat
import java.util.*


class CreatePickUps : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pick_ups)

        btnNewPickUp.setOnClickListener {

            showBottomSheetDialog()

        }

    }

    private fun showBottomSheetDialog() {

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_persistent)

        val etPickUpName = bottomSheetDialog.findViewById<EditText>(R.id.etPickUpName)
        val etAmount = bottomSheetDialog.findViewById<EditText>(R.id.etAmount)

        val map = bottomSheetDialog.findViewById<MapView>(R.id.map)

        val btnConfirmDetails = bottomSheetDialog.findViewById<Button>(R.id.btnConfirmDetails)

        val tvDate = bottomSheetDialog.findViewById<TextView>(R.id.tvDate)

        bottomSheetDialog.findViewById<Button>(R.id.btnChooseDate)?.setOnClickListener {

            day = 0
            month = 0
            year = 0
            hour = 0
            minute = 0
            myDay = 0
            myMonth = 0
            myYear = 0
            myHour = 0
            myMinute = 0

            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)

            val datePickerDialog =
                DatePickerDialog(
                    this@CreatePickUps, this@CreatePickUps,
                    year, month,day)
            datePickerDialog.show()

        }

        bottomSheetDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month + 1

        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this@CreatePickUps, this@CreatePickUps,
            hour, minute,
            false)
        timePickerDialog.show()
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute

        val dateTime = "$myYear-$myMonth-$myDay $myHour:$myMinute"
        Log.e("+++++++", dateTime)
    }
}