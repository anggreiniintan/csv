package com.example.sensorcsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Display extends AppCompatActivity {
    private static final String TAG = "Display";
    ArrayList accX, accY, accZ,magnetX,magnetY, magnetZ,gyroX,gyroY,gyroZ,filterX,filterY,filterZ,magnitudeAcc;
    GridAdapter adapter;
    ListView listView;
    String saccX[];
    String saccY[];
    String saccZ[],smagnetX[],smagnetY[],smagnetZ[],sgyroX[],sgyroY[],sgyroZ[],sfilterX[],sfilterY[],sfilterZ[],smagnitudeAcc[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        listView=(ListView) findViewById(R.id.List);

        accX = new ArrayList();
        accY = new ArrayList();
        accZ= new ArrayList();
        magnetX= new ArrayList();
        magnetY= new ArrayList();
        magnetZ= new ArrayList();
        gyroX= new ArrayList();
        gyroY= new ArrayList();
        gyroZ= new ArrayList();
        filterX= new ArrayList();
        filterY= new ArrayList();
        filterZ= new ArrayList();
        magnitudeAcc= new ArrayList();

        try {
            String csv = "/sdcard/ENK/tes11.csv";
            CSVReader csvReader = new CSVReader(new FileReader(csv));

            String nextLine[] =csvReader.readNext();
            while (nextLine!= null){
                System.out.println(Arrays.toString(nextLine));
//                accX.add(nextLine[0]);
//                accY.add(nextLine[1]);
//                accX.add(nextLine[0]);
//                accY.add(nextLine[1]);
//                accZ.add(nextLine[2]);
//                magnetX.add(nextLine[3]);
//                magnetY.add(nextLine[4]);
//                magnetZ.add(nextLine[5]);
//                gyroX.add(nextLine[6]);
//                gyroY.add(nextLine[7]);
//                gyroZ.add(nextLine[8]);
//                filterX.add(nextLine[9]);
//                filterY.add(nextLine[10]);
//                filterZ.add(nextLine[11]);
//                magnitudeAcc.add(nextLine[12]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Read Complete");

//        saccX = accX.toString().replace("[","").replace("]","").split(",");
//        saccY = accY.toString().replace("[","").replace("]","").split(",");
//        saccZ = accZ.toString().replace("[","").replace("]","").split(",");
//        smagnetX = magnetX.toString().replace("[","").replace("]","").split(",");
//        smagnetY = magnetY.toString().replace("[","").replace("]","").split(",");
//        smagnetZ = magnetZ.toString().replace("[","").replace("]","").split(",");
//        sgyroX = gyroX.toString().replace("[","").replace("]","").split(",");
//        sgyroY = gyroY.toString().replace("[","").replace("]","").split(",");
//        sgyroZ = gyroZ.toString().replace("[","").replace("]","").split(",");
//        sfilterX = filterX.toString().replace("[","").replace("]","").split(",");
//        sfilterY = filterY.toString().replace("[","").replace("]","").split(",");
//        sfilterZ = filterZ.toString().replace("[","").replace("]","").split(",");
//        smagnitudeAcc = magnitudeAcc.toString().replace("[","").replace("]","").split(",");
//        adapter = new GridAdapter(this,saccX,saccY,saccZ,smagnetX,smagnetY,smagnetZ,sgyroX,sgyroY,sgyroZ,
//                sfilterX,sfilterY,sfilterZ,smagnitudeAcc);
//        adapter = new GridAdapter(this,saccX,saccY,saccZ,smagnetX,smagnetY,smagnetZ,sgyroX,sgyroY,sgyroZ,
//                  sfilterX,sfilterY,sfilterZ,smagnitudeAcc);
//        listView.setAdapter(adapter);
//        adapter = new GridAdapter(this,saccX,saccY);
//        listView.setAdapter(adapter);


    }
}
