package dfts.testZone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.scene.image.Image;

public class DriveWriteFileOBJ {
    public static void main(String[] args) throws FileNotFoundException{
        FileInputStream inputstream = new FileInputStream("src/resources/images/stations/ss001.jpg");       
        Image logo = new Image(inputstream);
        testWriteFileOBJ mb = new testWriteFileOBJ();
        //mb.setImage("");
        try
        {
            FileOutputStream fileOut = new FileOutputStream("test.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mb);
            out.close();
            fileOut.close();
        } 
        catch (IOException i) 
        {
            i.printStackTrace();
        }
 
        // De-serialization code
        testWriteFileOBJ deserializedUser = null;
        try
        {
            FileInputStream fileIn = new FileInputStream("test.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserializedUser = (testWriteFileOBJ) in.readObject();
            in.close();
            fileIn.close();
 
            // verify the object state
            System.out.println(deserializedUser.toString());
        } 
        catch (IOException ioe) 
        {
            ioe.printStackTrace();
        } 
        catch (ClassNotFoundException cnfe) 
        {
            cnfe.printStackTrace();
        }
    }
}
