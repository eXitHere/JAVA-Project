
package genarateData;

import dfts.MapCode;
import static genarateData.UserGenerate.between;
import static genarateData.UserGenerate.genPhone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

public class SaleGenerate {
    static List<String> member;
    static List<String> nameStation = new ArrayList<>();
    static List<String> nameRailway = new ArrayList<>();
    static List<List<String>> subRailway = new ArrayList<>();
    static List<List<String>> timeRailway = new ArrayList<>();
    static List<Integer> price = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        readData();
        loadMember();
        Random random = new Random();
        for(int i=0;i<10000;i++){
            //System.out.println(Math.abs(random.nextInt()%member.size()));
            genSale(member.get(Math.abs(random.nextInt()%member.size())),true);
        }
        for(int i=0;i<1;i++){
            //System.out.println(Math.abs(random.nextInt()%member.size()));
            String phone = genPhone();
            genSale(phone,isMember(phone));
        }
    }
    
    private static boolean isMember(String phone){
        for(var x : member){
            if(x.equals(phone)) return true;
        }
        return false;
    }
    
    private static void genSale(String phone,boolean isMember){
        MapCode mc = new MapCode(nameStation, nameRailway, subRailway, timeRailway);
        Random random = new Random();
        String start = nameStation.get(Math.abs(random.nextInt()%nameStation.size()));
        String stop;
        do{
            stop = nameStation.get(Math.abs(random.nextInt()%nameStation.size()));
        }while(start.equals(stop));
        var output = mc.getPath(start,stop);
        int indexOutput = Math.abs(random.nextInt())%output.getValue().size();
        LocalDate _start = LocalDate.of(2019, Month.JULY, 1);
        LocalDate _end = LocalDate.now();
        LocalDate randomDate = between(_start, _end);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        
        Date date = Date.from(randomDate.atStartOfDay(defaultZoneId).toInstant());
        if(date.getDay()!=4) return;
        //System.out.println(output.getKey().getKey());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        int count = Math.abs(random.nextInt())%10+1;
        if(isMember){
            SaveToDB(String.format("%s_%s_%s_%d_%s_%.2f",phone
                                                          ,mc.getName(output.getKey().getKey().get(indexOutput).get(0))
                                                          ,mc.getName(output.getKey().getKey().get(indexOutput).get(output.getKey().getKey().get(indexOutput).size()-1))
                                                          ,count
                                                          ,dateFormat.format(date)
                                                          ,output.getValue().get(indexOutput)*0.95*count));
        }
        else{
            SaveToDB(String.format("%s_%s_%s_%d_%s_%.2f",genPhone()
                                                          ,mc.getName(output.getKey().getKey().get(indexOutput).get(0))
                                                          ,mc.getName(output.getKey().getKey().get(indexOutput).get(output.getKey().getKey().get(indexOutput).size()-1))
                                                          ,count
                                                          ,dateFormat.format(date)
                                                          ,output.getValue().get(indexOutput)*1.00*count));
        }
    }
    
    private static void SaveToDB(String out){
        out += '\n';
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        try(FileWriter fw=new FileWriter("src/resources/data/sale.bin",true);){  
            for (int i = 0; i < out.length(); i++) 
                fw.write(out.charAt(i));
        }
        catch(Exception e){
            
        }
        // </editor-fold>;
    }
    
    private static void loadMember() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        member = new ArrayList<>(); // Clear list
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
            //System.out.println(f.getName());
            String[] name = ((String)f.getName()).split(".user");
            member.add(name[0]);
            //System.out.println("member : " + name[0]);
        }
        // </editor-fold>;
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
        nameRailway = Arrays.asList(bufReturn.get(1).split(","));
        int lastIndex = 2;
        for (String nameRailway1 : nameRailway) {
            subRailway.add(Arrays.asList(bufReturn.get(lastIndex).split(",")));
            lastIndex++;
        }
        for (String nameRailway1 : nameRailway) {
            timeRailway.add(Arrays.asList(bufReturn.get(lastIndex).split(",")));
            lastIndex++;
        }
    }
}
