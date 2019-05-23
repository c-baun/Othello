package com.csa;

public class AI {

    // Made these private.
    private int[][] decision;
    private boolean[][] available;

    private Board b;

    private char player_char,ai_char;
    private int level;
    private int length;



    public AI(Board b,char player_char,char ai_char,int level){
        this.b = b;

        this.player_char = player_char;
        this.ai_char = ai_char;

        // int length = 0;

        decision = new int[b.getLength()][b.getLength()];
        available = new boolean[b.get]

        this.level = level;
    }



    public void possible_decision(){
        for(int row =0; row< b.getLength();row++){
            for(int col = 0;col<b.getLength();col++){
                decision[row][col] = b.numFlipped(row,col,ai_char);

                if(decision[row][col] == 0){
                    available[row][col] = false;}
            }
        }
    }

    public void refine_decision(){
        int side_checking = 1;// 1 = player, -1 = ai side
        for(int a = 0; a<level; a++){
            if(side_checking == 1){
                for(int row = 0; row<b.getLength();row++){
                    for(int col = 0;col<b.getLength();col++){
                        if(available[row][col]){
                            decision[row][col] -= b.numFlipped(row, col, player_char);
                        }
                    }
                }
                side_checking=-1;
            }
            else if(side_checking ==-1){
                for(int row = 0; row<b.getLength();row++){
                    for(int col = 0; col<b.getLength();col++){
                        if(available[row][col]){
                            decision[row][col] += b.numFlipped(row, col, ai_char);
                        }
                    }
                }
                side_checking  = 1;
            }
        }
    }

    public int[] best_decision(){

        int[] best_d = new int[2];
        best_d[0] = 0;
        best_d[1] = 0;
        int best_value = decision[0][0];



        for(int row = 0; row< b.getLength();row++){
            for(int col =0; col<b.getLength(); col++){

                if(available[row][col] && decision[row][col] > best_value){
                    best_d[0] =row;
                    best_d[1] = col;
                    best_value = decision[row][col];
                }

            }
        }

        return best_d;
    }

    public String toString(){
        String total ="";
        for(int r[]:decision){
            for (int c:r){
                total += c + " ";
            }
            total+="\n";
        }
        return total;
    }




}