package com.example.boris.doodle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clearClicked (View view) {
        //System.out.println("Here");
        DoodleView doodleView = (DoodleView) findViewById(R.id.view);
        doodleView.clear();
    }
}
