package Objects_CPU;

public class RequestWithChance extends Request {

    private boolean chance;

    public RequestWithChance(int whichCell) {
        super(whichCell);
        chance = true;
    }

    public boolean getChance() {
        return chance;
    }

    public void setChance(boolean chance) {
        this.chance = chance;
    }
}
