package Zone_Model;

import ArrayList.ArrayList;
import LRU.LRU_handling;
import Objects_CPU.Frame;
import Objects_CPU.Request;

public class ZoneModel {
    //I tried to make the names suggest the purpose of the variable
    private final LRU_handling[] processes;
    private final int howManyProcesses;
    private final int howManyFrame;
    private final ArrayList<ArrayList<Request>> process;
    private int error;
    private int unitTime;
    private final int timeWindow;
    private final int maxTimeFrozen;
    //list to detention free frames (released from frozen processes)
    private final ArrayList<Frame> freeFrames;


    public ZoneModel(int howManyProcesses, int howManyFrame, int timeWindow, int maxTimeFrozen, ArrayList<ArrayList<Request>>  process)
    {
        this.timeWindow = timeWindow;
        this.process = process;
        this.maxTimeFrozen = maxTimeFrozen;
        this.howManyFrame = howManyFrame;
        this.howManyProcesses = howManyProcesses;

        processes = new LRU_handling[howManyProcesses];
        freeFrames = new ArrayList<Frame>();

        error = 0;
        unitTime = 0;

        allocateFrames();
    }


    private void createFrame(int index, int howManyFrame)
    {
        processes[index] = new LRU_handling(howManyFrame, process.get(index));
    }

    //at the beginning, the algorithm assigns 2 frames to each algorithm to collect WSS statistics
    private void allocateFrames()
    {
        int framesForOneProcess = 2;

        for(int i=0; i < howManyProcesses; i++)
            createFrame(i, framesForOneProcess);

    }

    public void handling()
    {
        while(!checkAll())
        {
            incrementTimeDefrost();
            defrost();
            haveToDefrost();

            int tmp = (int) (Math.random() * howManyProcesses);
            if(!processes[tmp].isFrozen())
                if(!checkDone(tmp))
                    processes[tmp].handling();
                unitTime++;

                if(unitTime%timeWindow==0)
                {
                    calculateOrder();
                }
        }
    }

    private void calculateOrder() {
        for (int i = 0; i < howManyProcesses; i++)
        {
            if (processes[i].getOrder() > howManyFrame) {
                processes[i].setFrozen(true);
                takeTheFrames(i);
            }
            processes[i].setOrder();
    }
    }

    private void takeTheFrames(int index)
    {
        for(int i=0; i<processes[index].frozenZone(); i++)
        freeFrames.add(new Frame());

        giveTo();
    }

    private void giveTo()
    {
        for(int i=0; i<howManyProcesses; i++)
        {
            if(!processes[i].isFrozen()) {

                int percent = freeFrames.size() / process.get(i).size();
                processes[i].zoneMore(percent * freeFrames.size());
            }
        }
    }

    private void incrementTimeDefrost()
    {
        for (int i =0; i < howManyProcesses; i++)
            if(processes[i].isFrozen())
                processes[i].timeDefrostIncrementation();
    }

    private void defrost() {
        for (LRU_handling lru_handling : processes) {
            if (lru_handling.isFrozen() && availabilityFrames()) {
                if (lru_handling.getTimeDefrost() >= maxTimeFrozen) {
                    lru_handling.setFrozen(false);
                    for (int j = 0; j < freeFrames.size(); j++) {
                        lru_handling.changeFramesMore(freeFrames.get(0));
                        freeFrames.remove(0);
                    }

                }
            }
        }
    }

    private boolean allFrozen()
    {
        for (LRU_handling lru_handling : processes)
            if (!lru_handling.isFrozen())
                return false;

        return true;
    }


    private void haveToDefrost()
    {
        if(allFrozen())
        {
            processes[searchMin()].setFrozen(false);
        }
    }

    private int searchMin()
    {
        int min = processes[0].getOrder();
        for (LRU_handling lru_handling : processes)
            if (min > lru_handling.getOrder())
                min = lru_handling.getOrder();
        return min;
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
        return error == 0.3*sum();
    }
}
