package edu.wwu.csci412.a2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class InsertActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseManager dbmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbmanager = new DatabaseManager(this);
        setContentView(R.layout.activity_insert);
        Log.w("InsertActivity","We are in InsertActivity");
        Button cancel_Button = (Button) findViewById(R.id.buttonCancel);
        Button save_Button = (Button) findViewById(R.id.buttonSave);
        cancel_Button.setOnClickListener(this);
        save_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonCancel:
                goBack(v);
                break;
            case R.id.buttonSave:
                insert(v);
                goBack(v);
                break;
        }
    }

    public void insert(View v) {

        //Retrieve task information, duedate, name and email
        EditText taskinformationEditText = ( EditText) findViewById( R.id.add_TaskInformation);
        //EditText duedateEditText = ( EditText) findViewById( R.id.add_DueDate);

        EditText duedateEditText_YYYY = ( EditText) findViewById( R.id.add_YYYY);
        EditText duedateEditText_MM = ( EditText) findViewById( R.id.add_MM);
        EditText duedateEditText_DD = ( EditText) findViewById( R.id.add_DD);

        String duedateEditText_YYYY_string = duedateEditText_YYYY.getText().toString();
        String duedateEditText_MM_string = duedateEditText_MM.getText().toString();
        String duedateEditText_DD_string = duedateEditText_DD.getText().toString();

        String duedateStringUpdate = duedateEditText_YYYY_string + "-" + duedateEditText_MM_string + "-" + duedateEditText_DD_string;
        Log.w("InsertActivity","DueDate: " + duedateStringUpdate);


        EditText nameEditText = ( EditText) findViewById( R.id.add_Name);
        EditText emailEditText = ( EditText) findViewById( R.id.add_emailAddress);

        String taskinformationString = taskinformationEditText.getText().toString();
        //String duedateString = duedateEditText.getText().toString();
        String duedateString = duedateStringUpdate;
        String nameString = nameEditText.getText().toString();
        String emailString = emailEditText.getText().toString();

        // insert new candy in database (Shown in version 2)
        try {
            TaskHolder taskholderObject = new TaskHolder(0,taskinformationString,duedateString,nameString,emailString);
            dbmanager.insert(taskholderObject);
            Toast.makeText(this,"Task Added", Toast.LENGTH_SHORT ).show();
        } catch(NumberFormatException nfe) {
            Toast.makeText(this, "Error in adding",Toast.LENGTH_LONG);
        }

        ArrayList<TaskHolder> tasks = dbmanager.selectAll();
        for(TaskHolder taskObject : tasks)
            Log.w("InsertActivity", "task = " + taskObject.toString() );


        // clear data in the four EditTexts
        taskinformationEditText.setText("");
        //duedateEditText.setText("");
        duedateEditText_YYYY.setText("");
        duedateEditText_MM.setText("");
        duedateEditText_DD.setText("");
        nameEditText.setText("");
        emailEditText.setText("");
    }

    public void goBack(View v) {
        this.finish( );
    }

}
