public abstract class Player {
    public String name;
    protected String role;


    protected int vote_gained = 0;

    protected boolean voted = false; // see player voted or not
    protected boolean is_alive = true;
    protected boolean wakeupAtNight;
    protected boolean silence = false; // see player must be silent or not

    public Player(String name, String role) {
        this.name = name;
        this.role = role;
    }

    protected Player() {
    }

    //Getter & setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
