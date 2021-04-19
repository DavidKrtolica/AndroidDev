package com.example.listviewapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.listviewapp.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    // THIS WILL HOLD THE DATA
    private List<String> items;

    // CAN INFLATE LAYOUT FILES
    private LayoutInflater layoutInflater;

    public MyAdapter(List<String> items, Context context) {
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // FIRST MAKE A LAYOUT FILE
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.myrow, null);
        }
        LinearLayout linearLayout = (LinearLayout)convertView;
        TextView textView = convertView.findViewById(R.id.textView1);
        if(textView != null) {
            textView.setText(items.get(position));
        }
        return linearLayout;
    }

}
