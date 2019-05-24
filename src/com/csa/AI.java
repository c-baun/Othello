package com.csa;

public class AI {

    private int[][] decision;
    private boolean[][] available;

    private Board b;

    private char player_char,ai_char;
    private int level;
    private int length;



    public AI(Board b, char player_char, char ai_char, int level) {
        this.b = b;
        this.player_char = player_char;
        this.ai_char = ai_char;
        this.level = level;

        length = b.getLength();
        decision = new int[length][length];
        available = new boolean[length][length];
    }

    public Board newBoard() {
        Board new_b = new Board(length, b.getP1(), b.getP2(), b.getBlank());

        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                new_b.place(row, col, b.getPosition(row, col));
            }
        }
        return new_b;
    }

    public void possible_decision() {
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                decision[row][col] = b.numFlipped(row, col, ai_char);

                if (decision[row][col] == 0)
                    available[row][col] = false;
            }
        }
    }

    public int[] get_best() { // Level 1
        int highest_value = 0;
        int highest_row = 0;
        int highest_col = 0;

        int current_value;

        for (int row = 0; row < b.getLength(); row++) {
            for (int col = 0; col < b.getLength(); col++) {
                current_value = b.numFlipped(row, col, ai_char);
                if (current_value != 0) {
                    if(current_value> highest_value) {
                        highest_row = row;
                        highest_col = col;
                        highest_value = current_value;
                    }
                }
            }
        }
        int[] best_arr = {highest_row,highest_col};
        return best_arr;
    }

    public void refine_decision() {
        int side_checking = 1; // 1 = player, -1 = ai side
        for (int a = 0; a < level; a++) {
            if (side_checking == 1) {
                for (int row = 0; row < b.getLength(); row++) {
                    for (int col = 0; col < b.getLength(); col++) {
                        if (available[row][col])
                            decision[row][col] -= b.numFlipped(row, col, player_char);
                    }
                }
                side_checking = -1;
            }
            else if (side_checking == -1){
                for (int row = 0; row < b.getLength(); row++){
                    for (int col = 0; col < b.getLength(); col++){
                        if (available[row][col])
                            decision[row][col] += b.numFlipped(row, col, ai_char);
                    }
                }
                side_checking = 1;
            }
        }
    }

    /*public int[] best_decision() {
        int[] best_d = new int[2];
        best_d[0] = 0;
        best_d[1] = 0;
        int best_value = decision[0][0];

        for (int row = 0; row < b.getLength(); row++){
            for (int col = 0; col < b.getLength(); col++){
                if(available[row][col] && decision[row][col] > best_value){
                    best_d[0] =row;
                    best_d[1] = col;
                    best_value = decision[row][col];
                }
            }
        }
        return best_d;
    }*/

    public String toString() {
        String total = "";
        for (int row = 0; row < length; row++){
            for (int col = 0; col < length; col++){
                if (available[row][col])
                    total += decision[row][col];
                else
                    total += '/';
                total += " ";
            }
            total += "\n";
        }
        return total;
    }
}