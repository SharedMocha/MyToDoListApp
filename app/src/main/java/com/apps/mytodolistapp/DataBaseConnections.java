package com.apps.mytodolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Data Base Connections Class to handle table creation,updates,deletions
 */
public class DataBaseConnections extends SQLiteOpenHelper {
    //Table Details

    private static final String DB_NAME = "ToDOItemList";
    private static final int DB_VERSION = 1;

    //Column Details
    private static final String Column_Name1 = "ToDoItem";
    private static final String Column_Name2 = "DueDate";
    private static final String Column_Name3 = "isCompleted";
    private static final String Column_Name4 = "id";

    //Model to use
    public static class ToDOItemListFields {
        public String ToDoItemName;
        public String DateObtained;

        //public int id;

        public  ToDOItemListFields(String todoitems, int index,String dateObtained) {
            this.ToDoItemName = todoitems;
            //this.d = index;
            this.DateObtained = dateObtained;

        }
    }
    ArrayList<ToDOItemListFields> todoItemListFields;

    //ArrayList<ToDOItemListFields> todoListItemDetails = new ArrayList<ToDOItemListFields>();

    ArrayList<String> todoListItem = new ArrayList<String>();

    //ArrayList<String>[][] list = new ArrayList[10][10];

    public DataBaseConnections(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE "+DB_NAME+
                "("
                +Column_Name4+" INTEGER PRIMARY KEY,"
                +Column_Name1+" TEXT,"
                +Column_Name2+" TEXT,"
                +Column_Name3+" NUMERIC"
                +
                ")";
        //Log.i('SQL Create Query execution started",createTableSQL);
        db.execSQL(createTableSQL);
        //Log.i('SQL Create Query execution completed",createTableSQL);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }


    }
    // handle fetching of data from db
    public ArrayList<ToDOItemListFields> fetchDatafromDB()
    {
        String[] ColumnNames = {Column_Name4,Column_Name1};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor CursorData = null;
        //c = db.query("ToDOItemList",null,null,null,null,null,null);
        //Cursor c = db.query(
         String rawSQL = "SELECT * FROM ToDOItemList";
        //CursorData = db.rawQuery(rawSQL, null);
        try {
            /**CursorData = db.query(
                    DB_NAME,  // The table to query
                    ColumnNames,                               // The columns to return
                    null,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );
             */
            CursorData = db.rawQuery(rawSQL, null);
            if (CursorData != null) {
                //ToDOItemListFields todolistItems = new ToDOItemListFields();
                todoItemListFields = new ArrayList<ToDOItemListFields>();
                for (CursorData.moveToFirst(); !CursorData.isAfterLast(); CursorData.moveToNext()) {
                    String todoitem = CursorData.getString(CursorData.getColumnIndex(Column_Name1));
                    int indexno = CursorData.getInt(CursorData.getColumnIndex(Column_Name4));
                    String datetopass = CursorData.getString(CursorData.getColumnIndex(Column_Name2));
                    //String todoitem = CursorData.getString(CursorData.getColumnIndex(Column_Name1)).toString();
                    //int indexno = CursorData.getInt(CursorData.getColumnIndex(Column_Name4));
                    //String datetopass = CursorData.getString(CursorData.getColumnIndex(Column_Name2)).toString();

                    todoItemListFields.add(new ToDOItemListFields(todoitem, indexno,datetopass));
                    db.close();
                }
                return todoItemListFields;
            } else {
                db.close();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            e.getStackTrace();
            Log.e("READ", "Error while trying to READ post to database");
        }
        /**
         for(ToDOItemListFields todoItemsListTemp : todoListItemDetails) {
         String name = todoListItemDetails.toString();
         //String name = todoListItemDetails.ToDoItemName
         todoListItem.add(name);
         }
         */

        return todoItemListFields;
    }

    // handle saving  of data to  db
    public void savedatbacktoDB(ArrayList<ToDOItemListFields> todoItemListFields)
    {
        String[] ColumnNames = {"ToDoItem"};
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            cleanData();
            String[] ColumnNamestosave = {Column_Name1,Column_Name2};

            ContentValues values = new ContentValues();
            for (int i = 0; i < todoItemListFields.size(); i++) {
                ToDOItemListFields pandu = todoItemListFields.get(i);
                String valuetosave = pandu.ToDoItemName.toString();
                String date = pandu.DateObtained.toString();
                values.put("ToDoItem", valuetosave);
                values.put("DueDate", date);
                //Log.e("check", valuetosave);
                 db.insert("ToDOItemList", "ToDoItem,DueDate", values);

            }
        }
        catch(Exception e)
        {
            Log.e("WRITE", "Error while trying to WRITE post to database");

        }
        db.close();
    }

    public void cleanData()
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String rawSQL = "DELETE FROM ToDOItemList";
            db.execSQL(rawSQL);
        }
        catch(Exception e){
            Log.e("CleanDATA", "Error while trying to DELETE post to database");

        }
    }

}

