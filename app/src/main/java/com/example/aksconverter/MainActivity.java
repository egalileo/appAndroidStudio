package com.example.aksconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
    public View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextX = findViewById(R.id.inputX);
        editTextY = findViewById(R.id.inputY);
        editTextZ = findViewById(R.id.inputZ);
        txtResultado =findViewById(R.id.txtResultado);

    }

    public void convertirACartesiana(View view){
        double n, x, y, z, lat, lon, elv, latRad, lonRad;
        double ex1 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(a, 2);
        String salida = "", lonStr, latStr, elStr;

        lonStr = editTextX.getText().toString();
        latStr = editTextY.getText().toString();
        elStr  = editTextZ.getText().toString();

        if( lonStr.isEmpty() || latStr.isEmpty() || elStr.isEmpty()){
            salida = "*** Error ***\n  uno o más campos carece(n) de Datos";
            txtResultado.setText(salida);
        }else{

            lon = Double.parseDouble(lonStr);
            lat = Double.parseDouble(latStr);
            elv = Double.parseDouble(elStr);

            if(elv>0 && elv<1000000000){
                latRad = lat*(Math.PI/180.0);
                lonRad = lon*(Math.PI/180.0);
                n = (this.a) / ( ( 1 - Math.sqrt( 1 - ( (ex1) * (Math.pow(Math.sin(latRad),2))))));
                x = (n + elv) * (Math.cos(latRad)) * (Math.cos(lonRad));
                y = (n + elv)* (Math.cos(latRad)) * (Math.sin(lonRad));
                z = (((n)*( (Math.pow(b, 2))/ (Math.pow(a, 2)))) + elv) * (Math.sin(latRad));

                salida = "El Resultado es: \n x: "+(String.format("%.3f", x))+"\ny: "+(String.format("%.3f", y))+"\nz: "+(String.format("%.3f", z));
                txtResultado.setText(salida);
            }else{
                salida = "Error. el rango de elevación esta fuera del establecido ()";
                txtResultado.setText(salida);
            }
        }
    }

    public void convertirAGeodesica(View view){
        double n, x, y, z, p, angulo, lat, lon, elevacion, latRad;
        double ex1 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(a, 2);
        double ex2 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(b, 2);
        String salida = "", xStr, yStr, zStr;

        xStr = editTextX.getText().toString();
        yStr = editTextY.getText().toString();
        zStr  = editTextZ.getText().toString();

        if( xStr.isEmpty() || yStr.isEmpty() || zStr.isEmpty()){
            salida = "*** Error ***\n Uno o más campos carece(n) de Datos";
            txtResultado.setText(salida);
        }else {
            x = Double.parseDouble(xStr);
            y = Double.parseDouble(yStr);
            z = Double.parseDouble(zStr);

            p = Math.sqrt((Math.pow(x, 2)) + (Math.pow(y, 2)));
            angulo = Math.atan((z * this.a) / (p * this.b));
            lat = (Math.atan((z + ((ex2) * (this.b) * (Math.pow(Math.sin(angulo), 3)))) /
                    (p + ((ex1) * (this.a) * (Math.pow(Math.cos(angulo), 3)))))) * (180 / Math.PI);
            latRad = lat * (Math.PI / 180.0);
            lon = (Math.atan(y / x)) * (180 / Math.PI);
            n = (this.a) / (Math.sqrt(1 - ((ex1) * (Math.pow(Math.sin(latRad), 2)))));
            elevacion = (p / Math.cos(latRad)) - n;

            salida = "El Resultado es: \n latitude: " + (String.format("%.3f", lat)) + "\nlongitude: " + (String.format("%.3f", lon)) + "\nelevación: " + (String.format("%.3f", elevacion));
            txtResultado.setText(salida);
        }
    }
}