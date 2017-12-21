package edu.wwu.csci412.a2;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class InstructionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        Button back_Button = (Button) findViewById(R.id.button_Back);
        back_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_Back:
                goBack(v);
                break;
        }
    }

    public void goBack( View v ) {
        this.finish( );

    }

}
