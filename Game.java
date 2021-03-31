import java.util.Scanner;

public class Game {
    Scanner input = new Scanner(System.in);
    protected Player[] players;
    protected option op = new option();
//    protected bulletproof bullet = new bulletproof();

    // use sub class

    public static void main(String[] args) {

    }

    public void startUp() {
        System.out.println("Hi" + "\n" + "Welcome to Mafia" +
                "\n" + "Have Fun Time");
        System.out.println("Dude Are you ready ?");
        System.out.println("please enter create_game");
    }

    public void createGame() {
        int num;
        String name;
        if (op.Game_created) {
            System.out.println("game has already started");
            return;
        }
        System.out.println("How many people play ?");
        num = input.nextInt();
        while (num <= 9) {
            System.out.println("Need more Player" + "\n" + "please enter correct value");
            num = input.nextInt();
        }
        // update Player array
        op.totalPlayer = num;
        players = new Player[num];
        // use one by one cause we want beautiful  program
        System.out.println("please enter players name one by one");
        for (int i = 0; i < num; i++) {
            System.out.println(" enter player number " + op.Player_num + "name");
            name = input.next();
            players[(op.Player_num - 1)].name = name;
            op.Player_num += 1;
        }
        op.Game_created = true;
    }

    public void setAssignRole() {
        if (!op.Game_created) {
            System.out.println("no game created");
        }
        String playerName, role;
        boolean flag = false;
        System.out.println("please enter player name to assign role");
        playerName = input.next();
        for (int i = 0; i < op.getTotalPlayer(); i++) {
            if (players[i].name.equals(playerName)) {
                System.out.println("please enter" + players[i].name + "role");
                flag = true;
                role = input.next();
                switch (role) {
                    case "villager":
                        players[i] = new villager(playerName,role);
                        op.villagerCounter += 1;
                        break;
                    case "doctor":
                        players[i] = new doctor(playerName, role);
                        op.villagerCounter += 1;
                        break;
                    case "detective":
                        players[i] = new detective(playerName, role);
                        op.villagerCounter += 1;
                        break;
                    case "bulletproof":
                        players[i] = new bulletproof(playerName, role);
                        op.villagerCounter += 1;
                        break;
                    case "godfather":
                        players[i] = new godfather(playerName, role);
                        op.mafiaCounter += 1;
                        break;
                    case "mafia":
                        players[i] = new mafia(playerName, role);
                        op.mafiaCounter += 1;
                        break;
                    case "silencer":
                        players[i] = new silencer(playerName, role);
                        op.mafiaCounter += 1;
                        break;
                    case "Joker":
                        players[i] = new Joker(playerName, role);
                        op.joker = playerName;
                        op.jokerCounter += 1;
                        break;
                    default:
                        System.out.println("role not found");
                        break;
                }
            }
        }
        if (!flag) {
            System.out.println("user not found");
        }
        int player_Have_role = (op.jokerCounter + op.villagerCounter + op.mafiaCounter);
        if (player_Have_role == op.totalPlayer){
            op.roleAssigned = true;
        }
    }

    public void gameStart(){
        if (!op.Game_created){
            System.out.println("no game created");
            return;
        } else if (!op.roleAssigned){
            System.out.println("one or more player do not have a role");
        } else if (!op.gameStarted){
            System.out.println("game has already started");
        }
        // Day
        op.gameStarted = true;
    }

    public void Day(){
        if (op.gameDay == 1){
            System.out.println("Day" + op.gameDay);
            // vote time
            op.gameDay += 1;
            op.Day = false;
            return;
        }
        System.out.println("Day" + op.gameDay);
        op.gameDay += 1;
        op.Day = false;
        for (int i = 0; i < op.totalPlayer; i++) {

        }

    }

    public void resetVoteGained() {
        for (int i = 0; i < op.getTotalPlayer(); i++) {
            players[i].vote_gained = 0;
        }
    }
}
