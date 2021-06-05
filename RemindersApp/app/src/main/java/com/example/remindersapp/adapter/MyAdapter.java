package com.example.remindersapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.remindersapp.R;
import com.example.remindersapp.model.Reminder;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Reminder> reminders;

    private LayoutInflater layoutInflater;

    public MyAdapter(List<Reminder> reminders, Context context) {
        this.reminders = reminders;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public Object getItem(int position) {
        return reminders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.myrow, null);
        }
        LinearLayout linearLayout = (LinearLayout)convertView;
        TextView textView = convertView.findViewById(R.id.rowTitle);
        if(textView != null) {
            textView.setText(reminders.get(position).getTitle());
        }
        return linearLayout;
    }

}
