package com.example.dhadotid.ini;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dhadotid on 11/05/2017.
 */

public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> items;

    public Adapter(Activity activity, List<Data> items){
        this.activity = activity;
        this.items = items;
    }
    public int getCount(){
        return items.size();
    }
    public Object getItem(int location){
        return items.get(location);
    }
    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView judul = (TextView) convertView.findViewById(R.id.judul_curhat);
        TextView isi = (TextView) convertView.findViewById(R.id.isi_curhat);

        Data data = items.get(position);

        id.setText(data.getId());
        judul.setText(data.getJudul());
        isi.setText(data.getIsi());

        return convertView;
    }
}
