package FIFO;

import ArrayList.ArrayList;
import Objects_CPU.Frame;
import Objects_CPU.Request;

public class FIFO_handling {

    private Frame[] frames;
    private ArrayList<Request> requests;
    private Request tmp;

    private int error;
    private int lastNumber;

    public FIFO_handling(int howManyFrame, ArrayList<Request> requests)
    {
        frames = new Frame[howManyFrame];
        createFrame();
        this.requests = requests;

        lastNumber = 0;
        error = 0;
    }

    private void createFrame()
    {
        for(int i=0; i< frames.length; i++)
            frames[i] = new Frame();
    }

    public void handling()
    {
        while(requests.isEmpty()) {

            newRequest();

            if (!checkData()) {
                if (checkAvailability()) {
                    frames[getFreeFrame()].setWhichcSite(tmp.getWhichSite());
                    frames[getFreeFrame()].setIsFree(false);
                    done();
                    errorIncrementation();
                } else {

                    frames[lastNumber].setWhichcSite(tmp.getWhichSite());
                    lastNumber++;
                    errorIncrementation();
                    done();
                }

                if (lastNumber == frames.length) {
                    lastNumber = 0;
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
