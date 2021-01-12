package Dependent_On_Errors_Frequency;

import ArrayList.ArrayList;
import LRU.LRU_handling;
import Objects_CPU.Frame;
import Objects_CPU.Request;


public class Dependent {
    private final LRU_handling[] processes;
    private final int howManyProcesses;
    private final int howManyFrame;
    private final ArrayList<ArrayList<Request>> process;
    private int error;
    private int unitTime;
    private final ArrayList<Frame> freeFrames;
    private final int[] errorStart;
    private final int[] errorEnd;
    private final double[] ppf;
    private final int timeWindow;
    private final double minimumPPF;
    private final double maximumPPF;

    public Dependent(int howManyProcesses, int howManyFrame, int timeWindow, double maximumPPF, double minimumPPF, ArrayList<ArrayList<Request>>  process)
    {
        this.process = process;
        this.howManyFrame = howManyFrame;
        this.howManyProcesses = howManyProcesses;
        processes = new LRU_handling[howManyProcesses];
        freeFrames = new ArrayList<Frame>();
        errorStart= new int[howManyProcesses];
        errorEnd= new int[howManyProcesses];
        ppf= new double[howManyProcesses];

        error = 0;
        unitTime = 0;
        this.timeWindow = timeWindow;
        this.minimumPPF= minimumPPF;
        this.maximumPPF = maximumPPF;


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

        for(int i=0; i< howManyProcesses; i++)
            errorStart[i]=0;
    }

    public void handling()
    {
        while(!checkAll())
        {
            defrost();

            int tmp = (int) (Math.random() * howManyProcesses);
            if(!processes[tmp].isFrozen())
                if(!checkDone(tmp))
                    processes[tmp].handling();
                unitTime++;

                if(unitTime%timeWindow == 0)
                {
                    calculatePPF();
                    checkRelease();
                    haveToDefrost();
                }
        }
    }

    private void calculatePPF()
    {
        for(int i=0; i<howManyProcesses; i++)
            errorEnd[i]=processes[i].getError();


        for(int i=0; i<howManyProcesses; i++)
            ppf[i]= (((double)errorEnd[i]-(double)errorStart[i])/((double) timeWindow));


        System.arraycopy(errorEnd, 0, errorStart, 0, errorStart.length);
    }

    private void checkRelease()
    {
        for(int i=0; i<ppf.length; i++)
        {
            if(ppf[i]<=minimumPPF)
                if(!processes[i].isFrozen() && processes[i].getHowManyFrame() > 1)
                    freeFrames.add(processes[i].changeFramesLess());
            if(ppf[i]>=maximumPPF)
            {
                if(availabilityFrames())
                {
                    processes[i].changeFramesMore(freeFrames.get(0));
                    freeFrames.remove(0);

                }
                else
                    processes[i].setFrozen(true);
            }
        }
    }


    private void defrost()
    {
        for (LRU_handling lru_handling : processes) {
            if (lru_handling.isFrozen() && availabilityFrames()) {
                lru_handling.setFrozen(false);
                lru_handling.changeFramesMore(freeFrames.get(0));

            }
        }
    }

    private void haveToDefrost()
    {
        if(allFrozen())
        {
            processes[searchMin(ppf)].setFrozen(false);
        }
    }

    private int searchMin(double[] array)
    {
        double min = array[0];

        for (double v : array)
            if (min > v) {
                min = v;
            }

        for(int i=0; i<array.length; i++)
            if(min == array[i])
                return i;

            return (-1);

    }

    private boolean allFrozen()
    {
        for (LRU_handling lru_handling : processes)
            if (!lru_handling.isFrozen())
                return false;

            return true;
    }

    private boolean availabilityFrames()
    {
        return freeFrames.isEmpty();
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
