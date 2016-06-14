/**
 * Created by artmmslv on 07.06.16.
 */
public class Table {
    //this place cant be filled
    static final int empty = 0;
    //this place is taken
    static final int taken = 1;
    //this place can be filled
    static final int maybe = 2;

    //these arrays represent what places can be taken or already taken on the table
    int[][] lists = new int[8][13];
    //8 arrays are lists of cards ane by one
    //[0]:      A♠ 2♠ 3♠ 4♠ 5♠ 6♠ 7♠ 8♠ 9♠ 0♠ J♠ Q♠ K♠
    //[1]:      A♣ 2♣ ...
    //[2]:      A♦ 2♦ ...
    //          ....
    //[7]:      A♥ 2♥ 3♥ 4♥ 5♥ ...
    int[][] quads = new int[13][8];
    //[0]:      A♠ A♣ A♦ A♥ A♠ A♣ A♦ A♥
    //[1]:      2♠ 2♣ 2♦ 2♥ 2♠ 2♣ 2♦ 2♥
    //...       ...
    //[13]:     K♠ K♣ K♦ K♥ K♠ K♣ K♦ K♥


}
