public class mafia extends Player{
    public mafia(String name, String role) {
        super(name, role);
        super.wakeupAtNight = true;
    }
}
