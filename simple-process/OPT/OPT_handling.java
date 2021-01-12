package OPT;

import ArrayList.ArrayList;
import Objects_CPU.Frame;
import Objects_CPU.Request;

public class OPT_handling {

    private Frame[] frames;
    private ArrayList<Request> requests;
    private Request tmp;
    private ArrayList<Integer> howMuchTime = new ArrayList<Integer>();
    private int error;
    private int lastNumber;
    private int howManyFrame;

    public OPT_handling(int howManyFrame, ArrayList<Request> requests)
    {
        this.howManyFrame = howManyFrame;
        frames = new Frame[howManyFrame];
        createFrame();
        this.requests = requests;

        error = 0;
        lastNumber = 0;
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
                        frames[searchUseless()].setWhichcSite(tmp.getWhichSite());
                        errorIncrementation();
                        done();
                        howMuchTime.clear();
                }
            }
        }
    }

    private int searchUseless()
    {
        for (Frame frame : frames) {
            for (int j = 0; j < requests.size(); j++) {
                if (requests.get(j).getWhichSite() == frame.getWhichSite()) {
                    howMuchTime.add(j);
                    break;
                }
                if (j == (requests.size() - 1) && requests.get(j).getWhichSite() != frame.getWhichSite()) {
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
        int max = list.get(0);

        for(int i=0; i<list.size(); i++)
            if(list.get(i)>max)
                max = list.get(i);

            return list.indexOf(max);
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
        for (Frame frame : frames)
            if (frame.getWhichSite() == tmp.getWhichSite()) {
                done();
                return true;
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
