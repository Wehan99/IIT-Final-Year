package JavaCourseWork;

import java.util.Random;

public class TonerTechnician extends Thread{

    private String name;
    private ThreadGroup group;
    private ServicePrinter printer;


    public TonerTechnician(String name, ThreadGroup group, ServicePrinter printer) {
        this.name = name;
        this.group = group;
        this.printer = printer;
    }

    @Override
    public void run(){
        int count = 0;
        Random random = new Random();       //creating random class

        for (int i=0; i <= 2; i++){
            printer.replaceTonerCartridge();

            //check  toner replaced if yes increase count
            if (((LaserPrinter)printer).isRePlaceToner()){
                count ++;
            }
            int MIN_TIME = 1000;            //generate random number between 1 - 5 seconds
            int MAX_TIME = 5000;
            int sleepingTime = MAX_TIME + random.nextInt(MAX_TIME - MIN_TIME);
            try {

                Thread.sleep(sleepingTime);         //set sleep
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Toner Technician Finished, cartridges replaced :  "+count);



    }


}
