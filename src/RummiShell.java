import java.util.Scanner;

/**
 * Created by artmmslv on 05.06.16.
 */
public class RummiShell {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        RummicubeSolver rs = new RummicubeSolver();
        while (true){
            String s = in.next();
            s = s.toUpperCase();
            if(s.equals("!Q"))break;
            //commands for hand
            if(s.charAt(0)=='H'){
                if(s.equals("HELP")){
                    System.out.println("♠ ♣ ♦ ♥");
                    System.out.println("S C D H");
                    System.out.println();
                    System.out.println("A 2 3 4 5 6 7 8 9 10 J Q K");
                    System.out.println("A 2 3 4 5 6 7 8 9  0 J Q K");
                    System.out.println("                          ");
                    continue;
                }
                if(s.equals("HAND")){
                    System.out.print("Your hand: ");
                    for (int i = 0; i < 104; i++) {
                        if(rs.whereCardsAre[i]==Possibility.inHand){
                            System.out.print(cardName(i)+" ");
                        }
                    }
                    System.out.println();
                    continue;
                }
                if(s.length()==4 && s.charAt(1)=='+') {
                    try {
                        int halfid = halfId(s.charAt(2), s.charAt(3));

                        if (rs.whereCardsAre[2 * halfid] == Possibility.inStack) {
                            rs.whereCardsAre[2 * halfid] = Possibility.inHand;
                            System.out.println("Card " + cardName(2*halfid) +" was added");
                        } else if (rs.whereCardsAre[2 * halfid + 1] == Possibility.inStack) {
                            rs.whereCardsAre[2 * halfid + 1] = Possibility.inHand;
                            System.out.println("Card " + cardName(2*halfid+1) +" was added");
                        } else
                            System.out.println("No such card in stack");
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Sad, but card " + s.substring(2) + " was not added!");
                    }
                    continue;
                }
                if(s.length()==4 && s.charAt(1)=='-') {
                    try {
                        int halfid = halfId(s.charAt(2), s.charAt(3));

                        if (rs.whereCardsAre[2 * halfid+1] == Possibility.inHand) {
                            rs.whereCardsAre[2 * halfid+1] = Possibility.inStack;
                            System.out.println("Card " + cardName(2*halfid+1) +" was moved to stack");
                        } else if (rs.whereCardsAre[2 * halfid] == Possibility.inHand) {
                            rs.whereCardsAre[2 * halfid] = Possibility.inStack;
                            System.out.println("Card " + cardName(2*halfid) +" was moved to stack");
                        } else
                            System.out.println("No such card on hand");
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Sad, but card " + s.substring(2) + " was not added!");
                    }
                    continue;
                }
            }else if (s.charAt(0)=='G'){
                int go = rs.go();
                Possibility pos = rs.list.get(go);
                String tmp = "";
                for (int j = 0; j < 104; j++) {
                    if(rs.whereCardsAre[j] == Possibility.inHand &&
                            pos.isPlaced(j) ){
                        tmp=tmp+RummiShell.cardName(j)+" ";
                    }
                }
                System.out.println(tmp);
                    rs.list.get(go).printTable();
                    rs.updateList(rs.list.get(go).whereCardsAre);
                continue;
            }else if(s.charAt(0)=='M'){
                try{
                String[] a = s.split("-");
                int index = Integer.parseInt(a[1]);
                int condition = Integer.parseInt(a[2]);
                rs.whereCardsAre[index]=condition;
                }catch (Exception e){
                    System.out.println("M: nothing is done");
                }
            }
            else  if(s.charAt(0)=='T'){
                if(s.equals("TABLE")){
                    System.out.print("OnTable: ");
                    for (int i = 0; i < 104; i++) {
                        if(rs.whereCardsAre[i]==Possibility.inHeap){
                            System.out.print(cardName(i)+" ");
                        }
                    }
                    System.out.println();
                    continue;
                }
                if(s.length()==4 && s.charAt(1)=='+') {
                    try {
                        int halfid = halfId(s.charAt(2), s.charAt(3));

                        if (rs.whereCardsAre[2 * halfid] == Possibility.inStack) {
                            rs.whereCardsAre[2 * halfid] = Possibility.inHeap;
                            System.out.println("Card " + cardName(2*halfid) +" was added");
                        } else if (rs.whereCardsAre[2 * halfid + 1] == Possibility.inStack) {
                            rs.whereCardsAre[2 * halfid + 1] = Possibility.inHeap;
                            System.out.println("Card " + cardName(2*halfid+1) +" was added");
                        } else
                            System.out.println("No such card in stack");
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Sad, but card " + s.substring(2) + " was not added!");
                    }
                    continue;
                }
                if(s.length()==4 && s.charAt(1)=='-') {
                    try {
                        int halfid = halfId(s.charAt(2), s.charAt(3));

                        if (rs.whereCardsAre[2 * halfid+1] == Possibility.inHeap) {
                            rs.whereCardsAre[2 * halfid+1] = Possibility.inStack;
                            System.out.println("Card " + cardName(2*halfid+1) +" was moved to stack");
                        } else if (rs.whereCardsAre[2 * halfid] == Possibility.inHeap) {
                            rs.whereCardsAre[2 * halfid] = Possibility.inStack;
                            System.out.println("Card " + cardName(2*halfid) +" was moved to stack");
                        } else
                            System.out.println("No such card on hand");
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Sad, but card " + s.substring(2) + " was not added!");
                    }
                    continue;
                }

        }
    }}
    public static String cardName(int index) {
        int color = (index % 8) / 2;
        int value = index / 8;
        return cardname(value,color);
    }
    public static String cardname(int value, int color){
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
    public static int halfId(char c1, char c2){
        int color=0;
        int value;

        switch (c1){
            //china style (-_-)
            case '2':{value = 1; break;}
            case '3':{value = 2; break;}
            case '4':{value = 3; break;}
            case '5':{value = 4; break;}
            case '6':{value = 5; break;}
            case '7':{value = 6; break;}
            case '8':{value = 7; break;}
            case '9':{value = 8; break;}

            case 'A':{value = 0; break;}
            case '0':{value = 9; break;}
            case 'J':{value = 10; break;}
            case 'Q':{value = 11; break;}
            case 'K':{value = 12; break;}
            default:{value = 1000;}
        }
        switch (c2){
            case 'S':{color = 0;break;}
            case 'C':{color = 1;break;}
            case 'D':{color = 2;break;}
            case 'H':{color = 3;break;}
            default:color=1000;
        }
        return value*4+color;
    }
}
