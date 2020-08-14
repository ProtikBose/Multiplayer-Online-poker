/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Soumit
 */
public class Dealer {

    ServerSocket servsock;
    int DealNo = 1;
    int PlayerCounter = 0, CheckPersonNo;
    Socket sock1, sock2;
    ArrayList InGamePlayers = new ArrayList(10);
    ArrayList InDealPlayers = new ArrayList(10);
    Deck deck1;
    ObjectInputStream ois1, ois2;
    ObjectOutputStream oos1, oos2;
    Card[] Player1Hand, Player2Hand, pl1besthand, pl2besthand;
    int balance1 = 1000, balance2 = 1000;
    CommunicationMsg msg1, msg2, finalmsg1, finalmsg2;
    Card[] river;
    boolean folded = false;
    static int GameNo = 0;
    int TurnNo = 0, bet1, bet2, blindamount, pot, betamount, HighestBidderID;

    public Dealer() {
        try {

            servsock = new ServerSocket(12345);
            sock1 = servsock.accept();
            PlayerCounter++;
            oos1 = new ObjectOutputStream(sock1.getOutputStream());
            ois1 = new ObjectInputStream(sock1.getInputStream());
            sock2 = servsock.accept();
            PlayerCounter++;
            oos2 = new ObjectOutputStream(sock2.getOutputStream());
            ois2 = new ObjectInputStream(sock2.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Dealer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Dealer D = new Dealer();
        while (true) {
            GameNo = (GameNo + 1) % 2;
            D.Deal();
            try {
                D.play();
            } catch (IOException ex) {
                Logger.getLogger(Dealer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Dealer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void play() throws IOException, ClassNotFoundException {
        CommunicationMsg temp = msg1;
        folded = false;
        if (!folded) {
            DealNo = GameNo % 2;
            if (DealNo == 0) {
                DealNo = 2;
            }
            //CommunicationMsg temp = msg1;
            this.blind(DealNo);
            this.bet();
        }
        if (!folded) {
            this.ShowYourCard();
            CheckPersonNo = 0;
            this.bet();
        }
        if (!folded) {
            this.ShowYourCard2();
            CheckPersonNo = 0;
            this.bet();
        }

        if (!folded) {
            this.ShowYourCard3();
            CheckPersonNo = 0;
            this.bet();
        }

        if (!folded) {
            CheckPersonNo = 0;
            //this.showWinner();
            msg1.type = 8;
            msg2.type = 8;
            CommunicationMsg temp1 = MakeNewMsg(msg1);
            CommunicationMsg temp2 = MakeNewMsg(msg2);
            oos1.writeObject(temp1);
            finalmsg1 = (CommunicationMsg) ois1.readObject();
            oos2.writeObject(temp2);
            finalmsg2 = (CommunicationMsg) ois2.readObject();
            System.out.println("In pot       " + pot);
            //CommunicationMsg winset1, winset2;
            //winset1 = msg1;
            //winset2 = msg2;
//            System.out.println("******");
//
//            this.display(finalmsg1.SortedCard[0]);
//            this.display(finalmsg1.SortedCard[1]);
//            this.display(finalmsg1.SortedCard[2]);
//            this.display(finalmsg1.SortedCard[3]);
//            this.display(finalmsg1.SortedCard[4]);
//            this.display(finalmsg1.SortedCard[5]);
//            this.display(finalmsg1.SortedCard[6]);
//            System.out.println("Player 1 Winning Card");
//            this.display(finalmsg1.winningcards[0]);
//            this.display(finalmsg1.winningcards[1]);
//            this.display(finalmsg1.winningcards[2]);
//            this.display(finalmsg1.winningcards[3]);
//            this.display(finalmsg1.winningcards[4]);
//            System.out.println(finalmsg1.rank);
//
//            System.out.println("******");
//
//            this.display(finalmsg2.SortedCard[0]);
//            this.display(finalmsg2.SortedCard[1]);
//            this.display(finalmsg2.SortedCard[2]);
//            this.display(finalmsg2.SortedCard[3]);
//            this.display(finalmsg2.SortedCard[4]);
//            this.display(finalmsg2.SortedCard[5]);
//            this.display(finalmsg2.SortedCard[6]);
//            System.out.println(finalmsg2.rank);
//            System.out.println("Player 2 Winning Card");
//            this.display(finalmsg2.winningcards[0]);
//            this.display(finalmsg2.winningcards[1]);
//            this.display(finalmsg2.winningcards[2]);
//            this.display(finalmsg2.winningcards[3]);
//            this.display(finalmsg2.winningcards[4]);
//            System.out.println(finalmsg2.rank);

//            msg1 = this.WinningHand(msg1.SortedCard);
//            msg2 = this.WinningHand(msg2.SortedCard);
//            msg1.handname = winset1.handname;
//            msg2.handname = winset2.handname;
//            msg1.rank = winset1.rank;
//            msg2.rank = winset2.rank;
//            msg1.winningcards = winset1.winningcards;
//            msg2.winningcards = winset2.winningcards;
            if (finalmsg1.rank > finalmsg2.rank) {
                finalmsg2.winningcards = finalmsg1.winningcards;
                finalmsg1.winnerID = 1;
                finalmsg2.winnerID = 1;
                finalmsg1.pot = pot;
                finalmsg1.message = "Player 1 Wins Getting " + finalmsg1.handname;
                finalmsg2.message = "Player 1 Wins Getting " + finalmsg1.handname;
                this.showWinner();
            } else if (finalmsg1.rank < finalmsg2.rank) {
                finalmsg1.winningcards = finalmsg2.winningcards;
                finalmsg1.winnerID = 2;
                finalmsg2.winnerID = 2;
                finalmsg2.pot = pot;
                finalmsg2.message = "Player 2 Wins Getting " + finalmsg2.handname;
                finalmsg1.message = "Player 2 Wins Getting " + finalmsg2.handname;
                this.showWinner();
            } else {
                int i = EvaluatefromSameRank(finalmsg1.winningcards, finalmsg2.winningcards, finalmsg1.rank);
                if (i == 1) {
                    finalmsg2.winningcards = finalmsg1.winningcards;
                    finalmsg1.winnerID = 1;
                    finalmsg2.winnerID = 1;
                    finalmsg1.pot = pot;
                    finalmsg1.message = "Player 1 Wins Getting " + finalmsg1.handname;
                    finalmsg2.message = "Player 1 Wins Getting " + finalmsg1.handname;
                    this.showWinner();
                }
                if (i == 2) {
                    finalmsg1.winningcards = finalmsg2.winningcards;
                    finalmsg1.winnerID = 2;
                    finalmsg2.winnerID = 2;
                    finalmsg2.pot = pot;
                    finalmsg2.message = "Player 2 Wins Getting " + finalmsg2.handname;
                    finalmsg1.message = "Player 2 Wins Getting " + finalmsg2.handname;
                    this.showWinner();
                }

                if (i == 3) {
                    finalmsg2.winningcards = finalmsg1.winningcards;
                    finalmsg1.message = "Draw";
                    this.showWinner();
                }
            }
        }
    }

    public int EvaluatefromSameRank(Card[] a1, Card[] a2, int r) {
        if (r == 22 || r == 18 || r == 19) {
            if (a1[4].rank > a2[4].rank) {
                return 1;
            } else if (a2[4].rank > a1[4].rank) {
                return 2;
            } else {
                return 0;
            }
        } else if (r == 17) {
            int p1power, p1kicker;
            int p2power, p2kicker;
            if (a1[0] == a1[1] && a1[1] == a1[2]) {
                p1power = a1[1].rank;
                p1kicker = a1[3].rank;
            } else {
                p1power = a1[2].rank;
                p1kicker = a1[0].rank;
            }

            if (a2[0] == a2[1] && a2[1] == a2[2]) {
                p2power = a2[1].rank;
                p2kicker = a2[3].rank;
            } else {
                p2power = a2[2].rank;
                p2kicker = a2[0].rank;
            }

            if (p1power > p2power) {
                return 1;
            } else if (p1power < p2power) {
                return 2;
            } else {
                if (p1kicker > p2kicker) {
                    return 1;
                } else if (p1kicker < p2kicker) {
                    return 2;
                } else {
                    return 0;
                }
            }
        } else if (r == 16) {
            int check = 0;
            int p1power1 = 0, p1kicker = 0, p1power2 = 0;
            int p2power1 = 0, p2kicker = 0, p2power2 = 0;
            for (int counter = 1; counter < 5; counter++) {
                if (a1[counter - 1].rank == a1[counter].rank) {
                    check++;
                }
                if (check == 1) {
                    p1power2 = a1[counter].rank;
                }
                if (check == 2) {
                    p1power1 = a1[counter].rank;
                }
            }
            p1kicker = a1[0].rank + a1[1].rank + a1[2].rank + a1[3].rank + a1[4].rank - (p1power1 * 2 + p1power2 * 2);
            for (int counter = 1; counter < 5; counter++) {
                if (a2[counter - 1].rank == a2[counter].rank) {
                    check++;
                }
                if (check == 1) {
                    p2power2 = a2[counter].rank;
                }
                if (check == 2) {
                    p2power1 = a2[counter].rank;
                }
            }
            p2kicker = a2[0].rank + a2[1].rank + a2[2].rank + a2[3].rank + a2[4].rank - (p2power1 * 2 + p2power2 * 2);

            if (p1power1 > p2power1) {
                return 1;
            } else if (p1power1 < p2power1) {
                return 2;
            } else {
                if (p1power2 > p2power2) {
                    return 1;
                } else if (p1power1 < p2power1) {
                    return 2;
                } else {
                    if (p1kicker > p2kicker) {
                        return 1;
                    } else if (p1kicker < p2kicker) {
                        return 2;
                    } else {
                        return 0;
                    }
                }
            }
        } else if (r == 15) {
            int check = 0, x = 0, y = 0, i, j, k;
            int p1power = 0, p2power = 0, p1kicker1 = 0, p2kicker1 = 0, p1kicker2 = 0, p2kicker2 = 0, p1kicker3 = 0, p2kicker3 = 0;
            for (int counter = 1; counter < 5; counter++) {
                if (a1[counter - 1].rank == a1[counter].rank) {
                    check++;
                    x = counter;
                    y = counter - 1;
                }
                if (check == 1) {
                    p1power = a1[counter].rank;
                }
            }
            for (i = 0; i < 5; i++) {
                if (i != x && i != y) {
                    p1kicker3 = a1[i].rank;
                    break;
                }
            }
            for (j = i + 1; j < 5; j++) {
                if (j != x && j != y) {
                    p1kicker2 = a1[i].rank;
                    break;
                }
            }
            for (k = j + 1; k < 5; k++) {
                if (k != x && k != y) {
                    p1kicker1 = a1[i].rank;
                    break;
                }
            }

            for (int counter = 1; counter < 5; counter++) {
                if (a2[counter - 1].rank == a2[counter].rank) {
                    check++;
                    x = counter;
                    y = counter - 1;
                }
                if (check == 1) {
                    p2power = a2[counter].rank;
                }
            }
            for (i = 0; i < 5; i++) {
                if (i != x && i != y) {
                    p2kicker3 = a2[i].rank;
                    break;
                }
            }
            for (j = i + 1; j < 5; j++) {
                if (j != x && j != y) {
                    p2kicker2 = a2[i].rank;
                    break;
                }
            }
            for (k = j + 1; k < 5; k++) {
                if (k != x && k != y) {
                    p1kicker1 = a2[i].rank;
                    break;
                }
            }

            if (p1power > p2power) {
                return 1;
            } else if (p1power < p1power) {
                return 2;
            } else {
                if (p1kicker1 > p2kicker1) {
                    return 1;
                } else if (p1kicker1 < p2kicker1) {
                    return 2;
                } else {
                    if (p1kicker2 > p2kicker2) {
                        return 1;
                    } else if (p1kicker2 < p2kicker2) {
                        return 2;
                    } else {
                        if (p1kicker3 > p2kicker3) {
                            return 1;
                        } else if (p1kicker3 < p2kicker3) {
                            return 2;
                        } else {
                            return 0;
                        }
                    }
                }
            }

        } else {
            for (int i = 3; i >= 0; i--) {
                if (a1[i].rank > a2[i].rank) {
                    return 1;
                }
                if (a1[i].rank < a2[i].rank) {
                    return 2;
                }
            }
            return 0;
        }
    }

    public void foldedWinner(int i) throws IOException {
        msg1.foldID = i;
        msg2.foldID = i;
        msg1.type = 10;
        msg2.type = 10;
        msg1.pot = pot;
        msg2.pot = pot;
        if (i == 1) {
            msg1.message = "Player 1 folded. Player 2 wins";
            msg2.message = "Player 1 folded. Player 2 wins";
        }
        if (i == 2) {
            msg1.message = "Player 2 folded. Player 1 wins";
            msg2.message = "Player 2 folded. Player 1 wins";
        }
        CommunicationMsg temp = MakeNewMsg(msg1);
        oos1.writeObject(temp);
        oos2.writeObject(temp);
    }

    public void bet() throws IOException, ClassNotFoundException {
        while (CheckPersonNo < 2 && !folded) {
            switch (DealNo % 2) {
                case 1:
                    CommunicationMsg temp = ReqForBetOrMatchOrFold(2);
                    if (temp.choice == 1) { //match
                        System.out.println("Player " + temp.playerID + " matched");
                        pot = pot + temp.Bet;
                        msg1.Bet = temp.Bet;
                        temp.pot = pot;
                        balance2 = temp.balance;
                        msg2 = MakeNewMsg(temp);
                        DealNo++;
                        CheckPersonNo++;
                    } else if (temp.choice == 2) {
                        System.out.println("Player " + temp.playerID + " raised bet to " + temp.Bet + "$");
                        HighestBidderID = temp.playerID;
                        temp.bidderID = HighestBidderID;
                        pot = pot + temp.Bet;
                        msg1.Bet = temp.Bet;
                        temp.pot = pot;
                        msg2 = MakeNewMsg(temp);
                        DealNo++;
                        CheckPersonNo = 1;
                    } else if (temp.choice == 3) {
                        this.foldedWinner(temp.playerID);
                        DealNo++;
                        CheckPersonNo = 0;
                        folded = true;
                        continue;
                    }
                    break;
                case 0:
                    temp = ReqForBetOrMatchOrFold(1);
                    if (temp.choice == 1) {
                        System.out.println("Player " + temp.playerID + " matched");
                        pot = pot + temp.Bet;
                        msg2.Bet = temp.Bet;
                        temp.pot = pot;
                        balance1 = temp.balance;
                        msg1 = MakeNewMsg(temp);
                        DealNo++;
                        CheckPersonNo++;
                    } else if (temp.choice == 2) {
                        System.out.println("Player " + temp.playerID + " raised bet to " + temp.Bet + "$");
                        HighestBidderID = temp.playerID;
                        temp.bidderID = HighestBidderID;
                        pot = pot + temp.Bet;
                        msg2.Bet = temp.Bet;
                        temp.pot = pot;
                        msg1 = MakeNewMsg(temp);
                        DealNo++;
                        CheckPersonNo = 1;
                    } else if (temp.choice == 3) {
                        this.foldedWinner(temp.playerID);
                        DealNo++;
                        CheckPersonNo = 0;
                        folded = true;
                        continue;
                    }
                    break;
            }
        }
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

    public void ShowYourCard() throws IOException {
        CommunicationMsg temp = null;
        msg1.type = 4;
        msg2.type = 4;
        temp = MakeNewMsg(msg1);
        oos1.writeObject(temp);
        temp = MakeNewMsg(msg1);
        oos2.writeObject(temp);
    }

    public CommunicationMsg ReqForBetOrMatchOrFold(int i) throws IOException, ClassNotFoundException {
        CommunicationMsg temp = null;
        //int i = (int) InGamePlayers.get(TurnNo % InGamePlayers.size());
        switch (i) {
            case 1:
                msg1.type = 2;
                msg1.Bet = msg2.Bet;
                //msg1.message = msg2.message;
                temp = MakeNewMsg(msg1);
                oos1.writeObject(temp);
                temp = (CommunicationMsg) ois1.readObject();
                System.out.println(temp.Bet);
                break;
            case 2:
                msg2.type = 2;
                msg2.Bet = msg1.Bet;
                //msg2.message = msg1.message;
                temp = MakeNewMsg(msg2);
                oos2.writeObject(temp);
                temp = (CommunicationMsg) ois2.readObject();
                //System.out.println(temp.Bet);
                break;
        }
        return temp;
    }

    public void blind(int i) throws IOException {
        CommunicationMsg temp;
        switch (i) {
            case 1:
                HighestBidderID = 1;
                msg1.type = 1;
                msg1.Bet = blindamount;
                msg1.balance = msg1.balance - blindamount;
                balance1 = msg1.balance;
                pot = blindamount;
                msg1.pot = pot;
                msg1.bidderID = HighestBidderID;
                //System.out.println("Type changed from 0 to 1");
                temp = MakeNewMsg(msg1);
                oos1.writeObject(temp);
                break;
            case 2:
                HighestBidderID = 2;
                msg2.type = 1;
                msg2.Bet = blindamount;
                msg2.balance = msg2.balance - blindamount;
                balance2 = msg2.balance;
                pot = blindamount;
                msg1.bidderID = HighestBidderID;
                msg2.pot = pot;
                temp = MakeNewMsg(msg2);
                oos2.writeObject(temp);
                break;
        }
        TurnNo++;
    }

    public CommunicationMsg MakeNewMsg(CommunicationMsg x) {
        CommunicationMsg temp = new CommunicationMsg();
        temp.Bet = x.Bet;
        temp.foldID = x.foldID;
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

    public void Deal() {
        deck1 = new Deck();
        deck1.fillDeck();
        deck1.shuffle();
        blindamount = 50;
        Player1Hand = new Card[2];
        Player2Hand = new Card[2];
        Player1Hand = deck1.deal(2);
        Player2Hand = deck1.deal(2);
        pl1besthand = new Card[5];
        pl1besthand = new Card[5];
        msg1 = new CommunicationMsg();
        msg2 = new CommunicationMsg();
        msg1.hand = Player1Hand;
        msg1.type = 11;
        msg1.playerID = 1;
        msg2.hand = Player2Hand;
        msg2.type = 11;
        msg2.playerID = 2;
        river = new Card[5];
        river = deck1.deal(5);
        msg1.river = river;
        msg2.river = river;
        Card[] temp1 = new Card[7];
        Card[] temp2 = new Card[7];
        System.arraycopy(msg1.hand, 0, temp1, 0, 2);
        System.arraycopy(msg1.river, 0, temp1, 2, 5);
        Arrays.sort(temp1);
        msg1.SortedCard = temp1;
        System.arraycopy(msg2.hand, 0, temp2, 0, 2);
        System.arraycopy(msg2.river, 0, temp2, 2, 5);
        Arrays.sort(temp2);
        msg2.SortedCard = temp2;

        System.out.println(msg1.type);

        //System.out.println("OK");
        try {
            System.out.println("type 0 going to 1");
            oos1.writeObject(msg1);
            //oos1.writeObject("something");
            System.out.println("type 0 going to 2");
            oos2.writeObject(msg2);
            //oos1.writeObject("something2");
        } catch (IOException ex) {
            Logger.getLogger(Dealer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ShowYourCard2() throws IOException {
        CommunicationMsg temp = null;
        msg1.type = 5;
        msg2.type = 5;
        temp = MakeNewMsg(msg1);
        oos1.writeObject(temp);
        temp = MakeNewMsg(msg1);
        oos2.writeObject(temp);
    }

    public void ShowYourCard3() throws IOException {
        CommunicationMsg temp = null;
        msg1.type = 6;
        msg2.type = 6;
        temp = MakeNewMsg(msg1);
        oos1.writeObject(temp);
        temp = MakeNewMsg(msg1);
        oos2.writeObject(temp);
    }

    public void showWinner() throws IOException {
        CommunicationMsg temp = null;
        finalmsg1.type = 7;
        finalmsg2.type = 7;
        finalmsg1.pot = pot;
        finalmsg2.pot = pot;
        temp = MakeNewMsg(finalmsg1);
        oos1.writeObject(temp);
        temp = MakeNewMsg(finalmsg2);
        oos2.writeObject(temp);
    }
}
