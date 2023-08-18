package JavaCourseWork;

import java.io.Console;

public class LaserPrinter implements ServicePrinter{
    private String name;
    private int id;
    private int currentTonerLevel ;
    private int currentPaperLevel ;
    private String documentName;
    public int documentPrinted;
    private boolean reFillPaper= false;         //when a refill is done return this
    private boolean rePlaceToner = false;       //when a replace is done return this
    private ThreadGroup threadGroup;            //creating the thread group


    //creating the constructor
    public LaserPrinter(String name, int id, int currentTonerLevel, int currentPaperLevel, String documentName, int documentPrinted, ThreadGroup tg1) {
        super();
        this.name = name;
        this.id = id;
        this.currentTonerLevel = currentTonerLevel;
        this.currentPaperLevel = currentPaperLevel;
        this.documentName = documentName;
        this.documentPrinted = documentPrinted;
        this.threadGroup = tg1;

    }
    //return a boolean value that is thread group's active thread count zero or not
    private boolean isConfirmed(){
        return threadGroup.activeCount() == 0;
    }

    //return a boolean value that is paper refill or not
    public boolean isReFillPaper(){
        return reFillPaper;
    }

    //return a boolean value that is tonor replaced or not
    public boolean isRePlaceToner(){
        return rePlaceToner;
    }


    //printing method
    @Override
    public synchronized void printDocument(Document document) {
        //checking the printing process can be done or not
        while ( document.getNumberOfPages() >= currentPaperLevel || document.getNumberOfPages() >= currentTonerLevel){
            System.out.println( ConsoleColors.RED+"Toner, Paper level is low... Can not print documents..."+ConsoleColors.RESET);
            System.out.println("");

            try{
                //if while is true, wait
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        //reduce paper and toner level
        this.currentPaperLevel -= document.getNumberOfPages();
        this.currentTonerLevel -= document.getNumberOfPages();
        //increasing printed documents
        this.documentPrinted++;

        System.out.println(ConsoleColors.GREEN+Thread.currentThread().getName()+ " has Printed Document : "+document.getUserID()+" Successfully..."+ConsoleColors.RESET);
        //printing current states
        System.out.println("Current Toner Level = "+this.currentTonerLevel);
        System.out.println("Current Paper Level = "+this.currentPaperLevel);
        System.out.println(ConsoleColors.GREEN+"Printed Documents Count = "+ConsoleColors.RESET+documentPrinted);
        System.out.println("**********************************************************************************");
        notifyAll();


    }

    //replace method
    @Override
    public synchronized void replaceTonerCartridge() {
        this.rePlaceToner = false; //accessing rePlaceToner
        boolean isReplace = this.currentTonerLevel >= (Minimum_Toner_Level);
        //check current toner level is low to minimum toner level
        while (isReplace){
            System.out.println(ConsoleColors.RESET+"Minimum toner level not exceeded, Current toner level : "+currentTonerLevel+ConsoleColors.RESET);

            //if while is true wait 5s toner technician
                try {
                    wait(5000);
                    if (isConfirmed()){     //check there is any active thread or not, if yes goes down
                        return;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        //replacing the toner
        System.out.println(ConsoleColors.RED+"Current toner level is low, Toner level : "+currentTonerLevel+ConsoleColors.RESET);
        this.currentTonerLevel = Full_Toner_Level;
        rePlaceToner=true;          //set true rePLaceToner
        System.out.println(ConsoleColors.YELLOW+"Toner Refilled Successfully..!"+ConsoleColors.RESET);
        System.out.println("**********************************************************************************");

        notifyAll();




    }

    //refill method
    @Override
    public synchronized void refillPaper() {

        this.reFillPaper = false;   //accessing rePlaceToner
        boolean isReFill = this.currentPaperLevel >= (Full_Paper_Tray - SheetsPerPack);
        //checking the paper sheet should fill or not
        while (isReFill) {
            System.out.println(ConsoleColors.RESET+"Minimum Paper Level Not Exceeded, Current Paper Level : "+this.currentPaperLevel+ConsoleColors.RESET);

            try {
                //if while is true wait 5s paper technician
                wait(5000);
                if (isConfirmed()){     //check there is any active thread or not, if yes goes down
                    return;
                }
                //checking current paper count exceeding the minimum paper count
                isReFill = this.currentPaperLevel >= (Full_Paper_Tray - SheetsPerPack);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
        //if condition is fouls paper sheet will be refilled
        System.out.println(ConsoleColors.RED+"Current Paper level is low, Paper level : "+currentPaperLevel+ConsoleColors.RESET);
        this.currentPaperLevel += SheetsPerPack;
        reFillPaper = true;         //paper refilled 1 time, set reFillPaper true
        System.out.println(ConsoleColors.YELLOW+"Paper Refilled Successfully..!"+ConsoleColors.RESET);
        System.out.println("Current Paper Level : "+this.currentPaperLevel);
        System.out.println("**********************************************************************************");
        notifyAll();





    }
    //to string method
    @Override
    public String toString() {
        return "LaserPrinter{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", currentTonerLevel=" + currentTonerLevel +
                ", currentPaperLevel=" + currentPaperLevel +
                ", documentName='" + documentName + '\'' +
                ", documentPrinted=" + documentPrinted +

                '}';
    }


}
