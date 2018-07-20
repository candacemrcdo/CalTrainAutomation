package Thread;

public abstract class RunnableThread implements Runnable {

    private Thread t;
    private String threadName;

    RunnableThread(String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    @Override
    public abstract void run();

    public void start () {
        System.out.println("Starting " +  threadName);
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }


}