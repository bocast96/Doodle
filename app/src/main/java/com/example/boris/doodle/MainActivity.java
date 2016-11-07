package com.example.boris.doodle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    View currColor;
    final float small = 5, med = 10, big = 20, big2 = 35, big3 = 50;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currColor = findViewById(R.id.white);
        currColor.setVisibility(View.INVISIBLE);
        dialog  = new Dialog(this);
    }

    public void clearClicked (View view) {
        //System.out.println("Here");
        DoodleView doodleView = (DoodleView) findViewById(R.id.view);
        doodleView.clear();
    }

    public void colorChange (View view){
        ColorDrawable buttonColor = (ColorDrawable) view.getBackground();
        int color = buttonColor.getColor();
        view.setVisibility(View.INVISIBLE);
        currColor.setVisibility(View.VISIBLE);
        currColor = view;

        DoodleView doodleView = (DoodleView) findViewById(R.id.view);
        doodleView.changeColor(color);
    }

    public void sizeDialog(View view) {
        dialog.setTitle("Brush size:");
        dialog.setContentView(R.layout.brush_size);
        dialog.show();
    }

    public void sizeChange(View view) {
        int id = view.getId();
        float sz = 0;

        switch (id){
            case R.id.small:
                sz = small;
                break;
            case R.id.med:
                sz = med;
                break;
            case R.id.big:
                sz = big;
                break;
            case R.id.big2:
                sz = big2;
                break;
            case R.id.big3:
                sz = big3;
                break;
        }

        DoodleView doodleView = (DoodleView) findViewById(R.id.view);
        doodleView.setSize(sz);

        dialog.dismiss();
    }

    public void save(View view){
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing?");
        saveDialog.setIcon(R.drawable.save2);
        boolean ans;
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                saveImage();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    public void saveImage(){
        DoodleView doodleView = (DoodleView) findViewById(R.id.view);
        doodleView.setDrawingCacheEnabled(true);
        String img = MediaStore.Images.Media.insertImage(getContentResolver(), doodleView.getDrawingCache(), UUID.randomUUID().toString()+".png", "drawing");

        String msg = img == null ? "Image failed to save" : "Drawing saved!";
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
        doodleView.destroyDrawingCache();
    }
}
