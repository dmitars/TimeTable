package com.example.main_app_2.integratedClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.main_app_2.R;
import com.example.main_app_2.data_classes.DataForShowing;

public class PartOfDayDisplay {
    private LinearLayout rightLayout;
    private LinearLayout leftLayout;
    private LayoutInflater layoutInflater;

    public PartOfDayDisplay(LayoutInflater layoutInflater,
                            LinearLayout leftLayout, LinearLayout rightLayout){
        this.layoutInflater = layoutInflater;
        this.leftLayout = leftLayout;
        this.rightLayout = rightLayout;
    }

    public void showPartOfDay(DataForShowing dataForShowing){
        leftLayout.addView(getLeftItem(dataForShowing));
        rightLayout.addView(getRightItem(dataForShowing));
    }

    private View getLeftItem(DataForShowing dataForShowing){
        View item = layoutInflater.inflate(R.layout.left_item, leftLayout, false);
        TextView startTextView =  item.findViewById(R.id.timeBegin);
        TextView endTextView = item.findViewById(R.id.timeEnd);
        startTextView.setText(dataForShowing.getStart());
        endTextView.setText(dataForShowing.getEnd());
        return item;
    }

    private View getRightItem(DataForShowing dataForShowing){
        View item = layoutInflater.inflate(R.layout.item, rightLayout, false);
        TextView titleTextView =  item.findViewById(R.id.tvName);
        TextView firstDetailedTextView =  item.findViewById(R.id.tvPosition);
        TextView secondDetailedTextView =  item.findViewById(R.id.tvSalary);
        titleTextView.setText(dataForShowing.getTitle());
        firstDetailedTextView.setText(dataForShowing.getFirstDetailedData());
        secondDetailedTextView.setText(dataForShowing.getSecondDetailedData());
        return item;
    }

}
