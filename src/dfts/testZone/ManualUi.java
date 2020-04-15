package dfts.testZone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class ManualUi {
    private BorderPane body = new BorderPane();
    private Pane pane = new Pane();
    private Button centerButton = new Button("I'm a center");
    private ImageView circle = new ImageView();
    private List<Pair<Double,Double>> position = new ArrayList<>();
    private List<Button> listButton = new ArrayList<>();
    private List<Integer> Index = new ArrayList<>();
    private int click = 0;
    private int q = 0;
    private int top = 0;
    private int Q = 360;
    private boolean isActive = true;
    public ManualUi() throws FileNotFoundException {
        Image image = new Image(new FileInputStream(new File("src\\resources\\images\\circle.png")));
        this.circle = new ImageView(image);
        this.circle.setLayoutX(540-image.getWidth()/2);
        this.circle.setLayoutY(360-image.getHeight()/2);
        //this.circle,
        
        this.position.add(new Pair<>(540.0,105.0));
        this.position.add(new Pair<>(800.0,351.0));
        this.position.add(new Pair<>(540.0,605.0));
        this.position.add(new Pair<>(283.0,355.0));
        for(int i=0;i<4;i++){
            Button btn = new Button(String.format("I'm a button%d", i));
            btn.setId(String.format("%d", i));
            btn.setPrefSize(200, 100);
            btn.setOnAction(myHandler);
            setLayout(btn, this.position.get(i));
            setLayout(btn,new Pair<>(540.0,360.0));
            this.Index.add(i);
            this.listButton.add(btn);
            this.pane.getChildren().add(btn);
        }
        this.pane.getChildren().addAll(this.centerButton);
        this.body.setCenter(this.pane);
        this.centerButton.setPrefSize(200, 200);
        this.centerButton.setLayoutX(540-this.centerButton.getPrefWidth()/2);
        this.centerButton.setLayoutY(360-this.centerButton.getPrefHeight()/2);
        
        this.centerButton.setOnAction((ActionEvent ex)->{
            if(!this.isActive) return;
            new Thread(() -> {
                this.isActive = false;
                try {
                    for(int i=90;i<=360;i++){
                        setup(i-90,listButton.get(0),(i));
                        setup(i-90,listButton.get(1),(i+90)%360);
                        setup(i-90,listButton.get(2),(i+180)%360);
                        setup(i-90,listButton.get(3),(i+270)%360);
                        circle.setRotate(i);
                        TimeUnit.MILLISECONDS.sleep(5);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    this.isActive = true;
                });
            }).start();
        });
    }
    
    private void setup(int index,Button btn,int Q){
        double di = (double)Q; // tick num as double for easier math
        double angleFrom12 = di/360*2.0*Math.PI;
        double angleFrom3 = Math.PI/2.0-angleFrom12;
        btn.setRotate(Q);
        btn.setLayoutX(540+Math.cos(angleFrom3)*index-btn.getPrefWidth()/2);
        btn.setLayoutY(360-Math.sin(angleFrom3)*index-btn.getPrefHeight()/2);
    }
    
    private void moved(Button btn,int Q){
        double di = (double)Q; // tick num as double for easier math
        double angleFrom12 = di/360*2.0*Math.PI;
        double angleFrom3 = Math.PI/2.0-angleFrom12;
        btn.setRotate(Q);
        btn.setLayoutX(540+Math.cos(angleFrom3)*270-btn.getPrefWidth()/2);
        btn.setLayoutY(360-Math.sin(angleFrom3)*270-btn.getPrefHeight()/2);
    }
    
    private void setLayout(Button btn,Pair<Double,Double> pos){
        btn.setLayoutX(pos.getKey()-btn.getPrefWidth()/2);
        btn.setLayoutY(pos.getValue()-btn.getPrefHeight()/2);
        switch(this.position.indexOf(pos)){
            case 0: break;
            case 1: btn.setRotate(90); break;
            case 2: btn.setRotate(180); break;
            case 3: btn.setRotate(270);  break;
        }
    }
    
    private EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            Button btn = (Button)event.getTarget();
            new Thread(() -> {
                if(!isActive) return;
                click = Integer.parseInt(btn.getId());
                int tempClick = click;
                isActive = false;
                try {
                        while(click!=top){
                            moved(listButton.get(0),(q));
                            moved(listButton.get(1),(q+90)%360);
                            moved(listButton.get(2),(q+180)%360);
                            moved(listButton.get(3),(q+270)%360);
                            circle.setRotate(Q);
                            Q = (Q-1);
                            q = (q+1)%360;
                            if(q%90==0){
                                click = (click+1)%4;
                                //System.out.println("Click : " + click + " top : " + top);
                            }
                            TimeUnit.MILLISECONDS.sleep(10);
                            //System.out.println("Do");
                        }
                        top = tempClick;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    isActive = true;
                });
            }).start();
        }
};
    
    public BorderPane getBody(){
        return this.body;
    }
}
