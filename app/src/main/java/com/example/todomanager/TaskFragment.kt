package com.example.todomanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_task.editTextTitle

private const val ARG_PARAM1 = "param1"

class TaskFragment(var number:Int) : Fragment() {

   private var taskId: Int = id
    lateinit var dbHandler: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dbHandler= DataBaseHelper(activity!!)
        var task = dbHandler.getTask(number)
        editTextTitle.setText(task.title)
        multiAutoCompleteTextViewDescription.setText(task.description.toString())
        if(task.priority==3)
        {
            radioButtonHighFrag.setChecked(true)
        }else if(task.priority==2)
        {
            radioButtonNormalFrag.setChecked(true)
        }else radioButtonLowFrag.setChecked(true)

        buttonCancel.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,ListFragment())?.commit()
        }


        buttonEdit.setOnClickListener{
            var priority:Int
            if(getView()?.findViewById<RadioButton>(radioGroupPriorityf.checkedRadioButtonId)==getView()?.findViewById<RadioButton>(radioButtonHighFrag.id))
            {
                priority = 3
            }else if (getView()?.findViewById<RadioButton>(radioGroupPriorityf.checkedRadioButtonId)==getView()?.findViewById<RadioButton>(radioButtonNormalFrag.id))
            {
                priority = 2
            }else priority = 1
            dbHandler.editTask(task(number,editTextTitle.text.toString(),multiAutoCompleteTextViewDescription.text.toString(),priority,"active",null),number)
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,ListFragment())?.commit()
        }
        buttonDone.setOnClickListener{
            dbHandler.doneTask(number)
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,ListFragment())?.commit()
        }
        buttonDelete.setOnClickListener{
            dbHandler.deleteTask(number)
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,ListFragment())?.commit()
        }
    }



}
