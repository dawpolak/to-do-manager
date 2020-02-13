package com.example.todomanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), TaskListAdapter.OnTaskListener {

    lateinit var dbHandler: DataBaseHelper
    lateinit var myAdapter: TaskListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dbHandler= DataBaseHelper(activity!!)
        loadRecords()
    }

    override fun onResume() {
        super.onResume()
        loadRecords()
    }

    private fun loadRecords() {
        myAdapter = TaskListAdapter(R.layout.one_task, dbHandler.allTasks,this)
        task_list.apply {
            layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    activity!!,
                    DividerItemDecoration.VERTICAL
                )
            )
            itemAnimator = DefaultItemAnimator()
            adapter = myAdapter
        }
    }

    override fun onTaskClick(position: Int) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,TaskFragment(myAdapter.getId(position)))?.addToBackStack(null)?.commit()
    }
}
