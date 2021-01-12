package Queue_FIFO;

public class FullQueueException extends Exception {

    public FullQueueException()
    {
        System.out.println("Queue is full");
    }
}
