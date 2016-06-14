package Old;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artmmslv on 14.06.16.
 */
public class RummicubeSolver {

    //♠ ♣ ♦ ♥
    //A K 2 Q 3 J 4 0 5 9 6 8 7
    //A 2 3 4 5 6 7 8 9 0 J Q K

    public static List<Possibility> list = new ArrayList<>();
    public final static String[] orderOfChecking =
            {       //this list is final
                    //Designed to cut as many possibilities as possible faster
                    //by method isAnyHope
                    "A♠", "A♣", "A♦", "A♥",
                    "K♠", "K♣", "K♦", "K♥",
                    "2♠", "2♣", "2♦", "2♥",
                    "Q♠", "Q♣", "Q♦", "Q♥",
                    "3♠", "3♣", "3♦", "3♥",
                    "J♠", "J♣", "J♦", "J♥",
                    "4♠", "4♣", "4♦", "4♥",
                    "0♠", "0♣", "0♦", "0♥",
                    "5♠", "5♣", "5♦", "5♥",
                    "9♠", "9♣", "9♦", "9♥",
                    "6♠", "6♣", "6♦", "6♥",
                    "8♠", "8♣", "8♦", "8♥",
                    "7♠", "7♣", "7♦", "7♥"
            };
    //reversing method
    //public static int[] valueOfCardByIndexFromOrderOfCheckingDividedByFour =  { 0, 12, 1, 11, 2, 10, 3, 9, 4, 8, 5, 7, 6 };
    public static int[] valueOfCardByIndexFromOrderOfCheckingDividedByFour =    { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

    int[] whereCardsAre = new int[104];
    protected int go(){
        list.clear();
        Possibility first = new Possibility(whereCardsAre);
        tryToPut(first);
        System.out.println();
        System.out.println("Results: ");
        int MaxLength = 0;
        int n = 0;
        for (int i = 0; i < list.size(); i++) {
            int tmp = 0;
            for (int j = 0; j < 104; j++) {
                if(whereCardsAre[j] == Possibility.inHand &&
                        list.get(i).isPlaced(j) ){
                    tmp+=1;
                }
            }
            if(tmp>MaxLength){
                MaxLength = tmp;
                n = i;}
        }
        return n;
    }
    protected void tryToPut(Possibility first){
        //looking for fit card we have to put somewhere
        int id = first.getFirstUnusedCardId();
        if(id==-1)//means all cards are used
        {
            if(first.fullyNoProblems())
                list.add(first);
            return;
        }
        //card #id and some other are not still used
        //todo:kostyl
        for (int where = Possibility.byColor1; where <= Possibility.byValue2; where++) {
            //create a copy of possibility for further search
            Possibility second = new Possibility(first, id, where);
            if(second.isAnyHope(id,where)){
                //recursive tree with up to 4 branches
                tryToPut(second);
            }
        }
        //We tried to put card id to all possible places
        //now we will skip it when calculating/*
        if(whereCardsAre[id] == Possibility.inHeap)return;
        Possibility second = new Possibility(first,id,Possibility.inHand_ignored);
        tryToPut(second);
    }
    public void updateList(int[] newData){
        for (int i = 0; i < 104; i++) {
            int condition = newData[i];
            if(condition == Possibility.byColor1||
                    condition == Possibility.byColor2||
                    condition == Possibility.byValue1||
                    condition == Possibility.byValue2||
                    condition == Possibility.inHeap)
                whereCardsAre[i] = Possibility.inHeap;
            else
            if(condition == Possibility.inHand||
                    condition == Possibility.inHand_ignored)
                whereCardsAre[i] = Possibility.inHand;
            if(condition == Possibility.inStack||
                    condition == Possibility.otherPlayer){}


        }
    }
}