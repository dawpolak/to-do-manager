package com.example.todomanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

//-----Database description-----
object TableInfo: BaseColumns {
    const val TABLE_NAME = "toDoDataBase"
    const val COLUMN_ID = "_id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_DESCRIPTION = "description"
    const val COLUMN_PRIORITY = "priority"
    const val COLUMN_STATUS = "status"
    const val COLUMN_DATE = "date"
}

//-----Primary commands of SQL-----
object BasicCommand{
    const val SQL_CREATE_TABLE:String="CREATE TABLE ${TableInfo.TABLE_NAME} ( ${TableInfo.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${TableInfo.COLUMN_TITLE} TEXT, " +
            "${TableInfo.COLUMN_DESCRIPTION} TEXT, " +
            "${TableInfo.COLUMN_PRIORITY} INT, " +
            "${TableInfo.COLUMN_STATUS} TEXT, " +
            "${TableInfo.COLUMN_DATE} TEXT )"
    const val SQL_DELETE_TABLE:String="DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"
}

class DataBaseHelper(context: Context): SQLiteOpenHelper(context,TableInfo.TABLE_NAME,null,2){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(BasicCommand.SQL_DELETE_TABLE)
        onCreate(db)
    }

    val allTasks: MutableList<task>
        get() {
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM ${TableInfo.TABLE_NAME} WHERE ${TableInfo.COLUMN_STATUS}='active' ORDER BY ${TableInfo.COLUMN_PRIORITY} DESC", null)

            var tasksList = mutableListOf<task>()

            if(cursor.moveToFirst()){
                do {
                    val task = task(0,null,null,0,null,null)
                    task.id=cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_ID))
                    task.title=cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_TITLE))
                    task.description=cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_DESCRIPTION))
                    task.priority=cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_PRIORITY))
                    task.status=cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_STATUS))
                    task.date=cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_DATE))
                    tasksList.add(task)
                } while(cursor.moveToNext())
            }

            cursor.close()
            db.close()

            return tasksList
        }

    fun addTask(task: task) {

        if(!task.title.isNullOrEmpty() && !task.description.isNullOrEmpty()){
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(TableInfo.COLUMN_TITLE, task.title)
            values.put(TableInfo.COLUMN_DESCRIPTION, task.description)
            values.put(TableInfo.COLUMN_PRIORITY, task.priority)
            values.put(TableInfo.COLUMN_STATUS, task.status)
            values.put(TableInfo.COLUMN_DATE, task.date)
            try{
                db.insertOrThrow(TableInfo.TABLE_NAME, null, values)
            }finally {
                db.close()
            }
        }
    }

    fun editTask (task: task, id: Int) {
        val updateRecord = ContentValues()

        updateRecord.put(TableInfo.COLUMN_TITLE, task.title.toString())
        updateRecord.put(TableInfo.COLUMN_DESCRIPTION, task.description.toString())
        updateRecord.put(TableInfo.COLUMN_PRIORITY, task.priority.toString())
        updateRecord.put(TableInfo.COLUMN_STATUS, "active")

        val db = this.writableDatabase

        db.update(TableInfo.TABLE_NAME, updateRecord, "${TableInfo.COLUMN_ID}=$id", null)
        db.close()
    }

    fun deleteTask (id: Int) {
        val db = this.writableDatabase
        db.delete(TableInfo.TABLE_NAME, "${TableInfo.COLUMN_ID}=$id", null)
        db.close()
    }

    fun doneTask (id: Int) {
        val db = this.writableDatabase
        val updateRecord = ContentValues()
        updateRecord.put(TableInfo.COLUMN_STATUS, "done")
        db.update(TableInfo.TABLE_NAME, updateRecord, "${TableInfo.COLUMN_ID}=$id", null)
        db.close()
    }

    fun getTask(id:Int):task{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${TableInfo.TABLE_NAME} WHERE ${TableInfo.COLUMN_ID}=$id", null)


        var task= task(id,null,null,0,null,null)
        if(cursor.moveToFirst()){
            task.title=cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_TITLE))
            task.description=cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_DESCRIPTION))
            task.priority=cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_PRIORITY))
            task.status=cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_STATUS))
            task.date=cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_DATE))
        }

        cursor.close()
        db.close()

        return task
    }





}