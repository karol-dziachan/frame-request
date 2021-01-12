package Presentation;


import ArrayList.ArrayList;
import Dependent_On_Errors_Frequency.Dependent;
import Equal_Allocation.Equal;
import Objects_CPU.Request;
import Proportionally_Allocation.Proportionally;
import Zone_Model.ZoneModel;

import java.util.Scanner;

public class PresentationClass {

    private final ArrayList<ArrayList<Request>> processDependent;
    private final ArrayList<ArrayList<Request>> processEqual;
    private final ArrayList<ArrayList<Request>> processProportionally;
    private final ArrayList<ArrayList<Request>> processZone;


    private final Equal equal;
    private final Proportionally proportionally;
    private final Dependent dependent;
    private final ZoneModel zoneModel;

    private int howManyProcesses;
    private int howManyFrame;
    private int sites;
    private int range;
    private int timeWindow;
    private double maximumPPF;
    private double minimumPPF;
    private int maxTimeFrozen;

    private final Scanner input;


    public PresentationClass()
    {
        processDependent = new ArrayList<ArrayList<Request>>();
        processEqual = new ArrayList<ArrayList<Request>>();
        processProportionally = new ArrayList<ArrayList<Request>>();
        processZone = new ArrayList<ArrayList<Request>>();

        input = new Scanner(System.in);

        setCondition();
        createRequest();

        equal = new Equal(howManyProcesses, howManyFrame, processEqual);
        proportionally = new Proportionally(howManyProcesses, howManyFrame, processProportionally);
        dependent = new Dependent(howManyProcesses, howManyFrame, timeWindow, maximumPPF, minimumPPF, processDependent);
        zoneModel = new ZoneModel(howManyProcesses, howManyFrame, timeWindow, maxTimeFrozen, processZone);
    }

    private void setCondition()
    {
        System.out.println("How many process do you want?");
        this.howManyProcesses = input.nextInt();
        System.out.println("How many frames do you want to have in all processes?");
        this.howManyFrame = input.nextInt();
        System.out.println("How much range of pages do u want to choose from?");
        this.sites = input.nextInt();
        System.out.println("Specify the range of request sizes for one process?");
        this.range = input.nextInt();


        System.out.println("Specify the range of time window (dependent algorithm and zone model)");
        this.timeWindow = input.nextInt();
        System.out.println("What is the range of the maximum freezing time of the process");
        this.maxTimeFrozen = input.nextInt();
        System.out.println("Specify the range of PPF");
        System.out.print("Maximum PPF: ");
        this.maximumPPF = input.nextDouble();
        System.out.print("Minimum PPF: ");
        this.minimumPPF = input.nextDouble();
    }

    private void createRequest()
    {

        ArrayList<Request> request;

        for(int i=0; i<howManyProcesses; i++)
        {
            request = new ArrayList<Request>();

            //this random ensure different number of requests in processes
            for(int j = 0; j< (int) ((Math.random()*range)+30); j++)
            {
                int tmp = (int) ((Math.random()*sites)+1);
                request.add(new Request(tmp));
            }

            this.processZone.add(request);
            this.processDependent.add(request);
            this.processProportionally.add(request);
            this.processEqual.add(request);
        }

    }


    private void handling()  {
        equal.handling();
        proportionally.handling();
        dependent.handling();
        zoneModel.handling();
    }

    public void displayResult()  {
        handling();

        System.out.println("\nEqual algorithm: " + equal.getError() + " errors");
        System.out.println("did the pages struggle during the algorithm? " + equal.getStruggle());

        System.out.println("Proportionally algorithm: " + proportionally.getError() + " errors");
        System.out.println("did the pages struggle during the algorithm? " + proportionally.getStruggle());

        System.out.println("Dependent algorithm: " + dependent.getError() + " errors");
        System.out.println("did the pages struggle during the algorithm? " + dependent.getStruggle());

        System.out.println("Zone model: " + zoneModel.getError() + " errors");
        System.out.println("did the pages struggle during the algorithm? " + zoneModel.getStruggle());


    }
}
