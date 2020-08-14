/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.Serializable;

/**
 *
 * @author Soumit
 */
public class Card implements Comparable<Card>,Serializable {
    // I.V.s are suit and rank
    public int suit;
    public int rank;

    @Override
    public int compareTo(Card o) {
        if (this.rank == (o.rank))
            return 0;
        else if ((this.rank) > (o.rank))
            return 1;
        else
            return -1;
    }
}