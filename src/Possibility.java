/**
 * Created by artmmslv on 05.06.16.
 */
public class Possibility {
    //it is a class that represents a situation on table
    //when all cards on table are in lines
    //and stores where these cards exactly are

    //where the card is placed?
    //cards in whereCardsAre are sorted as listed in RubbiCubeSolver.orderOfChecking by pairs
    //A♠ A♠ A♣ A♣ A♦ A♦ A♥ A♥ K♠ K♠ K♣ ...
    //this list contains their position(in hand, in stack, in tableListsetc)
    int[] whereCardsAre = new int[104];
    Table table;
    //other way to store cards, helpful. it doubles other database, so maybe should be reworked
    //
    //Each card can be placed to one of 4 places: 2 places by value and 2 places by color (except Aces, TODO: JQKA)
    //Also it can be in stack or in hand or in heap
    //16 = 4 colors * 4 places







    //    Premature optimization is the root of all evil (or at least most of it) in programming.



    //in methods isAnyHope...() we are trying to find can placing cards possibly give a result or not
    //it is a method that can return true even if actual answer is false - it is not critical UNTIL
    //until there are no cards left at all
    //if there are no cards void placeCardsOnTable cannot leave "maybe" marks
    public Possibility(int[] whereCardsAre){
        for (int i = 0; i < 104; i++) {
            this.whereCardsAre[i] = whereCardsAre[i];
        }
        table = new Table();
        table.placeCardsOnTable(whereCardsAre);
    }
    public Possibility(Possibility previous, int id,  int where){
        for (int i = 0; i < 104; i++) {
            whereCardsAre[i] = previous.whereCardsAre[i];
        }
        table = new Table();
        //here we mark id as used
        //at the next iteration id will be the next available card
        whereCardsAre[id] = where;
        table.placeCardsOnTable(whereCardsAre);
    }
    public boolean isAnyHope(int cardIndex/*0-103*/, int where/*byValue1, byColor2 etc*/) {
//        System.out.println("Hope: new card " + RummiShell.cardName(cardIndex) + " at " + where);
//        printTable();
        //first of all, we check if new card is placed to the same place as its copy
        if (where == whereCardsAre[indexOfCopy(cardIndex)]) {
 //           System.out.println("noHope, index = " + cardIndex + ", where = " + where + ", two cards on the same place");
            return false;
        }
        int value = cardIndex / 8;
        int color = (cardIndex % 8) / 2;
        //here we will check tableQuads
        //in each quad (0-3) and (4-7) can be 3 or 4 marks "maybe" or "taken"
        //this means there can be a good line
        //or in the quads can be no marks "taken" - this means the line is empty
        //anyway, a new cardplace must be taken
        //otherwise, it`s impossible to finish placing cards successfully


        //todo: check is it important:
        //boolean isAnyHopeQuads is supposed to be used after adding a single element "cardIndex" to "where"
        //furthermore, it is supposed that previous configuration (without new card) was good
        //todo: check a statement:
        //todo: empty table is good by default
        if(where==Card.byValue1||where==Card.byValue2) {
            int shift = where == Card.byValue1? 0:4;
            if(isProblemWithCardInQuad(value,shift)){
                return false;
            }
        }
        else
        if(where==Card.byColor1||where==Card.byColor2) {
            int line = color;
            if(where == Card.byColor2)
                line = color + 4;
            if(/*there is a problem*/ isProblemWithCardinLine(value,line))
                return /*no hope*/false;
        }
        else throw new IllegalArgumentException();
 //       System.out.println("there is Hope, index = " + cardIndex + ", where = " + where);
        return true;



    }
    public boolean isProblemWithCardInQuad(int value, int shift){
        int maybeTakenPlaces = 0;
            for (int column = shift; column < 4+shift; column++) {
                if (table.tableQuads[value][column] == table.taken) {
                    maybeTakenPlaces++;
                }
                if (table.tableQuads[value][column] == table.maybe) {
                    maybeTakenPlaces++;
                }
            }
            if (maybeTakenPlaces ==1 || maybeTakenPlaces ==2) {
         //       System.out.println("noHope, value = " + value + ", too few cards in a quad");
                return true;
            }
        return false;
        }

    public boolean isProblemWithCardinLine(int value, int line){
        if(table.tableLists[line][value]==Table.empty){
            return false;
        }
        int maybeTakenPlaces = 1;
        if (value > 0) {
            int condition = table.tableLists[line][value - 1];
            if (canBeTaken(condition)) {
                maybeTakenPlaces++;
                if (value > 1) {
                    condition = table.tableLists[line][value - 2];
                    if (canBeTaken(condition))
                        maybeTakenPlaces++;
                }
            }
        }
        if (value < 12) {
            int condition = table.tableLists[line][value + 1];
            if (canBeTaken(condition)) {
                maybeTakenPlaces++;
                if (value < 11) {
                    condition = table.tableLists[line][value + 2];
                    if (canBeTaken(condition))
                        maybeTakenPlaces++;
                }
            }
        }
        if (maybeTakenPlaces < 3) {
//            System.out.println("noHope, value = " + value + ", where = " + line + ", too few cards in the list");
            return true;
        }
        else return false;
    }
    public int indexOfCopy(int index){
        if(index % 2 == 0)
            return index+1;
        return index - 1;
    }
    public boolean canBeTaken(int condition){
        if(condition == Table.maybe||condition == Table.taken)
            return true;
        return false;
    }
    public int getFirstUnusedCardId(){
        for (int index = 0; index < whereCardsAre.length; index++) {
            if (Card.isAvailable(whereCardsAre[index])) {
                return index;
            }
        }
        return -1;
    }


    public boolean fullyNoProblems(){
        for (int i = 0; i < 13; i++) {

            if(isProblemWithCardInQuad(i, 0))return false;
            if(isProblemWithCardInQuad(i, 4))return false;
            for (int j = 0; j < 8; j++) {
                if(isProblemWithCardinLine(i,j))return false;

            }

        }
        return true;
    }

    public void printCards(){
        for (int i = 0; i < 104; i++) {
            System.out.print(RummiShell.cardName(i));
            System.out.print(whereCardsAre[i]);
            System.out.print(' ');
            if(i%8==7) System.out.println();
        }
        System.out.println();
    }
}
