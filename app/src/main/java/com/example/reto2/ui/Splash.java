package com.example.reto2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.reto2.MainActivity;
import com.example.reto2.R;

import org.w3c.dom.Text;

public class Splash extends AppCompatActivity {
    private int time = 5000;
    private ProgressBar progressBar, progressBar2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        textView = (TextView) findViewById(R.id.textProgressBar);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                progressBar2.incrementProgressBy(20);
                System.out.println(progressBar2.getProgress());

                if(progressBar2.getProgress() == 20){
                    textView.setText("Cargando archivos");
                }else if (progressBar2.getProgress() == 40){
                    textView.setText("Desciptandolo archivos");
                }else if (progressBar2.getProgress() == 60){
                    textView.setText("Importando modulos");
                }else if (progressBar2.getProgress() == 80){
                    textView.setText("Obteniendo datos");
                }

            }
        };
        for(int i=0; i<5; i++){
            new Handler().postDelayed(runnable,(i+1)*1000);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },time);
    }
}