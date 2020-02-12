package com.example.todomanager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(var resource: Int, var taskList: MutableList<task>, onTaskListener: OnTaskListener) : RecyclerView.Adapter<TaskListAdapter.CustomViewHolder>() {

    private var mOnTaskListener = onTaskListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.one_task, parent, false),mOnTaskListener)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val task = taskList[position]
        holder.title.text = task.title.toString()

        if(task.priority==3)
        {
            holder.priority.text = "high"
        }else if(task.priority==2)
        {
            holder.priority.text = "normal"
        }else holder.priority.text = "low"

        holder.status.text = task.status.toString()
        holder.date.text = task.date.toString()
    }


    class CustomViewHolder(view: View, var onTaskListener: OnTaskListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val title: TextView = view.findViewById(R.id.textViewTitle)

        val priority: TextView = view.findViewById(R.id.textViewPriority)

        val status: TextView = view.findViewById(R.id.textViewStatus)
        val date: TextView = view.findViewById(R.id.textViewDate)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onTaskListener.onTaskClick(adapterPosition)
        }
    }
    fun getId(position:Int) : Int{
        return taskList[position].id
    }

    interface OnTaskListener {
        fun onTaskClick(position: Int)

    }
}