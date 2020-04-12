/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfts;

import java.io.FileNotFoundException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Thana
 */
public class DriverPaymentUI extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        
        PaymentUI payment = new PaymentUI("ก๋วยเตี๋ยว","น้ำตก",120,2);
        Scene scene = new Scene(payment.getBody(),1080,720);
        stage.setScene(scene);
        //saveAsPng(payment.takeShot(),"test");
        stage.show();
        
        }
}
