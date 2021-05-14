package com.example.snapapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snapapp.R;
import com.example.snapapp.model.ImageRef;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<ImageRef> images;

    // CAN INFLATE LAYOUT FILES
    private LayoutInflater layoutInflater;

    public MyAdapter(List<ImageRef> images, Context context) {
        this.images = images;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
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
        TextView textView = convertView.findViewById(R.id.rowText);
        if(textView != null) {
            textView.setText(images.get(position).getRef());
        }
        return linearLayout;
    }
}
