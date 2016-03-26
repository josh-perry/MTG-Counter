package com.literallyjosh.mtg_counter;

import java.util.ArrayList;

/**
 * Created by Josh on 26/03/2016.
 */
public class Game {
    public int TurnCount;
    public int Players;
    public Turn CurrentTurn;
    public ArrayList<Turn> Turns;

    public Game() {
        Players = 0;
        TurnCount = 0;
        CurrentTurn = new Turn();
        CurrentTurn.Player = 1;

        Turns = new ArrayList<>();
    }

    public void ChangeTurns() {
        int p = CurrentTurn.Player + 1;

        Turns.add(CurrentTurn);
        CurrentTurn = new Turn();

        TurnCount++;

        CurrentTurn.Player = p;

        if(CurrentTurn.Player > Players) {
            CurrentTurn.Player = 1;
        }
    }
}

