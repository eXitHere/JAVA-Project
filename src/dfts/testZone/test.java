/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfts.testZone;
import dfts.Station;
import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.util.GregorianCalendar;

/**
 *
 * @author Thana
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class test extends Application{
    
    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception{
        
        Station station = new Station();
        FileInputStream inputstream = new FileInputStream("src/resources/images/stations/ss001.jpg");       
        Image logo = new Image(inputstream);
        //station.setImage(logo);
        station.setName("This is name");
        station.setPersen(20.512f);
        station.setRating(3);
        station.setReview("this is a good place\nHello World!");
        station.getVbox();
        stage.setTitle("Test");
        
        this.stop();
        
        try {
            FileOutputStream f = new FileOutputStream(new File("myObjects.station"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(station);

            o.close();
            f.close();
            
            FileInputStream fi = new FileInputStream(new File("myObjects.station"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            Station pr1 = (Station) oi.readObject();
            Scene scene = new Scene(pr1.getVbox());
            stage.setScene(scene);
            stage.show();
            System.out.println(pr1.toString());

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
                System.out.println("File not found");
        } catch (IOException e) {
                System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) throws Exception{
        launch(args);
    }

}
