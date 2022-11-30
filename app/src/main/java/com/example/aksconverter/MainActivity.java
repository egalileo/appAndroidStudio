package com.example.aksconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.lang.Math.*;

public class MainActivity extends AppCompatActivity {
    public double a = 6378137.000;
    public double b = 6356752.314;
    public int opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void convertir(int tipo){
        double n, x, y, z, p, angulo, lat, lon, elevacion;
        double ex1 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(a, 2);
        double ex2 = ( Math.pow(a,2) - Math.pow(b,2) ) / Math.pow(b, 2);

        if(tipo == 0) { // Geodesicas a Cartesianas
            n = (this.a) / ( ( 1 - Math.sqrt( 1 - ( (ex1) * (Math.pow(Math.sin(lat),2))))));
            x = (n + elevacion) * (Math.cos(lat)) * (Math.cos(lon));
            y = (n + elevacion)* (Math.cos(lat)) * (Math.sin(lon));
            z = (((n)*( (Math.pow(b, 2))/ (Math.pow(a, 2)))) + elevacion) * (Math.sin(lat));

            //pegar x, y, z en los input
        }else if(tipo == 1){ //Cartesianas a Geodesicas
            p = Math.sqrt( (Math.pow(x)) + (Math.pow(y)) );
            angulo = Math.atan( (z * this.a) / (p * this.b));
            lat = ( Math.atan( (z + ( (ex2)*(this.b)*(Math.pow( Math.sin(angulo),3))))/
                    (p + ( (ex1)*(this.a)*(Math.pow(Math.cos(angulo),3)))))) * (180 / Math.PI);
            lon = (Math.atan(y/x))*(180/Math.PI);
            n = (this.a) / (Math.sqrt( 1 - ((ex1) * (Math.pow(Math.sin(lat),2)))));
            elevacion = (p / Math.cos(lat)) - n;

        }

    }
}