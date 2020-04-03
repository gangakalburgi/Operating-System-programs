import java.util.Scanner;

public class ShortestJobFirstProcessor {

    private int[][] processMatrix;
    private int processesCount;
    private int startTime = 0;

    private ShortestJobFirstProcessor(int count) {
        this.processesCount = count;
        processMatrix = new int[processesCount][7];
    }

    private void readProcessDetails() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < processesCount; i++) {
            System.out.println("enter Process id");
            int processId = sc.nextInt();
            System.out.println("enter Arrival time for Process id : " + processId);
            int arrivalTime = sc.nextInt();
            System.out.println("enter Burst time for Process id : " + processId);
            int burstTime = sc.nextInt();
            processMatrix[i][0] = processId;
            processMatrix[i][1] = arrivalTime;
            processMatrix[i][2] = burstTime;
        }
    }

    private void executeInitialProcess() {
        for (int i = 0; i < processesCount; i++) {
            int init = 0;
            int minBurstTime = getMinBurstTimeBasedOnMin(processMatrix, init, processesCount);

            if (processMatrix[i][1] == init && processMatrix[i][6] == 0 && processMatrix[i][2] == minBurstTime) //checking first process and flag stored in 6th column
            {
                int burstTime = processMatrix[i][2];
                int arrivalTime = processMatrix[i][1];

                // completion time calculation
                processMatrix[i][3] = init + burstTime;
                int completionTime = processMatrix[i][3];

                //turn around time calculation
                processMatrix[i][4] = completionTime - arrivalTime;
                int turnAroundTime = processMatrix[i][4];

                //calculating waiting time.
                processMatrix[i][5] = turnAroundTime - burstTime;

                processMatrix[i][6] = 1; //flagging the process is complete.

                startTime = processMatrix[i][3]; //startTime for next process
            }
        }
    }

    private void executeRemainingProcess() {
        for (int j = 0; j < processesCount; j++) {
            //if 2 process arrive at same init time then execute one with min BT
            int minBurstTime = checkMinBurstTime(processMatrix, processesCount);
            //also check if 2 process having same burst time then check for their arrival
            int minAT = checkingFirstArrival(processMatrix, minBurstTime, processesCount);

            for (int i = 0; i < processesCount; i++) {
                if (processMatrix[i][6] == 0 && processMatrix[i][2] == minBurstTime && processMatrix[i][1] == minAT || processMatrix[i][6] == 0 && processMatrix[i][2] == minBurstTime) //check if process is execute or not
                {
                    processMatrix[i][3] = startTime + processMatrix[i][2];//calculating completion time
                    startTime = processMatrix[i][3];
                    processMatrix[i][4] = processMatrix[i][3] - processMatrix[i][1]; //turn around time calculation
                    processMatrix[i][5] = processMatrix[i][4] - processMatrix[i][2]; //calculating waiting time.
                    processMatrix[i][6] = 1; //flagging the process is complete.
                }
            }
        }
    }

    private void calculateAvgTimesDisplay() {
        System.out.println("Processid\t Arrivaltime\tBursttime\tCompletiontime\tturnaroundtime\twaiting time");
        for (int i = 0; i < processesCount; i++) {
            System.out.println(processMatrix[i][0] + "\t\t\t\t" + processMatrix[i][1] + "\t\t\t\t" + processMatrix[i][2] + "\t\t\t\t" + processMatrix[i][3] + "\t\t\t\t" + processMatrix[i][4] + "\t\t\t\t" + processMatrix[i][5]);
        }

        int avgCompTime;
        int avgTat;
        int avgWT;
        int totalcomptime = 0;
        int totaltat = 0;
        int totalwt = 0;

        for (int i = 0; i < processesCount; i++) {
            totalcomptime = totalcomptime + processMatrix[i][3];
            totaltat = totaltat + processMatrix[i][4];
            totalwt = totalwt + processMatrix[i][5];
        }
        avgCompTime = totalcomptime / processesCount;
        avgTat = totaltat / processesCount;
        avgWT = totalwt / processesCount;
        System.out.println("Average Completion Time : " + avgCompTime);
        System.out.println("Average Turn Around Time : " + avgTat);
        System.out.println("Average Waiting Time : " + avgWT);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hi Human :) ");
        System.out.println("Enter number of processes:");
        int processesCount = sc.nextInt();
        ShortestJobFirstProcessor sjf = new ShortestJobFirstProcessor(processesCount);

        sjf.readProcessDetails();

        sjf.executeInitialProcess();

        sjf.executeRemainingProcess();

        sjf.calculateAvgTimesDisplay();
    }


    private static int checkMinBurstTime(int[][] array, int n) {
        int min = 9999;
        for (int i = 0; i < n; i++) {
            if (array[i][2] <= min && array[i][6] == 0) {
                min = array[i][2]; //return min burst time.
            }
        }
        return min;
    }

    private static int checkingFirstArrival(int[][] array, int min, int n) {
        int minArrivalTime = 0;
        for (int i = 0; i < n; i++) {
            if (array[i][2] == min && array[i][6] == 0) {
                for (int j = i; j < n - 1; j++) {
                    if (array[j][2] == min) {
                        minArrivalTime = array[j][1];
                    }
                }
            }

        }
        return minArrivalTime;
    }

    private static int getMinBurstTimeBasedOnMin(int[][] array, int min, int n) {
        int minBurstTime = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (array[i][1] == min && array[i][6] == 0) {
                minBurstTime = Math.min(minBurstTime, array[i][2]);
            }

        }
        return minBurstTime;
    }
}
