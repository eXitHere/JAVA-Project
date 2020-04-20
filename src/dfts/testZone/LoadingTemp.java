package dfts.testZone;

import java.io.File;
import java.io.FileInputStream;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingTemp {
    Pane body = new Pane();
    Stage mainStage = new Stage();
    Label title = new Label("กำลังโหลด");
    public LoadingTemp() {
        Scene scene = new Scene(this.body,750,420);
        this.mainStage.setScene(scene);
    }
    
    public void init(){
        this.mainStage.setTitle("Loading...");
        this.mainStage.initModality(Modality.APPLICATION_MODAL);
        this.mainStage.setResizable(false);
        this.mainStage.centerOnScreen();
        this.mainStage.initStyle(StageStyle.UNDECORATED);
        /*
        init
        */
        try{
            ImageView imageView = new ImageView(new Image(new FileInputStream(new File("src/resources/images/loading_admin.png"))));
            ImageView gifLoad = new ImageView(new Image(new FileInputStream(new File("src/resources/images/Spin-1s-200px.gif"))));
            gifLoad.setLayoutX(270);
            gifLoad.setLayoutY(100);
            gifLoad.setScaleX(0.6);
            gifLoad.setScaleY(0.6);
            this.title.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 25));
            this.title.setLayoutX((this.body.getWidth()/2)-(title.getWidth()/2)-50);
            this.title.setLayoutY(320);
            this.body.getChildren().addAll(imageView,gifLoad,this.title);
        }
        catch(Exception e){e.printStackTrace();};
    }
    
    private Label a;
    public void functionA(){
        a = new Label();
    }
    
    public void show(){
        this.mainStage.show();
    }
    
    public void hide(){
        this.mainStage.hide();
    }
}
