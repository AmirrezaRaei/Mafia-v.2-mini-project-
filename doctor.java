public class doctor extends Player{
    protected boolean useAbility = false;
    protected String playerChoose = "";
    public doctor(String name, String role) {
        super(name, role);
        super.wakeupAtNight = true;
    }
    public doctor(String name){
        super(name);
    }
}
