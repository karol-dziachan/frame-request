package Random;

import ArrayList.ArrayList;
import Objects_CPU.Frame;
import Objects_CPU.Request;

public class Random_handling {


    private Frame[] frames;
    private ArrayList<Request> requests;
    private Request tmp;

    private int error;
    private int howManyFrame;

    public Random_handling(int howManyFrame, ArrayList<Request> requests)
    {
        this.howManyFrame = howManyFrame;
        frames = new Frame[howManyFrame];
        createFrame();
        this.requests = requests;

        error = 0;
    }

    private void createFrame()
    {
        for(int i=0; i< frames.length; i++)
            frames[i] = new Frame();
    }


    public void handling()
    {
        while(requests.isEmpty())
        {
            newRequest();

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
                    int random = (int) (Math.random()*howManyFrame);
                    frames[random].setWhichcSite(tmp.getWhichSite());
                    errorIncrementation();
                    done();
                }
            }
        }
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
        requests.remove(0);
    }

    private void newRequest()
    {
        tmp = requests.get(0);
    }

    private void errorIncrementation()
    {
        error++;
    }

    public int getError()
    {
        return error;
    }
}
