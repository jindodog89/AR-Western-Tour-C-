package edu.wwu.csci412.a2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        dbManager = new DatabaseManager( this );
        updateView( );
    }

    // Build a View dynamically with all the candies
    public void updateView( ) {

        ArrayList<TaskHolder> tasks = dbManager.selectAll();
        RelativeLayout layout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup group = new RadioGroup(this);
        for (TaskHolder taskObject: tasks) {

            RadioButton rb = new RadioButton(this);
            rb.setId(taskObject.getId());
            rb.setText(taskObject.getTaskInformation());
            group.addView(rb);
        }

        // set up event handling
        RadioButtonHandler rbh = new RadioButtonHandler();
        group.setOnCheckedChangeListener(rbh);

        // create a back button
        Button backButton = new Button(this);
        backButton.setText(R.string.button_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //DeleteActivity.this.finish();
                goBack(v);
            }
        });

        scrollView.addView(group);
        layout.addView(scrollView);

        // add back button at bottom
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); // aligns child's bottom edge with RelativeLayout parent's bottom edge.
        params.addRule(RelativeLayout.CENTER_HORIZONTAL); //Place object in horizontal center of its container, not changing its size.
        params.setMargins(0, 0, 0, 50); //left, top, right, bottom
        layout.addView(backButton, params);
        setContentView(layout);
    }


    public void goBack(View v) {
        this.finish( );
    }


    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup group, int checkedId ) {
            // delete task from database
            dbManager.deleteById( checkedId );
            ArrayList<TaskHolder> tasks = dbManager.selectAll();
            int leftItem = tasks.size();

//            if(leftItem == 0) {
//                dbManager.deleteAllById();
//            }

            Log.w( "DeleteActivity", "number of left Items: "+Integer.toString(leftItem) );
            Toast.makeText(DeleteActivity.this, "Task deleted",Toast.LENGTH_SHORT ).show( );

            // update screen
            updateView( );
        }
    }
}
