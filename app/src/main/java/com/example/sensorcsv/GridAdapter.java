package com.example.sensorcsv;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    Activity activity;
    String[] AccX;
    String[] AccY;
    String[] AccZ;
    String[] GyroX;
    String[] GyroY;
    String[] GyroZ;
    String[] FilterX;
    String[] FilterY;
    String[] FilterZ;
    String[] MagnitudeAcc;
    Display display;
    static LayoutInflater inflater;

    public GridAdapter(Activity Activity, String[] saccX, String[] saccY, String[] saccZ,
                       String[] sgyroX, String[] sgyroY, String[] sgyroZ, String[] sfilterX,
                       String[] sfilterY, String[] sfilterZ, String[] magnitudeAcc) {
        this.activity =Activity;
        this.AccX = saccX;
        this.AccY = saccY;
        this.AccZ = saccZ;
        this.GyroX = sgyroX;
        this.GyroY = sgyroY;
        this.GyroZ = sgyroZ;
        this.FilterX = sfilterX;
        this.FilterY = sfilterY;
        this.FilterZ = sfilterZ;
        this.MagnitudeAcc = magnitudeAcc;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    public GridAdapter(Display display, String[] saccX, String[] saccY) {
//        this.display = display;
//        this.AccX =saccX;
//        this.AccY=saccY;
//        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

//    public GridAdapterr(Display display, String[] saccX, String[] saccY,String[] saccZ,
//                        String[] smagnetX, String[] smagnetY, String[] smagnetZ, String[] sgyroX, String[] sgyroY, String[] sgyroZ, String[] sfilterX, String[] sfilterY, String[] sfilterZ, String[] smagnitudeAccc) {
//        this.display = display;
//        this.AccX = saccX;
//        this.AccY = saccY;
//        this.AccZ = saccZ;
//        this.MagnetX = smagnetX;
//        this.MagnetY = smagnetY;
//        this.MagnetZ = smagnetZ;
//        this.GyroX = sgyroX;
//        this.GyroY = sgyroY;
//        this.GyroZ = sgyroZ;
//        this.FilterX = sfilterX;
//        this.FilterY = sfilterY;
//        this.FilterZ = sfilterZ;
//        this.MagnitudeAccc = smagnitudeAccc;
//        this.inflater= (LayoutInflater) display.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//    }

    @Override
    public int getCount() {
        return AccX.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
       View view = convertView;
       if(view==null){
           view = inflater.inflate(R.layout.gridview_item, null);
           TextView saccx = (TextView)view.findViewById(R.id.saccx);
           TextView saccy = (TextView)view.findViewById(R.id.saccy);
           TextView saccz = (TextView)view.findViewById(R.id.saccz);
           TextView sgyrox =(TextView) view.findViewById(R.id.sgyrox);
           TextView sgyroy = (TextView) view.findViewById(R.id.sgyroy);
           TextView sgyroz = (TextView) view.findViewById(R.id.sgyroz);
           TextView sfilterx = (TextView) view.findViewById(R.id.sfilterx);
           TextView sfiltery = (TextView) view.findViewById(R.id.sfiltery);
           TextView sfilterz = (TextView) view.findViewById(R.id.sfilterz);
           TextView sMagnitudeAcc = (TextView) view.findViewById(R.id.smagnitude);

           saccx.setText(""+AccX[i]);
           saccy.setText(""+AccY[i]);
           saccz.setText(""+AccZ[i]);
           sgyrox.setText(""+GyroX[i]);
           sgyroy.setText(""+GyroY[i]);
           sgyroz.setText(""+GyroZ[i]);
           sfilterx.setText(""+FilterX[i]);
           sfiltery.setText(""+FilterY[i]);
           sfilterz.setText(""+FilterZ[i]);
           sMagnitudeAcc.setText(""+MagnitudeAcc[i]);
       } return view;
    }
}
