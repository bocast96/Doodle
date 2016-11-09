package com.example.boris.doodle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private View currColor;
    private float small = 5, med = 10, big = 20, big2 = 35, big3 = 50;
    private Dialog dialog, colorPicker;
    private DoodleView doodleView;
    private int background = Color.BLACK;
    private SeekBar rBar, bBar, gBar;
    private EditText rNum, gNum, bNum;
    private LayoutInflater inflater;
    private View layout, space;
    //private Space space;
    private int r, g, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


    }

    private void init(){
        currColor = findViewById(R.id.white);
        currColor.setVisibility(View.INVISIBLE);
        dialog  = new Dialog(this);
        doodleView = (DoodleView) findViewById(R.id.view);
        colorPicker = new Dialog(this);
        inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.color_picker, (ViewGroup)findViewById(R.id.colorPicker));
        colorPicker.setContentView(layout);
        //colorPicker.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        space = (View) layout.findViewById(R.id.colorPicker);
        space.setBackgroundColor(Color.BLACK);
        rBar = (SeekBar) layout.findViewById(R.id.redBar);
        rNum = (EditText) layout.findViewById(R.id.redNum);
        gBar = (SeekBar) layout.findViewById(R.id.greenBar);
        gNum = (EditText) layout.findViewById(R.id.greenNum);
        bBar = (SeekBar) layout.findViewById(R.id.blueBar);
        bNum = (EditText) layout.findViewById(R.id.blueNum);
    }

    public void colorPicker(View view){
        rBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                r = progresValue;
                rNum.setText(String.valueOf(r));
                space.setBackgroundColor(Color.rgb(r, g, b));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        gBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                g = progresValue;
                gNum.setText(String.valueOf(g));
                space.setBackgroundColor(Color.rgb(r, g, b));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        bBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                b = progresValue;
                bNum.setText(String.valueOf(b));
                space.setBackgroundColor(Color.rgb(r, g, b));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        colorPicker.show();
    }

    public void confirmColor(View view){
        currColor.setVisibility(View.VISIBLE);
        doodleView.changeColor(Color.rgb(r,g,b));
        colorPicker.dismiss();
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

        //DoodleView doodleView = (DoodleView) findViewById(R.id.view);
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

        //DoodleView doodleView = (DoodleView) findViewById(R.id.view);
        doodleView.setSize(sz);

        dialog.dismiss();
    }

    public void invertBackground(View view){
        background = background == Color.BLACK ? Color.WHITE : Color.BLACK;
        doodleView.setBackgroundColor(background);
    }

    public void save(View view){
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing?");
        saveDialog.setIcon(R.drawable.save2);
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
        //DoodleView doodleView = (DoodleView) findViewById(R.id.view);
        doodleView.setDrawingCacheEnabled(true);
        String img = MediaStore.Images.Media.insertImage(getContentResolver(), doodleView.getDrawingCache(), UUID.randomUUID().toString()+".png", "drawing");

        String msg = img == null ? "Image failed to save" : "Drawing saved!";
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
        doodleView.destroyDrawingCache();
    }


}
