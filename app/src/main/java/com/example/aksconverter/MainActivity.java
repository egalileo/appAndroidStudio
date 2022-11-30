package com.example.aksconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.Math.*;

public class MainActivity extends AppCompatActivity {
    public double a = 6378137.000;
    public double b = 6356752.314;
    public EditText editTextX;
    public EditText editTextY;
    public EditText editTextZ;
    public TextView txtResultado;
    public RadioButton btnOpcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextX = findViewById(R.id.inputX);
        editTextY = findViewById(R.id.inputY);
        editTextZ = findViewById(R.id.inputZ);
        txtResultado =findViewById(R.id.txtResultado);




    }

    public void convertirACartesiana(){
        double n, x, y, z, p, angulo, lat, lon, elevacion;
        double ex1 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(a, 2);
        double ex2 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(b, 2);
        String salida = "";

        lon = Double.parseDouble(editTextX.getText().toString());
        lat = Double.parseDouble(editTextY.getText().toString());
        elevacion = Double.parseDouble(editTextZ.getText().toString());

        n = (this.a) / ( ( 1 - Math.sqrt( 1 - ( (ex1) * (Math.pow(Math.sin(lat),2))))));
        x = (n + elevacion) * (Math.cos(lat)) * (Math.cos(lon));
        y = (n + elevacion)* (Math.cos(lat)) * (Math.sin(lon));
        z = (((n)*( (Math.pow(b, 2))/ (Math.pow(a, 2)))) + elevacion) * (Math.sin(lat));

        salida = "El Resultado es: \n x: "+x+"\ny: "+y+"\nz: "+z;
        txtResultado.setText(salida);

    }

    public void convertirAGeodesica(){
        double n, x, y, z, p, angulo, lat, lon, elevacion;
        double ex1 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(a, 2);
        double ex2 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(b, 2);
        String salida = "";

        x = Double.parseDouble(editTextX.getText().toString());
        y = Double.parseDouble(editTextY.getText().toString());
        z = Double.parseDouble(editTextZ.getText().toString());

        p = Math.sqrt( (Math.pow(x,2)) + (Math.pow(y,2)) );
        angulo = Math.atan( (z * this.a) / (p * this.b));
        lat = ( Math.atan( (z + ( (ex2)*(this.b)*(Math.pow( Math.sin(angulo),3))))/
                (p + ( (ex1)*(this.a)*(Math.pow(Math.cos(angulo),3)))))) * (180 / Math.PI);
        lon = (Math.atan(y/x))*(180/Math.PI);
        n = (this.a) / (Math.sqrt( 1 - ((ex1) * (Math.pow(Math.sin(lat),2)))));
        elevacion = (p / Math.cos(lat)) - n;

        salida = "El Resultado es: \n latitude: "+lat+"\nlongitude: "+lon+"\nelevación: "+elevacion;
        txtResultado.setText(salida);
    }
}