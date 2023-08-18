package JavaCourseWork;

public class PrintingSystem {
    public static void main(String[] args) {

        //creating thread groups for students and technicians
        ThreadGroup studentGroup = new ThreadGroup( "Students");
        ThreadGroup technicianGroup = new ThreadGroup("Technicians");

        //creating the LaserPrinter Object
        LaserPrinter laserPrinter = new LaserPrinter("Printing 1",1,500,250,"DOC1",0,studentGroup);


        //Students Threads
        Thread student1 = new Thread(studentGroup,new Student("Student1",studentGroup,laserPrinter),"Student1");
        Thread student2 = new Thread(studentGroup,new Student("Student2",studentGroup,laserPrinter),"Student2");
        Thread student3 = new Thread(studentGroup,new Student("Student3",studentGroup,laserPrinter),"Student3");
        Thread student4 = new Thread(studentGroup,new Student("Student4",studentGroup,laserPrinter),"Student4");

        //Technician Threads
        Thread paperTechnician = new Thread(technicianGroup, new PaperTechnician("Paper technician",technicianGroup,laserPrinter),"Laser Technician");
        Thread tonerTechnician = new Thread(technicianGroup, new TonerTechnician("Toner technician",technicianGroup,laserPrinter),"Toner Technician");

        //starting Threads
        student1.start();
        student2.start();
        student3.start();
        student4.start();

        paperTechnician.start();
        tonerTechnician.start();

        try {
            student1.join();
            student2.join();        //use join keyword for run these threads individually
            student3.join();
            student4.join();

            paperTechnician.join();
            tonerTechnician.join();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //showing final statistics
        System.out.println();
        System.out.println(ConsoleColors.GREEN +"Printing has ended successfully...."+ ConsoleColors.RESET);
        System.out.println("+++++++++++++++++++++++++++++++Summary+++++++++++++++++++++++++++++++");
        System.out.println(ConsoleColors.GREEN+laserPrinter.toString()+ConsoleColors.RESET);

    }
}