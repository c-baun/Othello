package com.csa;
import java.util.*;

/**
 * Welcome to Othello!
 * TODO:
 * 1) Make it continuous and not just one move only. ✔
 * 2) No flipping, no win condition. ✔
 * 3) Make GUI slightly better ,_, ✔
 * 4) SIMPLIFY
 * Bugs:
 * 1) User input mistakes not handled. (Both in intro and gameplay)
 * 2) Row 1 Column 1 doesn't work. ✔
 */

/* I think the most efficient way to handle user input is to always
 *  use nextLine() then convert it to other types. I dunno why but
 *  let's just try it because why not :) [[USER INPUT]] */

public class Main {

    private static Board b;
    private static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n--- Welcome to Othello! ---");
        while(true) { // Now THIS is how you correctly do user inputs.
            System.out.print("Customize? (Y/N) ");
            String customize = s.nextLine();
            if (customize.toLowerCase().equals("y")) {
                customize();
                break;
            } else if (customize.toLowerCase().equals("n")) {
                b = new Board();
                break;
            }
            System.out.println("Type Y or N.");
        }

        System.out.println("\n" + b);

        boolean player = false;
        while(!b.hasEnded()) {
            if (!b.canMove(b.getP1()) && !b.canMove(b.getP2())) break;

            player = !player;
            if (player && b.canMove(b.getP1())) {
                System.out.println("--- Player 1 (" + b.getP1() + ") ---");
                input(b.getP1());
            } else if (!player && b.canMove(b.getP2())){
                System.out.println("--- Player 2 (" + b.getP2() + ") ---");
                input(b.getP2());
            }
        }

        if (b.getNumP1() > b.getNumP2())
            System.out.println("Player 1 has won the game!");
        else if (b.getNumP2() > b.getNumP1())
            System.out.println("Player 2 has won the game!");
        else
            System.out.println("It's a tie!");
    }

    // Private methods
    private static void customize() {
        System.out.print("Board size? ");
        int gSize = s.nextInt();
        System.out.print("Player 1 symbol? ");
        char p1 = s.next().charAt(0);
        System.out.print("Player 2 symbol? ");
        char p2 = s.next().charAt(0);
        System.out.print("Blank space symbol? ");
        char blank = s.next().charAt(0);

        b = new Board(gSize, p1, p2, blank);
    }
    private static void bigAssSpace() {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }
    private static void count() {
        System.out.println("P1 (" + b.getP1() + "): " + b.getNumP1());
        System.out.println("P2 (" + b.getP2() + "): " + b.getNumP2());
    }
    private static void input(char p) {
        while(true) {
            System.out.print("Row: ");
            int r = s.nextInt()-1;
            System.out.print("Column: ");
            int c = s.nextInt()-1;

            /*  Once we get user input,
             *      - Check if you can actually place it and check
             *      if there are pieces that can be flipped.
             *      - Then place and flip the piece.
             *      - Then recount the number of pieces on the board. */

            if (b.canPlace(r, c) && b.numFlipped(r, c, p) != 0) {
                b.place(r, c, p); b.flip(r, c, p); b.count(); break; }
            else { System.out.println("Enter another coordinate.\n"); }
        }
        bigAssSpace();
        count();
        System.out.println("\n" + b);
    }
}