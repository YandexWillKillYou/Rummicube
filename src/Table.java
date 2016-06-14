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
    int[][] tableLists = new int[8][13];
    //8 arrays are lists of cards ane by one
    //[0]:      A♠ 2♠ 3♠ 4♠ 5♠ 6♠ 7♠ 8♠ 9♠ 0♠ J♠ Q♠ K♠
    //[1]:      A♣ 2♣ ...
    //[2]:      A♦ 2♦ ...
    //          ....
    //[7]:      A♥ 2♥ 3♥ 4♥ 5♥ ...
    int[][] tableQuads = new int[13][8];
    //[0]:      A♠ A♣ A♦ A♥ A♠ A♣ A♦ A♥
    //[1]:      2♠ 2♣ 2♦ 2♥ 2♠ 2♣ 2♦ 2♥
    //...       ...
    //[13]:     K♠ K♣ K♦ K♥ K♠ K♣ K♦ K♥
    public void placeCardsOnTable(int[] whereCardsAre){
        for (int i = 0; i < whereCardsAre.length/2; i++) {
            //in this method we work with 2 similar cards
            //XXX
            int value = i/4;
            int color = i%4;  // 0 1 2 3
            // ♠ ♣ ♦ ♥
            //now we have to fill the position
            //if both cards are unavailable place cant be taken
            //todo: check why this part crushes result
            /*
                tableLists[color][value] = empty;
                tableLists[color+4][value] = empty;
                tableQuads[value][color  ] = empty;
                tableQuads[value][color+4] = empty;
            */
            //if one of cards is available any space can be taken
            if(        Card.isAvailable(whereCardsAre[2 * i])
                    || Card.isAvailable(whereCardsAre[2 * i + 1])) {
                tableLists[color  ][value] = maybe;
                tableLists[color+4][value] = maybe;
                tableQuads[value][color  ] = maybe;
                tableQuads[value][color+4] = maybe;
            }

            //if one of two cards takes place in byColor1
            if(     whereCardsAre[2 * i    ] == Card.byColor1 ||
                    whereCardsAre[2 * i + 1] == Card.byColor1)
                tableLists[color][value] = taken;
            //and so on
            if(     whereCardsAre[2 * i    ] == Card.byColor2 ||
                    whereCardsAre[2 * i + 1] == Card.byColor2)
                tableLists[color+4][value] = taken;
            if(     whereCardsAre[2 * i    ] == Card.byValue1 ||
                    whereCardsAre[2 * i + 1] == Card.byValue1)
                tableQuads[value][color] = taken;
            if(     whereCardsAre[2 * i    ] == Card.byValue2 ||
                    whereCardsAre[2 * i + 1] == Card.byValue2)
                tableQuads[value][color + 4] = taken;
        }
    }
    public void printTable(){

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 13; j++) {
                if(tableLists[i][j]==taken){
                    System.out.print(RummiShell.cardname(j, i % 4)+" ");
                }
                if(tableLists[i][j]==maybe){
                    System.out.print("?? ");
                }
                if(tableLists[i][j]==empty){
                    System.out.print("-- ");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 8; j++) {
                if(tableQuads[i][j]==taken){
                    System.out.print(RummiShell.cardname(i, j % 4)+" ");
                }
                if(tableQuads[i][j]==maybe){
                    System.out.print("?? ");
                }
                if(tableQuads[i][j]==empty){
                    System.out.print("-- ");
                }
            }
            System.out.println();
        }
    }
}
