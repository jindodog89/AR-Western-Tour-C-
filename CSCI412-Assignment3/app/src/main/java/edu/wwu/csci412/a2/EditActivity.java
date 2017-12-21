package edu.wwu.csci412.a2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseManager dbManager;

    private static EditText editText_taskInformation;
    private static EditText editText_dueDate;
    private static EditText editText_name;
    private static EditText editText_emailAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dbManager = new DatabaseManager(this);
        Log.w( "EditActivity", "We are in EditActivity Now " + Integer.toString(MainActivity.taskID) );
        updateView();

        Button cancel_Button = (Button) findViewById(R.id.buttonCancel_edit);
        Button save_Button = (Button) findViewById(R.id.buttonSave_edit);
        cancel_Button.setOnClickListener(this);
        save_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonCancel_edit:
                Log.w( "EditActivity", "Cancel clicked");
                goBack(v);
                break;
            case R.id.buttonSave_edit:
                //edit(v);
                updateDBTable();
                //updateView();
                Log.w( "EditActivity", "Save clicked");
                goBack(v);
                break;
        }
    }

    public void updateView() {

        ArrayList<TaskHolder> tasks = dbManager.selectAll( );
        if(tasks.size()>0) {
            //find the object whose id matches taskID and get its other information
            //then set those info into edit text views in activity_edit.xml
            for(TaskHolder task : tasks) {
                if(MainActivity.taskID == task.getId()) {
                    Log.w("EditActivity", "Id inside If statement: " + task.getId());
                    Log.w("EditActivity", "Taskinformation inside If: " + task.getTaskInformation());
                    fillEditText(task.getTaskInformation(), task.getDuedate(), task.getName(), task.getEmail());
                }
            }

        }
    }

    public void fillEditText(String taskInformation, String dueDate, String name, String emailAddress) {
        Log.w("EditActivity", "we are inisde fillEditText");
        editText_taskInformation = (EditText)findViewById(R.id.add_TaskInformation_edit);
        editText_dueDate = (EditText)findViewById(R.id.add_DueDate_edit);
        editText_name = (EditText)findViewById(R.id.add_Name_edit);
        editText_emailAddress = (EditText)findViewById(R.id.add_emailAddress_edit);

        editText_taskInformation.setText(taskInformation);
        editText_dueDate.setText(dueDate);
        editText_name.setText(name);
        editText_emailAddress.setText(emailAddress);

        //probably here save changed version of texts, and if save button is clicked, then update.
        //save changed attributes back to the database.
        Log.w("EditActivity", "Task ID: " + MainActivity.taskID);
        Log.w("EditActivity", "updating taskinformation: " + taskInformation);

        Log.w("EditActivity","Does it hit here?");
    }

    public void updateDBTable(){

        String taskInformation_edit_test = editText_taskInformation.getText().toString();
        String duedate_edit_test = editText_dueDate.getText().toString();
        String name_edit_test = editText_name.getText().toString();
        String emailAddress_edit_test = editText_emailAddress.getText().toString();

        Log.w("EditActivity","Edited Taskinformation: " + taskInformation_edit_test);

        try {
            dbManager.updateById(MainActivity.taskID,taskInformation_edit_test,duedate_edit_test ,name_edit_test,emailAddress_edit_test );
        } catch(Exception e){
            Log.w("EditActivity","try-catch: Can't update for some reason");
            Toast.makeText( EditActivity.this, "Can't update the table", Toast.LENGTH_SHORT).show( );
        }




    }

    public void goBack(View v) {
        this.finish( );
    }

}
