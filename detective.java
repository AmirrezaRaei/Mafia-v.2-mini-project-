public class detective extends Player{
    protected boolean useAbility = false;
    public detective(String name, String role) {
        super(name, role);
        super.wakeupAtNight = true;
    }
}
