package dfts.testZone;

import dfts.TimeTableUi;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Thana
 */
public class TimeTableDrive extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        List<String> nameStation = new ArrayList<>();
        List<String> nameRailway = new ArrayList<>();
        List<List<String>> subRailway = new ArrayList<>();
        List<List<String>> timeRailway = new ArrayList<>();
        readData(nameStation,nameRailway,subRailway,timeRailway);
        
        TimeTableUi ui = new TimeTableUi(nameStation,nameRailway,subRailway,timeRailway);
        
 //       System.out.println(subRailway.get(0));
        
//        for(var x : subRailway){
//            for(var y : x){
//                System.out.print(y + " ,");
//            }
//            System.out.println("");
//        }
        
       // TimeTableUi ui = new TimeTableUi();
        stage.setScene(new Scene(ui.getBody(),1080,720));
        stage.show();
    }
    
    private static void readData(List<String> nameStation,List<String> nameRailway,List<List<String>> subRailway,List<List<String>> timeRailway) throws FileNotFoundException, IOException{
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

    private void TimeTableUi(List<String> nameStation, List<String> nameRailway, List<List<String>> subRailway, List<List<String>> timeRailway) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
