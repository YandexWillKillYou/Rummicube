public class Card {
    //if a card is in stack it is no way to put it on table
    static final int inStack  = 0;
    static final int otherPlayer = 10;
    //these cards can be put on table
    static final int inHand   = 7;
    static final int inHand_ignored = 8;

    //these cards must be put on table in any way
    //they were put on table before and we are trying to find a place for them
    static final int inHeap   = 9;

    //these cards are already put on table somewhere
    //however this doesnt mean they all are in lines
    static final int byColor1 = 1;
    static final int byColor2 = 2;
    static final int byValue1 = 3;
    static final int byValue2 = 4;



    static int valueFromIndex(int index){
        return index/8;
    }
    static int colorFromIndex(int index){
        return index%8/2;
    }
    static int lesserIndexFromValueAndColor(int value, int color){
        return 8*value+2*color;
    }
    public int indexOfCopy(int index){
        if(index % 2 == 0)
            return index+1;
        return index - 1;
    }
    static String nameFromIndex(int index){
        int color = colorFromIndex(index);
        int value = valueFromIndex(index);
        return nameFromValueAndColor(value, color);
    }
    static String nameFromValueAndColor(int value, int color){
        String s;
        switch (value){
            case 0: {s = "A";
                break;}
            case 9: {s = "0";
                break;}
            case 10: {s = "J";
                break;}
            case 11: {s = "Q";
                break;}
            case 12: {s = "K";
                break;}
            default:s = Integer.toString(value+1);
        }
        switch (color){
            case 0:{s = s + "♠";
                break;}
            case 1:{s = s + "♣";
                break;}
            case 2:{s = s + "♦";
                break;}
            case 3:{s = s + "♥";
                break;}
        }
        return s;
    }
    static boolean isAvailable(int condition){
        if(condition == inHand||
                condition == inHeap)
            return true;
        else return false;
    }
    static boolean isPlaced(int condition){
        if(condition == byColor1||
                condition == byColor2||
                condition == byValue1||
                condition == byValue2)
            return true;
        else return false;
    }
}
