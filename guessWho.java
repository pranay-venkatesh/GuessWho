import java.util.*;
import java.io.*;

class guessWho{
    String colours[] = {"Red", "Blue", "Yellow", "Orange", "Purple"};
    ArrayList<String> names;
    File f = new File ("Names.txt");
    Scanner sc;
    int rowSize;
    boolean pWin;
    person compPerson;
    int gQuestions;
    ArrayList<person> board;
    Scanner in;
    person nullPerson;
    int bogus;
    guessWho() throws IOException //constructor for setting initial values
    {
        names = new ArrayList<String>(26);
        board = new ArrayList<person>(26);
        in = new Scanner(System.in);
        sc = new Scanner(f);
        pWin = false;
        gQuestions = 10;
        rowSize = 5;
        nullPerson = new person();
        bogus = 5;
    }
    void setInitValues()   //Getting inputs from the file "Names.txt" and setting them
    {
        while (sc.hasNextLine())
        {
            String name = sc.nextLine();
            names.add(name);  
        }
        int eVal, hVal, sVal, pVal;
        for (int i = 0; i<names.size(); i++)
        {
            eVal = (int)Math.floor(Math.random() * (colours.length-1));
            hVal = (int)Math.floor(Math.random() * (colours.length-1));
            sVal = (int)Math.floor(Math.random() * (colours.length-1));
            pVal = (int)Math.floor(Math.random() * (colours.length-1));
            person temp = new person(names.get(i), eVal, hVal, sVal, pVal);
        }
    }
    class person
    {
        String name;
        int eyeColour;
        int hairColour;
        int shirtColour;
        int pantColour;
        person(String n, int e, int h, int s, int p) //Sets initial values given
        {
            name = n;
            eyeColour = e;
            hairColour = h;
            shirtColour = s;
            pantColour = p;
            board.add(this);
        }
        person()  
        {
            name = "x";
            eyeColour = (int)Math.random()*4;
            hairColour = (int)Math.random() * 4;
            shirtColour = (int)Math.random() * 4;
            pantColour = (int)Math.random() * 4;
        }
    }
    void printBoard()  //Prints the board 
    {
        
        for (int i = 0; i<board.size(); i++)
        {
            System.out.print (board.get(i).name + '\t');
        }
        System.out.println();
    }
    boolean corrColour(int a, int c)   //Checks the colour
    {
        if (a == 1 && c == compPerson.eyeColour)
            return true;
        else if (a == 2 && c == compPerson.hairColour)
            return true;
        else if (a == 3 && c == compPerson.shirtColour)
            return true;
        else if (a == 4 && c == compPerson.pantColour)
            return true;
        return false;
    }
    void giveClue ()    //Allows the player to guess and eliminates values that are not of the type the user guessed
    {
        System.out.println("\nWhich feature do you guess?");
        System.out.println("1)Eye.");
        System.out.println("2)Hair.");
        System.out.println("3)Shirt.");
        System.out.println("4)Pant.");
        int a = in.nextInt();
        System.out.println("Enter the colour");
        for (int i = 0; i<colours.length-1;i++)
            System.out.println(i + ") " + colours[i]);
        int c = in.nextInt();
        boolean k = corrColour(a, c);
        if (k == false)
        {
            eliminate (a,c);
        }
        printBoard();
        System.out.println();
    }
    void eliminate (int e, int c)   //Eliminates values. Eg - All red shirts are eliminated.
    {
        for (int i = 0; i<board.size(); i++)
            if ((e == 1 && board.get(i).eyeColour == c)||(e == 2 && board.get(i).hairColour == c)||(e == 3 && board.get(i).shirtColour == c)||(e == 4 && board.get(i).pantColour ==c))
            {
                board.set(i, nullPerson);
            }
        System.out.println();
    }
    void pCheck ()  //Displays the various feature of a selected head.
    {
        System.out.println("Choose a name");
        int n = in.nextInt();
        System.out.println ("Eye Colour: " + colours[board.get(n).eyeColour]);
        System.out.println ("Hair Colour: " + colours[board.get(n).hairColour]);
        System.out.println ("Shirt Colour: " + colours[board.get(n).shirtColour]);
        System.out.println ("Pant Colour: " + colours[board.get(n).pantColour]);
    }
    boolean guess ()    //Allows the player to guess once. If the guess is wrong, it sets the value to "X"
    {
        System.out.println("Choose name");
        int n = in.nextInt();
        if (compPerson == board.get(n))
        {
            return true;
        }
        else
        {
            board.set(n, nullPerson);
            return false;
        }
    }
    boolean options()   //Options for each turn
    {
        System.out.println ("1)View Details.");
        System.out.println ("2)Guess Who.");
        int ch = in.nextInt();
        in.nextLine();
        switch(ch)
        {
                    case 1:
                    in.nextLine();
                    pCheck();
                    options();
                    break;
                    case 2:
                    boolean b = guess();
                    if (b)
                    {
                        pWin = true;
                        System.out.println("You win!");
                        System.exit(0);
                        return true;
                    }
                    else
                    {
                        gQuestions--;
                        System.out.println("Wrong answer");
                        System.out.println("You have " + gQuestions + " points left");
                        return false;
                    }
        }
        return false;
    }
    void mainMenu() //Displays a menu, which drives the game.
    {
        System.out.println ("1)Play Game");
        System.out.println ("2)Instructions");
        System.out.println ("3)Exit");
        int c = in.nextInt();
        switch(c)
        {
            case 1:
            while (gQuestions >= 0 && (pWin == false))
            {
                printBoard();
                giveClue();
                in.nextLine();
                boolean decide = options();
                if (decide == false)
                    continue;                    
                else
                    break;
            }
            break;
            case 2:
            System.out.println("");
            mainMenu();
            break;
            default:
            System.exit(0);
        }
        if (pWin == true)
        {
            System.out.println("You win");
            System.exit(0);
        }
        if (gQuestions <= 0)
        {
            System.out.println("You lose");
            System.exit(0);
        }
    }
    public static void main (String[]args) throws IOException
    {
        guessWho a = new guessWho();
        a.setInitValues();
        a.compPerson = a.board.get((int)(Math.floor(Math.random()* a.board.size())));
        a.mainMenu();
    }
}
