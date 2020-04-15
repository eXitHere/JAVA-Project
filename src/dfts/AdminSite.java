package dfts;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Scanner;

public class AdminSite {
    Stage body = new Stage();
    Scene scene;

    public AdminSite(Stage primary){
        String str = "admin,password";
  
        // attach a file to FileWriter  
        try{
            FileWriter fw=new FileWriter("src/resources/data/admin.bin"); 
  
            // read character wise from string and write  
            // into FileWriter  
            for (int i = 0; i < str.length(); i++) 
                fw.write(str.charAt(i)+15); 

            System.out.println("Writing successful"); 
            //close the file  
            fw.close(); 
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        
        int ch; 
  
        // check if File exists or not 
        FileReader fr=null; 
        try
        { 
            fr = new FileReader("src/resources/data/admin.bin"); 
            while ((ch=fr.read())!=-1) 
            System.out.print((char)(ch-15)); 
  
            // close the file 
            fr.close(); 
        } 
        catch (Exception e) 
        { 
            System.out.println("File not found"); 
        } 
  
        // read from FileReader till the end of file 
        
        
        this.body.setOnHidden((e)->{
            primary.show();
        });
        
        GridPane pane = new GridPane();
        this.scene = new Scene(pane,1280,720);
        this.body.setScene(scene);
    }    
    
    private void authen(String user,String pass){
        
    }
    
    public void show(){
        this.body.show();
    }
}
