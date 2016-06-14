import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by artmmslv on 05.06.16.
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

    int[] whereCardsAre = new int[104];

    protected void go(){
        list.clear();
        Possibility first = new Possibility(whereCardsAre);
        tryToPut(first);
    }
    public int longestResult(){
        int MaxLength = 0;
        int n = 0;
        for (int i = 0; i < list.size(); i++) {
            int tmp = 0;
            for (int j = 0; j < 104; j++) {
                if(whereCardsAre[j] == Card.inHand &&
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
    protected boolean tryToPut(Possibility first){
        //looking for fit card we have to put somewhere
        int id = first.getFirstUnusedCardId();
        if(id==-1)//means all cards are used

        {
            if(first.fullyNoProblems())
                list.add(first);
            return true;
        }
        //card #id and some other are not still used
        //todo:kostyl
        boolean success = false;
        for (int where = Possibility.byColor1; where <= Possibility.byColor2; where++) {
            //create a copy of possibility for further search
            Possibility second = new Possibility(first, id, where);
            if(second.isAnyHope(id,where)){
                //recursive tree with up to 4 branches
                boolean branchSucceeded = tryToPut(second);
                if(branchSucceeded) success = true;
            }
        }
        boolean thirdSucceed;
        {
            Possibility second = new Possibility(first, id, Possibility.byValue2);
            if (second.isAnyHope(id, Possibility.byValue2)) {
                //recursive tree with up to 4 branches
                thirdSucceed = tryToPut(second);
                if (thirdSucceed) success = true;
            }
        }
            Possibility second = new Possibility(first, id, Possibility.byValue2);
            if (second.isAnyHope(id, Possibility.byValue2)) {
                //recursive tree with up to 4 branches
                boolean branchSucceeded = tryToPut(second);
                if (branchSucceeded) success = true;
            }


        //We tried to put card id to all possible places
        //now we will skip it when calculating/*
        if(whereCardsAre[id] == Possibility.inHeap)return success;
       // Possibility second = new Possibility(first,id,Possibility.inHand_ignored);
        boolean branchSucceeded = tryToPut(second);
        if(branchSucceeded) success = true;
        return success;
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