package Objects_CPU;

public class Frame {
    private boolean isFree;
    private int whichSite;

    public Frame()
    {
        isFree = true;
    }

    public void setIsFree(boolean isFree)
    {
        this.isFree= isFree;
    }

    public boolean getIsFree()
    {
        return isFree;
    }

    public int getWhichSite() {
        return whichSite;
    }

    public void setWhichcSite(int whichcSIte) {
        this.whichSite = whichcSIte;
    }
}
