package Proportionally_Allocation;

import ArrayList.ArrayList;
import LRU.LRU_handling;
import Objects_CPU.Request;

public class Proportionally {
    private final LRU_handling[] processes;
    private final int howManyProcesses;
    private final ArrayList<ArrayList<Request>> process;
    private int error;
    private int sumRequest;
    private final int howManyFrame;

    public Proportionally(int howManyProcesses, int howManyFrame, ArrayList<ArrayList<Request>>  process)
    {
        this.howManyFrame = howManyFrame;
        this.process = process;
        this.howManyProcesses = howManyProcesses;
        processes = new LRU_handling[howManyProcesses];

        error = 0;
        sumRequest = 0;

        allocateFrames();
    }

    private void createFrame(int index, int howManyFrame)
    {
        processes[index] = new LRU_handling(howManyFrame, process.get(index));
    }

    private void allocateFrames()
    {
        makeASum();

        for(int i=0; i<howManyProcesses; i++)
        {
            int percent = sumRequest/process.get(i).size();
            createFrame(i, percent*howManyFrame);
        }

        checkSum();
    }

    private void checkSum()
    {
        int sum = 0;

        for (LRU_handling lru_handling : processes) {
            sum += lru_handling.getHowManyFrame();
        }

        if(sum<sumRequest)
        {
            int percent = sumRequest/process.get(howManyProcesses-1).size();
            createFrame((howManyProcesses-1), (percent*sumRequest) +(sumRequest-sum));
        }

    }

    private void makeASum()
    {
        for(int i=0; i<howManyProcesses; i++)
            sumRequest+=process.get(i).size();
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
