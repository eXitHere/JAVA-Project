package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingAdmin {
    Pane body = new Pane();
    Stage mainStage = new Stage();
    Label title = new Label("กำลังโหลดฐานข้อมูลและสร้างหน้าต่าง");
    public LoadingAdmin() {
        Scene scene = new Scene(this.body,750,420);
        try{
            this.mainStage.getIcons().add(new Image(new FileInputStream("src/resources/images/icon.png")));
        }catch(Exception e){};
        this.mainStage.setScene(scene);
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
            this.title.setLayoutX((this.body.getWidth()/2)-(title.getWidth()/2)-140);
            this.title.setLayoutY(320);
            this.body.getChildren().addAll(imageView,gifLoad,this.title);
        }
        catch(Exception e){e.printStackTrace();};
    }
    
    public void show(){
        this.mainStage.show();
    }
    
    public void hide(){
        this.mainStage.hide();
    }
}
