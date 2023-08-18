package JavaCourseWork;

public class Student extends Thread{

    private String name;
    private ThreadGroup group;
    private Printer printer;


    //creating the constructor
    public Student(String name,ThreadGroup group, Printer printer) {
        this.name = name;
        this.group = group;
        this.printer = printer;
    }

    @Override
    public void run() {

        Document []documents = new Document[5]; //Creating the document array
        documents[0] = new Document ("01","Document1", 2); //adding elements to the array
        documents[1] = new Document ("02","Document2",3);
        documents[2] = new Document ("03","Document3",7);
        documents[3] = new Document ("04","Document4",8);
        documents[4] = new Document ("05","Document5",10);
        int numOfPages =0;

        for(Document doc:documents){                        //checking and passing each documents to print documents method
            printer.printDocument(doc);
            numOfPages += doc.getNumberOfPages();
            try {
                int number = ((int)(Math.random()*100 + 1)); //generating the random number
                Thread.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(ConsoleColors.YELLOW+Thread.currentThread().getName()+ " Finished Printing 5 Documents, no of pages : " +numOfPages +ConsoleColors.RESET);
        }

    }
}