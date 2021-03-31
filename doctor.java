public class doctor extends Player{
    protected boolean useAbility = false;
    public doctor(String name, String role) {
        super(name, role);
        super.wakeupAtNight = true;
    }
}
