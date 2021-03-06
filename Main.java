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
        String playerCount;
        String customize;
        while(true) { // Now THIS is how you correctly do user inputs.
            System.out.print("How many players? (1/2) ");
            playerCount = s.nextLine();
            if (playerCount.equals("1") || playerCount.equals("2")) {
              while (true) {
                System.out.print("Customize? (Y/N) ");
                customize = s.nextLine();
                if (customize.toLowerCase().equals("y")) {
                    customize();
                    break;
                } else if (customize.toLowerCase().equals("n")) {
                    b = new Board();
                    break;
                }
                System.out.println("Type Y or N.");
              }
              break;
            }
            System.out.println("Type 1 or 2.");
        }

        if (playerCount.equals("1")) {
          Ai p1 = new Ai(b,b.getP2(),b.getP1(),b.getBlank(),4,false);

          Ai p2 = new Ai(b,b.getP1(),b.getP2(),b.getBlank(),-1,false);

          System.out.println(b);

          boolean player = false;
          while(!b.hasEnded()) {
            if (!b.canMove(b.getP1()) && !b.canMove(b.getP2())) break;

            player = !player;
            if (player && b.canMove(b.getP1())) {
              System.out.println("--- Player 1 (" + b.getP1() + ") ---");
              ai_input(b.getP1(),p1.get_decision());
            } else if (!player && b.canMove(b.getP2())){
              System.out.println("--- Player 2 (" + b.getP2() + ") ---");
              ai_input(b.getP2(),p2.get_decision());
            }
          }

          if (b.getNumP1() > b.getNumP2())
          System.out.println("Player 1 has won the game!");
          else if (b.getNumP2() > b.getNumP1())
          System.out.println("Player 2 has won the game!");
          else
          System.out.println("It's a tie!");

        } else {
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



        // Ai a = new Ai(b,b.getP1(),b.getP2(),0);
        // System.out.println(b);
        // input(b.getP1());
        // ai_input(b.getP2(),a.get_best());
        // input(b.getP1());
        // ai_input(b.getP2(),a.get_best());





/*
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


    */}


    // Private methods
    private static void customize() {
        int gSize;
        char p1;
        char p2;
        char blank;
        while(true){
          System.out.print("Board size? ");
          gSize = s.nextInt();
          if (gSize > 2 && gSize < 17) {
            break;
          }
        }
        System.out.print("Player 1 symbol? ");
        p1 = s.next().charAt(0);
        System.out.print("Player 2 symbol? ");
        p2 = s.next().charAt(0);
        System.out.print("Blank space symbol? ");
        blank = s.next().charAt(0);

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
        s.nextLine();
        bigAssSpace();
        count();
        System.out.println("\n" + b);
    }

    private static void ai_input(char p,int[] decision){
            int r= decision[0];
            int c = decision[1];

            /*  Once we get user input,
             *      - Check if you can actually place it and check
             *      if there are pieces that can be flipped.
             *      - Then place and flip the piece.
             *      - Then recount the number of pieces on the board. */

            if (b.canPlace(r, c) && b.numFlipped(r, c, p) != 0) {
                b.place(r, c, p); b.flip(r, c, p); b.count(); }

        System.out.println("\n"+"AI's Turn: (Hit ENTER to continue)" );
        s.nextLine();
        System.out.println("AI Placed on ("+ (r+1) + " , "+ (c+1)+ ") ");


        System.out.println();
        count();
        System.out.println("\n" + b);
    }
}
