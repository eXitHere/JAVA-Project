package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.effect.Glow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class AboutMeUI {
    private Pane body = new Pane();
    private ImageView background;
    private List<ImageView> listPlayer = new ArrayList<>();
    private List<Pair<Double,Double>> itemSetting = new ArrayList<>();
    private List<Double> itemScale = new ArrayList<>();
    private ImageView centerImage = null;
    private boolean isActive = true;
    
    public AboutMeUI(Stage primary) {
        this.body.setMaxSize(1080, 720);
        this.body.setMaxSize(1080, 720);
        
        /*
        init
        */
        this.itemSetting.add(new Pair<>(80.0,630.0));
        this.itemSetting.add(new Pair<>(820.0,580.0));
        this.itemSetting.add(new Pair<>(540.0,510.0));
        this.itemSetting.add(new Pair<>(260.0,580.0));
        this.itemSetting.add(new Pair<>(1000.0,630.0));
        this.itemScale.add(0.4);
        this.itemScale.add(0.65);
        this.itemScale.add(1.0);
        this.itemScale.add(0.65);
        this.itemScale.add(0.4);
        /*
        --------------------.
        */
        try{
            this.background = new ImageView(new Image(new FileInputStream(new File("src/resources/images/aboutme/title.jpg"))));
            this.body.getChildren().add(this.background);
            for(int i=1;i<=5;i++){
                ImageView temp = new ImageView(new Image(new FileInputStream(new File(String.format("src/resources/images/aboutme/%d.png", i)))));
                temp.setId(Integer.toString(i-1));
                setLayout(temp, this.itemSetting.get(i-1));
                setScale(temp, this.itemScale.get(i-1));
                temp.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    temp.setEffect(new Glow());
                    //temp.setEffect(new Shadow());
                });
                temp.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    temp.setEffect(null);
                });
                temp.setOnMouseClicked(e->{
                    if(!this.isActive) return;
                    ImageView body = (ImageView)e.getTarget();
                    setFadeDown(body, this.centerImage);
                    this.centerImage = body;
                });

                this.listPlayer.add(temp);
                this.body.getChildren().add(temp);
            }
            this.centerImage = this.listPlayer.get(2);
        }
        catch(Exception e){System.out.println("bg not found!");}
        
        Thread autoEffect = new Thread(() -> {
            try{
                while(true){
                    for(int i=0;i<5;i++){
                        this.listPlayer.get(i).setEffect(new MotionBlur());
                        TimeUnit.MILLISECONDS.sleep(200);
                        this.listPlayer.get(i).setEffect(null);
                        TimeUnit.MILLISECONDS.sleep(300);
                    }
                }
            }catch(Exception e){e.printStackTrace();};
            Platform.runLater(() -> {

            });
        });
        autoEffect.start();
        
        primary.setOnHidden(e->{
            autoEffect.stop();
        });
    }
    
    private void setFadeDown(ImageView obj1,ImageView obj2){
        this.isActive =false;
        new Thread(() -> {
            try{
                for(int i=0;i<20;i++){
                    //obj.setLayoutX((obj.getLayoutX()-obj.getImage().getWidth()/2)+1);
                    obj1.setLayoutY(obj1.getLayoutY()-10);
                    obj2.setLayoutY(obj2.getLayoutY()+10);
                    Thread.sleep(10);
                }
                for(int i=0;i<20;i++){
                    //obj.setLayoutX((obj.getLayoutX()-obj.getImage().getWidth()/2)+1);
                    obj1.setLayoutY(obj1.getLayoutY()+10);
                    obj2.setLayoutY(obj2.getLayoutY()-10);
                    Thread.sleep(10);
                }
            }catch(Exception e){e.printStackTrace();}
            Platform.runLater(() -> {
                //System.out.println("Swap2 : " + obj1.getId() + " " + obj2.getId());
                int index1 = Integer.parseInt(obj2.getId());
                int index2 = Integer.parseInt(obj1.getId());
                setLayout(obj1, this.itemSetting.get(index1));
                setScale(obj1, this.itemScale.get(index1));
                setLayout(obj2, this.itemSetting.get(index2));
                setScale(obj2, this.itemScale.get(index2));
                obj1.setId(Integer.toString(index1));
                obj2.setId(Integer.toString(index2));
                //System.out.println("Swap1 : " + obj1.getId() + " " + obj2.getId());
                this.isActive =true;
            });
        }).start();
    }
    
    private void setLayout(ImageView obj,Pair<Double,Double> pos){
        obj.setLayoutX(pos.getKey()-obj.getImage().getWidth()/2);
        obj.setLayoutY(pos.getValue()-obj.getImage().getHeight()/2);
    }
    
    private void setScale(ImageView obj,double x){
        obj.setScaleX(x);
        obj.setScaleY(x);
    }
    
    public Pane getBody(){
        return this.body;
    }
    
}
