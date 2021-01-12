package LRU;

import ArrayList.ArrayList;
import Objects_CPU.Frame;
import Objects_CPU.Request;

public class LRU_handling {

    private Frame[] frames;
    private final ArrayList<Request> requests;
    private Request tmp;
    private int error;
    private final ArrayList<Integer> howMuchTime;
    private int current;
    private int lastNumber;
    private int howManyFrame;
    private boolean ifDone;
    private boolean frozen;
    private int order;
    private int timeDefrost;

    public LRU_handling(int howManyFrame, ArrayList<Request> requests)
    {
        this.howManyFrame = howManyFrame;
        frames = new Frame[howManyFrame];
        createFrame();
        this.requests = requests;
        error = 0;
        howMuchTime = new ArrayList<Integer>();
        current = 0;
        lastNumber = 0;
        order = 0;
        ifDone = false;
        frozen = false;
    }


    private void createFrame()
    {
        for(int i=0; i< frames.length; i++)
            frames[i] = new Frame();
    }

    public void changeFramesMore(Frame frame)
    {
        frameIncrementation();
        Frame[] array = new Frame[howManyFrame];
        if (array.length - 1 >= 0) System.arraycopy(frames, 0, array, 0, array.length - 1);

        array[howManyFrame-1]= frame;

        frames = array;
    }

    public Frame changeFramesLess()
    {
        frameDecrementation();
        Frame[] array = new Frame[howManyFrame];

        System.arraycopy(frames, 0, array, 0, array.length);

        frames = array;

        return new Frame();
    }

    public int frozenZone()
    {
        if(isFrozen())
        {
            frames = null;
            howManyFrame = 0;

            return howManyFrame;
        }
        return (-1);
    }

    public void zoneMore(int howManyFrameAdd)
    {
        if(!isFrozen())
        {
            Frame[] array = new Frame[howManyFrameAdd+howManyFrame];

            if (howManyFrame >= 0) System.arraycopy(frames, 0, array, 0, howManyFrame);

            for(int i= array.length-howManyFrame-1; i < array.length; i++)
            array[i] = new Frame();

            frames = array;
            howManyFrame = frames.length;
        }
    }


    public void handling()
    {
            newRequest();
            orderIncrementation();

            if(!checkData())
            {
                if (checkAvailability()) {
                    frames[getFreeFrame()].setWhichcSite(tmp.getWhichSite());
                    frames[getFreeFrame()].setIsFree(false);
                    done();
                    errorIncrementation();
                }
                else
                {
                    frames[searchUseless()].setWhichcSite(tmp.getWhichSite());
                    errorIncrementation();
                    done();
                    howMuchTime.clear();
                }
            }
        }

    private int searchUseless()
    {

        for (Frame frame : frames) {
            for (int j = current; j >= 0; j--) {
                if (requests.get(j).getWhichSite() == frame.getWhichSite()) {
                    howMuchTime.add(j);
                    break;
                }
                if (j == 0 && requests.get(j).getWhichSite() != frame.getWhichSite()) {
                    howMuchTime.add(lastNumber);
                    lastNumber++;
                }
            }
        }
        if (lastNumber ==( howManyFrame -1 ))
            lastNumber = 0;
        return searchMax(howMuchTime);
    }


    private int searchMax(ArrayList<Integer> list)
    {

        int min = list.get(0);

        for(int i=0; i<list.size(); i++)
            if(list.get(i)<min)
                min = list.get(i);

        return list.indexOf(min);
    }

    private boolean checkAvailability()
    {
        for (Frame frame : frames) {
            if (frame.getIsFree())
                return true;
        }

        return false;
    }

    private int getFreeFrame()
    {
        for (int i = 0; i < frames.length; i++)
        {
            if (frames[i].getIsFree())
            {
                return i;
            }
        }
        return (-1);
    }

    private boolean checkData()
    {
        for (Frame frame : frames) {
            if (frame.getWhichSite() == tmp.getWhichSite()) {
                done();
                return true;
            }
        }
        return false;
    }

    private void done()
    {
        current++;

        if(current >= (requests.size()-1))
            setIfDone();
    }

    private void newRequest()
    {
        tmp = requests.get(current);
    }

    private void errorIncrementation()
    {
        error++;
    }

    public int getError()
    {
        return error;
    }

    public boolean isIfDone() {
        return ifDone;
    }

    private void setIfDone() {
        this.ifDone = true;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getHowManyFrame() {
        return howManyFrame;
    }

    private void frameIncrementation()
    {
        howManyFrame++;
    }

    private void frameDecrementation()
    {
        howManyFrame--;
    }

    private void orderIncrementation()
    {
        order++;
    }
    public void setOrder()
    {
        order = 0;
    }
    public int getOrder()
    {
        return order;
    }

    public int getTimeDefrost()
    {
        return timeDefrost;
    }

    public void timeDefrostIncrementation()
    {
        timeDefrost++;
    }
}
