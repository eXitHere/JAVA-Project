package dfts.testZone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class testWriteFileOBJ implements Serializable{
    
    private String pathImage = "src/resources/images/stations/ss001.jpg";
    private VBox vbox;
    
    
    public testWriteFileOBJ() throws FileNotFoundException {
        Image image;
        try(FileInputStream file =  new FileInputStream(new File(this.pathImage))){
            image = new Image(file);
            System.out.println(image);
        }
        catch(Exception e){
            image = null;
        }
        
    }
    
    void setImage(String path){
        this.pathImage = path;
    }
    
    @Override
    public String toString() {
        return "Hello World! " + this.vbox;
    }
    
}
