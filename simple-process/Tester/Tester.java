package Tester;

import Presentation.PresentationClass;
import Queue_FIFO.EmptyQueueException;
import Queue_FIFO.FullQueueException;

public class Tester {
    public static void main(String[] args) throws EmptyQueueException, FullQueueException {

        PresentationClass test = new PresentationClass();
        test.displayResult();
    }
}
