package com.literallyjosh.mtg_counter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import petrov.kristiyan.colorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {
    private int players = 2;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        StartGame();
    }

    private void StartGame() {
        ImageButton turnButton = (ImageButton)findViewById(R.id.turn_button);
        turnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.ChangeTurns();

                ImageButton turnButton = (ImageButton)findViewById(R.id.turn_button);

                UpdateTurnArrow();
            }
        });

        game = new Game();
        game.Players = players;

        CounterWidget p1 = (CounterWidget)findViewById(R.id.player1_widget);
        p1.Player = 1;
        p1.SetLife(20);

        CounterWidget p2 = (CounterWidget)findViewById(R.id.player2_widget);
        p2.Player = 2;
        p2.SetLife(20);

        AdjustSizeForPlayers();
        UpdateTurnArrow();
    }

    private void UpdateTurnArrow() {
        ImageButton turnButton = (ImageButton)findViewById(R.id.turn_button);

        if(game.CurrentTurn.Player == 1) {
            turnButton.setImageResource(R.drawable.ic_navigation_arrow_drop_up);
        }
        else {
            turnButton.setImageResource(R.drawable.ic_navigation_arrow_drop_down);
        }
    }

    private void AdjustSizeForPlayers() {
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

    public void CheckLifeTotals(int Player) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // Fix for https://github.com/josh-perry/MTG-Counter/issues/7
                        runOnUiThread(new Runnable() {
                            public void run() {
                                StartGame();
                            }
                        });

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("End Game");
        builder.setMessage(String.format("Player %s has fallen below 1 life!\nEnd game?", Player))
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    public void ShowColorPicker(final int player) {
        final ColorPicker colorPicker = new ColorPicker(MainActivity.this);
        colorPicker.setFastChooser(new ColorPicker.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int color) {
                colorPicker.dismissDialog();

                if(player == 1) {
                    CounterWidget p1 = (CounterWidget) findViewById(R.id.player1_widget);
                    p1.SetBackground(color);
                }
                else {
                    CounterWidget p2 = (CounterWidget) findViewById(R.id.player2_widget);
                    p2.SetBackground(color);
                }
            }
        }).setColors(Color.parseColor("#F44336"),
                     Color.parseColor("#2196F3"),
                     Color.parseColor("#000000"),
                     Color.parseColor("#FFFFFF"),
                     Color.parseColor("#4CAF50"))
          .setRoundButton(true)
          .setTitle("Select background colour")
          .show();
    }
}
