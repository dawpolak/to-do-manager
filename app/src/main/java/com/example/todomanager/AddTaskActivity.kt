package com.example.todomanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_task.*
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    var dbHandler = DataBaseHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        buttonAddTask.setOnClickListener {
            if (editTextTitle.text.toString() != "" && editTextDescription.text.toString() != "" && findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)!=null) {
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())

                var priority:Int
                if(findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)==findViewById<RadioButton>(radioButtonHigh.id))
                {
                    priority = 3
                }else if (findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)==findViewById<RadioButton>(radioButtonNormal.id))
                {
                    priority = 2
                }else priority = 1
                var newTask =
                    task(0, editTextTitle.text.toString(), editTextDescription.text.toString(),priority,"active",currentDate.toString() )
                dbHandler.addTask(newTask)

                Toast.makeText(this, "Dodano zadanie ${newTask.title}", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Wypełnij dane", Toast.LENGTH_LONG).show()
            }
        }
    }
}
