package Equal_Allocation;

import ArrayList.ArrayList;
import LRU.LRU_handling;
import Objects_CPU.Request;

public class Equal {
    private final LRU_handling[] processes;
    private final int howManyProcesses;
    private final int howManyFrame;
    private final ArrayList<ArrayList<Request>> process;
    private int error;
    public Equal(int howManyProcesses, int howManyFrame, ArrayList<ArrayList<Request>>  process)
    {
        this.process = process;
        this.howManyFrame = howManyFrame;
        this.howManyProcesses = howManyProcesses;
        processes = new LRU_handling[howManyProcesses];

        error = 0;

        allocateFrames();
    }

    private void createFrame(int index, int howManyFrame)
    {
        processes[index] = new LRU_handling(howManyFrame, process.get(index));
    }

    private void allocateFrames()
    {
        int framesForOneProcess = howManyFrame / howManyProcesses;

        for(int i=0; i < howManyProcesses; i++)
            createFrame(i, framesForOneProcess);

        if(framesForOneProcess < howManyProcesses*howManyFrame)
            createFrame(howManyProcesses-1, framesForOneProcess +1);
    }

    public void handling()
    {
        while(!checkAll())
        {
            int tmp = (int) (Math.random() * howManyProcesses);
            if(!processes[tmp].isFrozen())
                if(!checkDone(tmp))
                    processes[tmp].handling();

        }
    }



    private boolean checkAll()
    {
        for(int i=0; i<howManyProcesses; i++)
            if(!processes[i].isIfDone())
                return false;

            return true;
    }

    private boolean checkDone(int index)
    {
        return processes[index].isIfDone();
    }

    public int getError()
    {
        for(int i=0; i < howManyProcesses; i++)
        {
            error+=processes[i].getError();
        }

        return error;
    }

    private int sum()
    {
        int sum = 0;

        for (int i=0; i<process.size(); i++)
        {
            sum += process.get(i).size();
        }

        return sum;
    }

    public boolean getStruggle()
    {
        return error == 0.6*sum();
    }

}
