package com.csa;

public class Board {

    private int gSize;
    private char p1, p2, blank;

    private char[][] arr;

    // To be used in flip()
    private int right = 0;
    private int left = 0;
    private int down = 0;
    private int top = 0;
    private int tRight = 0;
    private int tLeft = 0;
    private int dRight = 0;
    private int dLeft = 0;

    // To be used in count()
    private int numP1 = 0;
    private int numP2 = 0;



    // Constructors. Always going to be a square no matter what.
    Board() {
        gSize = 8;
        p1 = '+'; p2 = '='; blank = '-';
        init();
    }
    Board(int gSize, char p1, char p2, char blank) {
        this.gSize = gSize;
        this.p1 = p1; this.p2 = p2; this.blank = blank;
        init();
    }



    // Might want to combine some methods.
    boolean canMove(char p) {
        /*  Go through every possible blank using numFlipped.
         *   If it doesn't return 0 at least once, meaning there
         *   is a possible move, then return true. */
        boolean move = false;
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (arr[r][c] == blank && numFlipped(r, c, p) != 0) { move = true; break; } } }
        return move;
    }
    boolean canPlace(int r, int c) {
        // Checking if params are valid.
        return r+1 <= gSize && r+1 >= 1 && c+1 <= gSize && c+1 >= 1 && arr[r][c] == blank;
    }
    void place(int r, int c, char p) {
        arr[r][c] = p;
    }
    void count() {
        numP1 = 0;
        numP2 = 0;
        for (char[] r : arr) {
            for (char elem : r) {
                if (elem == p1) numP1++;
                else if (elem == p2) numP2++;
            }
        }
    }
    boolean hasEnded() {
        /*  Loop through the entire array and check if there are no more
         *   blank spaces. If there are none, return true. Else return false. */
        for (char[] ar : arr) {
            for (char player : ar) {
                if (player == blank)
                    return false;
            }
        }
        return true;
    }



    int numFlipped(int r, int c, char p) {
        /*  === PLEASE SIMPLIFY ===
         *   A method that returns the number of flippable pieves.
         *   If 0, reread input.
         *   @param: the desired position, and the current player. */
        try {
            right = 0; left = 0; down = 0; top = 0;
            tRight = 0; tLeft = 0; dRight = 0; dLeft = 0;

            // Base
            int num = 0;

            // Check the entire row first. If the opposite player
            // isn't present, or if there is a discontinuity, or if
            // it isn't on a blank place, return 0. Else add to the
            // number that can be flipped. ENTIRE THING CAN MOST
            // LIKELY BE SIMPLIFIED.

            /*  RIGHT:  r, i+1
             *   LEFT:   r, i-1
             *   DOWN:   i+1, c
             *   TOP:    i-1, c */

            int i = c; try {
                while (arr[r][i+1] == oppPlayer(p)) { // RIGHT - DONE
                    right++; i++;
                }
                if (arr[r][i+1] == blank) right = 0;
            } catch (Exception e) { right = 0; }
            num += right;

            i = c; try {
                while (arr[r][i-1] == oppPlayer(p)) { // LEFT - DONE
                    left++; i--;
                }
                if (arr[r][i-1] == blank) left = 0;
            } catch (Exception e) { left = 0; }
            num += left;

            i = r; try {
                while (arr[i+1][c] == oppPlayer(p)) { // DOWN - DONE
                    down++; i++;
                }
                if (arr[i+1][c] == blank) down = 0;
            } catch (Exception e) { down = 0; }
            num += down;

            i = r; try {
                while (arr[i-1][c] == oppPlayer(p)) { // TOP - DONE
                    top++; i--;
                }
                if (arr[i-1][c] == blank) top = 0;
            } catch (Exception e) { top = 0; }
            num += top;

            /*  TRIGHT: tempR-1, tempC+1
             *   TLEFT:  tempR-1, tempC-1
             *   DRIGHT: tempR+1, tempC+1
             *   DLEFT:  tempR+1, tempC-1 */

            int tempR = r; int tempC = c; try {
                while (arr[tempR-1][tempC+1] == oppPlayer(p)) { // TRIGHT
                    tRight++; tempR--; tempC++;
                }
                if (arr[tempR-1][tempC+1] == blank) tRight = 0;
            } catch (Exception e) { tRight = 0; }
            num += tRight;

            tempR = r; tempC = c; try {
                while (arr[tempR-1][tempC-1] == oppPlayer(p)) { // TLEFT
                    tLeft++; tempR--; tempC--;
                }
                if (arr[tempR-1][tempC-1] == blank) tLeft = 0;
            } catch (Exception e) { tLeft = 0; }
            num += tLeft;

            tempR = r; tempC = c; try {
                while (arr[tempR+1][tempC+1] == oppPlayer(p)) { // DRIGHT
                    dRight++; tempR++; tempC++;
                }
                if (arr[tempR+1][tempC+1] == blank) dRight = 0;
            } catch (Exception e) { dRight = 0; }
            num += dRight;

            tempR = r; tempC = c; try {
                while (arr[tempR+1][tempC-1] == oppPlayer(p)) { // DLEFT
                    dLeft++; tempR++; tempC--;
                }
                if (arr[tempR+1][tempC-1] == blank) dLeft = 0;
            } catch (Exception e) { dLeft = 0; }
            num += dLeft;

            return num;
        } catch (Exception e) { return 0; }
    }
    void flip(int r, int c, char p) {
        for (int i = 1; i <= right; i++)
            arr[r][c+i] = p;
        for (int i = 1; i <= left; i++)
            arr[r][c-i] = p;
        for (int i = 1; i <= down; i++)
            arr[r+i][c] = p;
        for (int i = 1; i <= top; i++)
            arr[r-i][c] = p;
        for (int i = 1; i <= dRight; i++)
            arr[r+i][c+i] = p;
        for (int i = 1; i <= dLeft; i++)
            arr[r+i][c-i] = p;
        for (int i = 1; i <= tRight; i++)
            arr[r-i][c+i] = p;
        for (int i = 1; i <= tLeft; i++)
            arr[r-i][c-i] = p;
    }



    @Override
    public String toString() {
        /*  Prints board. A getter method for this class.
         *   Must call from a stored array. */
        StringBuilder fin = new StringBuilder();
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                fin.append(arr[r][c]);
                if (c != arr[c].length-1)
                    fin.append(" ");
            }
            if (r != arr.length-1)
                fin.append("\n");
        }
        return fin.toString() + "\n";
    }
    // Private methods
    private void init() {
        // This is where we set the default value of the board
        // and its pieces. If the rows are even, place p1 on
        // index rSize/2 (same for cSize) and place the remaining
        // pieces on the top left, bottom left, and top right.
        // If rows are odd, place p1 on index rSize/2
        arr = new char[gSize][gSize];
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (r == gSize/2 && c == gSize/2) {
                    if (gSize % 2 == 0) {
                        arr[r][c] = p2;
                        arr[r-1][c-1] = p2;
                        arr[r-1][c] = p1;
                        arr[r][c-1] = p1;
                    } else {
                        arr[r][c] = p1;
                    }
                } else {
                    arr[r][c] = blank;
                }
            }
        }
    }
    private char oppPlayer(char p) { // Returns opposite player.
        if (p == p1)
            return p2;
        else if (p == p2)
            return p1;
        return blank;
    }
    // Other methods
    char getP1() {
        return p1;
    }
    char getP2() {
        return p2;
    }
    int getNumP1() {
        return numP1;
    }
    int getNumP2() {
        return numP2;
    }
    char getBlank() {
        return blank;
    }
    int getLength(){
        return this.gSize;
    }
    char getPosition(int row,int col){
        return arr[row][col];
    }
}