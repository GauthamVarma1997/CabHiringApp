package com.hfad.cabhiring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class yourRides extends AppCompatActivity {


    ArrayList<yourRidesAdapter> listnewsData = new ArrayList<yourRidesAdapter>();
    MyCustomAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_rides);

        ListView list = (ListView) findViewById(R.id.list);

        //add data and view it change this part withdata fom firebase
        listnewsData.add(new yourRidesAdapter(1,"01102019","auto","amrita","gandhipuram"));
        listnewsData.add(new yourRidesAdapter(1,"01102019","sedan","ganapathy","gandhipuram"));
        listnewsData.add(new yourRidesAdapter(1,"01102019","micro","wayanad","amrita"));
        listnewsData.add(new yourRidesAdapter(1,"01102019","mini","kodai","gandhipuram"));
        myadapter=new MyCustomAdapter(listnewsData);
        ListView  lsNews=(ListView)findViewById(R.id.list);
        lsNews.setAdapter(myadapter);//initialize with data

        //update  data in listview
        listnewsData.add(new yourRidesAdapter(1,"01102019","bike","kodai","gandhipuram"));
        myadapter.notifyDataSetChanged();

    }

    private class MyCustomAdapter extends BaseAdapter {
        public  ArrayList<yourRidesAdapter>  listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<yourRidesAdapter>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.items, null);

            final   yourRidesAdapter s = listnewsDataAdpater.get(position);

            TextView txtCar=(TextView) myView.findViewById(R.id.Car);
            txtCar.setText(s.Car);
            TextView txtDate=(TextView) myView.findViewById(R.id.Date);
//            String day = s.Car.toString().substring(0,3);
//            String month = s.Car.toString().substring(3,5);
//            String year = s.Car.toString().substring(5,9);
            txtDate.setText(s.Date);
            TextView txtFromloc=(TextView) myView.findViewById(R.id.Fromloc);
            txtFromloc.setText("From : "+s.Fromloc);
            TextView txtToloc=(TextView) myView.findViewById(R.id.Toloc);
            txtToloc.setText("To      : "+s.Toloc);
            return myView;
        }
    }
}
