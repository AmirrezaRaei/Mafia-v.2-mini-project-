public class option {
    protected int villagerCounter = 0;
    protected int mafiaCounter = 0;
    protected int jokerCounter = 0;
    protected int gameDay = 1 , gameNight = 1;
    protected int totalPlayer;
    protected int Player_num = 1;

    // Game option
    protected boolean Game_created = false;
    protected boolean roleAssigned = false;
    protected boolean gameStarted = false;
    protected boolean someoneDead = false;
    protected boolean Day = true;// check time
    // special people
    protected String joker;
    protected String lastNightTarget;
    protected String mafiaTarget1 = null;
    protected String mafiaTarget2 = null;

    public int getTotalPlayer() {
        return totalPlayer;
    }
}
