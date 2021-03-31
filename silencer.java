public class silencer extends Player{
    protected boolean useAbility = false;
    public silencer(String name, String role) {
        super(name, role);
        super.wakeupAtNight = true;
    }
}
