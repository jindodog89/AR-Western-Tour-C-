package edu.wwu.csci412.a2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskholderDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASKHOLDER = "taskholder";
    private static final String ID = "id";
    private static final String TASKINFORMATION = "taskinformation";
    private static final String DUEDATE = "duedate";
    private static final String NAME = "name";
    private static final String EMAILADDRESS = "emailaddress";

    public DatabaseManager( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    //must override
    public void onCreate( SQLiteDatabase db ) {
        // Create the task holder table
        String sqlCreate = "create table " + TABLE_TASKHOLDER + "( " + ID;
        sqlCreate += " integer primary key autoincrement, " + TASKINFORMATION;
        sqlCreate += " text, " + DUEDATE + " text, " + NAME;
        sqlCreate += " text, " + EMAILADDRESS + " text ) ";
        db.execSQL(sqlCreate);
    }

    //must override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old table if it exists and recreates
        db.execSQL("drop table if exists " + TABLE_TASKHOLDER);
        onCreate( db );
    }

    public void insert(TaskHolder taskholder) {

        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlInsert = "insert into " + TABLE_TASKHOLDER;
        sqlInsert += " values( null, '" + taskholder.getTaskInformation();
        sqlInsert += "', '" + taskholder.getDuedate();
        sqlInsert += "', '" + taskholder.getName();
        sqlInsert += "', '" + taskholder.getEmail() + "' )";
        db.execSQL( sqlInsert );
        db.close( );
    }

    public void deleteById( int id ) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlDelete = "delete from " + TABLE_TASKHOLDER;
        sqlDelete += " where " + ID + " = " + id;
        db.execSQL( sqlDelete );
        db.close( );
    }

    //Test
    public void deleteAllById() {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlDeleteAll = "delete * from " + TABLE_TASKHOLDER;
        db.execSQL( sqlDeleteAll );
        db.close( );
    }


    public void updateById( int id, String taskinformation, String duedate, String name, String emailaddress) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = "update " + TABLE_TASKHOLDER;
        sqlUpdate += " set "      + TASKINFORMATION       + " = '" + taskinformation + "', ";
        sqlUpdate += DUEDATE      + " = '" + duedate      + "' " + ", ";
        sqlUpdate += NAME         + " = '" + name         + "' " + ", ";
        sqlUpdate += EMAILADDRESS + " = '" + emailaddress + "' ";
        sqlUpdate += " where " + ID + " = " + id;

        db.execSQL( sqlUpdate );
        db.close( );
    }

    public void updateByIdTrial( int id, String taskinformation){
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = "update " + TABLE_TASKHOLDER + " set " + TASKINFORMATION + " = '" + taskinformation + "' where " + ID + " = " + id;
        db.execSQL( sqlUpdate );
        db.close( );
    }

    public TaskHolder selectById( int id ) {
        String sqlQuery = "select * from " + TABLE_TASKHOLDER;

        sqlQuery += " where " + ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );
        TaskHolder taskholder = null;
        if( cursor.moveToFirst( ) )
            taskholder = new TaskHolder( Integer.parseInt( cursor.getString( 0 ) ),
                    cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ) );

        return taskholder;
    }

    public ArrayList<TaskHolder> selectAll( ) {
        String sqlQuery = "select * from " + TABLE_TASKHOLDER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        ArrayList<TaskHolder> tasks = new ArrayList<TaskHolder>();
        while (cursor.moveToNext()) {
            TaskHolder currentCandy = new TaskHolder(Integer.parseInt(cursor.getString(0)),

                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );

            tasks.add(currentCandy);
        }
        db.close();
        return tasks;
    }



}
