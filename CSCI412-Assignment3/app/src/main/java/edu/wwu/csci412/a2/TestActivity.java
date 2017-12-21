package edu.wwu.csci412.a2;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.text.ParseException;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseManager dbManager;
    private int buttonWidth;
    private String getQuery;
    private ScrollView scrollView;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_test);
        dbManager = new DatabaseManager( this );

        scrollView = (ScrollView) findViewById(R.id.scrollview_test);

        final SearchView searchView = (SearchView)findViewById(R.id.searchView_test);
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
                getQuery = query;
                Log.w("TestActivity","getQuery here: " + getQuery);
                try {
                    //scrollView.removeAllViewsInLayout( );
                    updateView(query);
                } catch(ParseException e){
                    Log.w( "TestActivity", "UpdateView() Error" );
                }
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        Button back_Button = (Button) findViewById(R.id.button_test);
        back_Button.setOnClickListener(this);

        //updateView();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_test:
                goBack(v);
                break;
        }
    }

    // Build a View dynamically with all the candies
    public void updateView( String emailAddress) throws ParseException {

        ArrayList<TaskHolder> tasks = dbManager.selectAll();
        if(tasks.size() >0) {
            //scrollView = (ScrollView) findViewById(R.id.scrollview_test);
            //scrollView.removeAllViewsInLayout( );
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            buttonWidth = size.x / 2;

            TaskButton[] buttons = new TaskButton[tasks.size()];
            GridLayout grid = (GridLayout) findViewById(R.id.grid_test);
            grid.setRowCount((tasks.size() + 1) / 2);
            grid.setColumnCount(2);
            Log.w("TestActivity", "getQuery here1: " + emailAddress);
            int i = 0;

            for (TaskHolder task : tasks) {
                Log.w("TestActivity", "Test: " + task.getTaskInformation());
                Log.w("TestActivity", "getQuery: " + getQuery);
                if (emailAddress.equals(task.getEmail())) {
                    // create the button
                    buttons[i] = new TaskButton(this, task);
                    buttons[i].setText(task.getTaskInformation() + "\n" + task.getName());

                    String datePattern = "\\d{4}-\\d{2}-\\d{4}";
                    if (!dateCompare(task.getDuedate())) {
                        buttons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorRED_PastDue1));
                    } else {
                        buttons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorGREEN_STILLDue));
                    }


                    // add the button to grid
                    grid.addView(buttons[i], buttonWidth, GridLayout.LayoutParams.WRAP_CONTENT);
                }
                i++;

            }
        }

    }

    public boolean dateCompare(String dueDate) throws ParseException {

        if(dueDate.isEmpty()){
            return true;
        }

        try {
            Date today = new Date();
            DateFormat dateToCompare = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = dateToCompare.parse(dueDate);

            //today is after date1
            if(today.after(date1)){
                return false;
            } else{
                return true;
            }
        } catch(ParseException e){
            return true;
        }

    }



    public void goBack(View v) {
        this.finish( );
    }


}

