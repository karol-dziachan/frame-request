package Presentation;


import Approximated_LRU.Approximated_handling;
import ArrayList.ArrayList;
import FIFO.FIFO_handling;
import LRU.LRU_handling;
import OPT.OPT_handling;
import Objects_CPU.Request;
import Objects_CPU.RequestWithChance;
import Queue_FIFO.EmptyQueueException;
import Queue_FIFO.FullQueueException;
import Random.Random_handling;

public class PresentationClass {
    private ArrayList<Request> requestsRandom;
    private ArrayList<Request> requestsFIFO;
    private ArrayList<Request> requestOPT;
    private ArrayList<Request> requestLRU;
    private ArrayList<RequestWithChance> requestsApproximated;

    private Random_handling random;
    private FIFO_handling fifo;
    private OPT_handling opt;
    private LRU_handling lru;
    private Approximated_handling approximated;

    public PresentationClass()
    {
        requestsApproximated = new ArrayList<RequestWithChance>();
        requestsRandom= new ArrayList<Request>();
        requestsFIFO=new ArrayList<Request>();
        requestOPT =new ArrayList<Request>();
        requestLRU=new ArrayList<Request>();

        createRequest();
        fifo = new FIFO_handling(10, requestsFIFO);
        random = new Random_handling(10, requestsRandom);
        opt = new OPT_handling(10, requestOPT);
        lru = new LRU_handling(4, requestLRU);

        approximated = new Approximated_handling(4, requestsApproximated);
    }

    private void createRequest()
    {
      for (int i=0; i<50; i++)
        {
            int tmp = ((int) ((Math.random()*20)+0));
            requestsApproximated.add(new RequestWithChance(tmp));
            requestsRandom.add(new Request(tmp));
            requestsFIFO.add(new Request(tmp));
            requestOPT.add(new Request(tmp));
            requestLRU.add(new Request(tmp));
        }


        /*requestsApproximated.add(new RequestWithChance(1));
        requestsApproximated.add(new RequestWithChance(2));
        requestsApproximated.add(new RequestWithChance(3));
        requestsApproximated.add(new RequestWithChance(4));
        requestsApproximated.add(new RequestWithChance(1));
        requestsApproximated.add(new RequestWithChance(2));
        requestsApproximated.add(new RequestWithChance(5));
        requestsApproximated.add(new RequestWithChance(3));
        requestsApproximated.add(new RequestWithChance(2));
        requestsApproximated.add(new RequestWithChance(1));
        requestsApproximated.add(new RequestWithChance(4));
        requestsApproximated.add(new RequestWithChance(5));*/

       /* requestLRU.add(new Request(1));
        requestLRU.add(new Request(2));
        requestLRU.add(new Request(3));
        requestLRU.add(new Request(4));
        requestLRU.add(new Request(1));
        requestLRU.add(new Request(2));
        requestLRU.add(new Request(5));
        requestLRU.add(new Request(1));
        requestLRU.add(new Request(2));
        requestLRU.add(new Request(3));
        requestLRU.add(new Request(4));
        requestLRU.add(new Request(5));*/


    }
    private void handling() throws EmptyQueueException, FullQueueException {
       approximated.handling();
       fifo.handling();
       random.handling();
       opt.handling();
       lru.handling();
    }

    public void displayResult() throws EmptyQueueException, FullQueueException {
        handling();

        System.out.println("FIFO errors: " + fifo.getError());
        System.out.println("OPT errors: " + opt.getError());
        System.out.println("RANDOM errors: " + random.getError());
        System.out.println("LRU errors: " + lru.getError());
        System.out.println("Approximated (LRU) errors: " + approximated.getError());
    }
}
