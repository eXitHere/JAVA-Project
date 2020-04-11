package dfts.testZone;

import dfts.Station;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class WriteStationToFile {
    public static void main(String[] args) throws IOException {
        build();
    }
    
    public static void build() throws FileNotFoundException, IOException{
        Random randome = new Random();
        String line;
        ArrayList bufReturn = new ArrayList<>();
        List<String> tempName = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/resources/data/", "data.txt") , Charset.forName("UTF-8"))) {
            tempName = Arrays.asList(reader.readLine().split(","));
        }catch (IOException e) {
            System.err.println(e);
        }
        int index =0;
        System.out.println(tempName);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/resources/data/", "reviews.txt") , Charset.forName("UTF-8"))) {
            
            while((line = reader.readLine())!=null){
                String[] n = line.split(":");
                //System.out.println(n[0] + " " + n[1]);

                Station station = new Station();
                station.setName(n[0]);
                station.setPersen((Math.random()*10));
                station.setRating(Math.abs(randome.nextInt()%10)+1);
                station.setReview(n[1]);
                station.setPath("src/resources/images/stations/"+String.format("ss%04d.jpg", index+1));
                //System.out.println(station.toString());
                FileOutputStream f = new FileOutputStream(new File(String.format("src/resources/data/%s.station", n[0])));
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(station); 
                o.close();
                f.close();
                index++;
                
            }
            
        }catch (IOException e) {
            System.err.println(e);
        }
        
        try(FileInputStream fi = new FileInputStream(new File("src/resources/data/stations.station"))){
            ObjectInputStream oi = new ObjectInputStream(fi);
            Station pr1;
            while((pr1 = (Station) oi.readObject())!=null){
                System.out.println(pr1.toString());
            }
            oi.close();
            fi.close();
        }
        catch (Exception ex){
            
        }
    }
}
