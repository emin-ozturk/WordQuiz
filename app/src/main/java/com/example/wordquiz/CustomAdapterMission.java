package com.example.wordquiz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterMission extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Mission> listMission;

    CustomAdapterMission(Activity activity, List<Mission> listMission) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listMission = listMission;
    }

    @Override
    public int getCount() {
        return listMission.size();
    }

    @Override
    public Object getItem(int position) {
        return listMission.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.custom_list, null);
        ImageView imgCup = view.findViewById(R.id.imgCup);
        TextView txtMission = view.findViewById(R.id.txtMission);
        TextView txtStatus = view.findViewById(R.id.txtStatus);
        ProgressBar pBarStatus = view.findViewById(R.id.pBarStatus);

        Mission mission = listMission.get(position);
        txtMission.setText(mission.getMission());
        txtStatus.setText(mission.getStatusText());
        pBarStatus.setMax(mission.getPBarMax());
        pBarStatus.setProgress(mission.getPBarProgress());

        if (mission.getSuccess()) {
            imgCup.setImageResource(R.drawable.trophy_two);
        } else {
            imgCup.setImageResource(R.drawable.trophy_one);
        }

        return view;
    }
}
