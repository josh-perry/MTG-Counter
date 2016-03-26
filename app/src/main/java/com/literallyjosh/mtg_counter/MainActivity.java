package com.literallyjosh.mtg_counter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private int players = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        adjustSizeForPlayers();
    }

    private void adjustSizeForPlayers() {
        CounterWidget p1 = (CounterWidget)findViewById(R.id.player1_widget);
        CounterWidget p2 = (CounterWidget)findViewById(R.id.player2_widget);
        CounterWidget p3 = (CounterWidget)findViewById(R.id.player3_widget);
        CounterWidget p4 = (CounterWidget)findViewById(R.id.player4_widget);

        LinearLayout p34_layout = (LinearLayout)findViewById(R.id.p34_layout);

        if(players == 2) {
            // Just collapse players 3 + 4
            p3.setVisibility(View.GONE);
            p4.setVisibility(View.GONE);
            p34_layout.setVisibility(View.GONE);
        }

        if(players == 3) {
            p4.setVisibility(View.INVISIBLE);
        }
    }
}
