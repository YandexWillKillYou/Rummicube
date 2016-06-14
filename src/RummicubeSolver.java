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
                        Card.isPlaced(list.get(i).whereCardsAre[j]) ){
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
        for (int where = Card.byColor1; where <= Card.byColor2; where++) {
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
            Possibility second = new Possibility(first, id, Card.byValue2);
            if (second.isAnyHope(id, Card.byValue2)) {
                //recursive tree with up to 4 branches
                thirdSucceed = tryToPut(second);
                if (thirdSucceed) success = true;
            }
        }
            Possibility second = new Possibility(first, id, Card.byValue2);
            if (second.isAnyHope(id, Card.byValue2)) {
                //recursive tree with up to 4 branches
                boolean branchSucceeded = tryToPut(second);
                if (branchSucceeded) success = true;
            }


        //We tried to put card id to all possible places
        //now we will skip it when calculating/*
        if(whereCardsAre[id] == Card.inHeap)return success;
       // Possibility second = new Possibility(first,id,Possibility.inHand_ignored);
        boolean branchSucceeded = tryToPut(second);
        if(branchSucceeded) success = true;
        return success;
    }
    public void updateList(int[] newData){
        for (int i = 0; i < 104; i++) {
            int condition = newData[i];
            if(condition == Card.byColor1||
                    condition == Card.byColor2||
                    condition == Card.byValue1||
                    condition == Card.byValue2||
                    condition == Card.inHeap)
                whereCardsAre[i] = Card.inHeap;
            else
            if(condition == Card.inHand||
                    condition == Card.inHand_ignored)
                whereCardsAre[i] = Card.inHand;
            if(condition == Card.inStack||
                    condition == Card.otherPlayer){}
        }
    }
}