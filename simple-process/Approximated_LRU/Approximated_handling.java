package Approximated_LRU;

import ArrayList.ArrayList;
import Objects_CPU.Frame;
import Objects_CPU.RequestWithChance;
import Queue_FIFO.EmptyQueueException;
import Queue_FIFO.FullQueueException;
import Queue_FIFO.Queue;

public class Approximated_handling {

    //here are frames (polymorphic array)
    private Frame[] frames;
    //here are requests with site number
    private ArrayList<RequestWithChance> requests;
    //auxiliary variable
    private RequestWithChance tmp;
    //how many errors reported during program operation (for comparing results)
    private int error;
    //auxiliary variable
    private RequestWithChance helpView;
    //FIFO queue to task
    private Queue<RequestWithChance> capabilities;

    public Approximated_handling(int howManyFrame, ArrayList<RequestWithChance> requests)
    {
        frames = new Frame[howManyFrame];
        createFrame();
        this.requests = requests;
        error = 0;
        capabilities = new Queue<>();
    }

    //fill the polymorphic array
    private void createFrame()
    {
        for(int i=0; i< frames.length; i++)
            frames[i] = new Frame();
    }


    public void handling() throws FullQueueException, EmptyQueueException {

        //follow as long as there are applications
        while(requests.isEmpty() )
        {
            //go to next requests
            newRequest();
            //search current frames
            if(!searchInCapabilities())
            {
                //check if any frame is unused
                if (checkAvailability()) {
                    frames[getFreeFrame()].setWhichcSite(tmp.getWhichSite());
                    frames[getFreeFrame()].setIsFree(false);
                    capabilities.enqueue(requests.get(0));
                    done();
                    errorIncrementation();
                }
                else
                {
                    //iteration through frames in the search for a frame without a chance
                    iterationCapabilities();
                    //request is done
                    done();
                }
            }
        }
    }

    private boolean searchInCapabilities() throws EmptyQueueException, FullQueueException {

        for (Frame frame : frames) {
            if (frame.getWhichSite() == tmp.getWhichSite()) {
                selectFromQueue(frame.getWhichSite());
                done();
                return true;
            }
        }
        return false;
    }

    private void iterationCapabilities() throws EmptyQueueException, FullQueueException {

        boolean orChange = false;

        while (!orChange)
        {
            //iteration by queue
            for (int i = 0; i < capabilities.size(); i++) {

                helpView = capabilities.dequeue();

                //if the downloaded item has a chance, change the bit and put it back to the queue
                if (helpView.getChance())
                {
                    helpView.setChance(false);
                    capabilities.enqueue(helpView);
                }
                else
                    {
                        searchInFrame();
                    helpView = tmp;
                    helpView.setChance(true);

                    orChange = true;
                    capabilities.enqueue(helpView);
                    errorIncrementation();
                    break;
                    }
            }
        }
    }

    private void searchInFrame()
    {
        for (Frame frame : frames) {
            if (frame.getWhichSite() == helpView.getWhichSite()) {
                frame.setWhichcSite(tmp.getWhichSite());
                break;
            }
        }

    }

    private void selectFromQueue(int site) throws EmptyQueueException, FullQueueException {

        ArrayList<RequestWithChance> helpMove = new ArrayList<RequestWithChance>();
        RequestWithChance help;

        for(int i=0; i<capabilities.size(); i++)
        {
            help = capabilities.dequeue();
            capabilities.enqueue(help);
        }


        for(int i=0; i<capabilities.size(); i++)
        {

            help = capabilities.dequeue();

            if(help.whichSite == site)
            {
                capabilities.enqueue(help);
                break;
            }

            helpMove.add(help);
        }

        for(int i = 0; i< helpMove.size(); i++)
        {
            capabilities.enqueue(helpMove.get(0));
            helpMove.remove(0);
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
