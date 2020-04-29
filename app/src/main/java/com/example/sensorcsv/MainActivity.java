package com.example.sensorcsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "MainActivity";  //log tag activity
    private final float getAlpha = 0.8f;
    private SensorManager sensorManager;
    Sensor accelerometer, mMagno, linieracc, gyro;
    TextView xValue, yValue, zValue, acctot, filtrasi1, filtrasi2, filtrasi3,
            filtrasilow1, filtrasilow2, filtrasilow3, xmagno, ymagno, zmagno, gyroxV, gyroyV, gyrozV;
    private long mLastUpadate;
    Button mulai;
    float[] gravitasi = new float[3];
    float[] acc = new float[3];
    float akar;
    Button openfile, stop;


    private float lowpass(float current, float gravity) {
        return gravity * getAlpha + current * (1 - getAlpha);
    }

    private float highpass(float current, float gravity) {

        return current - gravity;
    }



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);
        acctot = (TextView) findViewById(R.id.acctot);
        filtrasi1 = (TextView) findViewById(R.id.xfilter);
        filtrasi2 = (TextView) findViewById(R.id.yfilter);
        filtrasi3 = (TextView) findViewById(R.id.zfilter);
        filtrasilow1 = (TextView) findViewById(R.id.xxfilter);
        filtrasilow2 = (TextView) findViewById(R.id.yyfilter);
        filtrasilow3 = (TextView) findViewById(R.id.zzfilter);
        gyroxV = findViewById(R.id.gyrox);
        gyroyV = findViewById(R.id.gyroy);
        gyrozV = findViewById(R.id.gyroz);
        openfile = findViewById(R.id.button);
        stop = findViewById(R.id.buttonstop);

        checkSDCardStatus();

        Log.d(TAG, "onCreate: Instalizing Sensor Services");
        //system servieces get permission
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        linieracc = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (accelerometer != null) {
            sensorManager.registerListener(MainActivity.this, accelerometer, 200000); // reading sensor
        } else {
            xValue.setText("Accelerometer Not Supported");
            yValue.setText("Accelerometer Not Supported");
            zValue.setText("Accelerometer Not Supported");
        }
        if (gyro != null){
            sensorManager.registerListener(MainActivity.this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered accelerometer listener");}
            else{
            gyroxV.setText("Gyro Not Supported");
            gyroyV.setText("Gyro Not Supported");
            gyrozV.setText("Gyro Not Supported");
        }

        openfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                }else{
                Intent intent = new Intent(MainActivity.this, Display.class);
                startActivity(intent);}
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
            }
        });
    }

    private boolean checkSDCardStatus() {
        String SDCardStatus = Environment.getExternalStorageState();

        // MEDIA_UNKNOWN: unrecognized SD card
        // MEDIA_REMOVED: no SD card at all
        // MEDIA_UNMOUNTED: SD card exist but not mounted, not available in Android 4.0+
        // MEDIA_CHECKING: preparing SD card, e.g. powered on and booting
        // MEDIA_MOUNTED: mounted and ready to use
        // MEDIA_MOUNTED_READ_ONLY
        switch (SDCardStatus) {
            case Environment.MEDIA_MOUNTED:
                Toast.makeText(this, "available.", Toast.LENGTH_LONG).show();
                return true;
            case Environment.MEDIA_MOUNTED_READ_ONLY:
                Toast.makeText(this, "SD card is read only.", Toast.LENGTH_LONG).show();
                return false;
            default:
                Toast.makeText(this, "SD card is not available.", Toast.LENGTH_LONG).show();
                return false;
        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensorr = sensorEvent.sensor;
        final float x = sensorEvent.values[0];
        final float y = sensorEvent.values[1];
        final float z = sensorEvent.values[2];

        if (sensorr.getType() == Sensor.TYPE_ACCELEROMETER) {
            long actualTime = System.currentTimeMillis();

            //high-pass filter
            acc[0] = highpass(x, acc[0]);
            acc[1] = highpass(y, acc[1]);
            acc[2] = highpass(z, acc[2]);

            //low-pass filter
            gravitasi[0] = lowpass(acc[0], gravitasi[0]);
            gravitasi[1] = lowpass(acc[1], gravitasi[1]);
            gravitasi[2] = lowpass(acc[2], gravitasi[2]);

            xValue.setText("" +  String.format("%.2f", x));
            yValue.setText("" +  String.format("%.2f",y));
            zValue.setText("" + String.format("%.2f", z));
        } else if (sensorr.getType() == Sensor.TYPE_GYROSCOPE){
            gyroxV.setText(""+  String.format("%.2f",x));
            gyroyV.setText("" +  String.format("%.2f",y));
            gyrozV.setText("" +  String.format("%.2f",z));
        } else{}

        filtrasi1.setText("" +  String.valueOf(acc[0]));
        filtrasi2.setText("" +  String.valueOf(acc[1]));
        filtrasi3.setText("" +  String.valueOf(acc[2]));

        filtrasilow1.setText("" +  String.valueOf(gravitasi[0]));
        filtrasilow2.setText("" +  String.valueOf(gravitasi[1]));
        filtrasilow3.setText("" +  String.valueOf(gravitasi[2]));

        akar = (float) Math.sqrt((gravitasi[0] * gravitasi[0]) + (gravitasi[1] * gravitasi[1]) + (gravitasi[2] * gravitasi[2]));
        acctot.setText("" + akar);
        try {
            String accX = xValue.getText().toString();
            String accY = yValue.getText().toString();
            String accZ = zValue.getText().toString();
            String HLaccX = filtrasilow1.getText().toString();
            String HLaccY = filtrasilow2.getText().toString();
            String HLaccZ = filtrasilow3.getText().toString();
            String gyroX = gyroxV.getText().toString();
            String gyroY = gyroyV.getText().toString();
            String gyroZ = gyrozV.getText().toString();
            String Magnitudeacc = acctot.getText().toString();


            File folder = new File(Environment.getExternalStorageDirectory(),(Environment.DIRECTORY_DOWNLOADS) + "/storage/ENKKK");
            folder.mkdirs();// membuat folder

            String csv = "/storage/ENKKK/tes1.csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv, true));
//                String Row[]= new String[]{"Acc X","Acc Y","Acc Z","Magnetometer X","Magnetometer Y","Magnetometer Z",
//                        "Gyro X", "Gyro Y", "Gyro Z","Filter X","Filter Y","Filter Z","Magnitude Acc"};
            String row[] = new String[]{accX, accY, accZ,gyroX,gyroY,gyroZ,HLaccX, HLaccY, HLaccZ, Magnitudeacc};
            csvWriter.writeNext(row);
            csvWriter.close();
            Toast.makeText(MainActivity.this, "File success created", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "ggl", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,200000);
        sensorManager.registerListener(MainActivity.this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//    private void readText(View input){
//        File file = new File(String.valueOf(input));
//        StringBuilder text = new StringBuilder();
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null){
//                text.append(line);
//                text.append("\n");
//            } br.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        text.toString();
//    }
//
//    private void selectfile(){
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("/sdcard/ENK/*");
//        startActivity(intent);
//    }




}

