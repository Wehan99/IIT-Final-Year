package JavaCourseWork;

import java.util.Random;

public class PaperTechnician extends Thread {
    private String name;
    private ThreadGroup group;
    private ServicePrinter printer;

    public PaperTechnician(String name, ThreadGroup group, ServicePrinter printer) {
        super();
        this.name = name;
        this.group = group;
        this.printer = printer;
    }

    @Override
    public void run() {
        Random random = new Random();       //creating random class

        int count = 0;
        for (int i = 0; i <= 2; i++){
            printer.refillPaper();

            if(((LaserPrinter)printer).isReFillPaper()){        //how many time paper refilled
                count++;
            }
            int MIN_TIME = 1000;            //generate random number between 1 - 5 seconds
            int MAX_TIME = 5000;
            int sleepingTime = MAX_TIME + random.nextInt(MAX_TIME - MIN_TIME);
            try {
                Thread.sleep(sleepingTime);             //set sleep that generated time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Paper Technician Finished, packs of paper used: " +count );
    }
}
