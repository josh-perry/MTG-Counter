package com.literallyjosh.mtg_counter;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CounterWidget extends FrameLayout {
    private int Life = 20;

    public CounterWidget(Context context) {
        super(context);
    }

    public CounterWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.counter_widget, this);

        Button minusButton = (Button)findViewById(R.id.minus_life_button);
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Life--;
                UpdateLifeDisplay();
            }
        });

        Button addButton = (Button)findViewById(R.id.add_life_button);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Life++;
                UpdateLifeDisplay();
            }
        });

        UpdateLifeDisplay();
    }

    private void UpdateLifeDisplay() {
        TextView life_display = (TextView)findViewById(R.id.life_display);
        life_display.setText(Integer.toString(Life));

        if(Life <= 0) {
            life_display.setTextColor(Color.RED);
        }
        else {
            life_display.setTextColor(Color.WHITE);
        }
    }
}
