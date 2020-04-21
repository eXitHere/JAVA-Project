package genarateData;

import static genarateData.UserGenerate.between;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javafx.util.Pair;

public class ReviewGenerate {
    static List<String> nameStation = new ArrayList<>();
    static List<String> listPhone = new ArrayList<>();
    static List<String> comment = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        readData();
        LoadComment();
        LoadUserFromDB();
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        LocalDate _start = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate _end = LocalDate.now();
        LocalDate randomDate = between(_start, _end);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        for(var x : nameStation){
        for(int i=0;i<10;i++){
            Date date = Date.from(randomDate.atStartOfDay(defaultZoneId).toInstant());
            SaveToDB(String.format("%s___%s___%s___%s|", listPhone.get(Math.abs(random.nextInt())%listPhone.size()),
                    comment.get(Math.abs(random.nextInt())%comment.size()),
                    Math.abs(random.nextInt())%5+6,
                    dateFormat.format(date))
                    ,x);
        }
        for(int i=0;i<20;i++){
            Date date = Date.from(randomDate.atStartOfDay(defaultZoneId).toInstant());
            SaveToDB(String.format("%s___%s___%s___%s|", listPhone.get(Math.abs(random.nextInt())%listPhone.size()),
                    comment.get(Math.abs(random.nextInt())%comment.size()),
                    Math.abs(random.nextInt())%8+3,
                    dateFormat.format(date))
                    ,x);
        }
        for(int i=0;i<10;i++){
            Date date = Date.from(randomDate.atStartOfDay(defaultZoneId).toInstant());
            SaveToDB(String.format("%s___%s___%s___%s|", listPhone.get(Math.abs(random.nextInt())%listPhone.size()),
                    comment.get(Math.abs(random.nextInt())%comment.size()),
                    Math.abs(random.nextInt())%10,
                    dateFormat.format(date))
                    ,x);
        }}
        //System.out.println(nameStation);
    }
    
    static void SaveToDB(String out,String station){
        System.out.println(out);
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        try(FileWriter fw=new FileWriter(String.format("src/resources/data/%s.review", station),true);){  
            for (int i = 0; i < out.length(); i++) 
                fw.write(out.charAt(i));
        }
        catch(Exception e){
            
        }
        // </editor-fold>;
    }
    
    static void LoadComment() throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("src/resources/generate_data/comment.txt"));
        while (scanner.hasNextLine()) {
           String line = scanner.nextLine();
           comment.add(line);
           // process the line
        }
    }
    
    static void LoadUserFromDB(){
        listPhone = new ArrayList<>(); // Clear
        File file = new File("src/resources/data/");
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.toLowerCase().endsWith(".user")){
                    return true;
                } else {
                    return false;
                }
            }
        });
        for(File f:files){
            String[] name = ((String)f.getName()).split(".user");
                listPhone.add(name[0]);
        }
    }
    
    private static void readData() throws FileNotFoundException, IOException{
        String line;
        List<String> bufReturn;
        bufReturn = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/resources/data/", "data.txt") , Charset.forName("UTF-8"))) {
            while ((line = reader.readLine()) != null ) {
              //separate all csv fields into string array
                //String[] lineVariables = line.split(","); 
                //System.out.println(Arrays.toString(lineVariables));
                bufReturn.add(line);
            }
        }catch (IOException e) {
            System.err.println(e);
        }
        nameStation = Arrays.asList(bufReturn.get(0).split(","));
    }
}
