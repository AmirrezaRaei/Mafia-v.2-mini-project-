import java.util.Scanner;

public class Game {
    static Scanner input = new Scanner(System.in);
    protected static Player[] players;
    protected static option op = new option();


    // use Player sub class
    protected static detective detect = new detective("");
    protected static doctor doc = new doctor("");
    protected static silencer silen = new silencer("");
    protected static bulletproof bullet = new bulletproof("");

    public static void main(String[] args) {


        boolean flag = false;
        Scanner input = new Scanner(System.in);
        String order;
        startUp();
        do {
            order = input.next();

            switch (order) {
                case "create_game":
                    createGame();
                    break;
                case "assign_role":
                    setAssignRole();
                    break;
                case "start_game":
                    gameStart();
                    while (!flag) {
                        order = input.next();
                        switch (order){
                            case "get_game_state":
                                getGameState();
                                break;
                            case "day":
                                System.out.println("everyone please wake up");
                                Day();
                                System.out.println("please enter night");
                                break;
                            case "night":
                                System.out.println("everyone please go to sleep");
                                Night();
                                System.out.println("please enter day");
                                break;
                        }
                    }
                    break;
                case "get_game_state":
                    getGameState();
                    break;
                default:
                    System.out.println("wrong order!!!");
            }
        } while (!flag);
    }

    public static void startUp() {
        System.out.println("Hi" + "\n" + "Welcome to Mafia" +
                "\n" + "Have Fun Time");
        System.out.println("Dude Are you ready ?");
        System.out.println("please enter create_game");
    }

    public static void createGame() {
        int num;
        String name;
        if (op.Game_created) {
            System.out.println("game has already started");
            return;
        }
        System.out.println("How many people play ?");
        num = input.nextInt();
        if (num < 8) {
            while (num <= 8) {
                System.out.println("Need more Player" + "\n" + "please enter correct value");
                num = input.nextInt();
            }
        }
        // update Player array
        op.totalPlayer = num;
        players = new Player[num];
        // use one by one cause we want beautiful  program
        System.out.println("please enter players name one by one");
        for (int i = 0; i < op.totalPlayer; i++) {
            System.out.println(" Enter the name of the player number " + op.Player_num);
            name = input.next();
            players[(op.Player_num - 1)] = new Player(name);
            players[(op.Player_num - 1)].name = name;
            op.Player_num += 1;
        }
        op.Game_created = true;
        System.out.println("please enter assign_role");
    }

    public static void setAssignRole() {
        if (!op.Game_created) {
            System.out.println("no game created");
            return;
        }
        String playerName, role;
        boolean flag = false;
        System.out.println("please enter player name to assign role");
        playerName = input.next();
        for (int i = 0; i < op.getTotalPlayer(); i++) {
            if (players[i].name.equals(playerName)) {
                System.out.println("please enter " + players[i].name + " role");
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
            System.out.println("please enter start_game");
        }
    }

    public static void gameStart() {
        if (!op.Game_created) {
            System.out.println("no game created");
            return;
        } else if (!op.roleAssigned) {
            System.out.println("one or more player do not have a role");
            return;
        } else if (op.gameStarted) {
            System.out.println("game has already started");
            return;
        }
        for (int i = 0; i < op.getTotalPlayer(); i++) {
            System.out.println(players[i].name + " : " + players[i].role);
        }
//        Day();
        op.gameStarted = true;
        return;
    }

    public static void Day() {
        resetVoteGained();
        String order, name;
        boolean flag = false;
        if (op.gameDay == 1) {
            System.out.println("Day " + op.gameDay);
            // vote time
            op.gameDay += 1;
            op.Day = false;
        } else {
            System.out.println("Day " + op.gameDay);
            op.gameDay += 1;
            op.Day = false;
            if (op.someoneDead) {
                System.out.println("last night mafias tries to kill " + op.lastNightTarget);
                System.out.println(op.lastNightTarget + ": dead");
                switch (op.lastNightTarget) {
                    case "Joker":
                        op.jokerCounter -= 1;
                        break;
                    default:
                        op.villagerCounter -= 1;
                }
            } else {
                System.out.println("mafia missed shoot");
            }
            showWinner();
            // see silencer use her ability or not
            if (silen.useAbility) {
                System.out.println("silenced" + silen.playerChoose);
            }
        }
        do {
            System.out.println("if any player want vote please enter vote");
            System.out.println("if not please enter end_vote");
            order = input.next();
            if (order.equals("end_vote")) {
                break;
            }
            flag = false;
            for (int i = 0; i < op.getTotalPlayer(); i++) {
                if (order.equals(players[i].name)) {
                    flag = true;
                    if (players[i].is_alive) {
                        voteAtDay(players[i]);
                    } else {
                        System.out.println("user is dead");
                    }
                }
            }
            if (!flag) {
                System.out.println("user not joined");
            }
            System.out.println("please enter new order");
        } while (!order.equals("end_vote"));
        // see who is going to dead
        int temp = dayVoteResult();
        if (temp != -1) {
            players[temp].is_alive = false;
            System.out.println(players[temp].name + ": dead");
            switch (players[temp].role) {
                case "godfather":
                case "silencer":
                case "mafia":
                    op.mafiaCounter -= 1;
                    break;
                case "Joker":
                    System.out.println("Joker won");
                    System.exit(0);
                    break;
                default:
                    op.villagerCounter -= 1;
                    break;
            }
        }
        showWinner();
        resetAbility();
        resetVoteGained();
        op.Day = false;
    }

    public static void Night() {
        System.out.println("Night " + op.gameNight);
        op.gameNight += 1;
        String order;
        boolean flag = false;
        for (int i = 0; i < players.length; i++) {
            if (players[i].wakeupAtNight && players[i].is_alive) {
                System.out.println(players[i].name + " : " + players[i].role);
            }
        }
        do {
            System.out.println("if any player want vote or use ability please enter player name");
            System.out.println("if not please enter end_vote");
            order = input.next();
            flag = false;
            if (order.equals("end_night")) {
                break;
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
                                    voteAtNight(players[i]);
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
                                    break;
                                case "mafia":
                                    System.out.println("mafia please wake up");
                                    voteAtNight(players[i]);
                                    System.out.println("mafia please go to sleep");
                                    break;
                                case "godfather":
                                    System.out.println("godfather please wake up");
                                    voteAtNight(players[i]);
                                    System.out.println("godfather please go to sleep");
                                    break;
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
        } while (!order.equals("end_night"));
        mafiaVoteResult();
        op.Day = true;
    }


    public static void resetVoteGained() {
        for (int i = 0; i < op.getTotalPlayer(); i++) {
            players[i].vote_gained = 0;
        }
    }

    public static void resetAbility() {
        // to rest abilities player use
        for (int i = 0; i < op.totalPlayer; i++) {
            if (players[i].is_alive) {
                switch (players[i].role) {
                    case "silencer":
                        players[i].voted = false;
                        silen.useAbility = false;
                        silen.playerChoose = "";
                        break;
                    case "doctor":
                        players[i].voted = false;
                        doc.useAbility = false;
                        doc.playerChoose = "";
                        break;
                    case "detective":
                        players[i].voted = false;
                        detect.useAbility = false;
                        detect.playerChoose = "";
                        break;
                    default:
                        players[i].voted = false;
                        break;
                }
            }
        }
        op.mafiaTarget2 = "";
        op.mafiaTarget1 = "";
        op.lastNightTarget = "";
        op.someoneDead = false;
    }

    public static void ability(String role) {
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

    public static void voteAtNight(Player player) {
        if (player.voted) {
            System.out.println("You can not vote");
            return;
        }

        String name, order;
        boolean flag = false;
        System.out.println("Do you want to vote?");
        order = input.next();
        if (order.equals("no")) {
            return;
        }

        System.out.println("please vote someone");
        name = input.next();
        for (int i = 0; i < op.totalPlayer; i++) {
            if (name.equals(players[i].name)) {
                flag = true;
                if (players[i].is_alive) {
                    players[i].vote_gained += 1;
                    player.voted = true;

                } else {
                    System.out.println("user is dead");
                }
            }
        }
        if (!flag) {
            System.out.println("votee already dead");
        }
    }

    public static void mafiaVoteResult() {
        int temp = 0;
        int votee1 = 0, votee2 = 0;
        for (int i = 0; i < op.totalPlayer; i++) {
            if (players[i].is_alive) {
                if (players[i].vote_gained > temp || op.mafiaTarget1 == "") {
                    op.mafiaTarget1 = players[i].name;
                    temp = players[i].vote_gained;
                    votee1 = players[i].vote_gained;
                } else if (players[i].vote_gained == temp) {
                    op.mafiaTarget2 = players[i].name;
                    votee2 = players[i].vote_gained;
                }
            }
        }
        if (votee1 > votee2) {
            op.mafiaTarget2 = "";
        }
        if (op.mafiaTarget1 == "") {
            return;
        }
        // now we want see doctor save anyone or not
        int index;
        if (!doc.useAbility) {
            if (op.mafiaTarget2 == "") {
                if (op.mafiaTarget1.equals(bullet.name) &&
                        bullet.shield == 1) { // see mafia hit bulletproof and bulletproof have shields
                    bullet.shield = 0;
                } else {
                    op.lastNightTarget = op.mafiaTarget1;
                    index = foundPlayer(op.lastNightTarget);
                    players[index].is_alive = false;
                    op.someoneDead = true;
                }
            }
            return;
        } else {
            if (op.mafiaTarget2 == "") {
                if (doc.playerChoose.equals(op.mafiaTarget1)) {
                    return;
                } else {
                    if (op.mafiaTarget1.equals(bullet.name) && bullet.shield == 1){
                        bullet.shield = 0;
                        return;
                    }
                    op.lastNightTarget = op.mafiaTarget1;
                    index = foundPlayer(op.lastNightTarget);
                    players[index].is_alive = false;
                    op.someoneDead = true;
                }
            } else {
                if (op.mafiaTarget1.equals(doc.playerChoose)) {
                    if (op.mafiaTarget2.equals(bullet.name) && bullet.shield == 1){
                        bullet.shield = 0;
                        return;
                    }
                    op.lastNightTarget = op.mafiaTarget2;
                    index = foundPlayer(op.lastNightTarget);
                    players[index].is_alive = false;
                    op.someoneDead = true;
                } else if (op.mafiaTarget2.equals(doc.playerChoose)) {
                    if (op.mafiaTarget1.equals(bullet.name) && bullet.shield == 1){
                        bullet.shield = 0;
                        return;
                    }
                    op.lastNightTarget = op.mafiaTarget1;
                    index = foundPlayer(op.lastNightTarget);
                    players[index].is_alive = false;
                    op.someoneDead = true;
                } else {
                    System.out.println("two players have same vote");
                    return;
                }
            }
        }
        return;
    }

    public static void voteAtDay(Player player) {
        if (player.voted) {
            System.out.println("You can not vote");
            return;
        }
        if (player.name.equals(silen.playerChoose)) {
            System.out.println("voter is silenced");
            return;
        }

        String name, order;
        boolean flag = false;
        System.out.println("Do you want to vote?");
        order = input.next();
        if (order.equals("no")) {
            return;
        }

        System.out.println("please vote someone");
        name = input.next();
        for (int i = 0; i < op.totalPlayer; i++) {
            if (name.equals(players[i].name)) {
                flag = true;
                if (players[i].is_alive) {
                    players[i].vote_gained += 1;
                    player.voted = true;

                } else {
                    System.out.println("votee already dead");
                }
            }
        }
        if (!flag) {
            System.out.println("user not found");
        }
    }

    public static int dayVoteResult() {
        int temp = 0, votee1 = 0, votee2 = 0;
        String killWillBeDead = "", target1 = "", target2 = "";
        for (int i = 0; i < op.getTotalPlayer(); i++) {
            if (players[i].is_alive) {
                if (players[i].vote_gained > temp || target1 == "") {
                    target1 = players[i].name;
                    temp = players[i].vote_gained;
                    votee1 = players[i].vote_gained;
                } else if (players[i].vote_gained == temp) {
                    target2 = players[i].name;
                    votee2 = players[i].vote_gained;
                }
            }
        }
        if (votee1 > votee2 || target2 == "") {
            killWillBeDead = target1;
            op.someoneDead = true;
            return foundPlayer(killWillBeDead);
        }
        System.out.println("two or more player have same vote");
        return -1;
    }

    public static void showWinner() {
        if (op.mafiaCounter == 0) {
            System.out.println("villagers won the game");
            System.exit(0);
        } else if (op.mafiaCounter >= op.villagerCounter) {
            System.out.println("mafias won the game");
            System.exit(0);
        }
    }

    public static void getGameState() {
        System.out.println("Mafia : " + op.mafiaCounter);
        System.out.println("villager :" + op.villagerCounter);
        return;
    }


    public static int foundPlayer(String name) { // found player index in players array
        for (int i = 0; i < op.getTotalPlayer(); i++) {
            if (name.equals(players[i].name)) {
                return i;
            }
        }
        return 0;
    }


}
