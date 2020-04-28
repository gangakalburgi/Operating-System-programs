import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


class PageFaultAging {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        int memoryReferences = 1000;
        int zeroBit = 0;
        int onlySignificantBitSet = 128;
        System.out.println("Number of page frames : ");
        int pageFrames = sc.nextInt();
        sc.close();

        File file = new File("C:\\Users\\Ganga\\Documents\\page_numbers.txt");
        Scanner scan = new Scanner(file);
        int [] pages = new int[1000];
        int [][] frame_faults = new int[pageFrames][2];

        int [][] memory = new int[8][2];


  
        PrintWriter writer = new PrintWriter("C:\\Users\\Ganga\\Desktop\\try.txt");

       //generating 10 page number sequence
        for(int i = 0 ; i < 1000 ; i++)
        {
            int k = (int)(Math.floor(Math.random()*pageFrames));

            writer.println(k+1);
        }
        writer.close();


        //scanning file
        while(scan.hasNextLine()){
            String s = scan.nextLine().trim();
            page[low] = Integer.parseInt(s);
            low++;
        }

        scan.close();
        for(int i : page)
        {
            boolean stillFound = false;
            boolean dobreak = false;
		    //search the memory for page reference
		    //if found, increment.
		    //else, decrement.
		   //every input is one clk .

            while(!stillFound){
                for(int []m : memory)
                {
                    if(m[0] == i)
                    {
//		    			page found increase the count and break.
                        m[1] = m[1] | high;
                        stillFound = false;
                        dobreak = true;
                        break;
                    }
                }
                stillFound=true;
            }
            if(stillFound == true && !dobreak){
		    	//memory does not contain, encounter page fault and shift the bits

                for( int [] m : memory){
                    m[1] = m[1] >> 1;
                }

                //after performing right shift, find the min value and replace

                int lowest = 0;
                for(int j = 0 ; j < 7; j++){
                    if(memory[j][1] > memory[j+1][1]){
                        lowest = j+1;
                    }
                }

                memory[lowest][0] = i;
                memory[lowest][1] = 128;

                //increment the page fault
                frame_faults[i-1][0]=i;
                frame_faults[i-1][1]=frame_faults[i-1][1]+1;
            }


        }

        /*System.out.println("memory");
        for(int []h : memory){
            System.out.println(h[0]+"    "+h[1]);
        }*/


        System.out.println("page faults occured:");
        for(int []h : frame_faults){
            if(h[0] > 0){

                System.out.println("page number " + h[0]+"    "+"page_fault_count:" +h[1]);
               // System.out.println(h[0]);

            }
        }

    }



}