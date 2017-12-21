package edu.wwu.csci412.a2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {

    //keep track of what id is clicked on longClickListener
    public static int taskID;
    private DatabaseManager dbManager;
    private String emailAddress;
    private String dueDate;
    private String taskInfo;
    private ScrollView scrollView;
    private int buttonWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        taskID = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbManager = new DatabaseManager(this);

        scrollView = ( ScrollView ) findViewById( R.id.scrollView );
        Point size = new Point( );


        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        buttonWidth = size.x / 2;
        //updateView( ); // Update the view when the app starts

        try{
            updateView( );
        } catch (ParseException e){
            Log.w( "MainActivity", "UpdateView() Error" );
        }

    }

    protected void onResume(){
        super.onResume();
        // Update view when user comes back from a secondary activities (e.g., add/ delete/ update )

        try{
            updateView( );
        } catch (ParseException e){
            Log.w( "MainActivity", "UpdateView() Error" );
        }
    }

    public void updateView() throws ParseException {
        ArrayList<TaskHolder> tasks = dbManager.selectAll( );
        if( tasks.size( ) > 0 ) {

            // remove subviews inside scrollView if necessary
            // User may added, deleted, or updated candies before coming back to
            // this view. Remove all button inside scrollView.
            scrollView.removeAllViewsInLayout( );
            // set up the grid layout
            GridLayout grid = new GridLayout( this );
            grid.setRowCount( ( tasks.size( ) + 1 ) / 2 );
            grid.setColumnCount( 2 );
            // create array of buttons, 2 per row
            TaskButton [] buttons = new TaskButton[tasks.size( )];
            //buttons.setBackgroundColor(Color.WHITE);
            ButtonHandler bh = new ButtonHandler( );
            ButtonLongHandler blh = new ButtonLongHandler( );

            // fill the grid
            int i = 0;

            for ( TaskHolder task : tasks ) {
                // create the button
                buttons[i] = new TaskButton( this, task );
                buttons[i].setText( task.getTaskInformation( ) + "\n" + task.getName( ) );
                //buttons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorGREY_STILLDue));

                //call a function to change color
                Log.w("MainActivity","DueDate: " + task.getDuedate());

                String datePattern = "\\d{4}-\\d{2}-\\d{4}";

                //if(!task.getDuedate().equals("--")) {
                if (!dateCompare(task.getDuedate())) {
                    buttons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorRED_PastDue1));
                } else {
                    buttons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorGREEN_STILLDue));
                }
                //}
                // set up event handling
                buttons[i].setOnClickListener( bh );
                buttons[i].setOnLongClickListener(blh);

                // add the button to grid
                grid.addView( buttons[i], buttonWidth,GridLayout.LayoutParams.WRAP_CONTENT );

                i++;

            }
            scrollView.addView( grid );

        } else {
            scrollView.removeAllViewsInLayout( );
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch ( id ) {
            case R.id.action_add:
                Log.w( "MainActivity", "Add selected" );
                Intent insertIntent = new Intent(MainActivity.this,InsertActivity.class);
                this.startActivity(insertIntent);
                return true;

            case R.id.action_delete:
                Log.w( "MainActivity", "Delete selected" );
                Intent deleteIntent = new Intent(this,DeleteActivity.class);
                this.startActivity(deleteIntent);
                return true;

            case R.id.action_instruction:
                Log.w( "MainActivity", "Update selected" );
                Intent instructionIntent = new Intent(this,InstructionActivity.class);
                this.startActivity(instructionIntent);
                return true;

            case R.id.action_search:
                Log.w( "MainActivity", "Search selected" );
                Intent searchIntent = new Intent(this,TestActivity.class);
                this.startActivity(searchIntent);
                return true;

//            case R.id.action_test:
//                Log.w( "MainActivity", "Test selected" );
//                Intent testIntent = new Intent(this,TestActivity.class);
//                this.startActivity(testIntent);
//                return true;

            default:
                return super.onOptionsItemSelected( item );
        }
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            // retrieve price of the candy and add it to total
            emailAddress = ( ( TaskButton ) v ).getEmail( );
            dueDate = ( ( TaskButton ) v ).getDueDate( );
            String emailAndDueDate = "Email Address: " + emailAddress + "\n" + "Due date: "+dueDate;

            Toast.makeText( MainActivity.this, emailAndDueDate, Toast.LENGTH_LONG ).show( );
        }
    }

    private class ButtonLongHandler implements View.OnLongClickListener {
        public boolean onLongClick(View v) {
            taskID = ( ( TaskButton ) v ).getId( );
            Log.w( "MainActivity", "set taskID: "+Integer.toString(taskID) );
            taskInfo = ( ( TaskButton ) v ).getTaskInformation( );
            String toastTaskInfo = "Update: " + taskInfo;
            //Toast.makeText( MainActivity.this, toastTaskInfo , Toast.LENGTH_SHORT).show( );
            //Log.w( "EditActivity", "Update - LongButton selected" );
            openEditActivity(v);
            return true;
        }

        public void openEditActivity(View view) {

            Intent openEdit = new Intent(view.getContext(), EditActivity.class);
            view.getContext().startActivity(openEdit);
        }

    }
}
