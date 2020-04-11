
package dfts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Loading{
    final Stage dialog = new Stage();
    final Scene scene;
    final StackPane stackpane = new StackPane();
    
    public Loading(Stage primaryStage) throws FileNotFoundException{
        GridPane gridPane = new GridPane();
        Label infomation = new Label("                    กำลังโหลดข้อมูล...");
        infomation.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 40));
        infomation.setStyle("-fx-text-fill: linear-gradient(#034A5A, #0BA09B);");
        this.scene = new Scene(stackpane,580,340);
        this.dialog.setTitle("Loading...");
        this.dialog.initModality(Modality.APPLICATION_MODAL);
        //this.dialog.initOwner(primaryStage);
        this.dialog.setResizable(false);
        this.dialog.centerOnScreen();
        this.dialog.initStyle(StageStyle.UNDECORATED);
        try{
            //System.out.println(System.getProperty("user.dir")); //get path working
            FileInputStream inputstream = new FileInputStream("src/resources/images/loading_750x420.jpg");       
            Image mapIM = new Image(inputstream);
            ImageView map = new ImageView(mapIM);
            map.setScaleX(1.01);
            map.setScaleY(1.01);
            FileInputStream inputstreamGif = new FileInputStream("src/resources/images/loading-215x215.gif");       
            Image mapIMGif = new Image(inputstreamGif);
            ImageView mapGif = new ImageView(mapIMGif);
            mapGif.setScaleX(0.5);
            mapGif.setScaleY(0.5);
            gridPane.setHgap(3);
            gridPane.setVgap(3);
            gridPane.add(mapGif, 1, 0);
            gridPane.add(infomation, 0,0);
            //map.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B)");
            this.stackpane.getChildren().addAll(map,gridPane);
        }
        catch (FileNotFoundException ex){
            System.out.println("Can't load logo");
        }
        this.dialog.setScene(this.scene);
        this.dialog.setOpacity(0.97);
    }
    
    public void show() {
        this.dialog.show();
    }
    
    public void hide() {
        this.dialog.hide();
    }
}
