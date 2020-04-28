import java.io.File;
import java.util.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
import java.util.logging.FileHandler;


public class Bankers {
     private static Logger logger = Logger.getLogger("log-to-file");

    public static void main(String[] args) throws IOException {
        int no_Ofprocess = 4;//no. of processes in system//
        int no_Ofresources = 5;//no.of max resources available//

        //the following are current allocated resources to process P1 to P4//
        int[][] Allocated = {{0, 1, 1, 1, 2},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {2, 1, 0, 0, 0}};

        //the following are currently allocated resources to each process//
        int[][] requested = {{1, 1, 0, 2, 1},
                {0, 1, 0, 2, 1},
                {0, 2, 0, 3, 1},
                {0, 2, 1, 1, 0}};


        //the following are max resources//
        int[] max = new int[]{2, 4, 1, 4, 4};
        //the following are current available resource//
        int[] available = new int[]{0, 1, 0, 2, 1};

        //to mark if the process is finished//
        int[] finish = new int[]{0, 0, 0, 0, 0};

        //temporary array//
        int[] temp = new int [no_Ofresources];

        int countSafe = 0;//number of safe processes
        //following is output log file//
        FileHandler fh =  new FileHandler("C:\\Users\\Ganga\\Desktop\\First quarter assgnments\\Operating sytems\\OS project and assignment\\Assignment 3\\logfile.log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);



            while(countSafe < no_Ofprocess)
            {
                boolean check = false;
                for (int i = 0; i < no_Ofprocess; i++)
                {
                    if (finish[i] == 0)
                    {
                        int j;
                        for (j = 0; j < no_Ofresources; j++)
                        {
                            if (requested[i][j] > available[j])//checcking if available has enough resources to give//
                                break;

                        }
                            if(j == no_Ofresources)
                            {
                                temp[countSafe++] = i;
                                finish[i] = 1;
                                check = true;
                                for (j = 0;j < no_Ofresources;j++)
                                {
                                    //after procecss has finished execution it will allocate resources back to available.
                                    available[j] = requested[i][j]+Allocated[i][j];

                                }
                                logger.info("Process" + (i+1) + "has finished execution");

                            }

                        }

                    }
                    if (check == false)
                    {

                        break;
                    }
                }

            if (countSafe < no_Ofprocess)
            {

                logger.severe("!!!!The system is unsafe!!!!");//if all process do not finish then the system is unsafe
                for (int i = 0; i < no_Ofprocess;i++){
                    if (finish[i] == 0){
                        logger.info("the process " +(i+1)+ " is not been executed");
                    }
                }
            }
            else
            {
                logger.info("The safe sequence of process is:");


            }

    }
}