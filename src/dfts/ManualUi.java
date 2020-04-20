package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ManualUi {
    private BorderPane body = new BorderPane();
    private Pane pane = new Pane();
    private Button centerButton = new Button();
    private ImageView circle = new ImageView();
    private List<Pair<Double,Double>> position = new ArrayList<>();
    private List<Button> listButton = new ArrayList<>();
    private List<Integer> Index = new ArrayList<>();
    private int click = 0;
    private int q = 0;
    private int top = 0;
    private int Q = 360;
    private boolean isActive = true;
    private List<List<ImageView>> listOFImageAll = new ArrayList<>();
    private StackPane mainPane = new StackPane();
    private Pane imagePane = new Pane();
    private int A,B;
    private Stage primary;
    private Thread btnThread,moveThread;
    
    
    public ManualUi(Stage primary) throws FileNotFoundException {
        this.primary = primary;
        this.primary.setOnHiding((e)->{
            if(this.btnThread!=null) this.btnThread.stop();
            if(this.moveThread!=null) this.moveThread.stop();
        });
        
        Image image = new Image(new FileInputStream(new File("src/resources/images/manual/bground.png")));
        this.circle = new ImageView(image);
        this.circle.setLayoutX(540-image.getWidth()/2);
        this.circle.setLayoutY(360-image.getHeight()/2);
        this.circle.setVisible(false);
        ImageView bg = new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/bg.png"))));
        this.pane.getChildren().addAll(bg);
        //this.circle,
        loadImageAll(); // โหลดรูปทั้งหมด
        this.position.add(new Pair<>(540.0,105.0));
        this.position.add(new Pair<>(800.0,351.0));
        this.position.add(new Pair<>(540.0,605.0));
        this.position.add(new Pair<>(283.0,355.0));
        for(int i=0;i<4;i++){
            Button btn = new Button();
            btn.setId(String.format("%d", i));
            btn.setPrefSize(250, 150);
            btn.setOnAction(myHandler);
            btn.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(new File(String.format("src/resources/images/manual/btn%d.png", i+1)))), 
                    BackgroundRepeat.SPACE, 
                    BackgroundRepeat.SPACE, 
                    BackgroundPosition.CENTER, 
                    BackgroundSize.DEFAULT)));
            //setLayout(btn, this.position.get(i));
            setLayout(btn,new Pair<>(540.0,360.0));
            btn.setVisible(false);
            btn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btn.setEffect(new DropShadow());
            });

            btn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btn.setEffect(null);
            });
            this.Index.add(i);
            this.listButton.add(btn);
            this.pane.getChildren().add(btn);
        }
        this.pane.getChildren().addAll(this.centerButton);
        this.body.setCenter(this.pane);
        this.centerButton.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(new File(String.format("src/resources/images/manual/centerbtn.png")))), 
                    BackgroundRepeat.SPACE, 
                    BackgroundRepeat.SPACE, 
                    BackgroundPosition.CENTER, 
                    BackgroundSize.DEFAULT)));
        this.centerButton.setPrefSize(250, 250);
        this.centerButton.setLayoutX(540-this.centerButton.getPrefWidth()/2);
        this.centerButton.setLayoutY(360-this.centerButton.getPrefHeight()/2);
        this.centerButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            this.centerButton.setEffect(new DropShadow());
        });

        this.moveThread = new Thread(() -> {
            //System.out.println("Click");
            this.isActive = false;
            for(var x : this.listButton) x.setVisible(true);
            try {
                for(int i=90;i<=360;i+=5){
                    setup(i-90,listButton.get(0),(i));
                    setup(i-90,listButton.get(1),(i+90)%360);
                    setup(i-90,listButton.get(2),(i+180)%360);
                    setup(i-90,listButton.get(3),(i+270)%360);
                    circle.setRotate(i);
                    TimeUnit.MILLISECONDS.sleep(20);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                this.isActive = true;
            });
        });
        
        this.centerButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            this.centerButton.setEffect(null);
        });
        this.centerButton.setOnAction((ActionEvent ex)->{
            if(!this.isActive) return;
            this.moveThread.start();
        });
        
        this.imagePane.setPrefSize(1080, 720);
        this.mainPane.getChildren().addAll(this.body,this.imagePane);
        switchPane();
    }
    
    private void switchPane(){
        ObservableList<Node> childs = this.mainPane.getChildren();
        if (childs.size() > 1) {
           // Top Component
            Node topNode = childs.get(childs.size()-1);
           topNode.toBack();
        }
    }
    
    private void loadImageAll(){
        /*
        การจองตั่ว 1 
        สมาชิก 2
        ตารางเดินรถ 3
        เกี่ยวกับสถานี 4
        */
        this.listOFImageAll.add(new ArrayList<>()); // init list 4 
        this.listOFImageAll.add(new ArrayList<>());
        this.listOFImageAll.add(new ArrayList<>());
        this.listOFImageAll.add(new ArrayList<>());
        try{ // การจองตั่ว
           // System.out.println("dO");
            this.listOFImageAll.get(0).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page1_1.png")))));
            this.listOFImageAll.get(0).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page1_2.png")))));
            this.listOFImageAll.get(0).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page1_3.png")))));
            this.listOFImageAll.get(0).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page1_4.png")))));
             // สมาชิก
            this.listOFImageAll.get(1).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page2_1.png")))));
            this.listOFImageAll.get(1).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page2_2.png")))));
             // ตารางเดินรถ
            this.listOFImageAll.get(2).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page4_1.png")))));
             // เกี่ยวกับสถานี
            this.listOFImageAll.get(3).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page3_1.png")))));
            this.listOFImageAll.get(3).add(new ImageView(new Image(new FileInputStream(new File("src/resources/images/manual/page3_2.png")))));
        }
        catch(Exception e){e.printStackTrace();};
        
        this.listOFImageAll.forEach((var x) -> {
            x.forEach((y) -> {
                y.setId("image");
                ImageView temp = (ImageView)y;
            });
        });
        //System.out.println("pass setimage 160");
        /*
        init button in map
        */
        try{
            Button btnClose = new Button();
            btnClose.setPrefSize(200, 100);
            btnClose.setOnAction(myHandler);
            btnClose.setId("btn");
            btnClose.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(new File(String.format("src/resources/images/manual/btnImage1.png")))), 
                    BackgroundRepeat.SPACE, 
                    BackgroundRepeat.SPACE, 
                    BackgroundPosition.CENTER, 
                    BackgroundSize.DEFAULT)));
            setLayout(btnClose,new Pair<>(105.0,55.0));
            Button btnPrev = new Button();
            btnPrev.setPrefSize(100, 100);
            btnPrev.setOnAction(myHandler);
            btnPrev.setId("btn");
            btnPrev.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(new File(String.format("src/resources/images/manual/btnImage3.png")))), 
                    BackgroundRepeat.SPACE, 
                    BackgroundRepeat.SPACE, 
                    BackgroundPosition.CENTER, 
                    BackgroundSize.DEFAULT)));
            setLayout(btnPrev,new Pair<>(65.0,660.0));
            Button btnNext = new Button();
            btnNext.setPrefSize(100, 100);
            btnNext.setId("btn");
            btnNext.setOnAction(myHandler);
            btnNext.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(new File(String.format("src/resources/images/manual/btnImage2.png")))), 
                    BackgroundRepeat.SPACE, 
                    BackgroundRepeat.SPACE, 
                    BackgroundPosition.CENTER, 
                    BackgroundSize.DEFAULT)));
            setLayout(btnNext,new Pair<>(1015.0,660.0));
            
            btnClose.setOnAction((e)->{
                switchPane();
            });
            
            btnNext.setOnAction((e)->{
                if(B < listOFImageAll.get(A).size()-1){
                    B++;
                    changeImage();
                }
            });
            
            btnPrev.setOnAction((e)->{
                if(B > 0){
                    B--;
                    changeImage();
                }
            });
            this.imagePane.getChildren().addAll(btnClose,btnNext,btnPrev);
            
            btnClose.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnClose.setEffect(new DropShadow());
            });

            btnClose.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnClose.setEffect(null);
            });
            
            btnNext.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnNext.setEffect(new DropShadow());
            });

            btnNext.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnNext.setEffect(null);
            });
            
            btnPrev.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnPrev.setEffect(new DropShadow());
            });

            btnPrev.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnPrev.setEffect(null);
            });
        }
        catch(Exception x){}
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
    
    private void changeImage(){
        ObservableList<Node> childs = imagePane.getChildren();
        for(var x : childs){
            if(x.getId().equals("image")){
                imagePane.getChildren().remove(x);
                break;
           }
        }
        imagePane.getChildren().add(listOFImageAll.get(A).get(B));
        for(var x : childs){
            if(x.getId().equals("image")){
                x.toBack();
                break;
           }
        }  
    }
    
    private EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            Button btn = (Button)event.getTarget();
            //System.out.println(btn.getId() + " clicked!");
            A = Integer.parseInt(btn.getId());
            B = 0;
            circle.setVisible(true);
            btnThread = new Thread(() -> {
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
                    try{
                        TimeUnit.MILLISECONDS.sleep(500);
                    }catch(Exception e){}
                    isActive = true;
                    changeImage();
                    switchPane();
                });
            });
            btnThread.start();
        }
    };
    
    public StackPane getBody(){
        return this.mainPane;
    }
}
