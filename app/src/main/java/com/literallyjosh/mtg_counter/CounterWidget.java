package com.literallyjosh.mtg_counter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import petrov.kristiyan.colorpicker.ColorPicker;

public class CounterWidget extends FrameLayout {
    private int Life = 20;
    public int Player;
    private boolean InverseTextColour;

    public CounterWidget(Context context) {
        super(context);
    }

    private CountDownTimer AlertTimer;

    public CounterWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.counter_widget, this);

        InverseTextColour = false;

        setBackgroundColor(Color.parseColor("#0099cc"));

        FrameLayout life_frame = (FrameLayout)findViewById(R.id.life_frame);
        life_frame.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ShowColorPicker();
                return false;
            }
        });

        ImageButton minusButton = (ImageButton)findViewById(R.id.minus_life_button);
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Life--;
                UpdateLifeDisplay();
            }
        });

        ImageButton addButton = (ImageButton)findViewById(R.id.add_life_button);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Life++;
                UpdateLifeDisplay();
            }
        });

        UpdateLifeDisplay();
    }

    private void ShowColorPicker() {
        try {
            MainActivity ma = (MainActivity) getContext();
            ma.ShowColorPicker(Player);
        }
        catch(Exception ex) {

        }
    }

    public void SetLife(int life) {
        Life = life;
        UpdateLifeDisplay();
    }

    private void UpdateLifeDisplay() {
        TextView life_display = (TextView)findViewById(R.id.life_display);
        life_display.setText(Integer.toString(Life));

        if(Life <= 0) {
            if(AlertTimer != null) {
                AlertTimer.cancel();
            }

            AlertTimer = new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    if(Life > 0) {
                        // Fix for https://github.com/josh-perry/MTG-Counter/issues/6
                        return;
                    }

                    MainActivity ma = (MainActivity)getContext();
                    ma.CheckLifeTotals(Player);
                }
            }.start();
        }

        UpdateTextColour();
    }

    public void SetBackground(int c) {
        String col = String.format("#%06X", (0xFFFFFF & c));
        this.setBackgroundColor(Color.parseColor(col));

        TextView life_display = (TextView)findViewById(R.id.life_display);
        if(c == -1) {
            InverseTextColour = true;
        }
        else {
            InverseTextColour = false;
        }

        UpdateTextColour();
    }

    private void UpdateTextColour() {
        TextView life_display = (TextView)findViewById(R.id.life_display);

        if(InverseTextColour) {
            if(Life >= 1) {
                life_display.setTextColor(Color.BLACK);
            }
            else {
                life_display.setTextColor(Color.RED);
            }

            return;
        }

        if(Life >= 1) {
            life_display.setTextColor(Color.WHITE);
        }
        else {
            life_display.setTextColor(Color.RED);
        }
    }
}
