package edu.wwu.csci412.a2;


import android.content.Context;
import android.support.v7.widget.AppCompatButton;

//display task name in each button
//on a button click, display email address and due date
public class TaskButton extends AppCompatButton {
    private TaskHolder task;
    public TaskButton(Context context, TaskHolder newTask) {
        super( context );
        task = newTask;
    }

    public int getId() {return task.getId(); }

    public String getName( ) {
        return task.getName();
    }

    public String getEmail( ) {
        return task.getEmail();
    }

    public String getDueDate( ) {
        return task.getDuedate();
    }

    public String getTaskInformation( ) {
        return task.getTaskInformation();
    }
}
