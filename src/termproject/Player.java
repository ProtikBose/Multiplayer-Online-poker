/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Soumit
 */
public class Player implements Runnable {

    //PokerTable pokerTable;
    public static int balance = 1000;
    public static int playerID;
    public static ObjectInputStream ois;
    public static ObjectOutputStream oos;
    public static CommunicationMsg msg, finalmsg;
    public static Card[] hand, river, winningcards, sortedcard;
    Scanner in;
    public static String handname;
    public static int rank, prebet, type;
    Thread t;

    void balancemsg() {
        System.out.println("Balance : " + balance + "$");
    }

    void blindmsg(int i) {
        balance = balance - i;
        prebet = i;
        msg.pot = i;
        System.out.println("Blind taken : " + i + "$");
    }

//    public void setPokerTable(PokerTable pTable)
//    {
//        pokerTable=pTable;
//    }
    void updatefinalmsg() {
        //System.out.println("Showing In update finalmsg  ----->");
        finalmsg.playerID = playerID;
        finalmsg.balance = balance;
        finalmsg.winningcards = winningcards;
        finalmsg.rank = rank;
        finalmsg.handname = handname;
        finalmsg.SortedCard = sortedcard;
        //System.out.println("handname " +finalmsg.handname);
        //System.out.println("rank " +finalmsg.rank);
    }

    public Player() {
        in = new Scanner(System.in);
        msg = new CommunicationMsg();
        try {
            Socket sock = new Socket("127.0.0.1", 12345);
            oos = new ObjectOutputStream(sock.getOutputStream());
            ois = new ObjectInputStream(sock.getInputStream());
            //msg = (CommunicationMsg) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        t = new Thread(this);
        t.start();
    }

    public static void main(String[] args) {
        Player p = new Player();
        try {
            p.Play();
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Play() throws IOException {

    }

    public void display(Card card) {
        if (card.rank == 14) {
            System.out.print("Ace of ");
        }
        if (card.rank == 2) {
            System.out.print("Two of ");
        }
        if (card.rank == 3) {
            System.out.print("Three of ");
        }
        if (card.rank == 4) {
            System.out.print("Four of ");
        }
        if (card.rank == 5) {
            System.out.print("Five of ");
        }
        if (card.rank == 6) {
            System.out.print("Six of ");
        }
        if (card.rank == 7) {
            System.out.print("Seven of ");
        }
        if (card.rank == 8) {
            System.out.print("Eight of ");
        }
        if (card.rank == 9) {
            System.out.print("Nine of ");
        }
        if (card.rank == 10) {
            System.out.print("Ten of ");
        }
        if (card.rank == 11) {
            System.out.print("Jack of ");
        }
        if (card.rank == 12) {
            System.out.print("Queen of ");
        }
        if (card.rank == 13) {
            System.out.print("King of ");
        }
        if (card.suit == 1) {
            System.out.print("Spades");
            System.out.println();
        }
        if (card.suit == 2) {
            System.out.print("Hearts");
            System.out.println();
        }
        if (card.suit == 3) {
            System.out.print("Diamonds");
            System.out.println();
        }
        if (card.suit == 4) {
            System.out.print("Clubs");
            System.out.println();
        }
    }

    public CommunicationMsg MakeNewMsg(CommunicationMsg x) {
        CommunicationMsg temp = new CommunicationMsg();
        temp.Bet = x.Bet;
        temp.winnerID = x.winnerID;
        temp.SortedCard = x.SortedCard;
        temp.balance = x.balance;
        temp.choice = x.choice;
        temp.hand = x.hand;
        temp.pot = x.pot;
        temp.river = x.river;
        temp.type = x.type;
        temp.bidderID = x.bidderID;
        temp.playerID = x.playerID;
        temp.rank = x.rank;
        temp.handname = x.handname;
        temp.winningcards = x.winningcards;
        temp.message = x.message;
        return temp;
    }

    public CommunicationMsg WinningHand(Card[] testcard) {
        Card[] wincards;
        Card[][] combinationcard = new Card[21][5];
        CommunicationMsg p = new CommunicationMsg();
        CommunicationMsg temp = new CommunicationMsg();

        combinationcard[0][0] = testcard[0];
        combinationcard[0][1] = testcard[1];
        combinationcard[0][2] = testcard[2];
        combinationcard[0][3] = testcard[3];
        combinationcard[0][4] = testcard[4];
        combinationcard[1][0] = testcard[0];
        combinationcard[1][1] = testcard[1];
        combinationcard[1][2] = testcard[2];
        combinationcard[1][3] = testcard[3];
        combinationcard[1][4] = testcard[5];
        combinationcard[2][0] = testcard[0];
        combinationcard[2][1] = testcard[1];
        combinationcard[2][2] = testcard[2];
        combinationcard[2][3] = testcard[4];
        combinationcard[2][4] = testcard[5];
        combinationcard[3][0] = testcard[0];
        combinationcard[3][1] = testcard[1];
        combinationcard[3][2] = testcard[3];
        combinationcard[3][3] = testcard[4];
        combinationcard[3][4] = testcard[5];
        combinationcard[4][0] = testcard[0];
        combinationcard[4][1] = testcard[2];
        combinationcard[4][2] = testcard[3];
        combinationcard[4][3] = testcard[4];
        combinationcard[4][4] = testcard[5];
        combinationcard[5][0] = testcard[1];
        combinationcard[5][1] = testcard[2];
        combinationcard[5][2] = testcard[3];
        combinationcard[5][3] = testcard[4];
        combinationcard[5][4] = testcard[5];
        combinationcard[6][0] = testcard[0];
        combinationcard[6][1] = testcard[1];
        combinationcard[6][2] = testcard[2];
        combinationcard[6][3] = testcard[3];
        combinationcard[6][4] = testcard[6];
        combinationcard[7][0] = testcard[0];
        combinationcard[7][1] = testcard[1];
        combinationcard[7][2] = testcard[2];
        combinationcard[7][3] = testcard[4];
        combinationcard[7][4] = testcard[6];
        combinationcard[8][0] = testcard[0];
        combinationcard[8][1] = testcard[1];
        combinationcard[8][2] = testcard[3];
        combinationcard[8][3] = testcard[4];
        combinationcard[8][4] = testcard[6];
        combinationcard[9][0] = testcard[0];
        combinationcard[9][1] = testcard[2];
        combinationcard[9][2] = testcard[3];
        combinationcard[9][3] = testcard[4];
        combinationcard[9][4] = testcard[6];
        combinationcard[10][0] = testcard[1];
        combinationcard[10][1] = testcard[2];
        combinationcard[10][2] = testcard[3];
        combinationcard[10][3] = testcard[4];
        combinationcard[10][4] = testcard[6];
        combinationcard[11][0] = testcard[0];
        combinationcard[11][1] = testcard[1];
        combinationcard[11][2] = testcard[2];
        combinationcard[11][3] = testcard[5];
        combinationcard[11][4] = testcard[6];
        combinationcard[12][0] = testcard[0];
        combinationcard[12][1] = testcard[1];
        combinationcard[12][2] = testcard[3];
        combinationcard[12][3] = testcard[5];
        combinationcard[12][4] = testcard[6];
        combinationcard[13][0] = testcard[0];
        combinationcard[13][1] = testcard[2];
        combinationcard[13][2] = testcard[3];
        combinationcard[13][3] = testcard[5];
        combinationcard[13][4] = testcard[6];
        combinationcard[14][0] = testcard[1];
        combinationcard[14][1] = testcard[2];
        combinationcard[14][2] = testcard[3];
        combinationcard[14][3] = testcard[5];
        combinationcard[14][4] = testcard[6];
        combinationcard[15][0] = testcard[0];
        combinationcard[15][1] = testcard[1];
        combinationcard[15][2] = testcard[4];
        combinationcard[15][3] = testcard[5];
        combinationcard[15][4] = testcard[6];
        combinationcard[16][0] = testcard[0];
        combinationcard[16][1] = testcard[2];
        combinationcard[16][2] = testcard[4];
        combinationcard[16][3] = testcard[5];
        combinationcard[16][4] = testcard[6];
        combinationcard[17][0] = testcard[1];
        combinationcard[17][1] = testcard[2];
        combinationcard[17][2] = testcard[4];
        combinationcard[17][3] = testcard[5];
        combinationcard[17][4] = testcard[6];
        combinationcard[18][0] = testcard[0];
        combinationcard[18][1] = testcard[3];
        combinationcard[18][2] = testcard[4];
        combinationcard[18][3] = testcard[5];
        combinationcard[18][4] = testcard[6];
        combinationcard[19][0] = testcard[1];
        combinationcard[19][1] = testcard[3];
        combinationcard[19][2] = testcard[4];
        combinationcard[19][3] = testcard[5];
        combinationcard[19][4] = testcard[6];
        combinationcard[20][0] = testcard[2];
        combinationcard[20][1] = testcard[3];
        combinationcard[20][2] = testcard[4];
        combinationcard[20][3] = testcard[5];
        combinationcard[20][4] = testcard[6];

        wincards = combinationcard[0];

        //printing all combinations taking 5 cards from 7
//        System.out.println("");
//        for (int i = 0; i < 21; i++) {
//            this.checkHand(combinationcard[i], 0, 5);
//        }
//        System.out.println("");
        int winrank = 0;
        //int x=0;
        for (int i = 0; i < 21; i++) {
            p = this.evaluate(combinationcard[i]);
            if (p.rank >= winrank) {
                temp = p;
                winrank = p.rank;
                wincards = combinationcard[i];
                //p.cards = combinationcard[i];
            }
        }
        temp.rank = winrank;
        temp.winningcards = wincards;
        //System.out.println(winrank);
        return temp;
    }

    public CommunicationMsg evaluate(Card[] t) {
        CommunicationMsg temp = new CommunicationMsg();
        temp.winningcards = t;

        if (this.royalFlush(t) == 1) {
            //System.out.println("You have a royal flush!");
            temp.rank = 23;
            temp.handname = "ROYAL FLUSH !!!";
        } else if (this.straightFlush(t) == 1) {
            temp.rank = 22;
            temp.handname = "STRAIGHT FLUSH !!!";
            //System.out.println("You have a straight flush!");
        } else if (this.fourOfaKind(t) == 1) {
            //System.out.println("You have four of a kind!");
            temp.rank = 21;
            temp.handname = "FOUR OF A KIND !!!";
        } else if (this.fullHouse(t) == 1) {
            //System.out.println("You have a full house!");
            temp.rank = 20;
            temp.handname = "FULL HOUSE !!!";
        } else if (this.flush(t) == 1) {
            //System.out.println("You have a flush!");
            temp.rank = 19;
            temp.handname = "FLUSH !!!";
        } else if (this.straight(t) == 1) {
            //System.out.println("You have a straight!");
            temp.rank = 18;
            temp.handname = "Straight !!!";
        } else if (this.triple(t) == 1) {
            //System.out.println("You have a triple!");
            temp.rank = 17;
            temp.handname = "THREE OF A KIND !!!";
        } else if (this.twoPairs(t) == 1) {
            //System.out.println("You have two pairs!");
            temp.rank = 16;
            temp.handname = "TWO PAIRS !!!";
        } else if (this.pair(t) == 1) {
            //System.out.println("You have a pair!");
            temp.rank = 15;
            temp.handname = "PAIR !!!";
        } else {
            int highCard = this.highCard(t);
            temp.rank = highCard;
            temp.handname = "HIGHCARD !!!";
            //System.out.println("Your highest card is " + highCard);
        }
        return temp;
    }

    // checks for a royal flush
    public int royalFlush(Card[] testcard) {
        if (testcard[4].rank == 14 && testcard[0].rank == 10 && testcard[1].rank == 11
                && testcard[2].rank == 12 && testcard[3].rank == 13) {
            if (testcard[0].suit == testcard[1].suit && testcard[0].suit == testcard[2].suit
                    && testcard[0].suit == testcard[3].suit && testcard[0].suit == testcard[4].suit) {
                return 1;
            }
        }
        return 0;
    }

    // checks for a straight flush
    public int straightFlush(Card[] testcard) {

        for (int counter = 1; counter < 5; counter++) {
            if (testcard[0].suit != testcard[counter].suit) {
                return 0;
            }
        }
        if (testcard[4].rank == 14) {
            for (int counter2 = 1; counter2 < 4; counter2++) {
                if (testcard[counter2 - 1].rank != (testcard[counter2].rank - 1)) {
                    return 0;
                }
            }

            if (testcard[0].rank == 10 || testcard[0].rank == 2) {
                return 1;
            } else {
                return 0;
            }
        }

        for (int counter2 = 1; counter2 < 5; counter2++) {
            if (testcard[counter2 - 1].rank != (testcard[counter2].rank - 1)) {
                return 0;
            }
        }

        return 1;

    }

    // checks for four of a kind
    public int fourOfaKind(Card[] testcard) {
        int flag1 = 1;
        int flag2 = 1;

        if (testcard[0].rank == testcard[1].rank) {
            flag1++;
            flag2++;
        }
        for (int i = 2; i < 5; i++) {
            if (testcard[0].rank == testcard[i].rank) {
                flag1++;
            }
            if (testcard[1].rank == testcard[i].rank) {
                flag2++;
            }
        }

        if (flag1 == 4 || flag2 == 4) {
            return 1;
        } else {
            return 0;
        }
    }

    // checks for full house
    public int fullHouse(Card[] testcard) {
        int comparison = 0;
        for (int counter = 1; counter < 5; counter++) {
            if (testcard[counter - 1].rank == testcard[counter].rank) {
                comparison++;
            }
        }
        if (comparison == 3) {
            return 1;
        } else {
            return 0;
        }
    }

    // checks for flush
    public int flush(Card[] testcard) {
        for (int counter = 1; counter < 5; counter++) {
            if (testcard[0].suit != testcard[counter].suit) {
                return 0;
            }
        }

        return 1;
    }

    // check for straight
    public int straight(Card[] testcard) {
        if (testcard[4].rank == 14) {
            for (int counter2 = 2; counter2 < 5; counter2++) {
                if (testcard[counter2 - 1].rank != (testcard[counter2].rank - 1)) {
                    return 0;
                }
            }

            if (testcard[0].rank == 2 || testcard[0].rank == 10) {
                return 1;
            } else {
                return 0;
            }
        }

        for (int counter2 = 1; counter2 < 5; counter2++) {
            if (testcard[counter2 - 1].rank != (testcard[counter2].rank - 1)) {
                return 0;
            }

        }
        return 1;
    }

    // checks for triple
    public int triple(Card[] testcard) {
        int comparison = 1;
        int x = testcard[0].rank;
        int i = 0;
        for (int counter = 1; counter < 5; counter++) {
            if (testcard[i].rank == testcard[counter].rank) {
                comparison++;
            } else {
                i++;
            }
        }
        if (comparison == 3) {
            return 1;
        } else {
            return 0;
        }
    }

    // checks for two pairs
    public int twoPairs(Card[] testcard) {
        int check = 0;
        for (int counter = 1; counter < 5; counter++) {
            if (testcard[counter - 1].rank == testcard[counter].rank) {
                check++;
            }
        }
        if (check == 2) {
            return 1;
        } else {
            return 0;
        }
    }

    // check for pair
    public int pair(Card[] testcard) {
        int check = 0;
        for (int counter = 1; counter < 5; counter++) {
            if (testcard[counter - 1].rank == testcard[counter].rank) {
                check++;
            }
        }
        if (check == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    // find highest card
    public int highCard(Card[] testcard) {
        int highCard = 0;
        for (int counter = 0; counter < 5; counter++) {
            if (testcard[counter].rank > highCard) {
                highCard = testcard[counter].rank;
            }
        }
        return highCard;
    }

    @Override
    public void run() {
        System.out.println("Working here");
        while (true) {
            try {
                msg = (CommunicationMsg) ois.readObject();
                //String anything=(String) ois.readObject();
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Read something");
            //System.exit(0);
            System.out.println(msg.type);

            if (msg.type == 11) {
                System.out.println("11 type msg");
                prebet = 0;
                finalmsg = new CommunicationMsg();
                winningcards = new Card[5];
                sortedcard = new Card[7];
                playerID = msg.playerID;
                System.out.println("You Player ID is : " + playerID);
                hand = new Card[2];
                hand[0] = msg.hand[0];
                hand[1] = msg.hand[1];
                river = new Card[5];
                river[0] = msg.river[0];
                river[1] = msg.river[1];
                river[2] = msg.river[2];
                river[3] = msg.river[3];
                river[4] = msg.river[4];
                sortedcard[0] = msg.SortedCard[0];
                sortedcard[1] = msg.SortedCard[1];
                sortedcard[2] = msg.SortedCard[2];
                sortedcard[3] = msg.SortedCard[3];
                sortedcard[4] = msg.SortedCard[4];
                sortedcard[5] = msg.SortedCard[5];
                sortedcard[6] = msg.SortedCard[6];
                System.out.println("Cards in hand :");
                this.display(hand[0]);
                this.display(hand[1]);

                finalmsg = this.WinningHand(sortedcard);
                winningcards = finalmsg.winningcards;
                handname = finalmsg.handname;
                rank = finalmsg.rank;
                System.out.println("River Cards");
                this.display(river[0]);
                this.display(river[1]);
                this.display(river[2]);
                this.display(river[3]);
                this.display(river[4]);
                System.out.println("Sorted Cards");
                this.display(sortedcard[0]);
                this.display(sortedcard[1]);
                this.display(sortedcard[2]);
                this.display(sortedcard[3]);
                this.display(sortedcard[4]);
                this.display(sortedcard[5]);
                this.display(sortedcard[6]);
                System.out.println(finalmsg.handname);
                System.out.println(finalmsg.rank);
                type = 11;
//                Platform.runLater(() -> {
//                    PokerTable.ok = 1;
//                  //  PokerTable.playScene();
//                    System.out.println("called");
//                });
            }

            if (msg.type == 1) {
                this.blindmsg(msg.Bet);
                this.balancemsg();
            }
            if (msg.type == 2) {
                /*msg choice
                1. Match/Check
                2. Raise
                3. Fold
                 */
                System.out.println("Type 2 ----->");
                System.out.println(prebet);
                System.out.println("received msg bet = " + msg.Bet);
                if (prebet != msg.Bet) {
                    System.out.println("EnterChoice :\n1.Match\n2.Raise\n3.Fold");
                    //msg.choice = in.nextInt();
                    if (msg.choice == 1) {
                        balance = balance - (msg.Bet - prebet);
                        msg.pot = msg.pot + msg.Bet - prebet;
                        prebet = msg.Bet;
                        msg.message = "Opponent Matched.";
                        CommunicationMsg temp = MakeNewMsg(msg);
                        System.out.println("******In choice 1");
                        try {
                            oos.writeObject(temp);
                        } catch (IOException ex) {
                            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.balancemsg();
                    } else if (msg.choice == 2) {
                        //System.out.println("Enter Amount :");
                        //msg.Bet = in.nextInt();
                        msg.pot = msg.pot + msg.Bet;
                        balance = balance - msg.Bet;
                        prebet = msg.Bet;
                        msg.balance = balance;
                        msg.bidderID = playerID;
                        System.out.println("******In choice 2");
                        msg.message = "Opponent raised bet amount to " + msg.Bet + "$";
                        System.out.println(msg.message);
                        CommunicationMsg temp = MakeNewMsg(msg);
                        System.out.println(temp.message);
                        try {
                            oos.writeObject(temp);
                        } catch (IOException ex) {
                            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.balancemsg();
                    } else if (msg.choice == 3) {
                        msg.message = "Opponent Folded. ";
                        msg.foldID = playerID;
                        CommunicationMsg temp = MakeNewMsg(msg);
                        try {
                            oos.writeObject(temp);
                        } catch (IOException ex) {
                            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.balancemsg();
                    }
                } else {
                    System.out.println("EnterChoice :1.Check\n2.Raise\n3.Fold");
                    msg.choice = in.nextInt();
                    if (msg.choice == 1) {
                        msg.message = "Opponent Checked.";
                        CommunicationMsg temp = MakeNewMsg(msg);
                        System.out.println("******In choice 1");
                        try {
                            oos.writeObject(temp);
                        } catch (IOException ex) {
                            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.balancemsg();
                    } else if (msg.choice == 2) {
                        System.out.println("Enter Amount :");
                        msg.Bet = in.nextInt();
                        msg.pot = msg.pot + msg.Bet;
                        balance = balance - msg.Bet;
                        prebet = msg.Bet;
                        msg.balance = balance;
                        msg.bidderID = playerID;
                        System.out.println("******In choice 2");
                        msg.message = "Opponent raised bet amount to " + msg.Bet + "$";
                        CommunicationMsg temp = MakeNewMsg(msg);
                        try {
                            oos.writeObject(temp);
                        } catch (IOException ex) {
                            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.balancemsg();
                    } else if (msg.choice == 3) {
                        msg.message = "Opponent Folded. ";
                        msg.foldID = playerID;
                        CommunicationMsg temp = MakeNewMsg(msg);
                        try {
                            oos.writeObject(temp);
                        } catch (IOException ex) {
                            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.balancemsg();
                    }

                }
                //System.out.println("choice");
//                Platform.runLater(() -> {
//                    PokerTable.ok = 2;
//                    System.out.println("ok 2");
//                    PokerTable.choice();
//                });
            }

            if (msg.type == 4) {
                System.out.println("ID : " + playerID);
                this.display(river[0]);
                this.display(river[1]);
                this.display(river[2]);
            }
            if (msg.type == 5) {
                System.out.println("ID : " + playerID);
                this.display(river[3]);
            }
            if (msg.type == 6) {
                System.out.println("ID : " + playerID);
                this.display(river[4]);
            }

            if (msg.type == 7) {
                System.out.println(msg.message);
                System.out.println("Winning Cards :");
                this.display(msg.winningcards[0]);
                this.display(msg.winningcards[1]);
                this.display(msg.winningcards[2]);
                this.display(msg.winningcards[3]);
                this.display(msg.winningcards[4]);
                if (msg.winnerID == playerID) {
                    balance = msg.pot;
                    System.out.println("After winning balance is : " + balance + "$");
                }
            }

            if (msg.type == 8) {
                this.updatefinalmsg();
                CommunicationMsg temp = MakeNewMsg(finalmsg);
                try {
                    oos.writeObject(temp);
                } catch (IOException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (msg.type == 10) {
                System.out.println(msg.message);
                if (msg.foldID != playerID) {
                    balance = balance + msg.pot;
                }
            }
        }
    }

}
