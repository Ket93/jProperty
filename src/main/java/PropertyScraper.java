import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PropertyScraper{
    public static ArrayList<House> houseList=new ArrayList<House>();

    public static void main(String[] args){
        scraper();
    }
    public static void scraper(){
        boolean last = false;
        int lineCounter = 0;
        int lastCounter = 0;
        String address,description,startDate,yearBuilt,price,numStories;
        address = price = description = startDate = yearBuilt = numStories = "";
        String numBeds,numBaths;
        numBeds = numBaths  = "";

        try{
            File housingFile = new File("Houses Listings.txt");
            Scanner fileReader = new Scanner(housingFile);
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                //System.out.println(last);
                if(line.equals(" ")){

                    houseList.add(new House(address,description,numStories,numBeds,numBaths,price,yearBuilt,startDate));
                    address = price = description = startDate = yearBuilt = numStories = "";
                    numBeds = numBaths = "";
                    lineCounter=0;
                    last = false;
                    //lastCounter++;
                    //System.out.println(description);
                    //System.out.println();
                }
                else if(last){
                    description+=" "+line;
                    //System.out.println(line);

                    //lastCounter = 0;

                }
                if(lineCounter == 1){

                    address = line;

                    //System.out.println("poop");
                }
                else if(lineCounter == 5){
                    startDate = line;

                }
                else if(lineCounter == 9){
                    numStories = line;

                }
                else if(lineCounter == 11){
                    numBeds = line;

                }
                else if(lineCounter == 13){
                    numBaths = line;

                }
                else if(lineCounter == 15){
                    price = line;

                }
                else if(lineCounter == 24){
                    yearBuilt = line;

                }
                else if(lineCounter == 42){
                    description = line;
                    //System.out.println();
                    //System.out.println(description);
                    last = true;

                }

                lineCounter++;
            }
            for(int i=0; i<100;i++){
               // System.out.println(house.getInfo());
                //System.out.println();
            }

        }
        catch(FileNotFoundException exception){
            exception.printStackTrace();
            System.out.println("error");
            System.exit(1);
        }
    }
    public static float randint(int low, int high){
        return (float)((int)(Math.random()*(high-low+1)+low));
    }
}
