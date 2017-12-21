package edu.wwu.csci412.a2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.w( "SeachActivity","We are in seachactivity");
        dbManager = new DatabaseManager(this);
        //name and task information:

        final SearchView searchView = (SearchView)findViewById(R.id.scrollView_id);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                Log.w("SearchActivity",query);
                TextView textInput = (TextView) findViewById(R.id.taskInformation_TextView_id);
                //textInput.setText(query);
                // call a function to find the corresponding name and task information
                findNameAndTaskInformation(query);
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        TextView textInput = (TextView) findViewById(R.id.taskInformation_TextView_id);
        textInput.setText(searchView.getQuery());
        //Toast.makeText( SearchActivity.this, searchView.getQuery() , Toast.LENGTH_SHORT).show( );
        Button back_Button = (Button) findViewById(R.id.button_back_search_id);
        //Button save_Button = (Button) findViewById(R.id.buttonSave);
        back_Button.setOnClickListener(this);
        //save_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_back_search_id:
                TextView textView_taskInformation = (TextView) findViewById(R.id.taskInformation_TextView_id);
                TextView textView_name = (TextView) findViewById(R.id.name_search_id);
                textView_taskInformation.setText("");
                textView_name.setText("");
                goBack(v);
                break;
        }
    }

    public void findNameAndTaskInformation(String emailAddress){
        Log.w("SearchActivity","We are in findNameAndTaskInformation");
        //pull db. (create dbmanager.)
        //use for each loop to find info and name.
        //and settext
        //before hit back make sure those text Ids are empty.
        ArrayList<TaskHolder> tasks = dbManager.selectAll( );
        if(tasks.size()>0) {
            for(TaskHolder task : tasks) {
                Log.w("SearchActivity", "Email found: " + task.getEmail());
                if(emailAddress.equals( task.getEmail() ) ) {
                    Log.w("SearchActivity","Inside If statement");
                    TextView textView_taskInformation = (TextView) findViewById(R.id.taskInformation_TextView_id);
                    TextView textView_name = (TextView) findViewById(R.id.name_search_id);
                    textView_taskInformation.setText(task.getTaskInformation());
                    textView_name.setText(task.getName());
                    return;
//                    Log.w("EditActivity", "Id inside If statement: " + task.getId());
//                    Log.w("EditActivity", "Taskinformation inside If: " + task.getTaskInformation());
                    //fillEditText(task.getTaskInformation(), task.getDuedate(), task.getName(), task.getEmail());

                }
            }
        }
        Toast.makeText( SearchActivity.this, "Email doesn't exist", Toast.LENGTH_SHORT).show( );
    }

    public void goBack(View v) {
        this.finish( );
    }



}
