import java.util.Scanner;

public class Property {
    String name;
    int index;
    
    Property(String name, int index){
        this.index = index;
        this.name = name;
    }
    Scanner input = new Scanner(System.in);
}

class LowBalance extends Exception{
    public LowBalance() {
        super("You do not have enough money");
    }
}
class WrongInput extends Exception{
    public WrongInput() {
        super("Wrong input! try again");
    }
}

class AirPort extends Property {
    
    AirPort( int index) {
        super("Airport", index);
    }

    public void buyTicket(Player player) throws LowBalance, WrongInput {
        if(player.balance < 50)
            throw new LowBalance();
        player.balance -= 50;
        System.out.println("Choose location to travel");
        if(player.index == 3) System.out.println("11 or 20");
        else if(player.index == 11) System.out.println("3 or 20");
        else if(player.index == 20) System.out.println("3 or 11");

        int inp = input.nextInt();

        switch (inp) {
            case 3 -> player.index = 3;
            case 11 -> player.index = 11;
            case 20 -> player.index = 20;
            default -> throw new WrongInput();  /// must be defined an exception
        }
    }
}

class Chance  {
    
    public void getMoney(Player player){
        player.balance+=200;
    }
    public void goToPrison(Player player){
        player.index = 13;
    }
    public void payToBank(Player player){
        player.balance -= player.balance*10/100;
    }
    public void goThreeCellsAhead(Player player){
        player.index+=3;
    }
    public void chanceToReleasePrison(Player player){
        player.chanceToRelease++;
    }
}

class Ground extends Property {
    Player owner = new Player("Bank");
    int numberOfHouses = 0;
    boolean isHotel = false;

    Ground( int index) {
        super("Ground", index);
    }
    public void setName(String name){
        super.name = name;
    }

    public void setOwner(Player player) throws LowBalance {
        if(player.balance < 100)
            throw new LowBalance();
        player.balance -= 200;
        owner = player;
    }
    public void build(Player player) throws LowBalance, WrongInput {
        boolean con = true;
        while (con){
            System.out.println("1- build house 2-change to hotel");
            switch (input.nextInt()){
                case 1: 
                    if(numberOfHouses >= 4){
                    System.out.println("You can not build it is max");
                    con = false;
                    }
                    else if(player.balance < 150){
                        throw new LowBalance();
                    }
                    else{
                        player.balance -= 150;
                        numberOfHouses++;
                        setName("House");}
                    break;
                    
                case 2:
                    if(player.balance < 100){
                        throw new LowBalance();
                    }
                    if(numberOfHouses == 4){
                        player.balance -= 100;
                        isHotel = true;
                    }
                    break;
                default: throw new WrongInput();
            }
        }
    }
}

class Cinema extends Property {
    
    Player owner = new Player("Bank");
    Cinema( int index) {
        super("Cinema", index);
    }

    public void payToOwner(Player player) throws LowBalance {
        if(!player.equals(owner)){
            if(owner.cinemas.size() == 1){
                if(player.balance >= 25){
                    owner.balance += 25;
                    player.balance -= 25;
                }else
                    throw new LowBalance();
            }
            else if(owner.cinemas.size() == 2){
                if(player.balance >= 50){
                    owner.balance += 50;
                    player.balance -= 50;
                }else
                    throw new LowBalance();
            }
            else if(owner.cinemas.size() >= 3){
                if(player.balance >= 100){
                    owner.balance += 100;
                    player.balance -= 100;
                }else
                    throw new LowBalance();
            }
        }
    }
    public void setOwner(Player player) throws LowBalance {
        if(player.balance < 200)
            throw new LowBalance();
        player.balance -= 200;
        owner = player;
    }
}

class Bank {
    void deposit(Player player) throws LowBalance {
        if(player.balance <= 1)
            throw new LowBalance();
        else{
            player.balance -= player.balance/2;
            player.depositCard++;
        }
    }
    void useDepositCard(Player player) throws WrongInput {
        if(player.depositCard>=1)
            player.balance += player.depositRemain*2;
        else{
            System.out.println("You don't have enough card");
            throw new WrongInput();
        }
    }
}


