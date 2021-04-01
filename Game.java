import java.util.Scanner;

public class Game {
    Scanner input = new Scanner(System.in);
    protected Player[] players;
    protected option op = new option();


    // use Player sub class
    protected detective detect;
    protected doctor doc;
    protected silencer silen;
    protected bulletproof bullet;

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
                        players[i] = new villager(playerName, role);
                        op.villagerCounter += 1;
                        break;
                    case "doctor":
                        players[i] = new doctor(playerName, role);
                        bullet = new bulletproof(playerName, role);
                        op.villagerCounter += 1;
                        break;
                    case "detective":
                        players[i] = new detective(playerName, role);
                        detect = new detective(playerName, role);
                        op.villagerCounter += 1;
                        break;
                    case "bulletproof":
                        players[i] = new bulletproof(playerName, role);
                        bullet = new bulletproof(playerName, role);
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
                        silen = new silencer(playerName, role);
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
        if (player_Have_role == op.totalPlayer) {
            op.roleAssigned = true;
        }
    }

    public void gameStart() {
        if (!op.Game_created) {
            System.out.println("no game created");
            return;
        } else if (!op.roleAssigned) {
            System.out.println("one or more player do not have a role");
        } else if (!op.gameStarted) {
            System.out.println("game has already started");
        }
        // Day
        op.gameStarted = true;
    }

    public void Day() {
        if (op.gameDay == 1) {
            System.out.println("Day" + op.gameDay);
            // vote time
            op.gameDay += 1;
            op.Day = false;
            return;
        }
        System.out.println("Day" + op.gameDay);
        op.gameDay += 1;
        op.Day = false;
        if (op.someoneDead) {
            System.out.println(op.lastNightTarget);
        } else {
            System.out.println("mafia missed shoot");
        }
        // see silencer use her ability or not
        if (silen.useAbility) {
            System.out.println(silen.playerChoose);
        }


    }

    public void Night() {
        System.out.println("Night" + op.gameDay);
        op.gameNight += 1;
        String order;
        boolean flag = false;
        for (int i = 0; i < players.length; i++) {
            if (players[i].wakeupAtNight && players[i].is_alive) {
                System.out.println(players[i].name + ":" + players[i].role);
            }
        }
        do {
            System.out.println("please enter name");
            order = input.next();
            flag = false;
            if (order.equals("end_night")) {
                return;
            }
            for (int i = 0; i < op.totalPlayer; i++) {
                if (order.equals(players[i].name)) {
                    flag = true;
                    if (players[i].wakeupAtNight) {
                        if (players[i].is_alive) {
                            switch (players[i].role) {
                                case "silencer":
                                    System.out.println("silencer please wake up");
                                    ability(players[i].role);
                                    System.out.println("silencer please go to sleep");
                                    break;
                                case "detective":
                                    if (detect.useAbility) {
                                        System.out.println("detective has already asked");
                                    } else {
                                        System.out.println("detective please wake up");
                                        ability(players[i].role);
                                        System.out.println("detective please go to sleep");
                                    }
                                    break;
                                case "doctor":
                                    System.out.println("doctor please wake up");
                                    ability(players[i].role);
                                    System.out.println("doctor please go to sleep");
                            }
                        } else {
                            System.out.println("user is dead!!!");
                        }
                    } else {
                        System.out.println("user can not wake up during night");
                    }
                }
            }
            if (!flag) {
                System.out.println("user not found");
            }
        } while (order.equals("end_night"));
    }


    public void resetVoteGained() {
        for (int i = 0; i < op.getTotalPlayer(); i++) {
            players[i].vote_gained = 0;
        }
    }

    public void resetAbility() {
        // to rest abilities player use
        for (int i = 0; i < op.totalPlayer; i++) {
            if (players[i].is_alive) {
                switch (players[i].role) {
                    case "silencer":
                        players[i].voted = false;
                        silen.useAbility = false;
                        silen.playerChoose = null;
                        break;
                    case "doctor":
                        players[i].voted = false;
                        doc.useAbility = false;
                        doc.playerChoose = null;
                        break;
                    case "detective":
                        players[i].voted = false;
                        detect.useAbility = false;
                        detect.playerChoose = null;
                        break;
                    default:
                        players[i].voted = false;
                        break;
                }
            }
        }
    }

    public void ability(String role) {
        String name, order;
        boolean flag = false;
        switch (role) {
            case "silencer":
                System.out.println("Do you want to use power?");
                System.out.println("please enter yes or no");
                order = input.next();
                if (order.equals("no")) {
                    return;
                } else if (order.equals("yes")) {
                    System.out.println("please choose some one");
                    name = input.next();
                    for (int i = 0; i < op.totalPlayer; i++) {
                        if (name.equals(players[i].name)) {
                            if (players[i].is_alive) {
                                silen.useAbility = true;
                                silen.playerChoose = name;
                                flag = true;
                            } else {
                                System.out.println("user is dead!!!");
                            }
                            return;
                        }
                    }
                    if (!flag) {
                        System.out.println("user not found");
                        return;
                    }
                }
                break;
            case "detective":
                System.out.println("Do you want to use power?");
                System.out.println("please enter yes or no");
                order = input.next();
                if (order.equals("no")) {
                    return;
                } else if (order.equals("yes")) {
                    System.out.println("please choose some one");
                    name = input.next();
                    for (int i = 0; i < op.totalPlayer; i++) {
                        if (name.equals(players[i].name)) {
                            if (players[i].is_alive) {
                                detect.useAbility = true;
                                detect.playerChoose = name;
                                flag = true;
                                switch (players[i].role) {
                                    case "mafia":
                                    case "silencer":
                                        System.out.println("This player is a mafia");
                                        break;
                                    default:
                                        System.out.println("This player is a villager");
                                        break;
                                }
                            } else {
                                System.out.println("suspect is dead!!!");
                            }
                            return;
                        }
                    }
                    if (!flag) {
                        System.out.println("user not found");
                        return;
                    }
                }
                break;
            case "doctor":
                System.out.println("Do you want to use power?");
                System.out.println("please enter yes or no");
                order = input.next();
                if (order.equals("no")) {
                    return;
                } else if (order.equals("yes")) {
                    System.out.println("please heal some one");
                    name = input.next();
                    for (int i = 0; i < op.totalPlayer; i++) {
                        if (name.equals(players[i].name)) {
                            if (players[i].is_alive) {
                                doc.useAbility = true;
                                doc.playerChoose = name;
                                flag = true;
                                System.out.println("doc heal (" + name + ":" + players[i].role + ")");
                            } else {
                                System.out.println("user is dead!!!");
                            }
                            return;
                        }
                    }
                    if (!flag) {
                        System.out.println("user not found");
                        return;
                    }
                }
                break;
        }
    }
}
