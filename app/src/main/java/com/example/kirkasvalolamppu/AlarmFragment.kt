package com.example.kirkasvalolamppu


import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_alarm.*
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cal = Calendar.getInstance()
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val currentDateAndTime = cal.timeInMillis
        val savedDate = sharedPref.getLong(getString(R.string.saved_time), currentDateAndTime)
        timeTextView.text = SimpleDateFormat("HH:mm").format(savedDate)

        alarmButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                timeTextView.text = SimpleDateFormat("HH:mm").format(cal.time)

               // Toast.makeText(this.context, "Cal get time!" + cal.timeInMillis, Toast.LENGTH_SHORT).show()

                with(sharedPref.edit()) {
                    putLong(getString(R.string.saved_time), cal.timeInMillis)
                    commit()
                }
            }
            TimePickerDialog(
                this.context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

}
