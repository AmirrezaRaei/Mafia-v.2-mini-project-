public class detective extends Player{
    protected boolean useAbility = false;
    protected String playerChoose;
    public detective(String name, String role) {
        super(name, role);
        super.wakeupAtNight = true;
    }
}
