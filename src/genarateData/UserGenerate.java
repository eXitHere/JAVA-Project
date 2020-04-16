package genarateData;

import dfts.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javafx.util.Pair;

public class UserGenerate {
    
    static List<String> name = new ArrayList<>();
    static List<String> password = new ArrayList<>();
    
    public static void readData(){
        Random random = new Random();
        String line;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/resources/generate_data/", "username.txt") , Charset.forName("UTF-8"))) {
            while ((line = reader.readLine()) != null ) {
                name.add(line);
            }
        }catch (IOException e) {
            System.err.println(e);
        }
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/resources/generate_data/", "password.txt") , Charset.forName("UTF-8"))) {
            while ((line = reader.readLine()) != null ) {
                password.add(line);
            }
        }catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public static String genPhone(){
        int leftLimit = '0'; // letter '0'
        int rightLimit = '9'; // letter '9'
        int targetStringLength = 9;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        buffer.append('0');
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
    
    public static void genUser(){
        Random randomInt = new Random();
        String phone = genPhone();
        LocalDate end = LocalDate.of(2007, Month.JANUARY, 1);
        LocalDate start = LocalDate.of(1930, Month.JANUARY, 1);
        LocalDate random = between(start, end);
        
        ZoneId defaultZoneId = ZoneId.systemDefault();
        
        Date date = Date.from(random.atStartOfDay(defaultZoneId).toInstant());
        
        SimpleDateFormat birthday =new SimpleDateFormat("MM/dd/yyyy");
        
        try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.user", phone))); ObjectInputStream oi = new ObjectInputStream(fi);){
            System.out.println("Duplicated!");
        }
        catch(Exception ex){
            User newUser = new User(name.get(Math.abs(randomInt.nextInt()%name.size())), 
                                    name.get(Math.abs(randomInt.nextInt()%name.size())),
                                    phone,
                                    birthday.format(date), 
                                    password.get(Math.abs(randomInt.nextInt()%password.size())));
            try(FileOutputStream f = new FileOutputStream(new File(String.format("src/resources/data/%s.user",phone)));ObjectOutputStream o = new ObjectOutputStream(f);){
                o.writeObject(newUser); 
                System.out.println("new user : " + newUser);
            }
            catch (Exception e){
                System.out.println("Create user fail!");
            }     
        }
    }
    
    public static LocalDate date() {
        int hundredYears = 100 * 365;
        return LocalDate.ofEpochDay(ThreadLocalRandom
          .current().nextInt(-hundredYears, hundredYears));
    }
    
    public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
          .current()
          .nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay);
    }
    
    public static void main(String[] args) {
        //readData();
        /*for(int i=0;i<2900;i++){
            genUser();
        }*/
    }
}
