public class bulletproof extends Player{
    protected int shield = 1;
    public bulletproof(String name, String role) {
        super(name, role);
        super.wakeupAtNight = false;
    }

//    public String bulletproof() {
//        return (super.name);
//    }

    public int getShield() {
        return shield;
    }
}
