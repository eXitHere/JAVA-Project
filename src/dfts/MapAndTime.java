package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Math.ceil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MapAndTime {
    private final Stage body = new Stage();
    private final Scene scene;
    private Scene ticketScene;
    private final BorderPane boarderPane = new BorderPane();
    private final BorderPane ticketBoarderPane = new BorderPane();
    private final HBox topHBox = new HBox();
    private final HBox _leftHBox = new HBox();
    private final HBox _rightHBox = new HBox();
    private final HBox _centerHBox = new HBox();
    private final HBox centerHBox = new HBox();
    private final BorderPane menuBorderPane = new BorderPane();
    private final StackPane stackpane = new StackPane();
    private List<List<Integer>> way = new ArrayList<>();
    private List<List<Integer>> time = new ArrayList<>();
    private List<String> nameStation = new ArrayList<>();
    private Button btnPrev,btnNext,btnTopRate,btnTopTime,btnHelp,btnClose,btnConfirm;
    private final List<Circle> listPostion = new ArrayList<>();
    private final ScrollPane mainScrollPane = new ScrollPane();
    private final BorderPane mainGrid = new BorderPane();
    private final VBox mainVBox = new VBox();
    private Label blank;
    private int indexSub = 0;
    private int indexMain = 0;
    private int indexTopWay = -1;
    private int indexMinTime = -1;
    private List<Integer> price = new ArrayList<>();
    private boolean priceShow = false;
    private boolean timeShow = false;
    private final List<Pair<Integer,Integer>> data = new ArrayList<>();
    private final MyMap<Integer,String> mapName = new MyMap<>();
    private final HashMap<String,VBox> ListReviews = new HashMap<>();
    private final HashMap<Integer,Integer> Rating = new HashMap<>();
    private int indexReviews = 0;
    private int indexSubReviews = 0;
    private int person_count=0;
    
    public MapAndTime(Stage primary,List<String> nameStation) throws FileNotFoundException {
        //System.out.println("Create Map and Time new !");
        this.scene = new Scene(boarderPane,1080,720);
        this.ticketScene = new Scene(this.ticketBoarderPane,1080,720);
        this.nameStation = nameStation;
        myInit();
        //Event
        this.body.setOnHiding( event -> {
            primary.show();
        });
        this.body.setResizable(false);
    }
    
    private Button initButton(String text) throws FileNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        Button temp = new Button(text);
        
        temp.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 25));
        temp.setPrefSize(140, 40);
        temp.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: linear-gradient(#042A5A, #0B509B);;");
        
        DropShadow shadow = new DropShadow();
        
        temp.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            temp.setEffect(shadow);
        });
                
        temp.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            temp.setEffect(null);
        });
        return temp;
        // </editor-fold>;
    }
    
    private Label initLabel(String txt,int size,String color) throws FileNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        Label temp = new Label(txt);
        temp.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), size));
        temp.setStyle("-fx-text-fill:"+ color +";");
        return temp;
        // </editor-fold>;
    }
    
    private void myInit() throws FileNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        FileInputStream logoStream = new FileInputStream("src/resources/images/icon.png");       
        Image logo = new Image(logoStream);
        this.body.getIcons().add(logo);
        this.body.setTitle("Map");
        this.body.setScene(this.scene);
        this.body.initModality(Modality.APPLICATION_MODAL);
        
        //init button
        this.btnPrev = initButton("ก่อนหน้า");
        this.btnNext = initButton("ถัดไป");
        this.btnTopTime = initButton("เร็วที่สุด");
        this.btnTopRate = initButton("ราคาถูกที่สุด");
        this.btnHelp = new Button("ราคาตั๋ว xxx บาท");
        //this.btnHelp.setVisible(false);
        this.btnClose = initButton("ปิด");
        this.btnConfirm = initButton("ออกตั๋ว");
        this.btnHelp.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        this.btnHelp.setPrefSize(140, 45);
        this.btnHelp.setStyle("-fx-background-color: linear-gradient(#148400, #23CC00);-fx-text-fill: white;");
        this.btnTopTime.setPrefSize(180, 40);
        this.btnTopRate.setPrefSize(180, 40);
        this.btnTopRate.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        this.btnTopTime.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        //this.btnHelp.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        this.btnClose.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        this.btnConfirm.setStyle("-fx-background-color: linear-gradient(#148400, #23CC00); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);");
        this.btnConfirm.setPrefSize(500, 40);
        //TOP LEFT BUTTON RIGHT
        HBox.setMargin(this.btnPrev, new Insets(20, 10, 20, 10));
        HBox.setMargin(this.btnNext, new Insets(20, 10, 20, 10));
        HBox.setMargin(this.btnTopTime, new Insets(20, 10, 20, 10));
        HBox.setMargin(this.btnTopRate, new Insets(20, 10, 20, 10));
        HBox.setMargin(this.btnHelp, new Insets(20, 10, 20, 10));
        HBox.setMargin(this.btnClose, new Insets(20, 10, 20, 10));
        //end button

        this._leftHBox.getChildren().addAll(this.btnPrev,this.btnNext);
        this._leftHBox.setStyle("-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        
        this._centerHBox.getChildren().addAll(this.btnTopTime,this.btnTopRate);
        this._centerHBox.setStyle("-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        
        //this.menuBorderPane.setStyle("-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        this.menuBorderPane.setBottom(this.btnConfirm);
        this.menuBorderPane.setTop(this.mainVBox);
        
        BorderPane boarderPant = new BorderPane();
        this.mainVBox.getChildren().add(boarderPant);
        boarderPant.setPrefSize(440,500);
        boarderPant.setMaxSize(440,500);
        boarderPant.setMinSize(440,500);
        boarderPant.setLeft(mainScrollPane);
        boarderPant.setStyle("-fx-background-color: linear-gradient(#F4F4F4, #F4F4F4);");
        this.mainScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.mainScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.mainScrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        
        this.mainScrollPane.setPrefViewportHeight(440);
        this.mainScrollPane.setPrefViewportWidth(500);
        this.mainScrollPane.setPrefSize(440,500);
        this.mainScrollPane.setFitToWidth(true);
        this.mainScrollPane.setFitToHeight(true);
        this.mainScrollPane.setContent(this.mainGrid);
        
        this.mainGrid.setPadding(new Insets(20));
        Label infomation = initLabel("         สถานีที่ใช้ในการเดินทาง",25,"darkblue");
        this.blank = new Label("                          ");
        this.blank.setId("no-delete3");
        Button btnNextTime = initButton("ถัดไป");
        btnNextTime.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white;");
        btnNextTime.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        btnNextTime.setPrefSize(80, 30);
        infomation.setId("no-delete1");
        btnNextTime.setId("no-delete2");
        
        //BTN EVENT
        btnNextTime.setOnAction((ActionEvent e) ->{
            //System.out.println("Clicked");
            //System.out.println("indexSub " + this.indexMain);
            this.indexSubReviews = this.indexSubReviews<this.way.get(this.indexMain).size()-1?this.indexSubReviews+1:0;
            this.indexReviews = this.way.get(this.indexMain).get(this.indexSubReviews);
            //System.out.println("index main" + this.indexMain);
            //System.out.println(this.indexReviews);
            //System.out.println("index Sub : " + this.indexSubReviews);
            //this.indexReviews = this.mapName.getKey(this.mapName.get(this.way.get(this.indexMain%this.way.size()).get(0)));
            chengeReview();
        });
        
        this.btnPrev.setOnAction((ActionEvent e) ->{
            try {
                
                if(this.indexMain!=0){
                    this.indexReviews = this.mapName.getKey(this.mapName.get(this.way.get(this.indexMain%this.way.size()).get(0)));
                    this.indexMain--;
                    this.priceShow = false;
                    this.timeShow =false;
                     //System.out.println("Clicked");
                     managerGrid(this.mainGrid,this.way.get(this.indexMain%this.way.size()));
                     this.indexSubReviews = 0;
                    this.indexReviews = this.mapName.getKey(this.mapName.get(this.way.get(this.indexMain%this.way.size()).get(0)));
                    chengeReview();
                }
            } catch (FileNotFoundException ex) {
                //System.out.println("Error!");
            }
        });
        
        this.btnNext.setOnAction((ActionEvent e) ->{
            //System.out.println("nexe click!");
            try {
                //System.out.println("Clicked");
                if(this.indexMain != this.way.size()-1){
                    this.indexReviews = this.mapName.getKey(this.mapName.get(this.way.get(this.indexMain%this.way.size()).get(0)));
                    this.indexMain++;
                    this.priceShow = false;
                    this.timeShow =false;
                    managerGrid(this.mainGrid,this.way.get(this.indexMain%this.way.size()));
                    this.indexSubReviews = 0;
                    this.indexReviews = this.mapName.getKey(this.mapName.get(this.way.get(this.indexMain%this.way.size()).get(0)));
                    chengeReview();
                }
            } catch (FileNotFoundException ex) {
                //System.out.println("Error!");
            }
        });
        
        this.btnClose.setOnAction((ActionEvent e) ->{
            this.body.close();
        });
        
        this.btnTopRate.setOnAction((ActionEvent e) ->{ //MIN PRICE
            //System.out.println("nexe click!");
            try {
                //System.out.println("Clicked");
                this.indexMain = this.indexTopWay;
                if(this.indexTopWay == this.indexMinTime && this.timeShow){
                    //System.out.println("break");
                    return;
                }
                if(!this.priceShow){
                    this.indexSubReviews = 0;
                    this.indexReviews = this.mapName.getKey(this.mapName.get(this.way.get(this.indexMain%this.way.size()).get(0)));
                    chengeReview();
                    managerGrid(this.mainGrid,getDataWithIndex(this.indexTopWay));  
                    priceShow = true;
                    timeShow =false;
                }              
                
            } catch (FileNotFoundException ex) {
                //System.out.println("Error!");
            }
        });
        
        this.btnTopTime.setOnAction((ActionEvent e) ->{ //MIN Time
            //System.out.println("nexe click!");
            try {
                //System.out.println("Clicked");
                //this.indexSub = 0;
                this.indexMain = this.indexMinTime;
                if(this.indexTopWay == this.indexMinTime && this.priceShow){
                    //System.out.println("Break");
                    return;
                }
                if(!this.timeShow){
                    this.indexSubReviews = 0;
                    this.indexReviews = this.mapName.getKey(this.mapName.get(this.way.get(this.indexMain%this.way.size()).get(0)));
                    chengeReview();
                    managerGrid(this.mainGrid,getDataWithIndex(this.indexMinTime));
                    priceShow = false;
                    timeShow =true;
                }
            } catch (FileNotFoundException ex) {
                //System.out.println("Error!");
            }
        });
        
        this.btnClose.setOnAction((ActionEvent e) ->{
            this.body.close();
        });
        
        //
        HBox hbox = new HBox();
        HBox.setMargin(infomation, new Insets(25,0,20,5));
        HBox.setMargin(blank, new Insets(30,5,10,20));
        HBox.setMargin(btnNextTime, new Insets(20,10,20,10));
        hbox.getChildren().add(infomation);
        hbox.getChildren().add(blank);
        hbox.getChildren().add(btnNextTime);
        boarderPant.setTop(hbox);
        //this.mainGrid.setTop(hbox);
        //this.mainScrollPane.
        
        this.centerHBox.setPadding(new Insets(25,25,25,25));
        this.centerHBox.setSpacing(30);
        //this.centerHBox.setPadding(new Insets(5,25,5,25));
        
        this._rightHBox.getChildren().addAll(this.btnHelp,this.btnClose);
        //this._rightHBox.setStyle("-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        
        this.topHBox.setPadding(new Insets(30,30,5,10));
        this.topHBox.getChildren().addAll(this._leftHBox,this._centerHBox,this._rightHBox);
        this.topHBox.setSpacing(10);
        
        this.boarderPane.setTop(topHBox);
        this.boarderPane.setCenter(centerHBox);
        this.boarderPane.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        
        BorderPane mapButtonPane = new BorderPane();
        try{
            //System.out.println(System.getProperty("user.dir")); //get path working
            FileInputStream inputstream = new FileInputStream("src/resources/images/Map-560x560.jpg");       
            Image mapIM = new Image(inputstream);
            ImageView map = new ImageView(mapIM);
            mapButtonPane.getChildren().addAll(addButtonToMap()); //BorderPane and method return list<btn>
            this.stackpane.getChildren().add(map);                     //Stackpane
            this.stackpane.getChildren().add(mapButtonPane);
            this.centerHBox.getChildren().addAll(this.stackpane,this.menuBorderPane);               
            //Borderpane main 
        }
        catch (FileNotFoundException ex){
            System.out.println("Can't load map");
        }
        
        this.btnConfirm.setOnAction((ActionEvent e)->{
            try{
                PaymentUI payment = new PaymentUI(this.mapName.get(this.way.get(0).get(0)),this.mapName.get(this.way.get(0).get(this.way.get(0).size()-1)),this.price.get(indexMain),this.person_count);
                this.ticketScene = new Scene(payment.getBody());
                this.body.setScene(this.ticketScene);
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        });
        // </editor-fold>;
    }
    
    private List<Integer> getDataWithIndex(int index){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        return this.way.get(index);
        // </editor-fold>;
    }
    
    private void LoadStation() throws FileNotFoundException, ClassNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        //String.format("src/resources/data/%s.station", n[0]
        for(var x : mapName.keySet()){
            try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.station", mapName.get(x)))); ObjectInputStream oi = new ObjectInputStream(fi)){
            Station pr1;
            while((pr1 = (Station) oi.readObject())!=null){
                //System.out.println(pr1.getName());
                //System.out.println(pr1.toString());
                ListReviews.put(pr1.getName(),pr1.getVbox());
                this.Rating.put(x, pr1.getRating());
            }
        }
        catch (IOException ex){
            //System.out.println(ex);
        }
        }
        /*for (String name: ListReviews.keySet()){
            String key = name.toString();
            String value = ListReviews.get(name).toString();  
            System.out.println(key + " " + value);  
        } */
        // </editor-fold>;
    }
    
    private void chengeReview(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        mainGrid.setLeft(this.ListReviews.get(this.mapName.get(this.indexReviews)));
        this.blank.setText("("+(this.indexSubReviews%this.way.get(indexMain).size()+1)+"/"+(this.way.get(indexMain).size())+")");
        // </editor-fold>;
    }
    
    private void managerGrid(BorderPane body,List<Integer> data) throws FileNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /*
            t 0x
            m 0x
            d1 t1
            d2 t2
        */
        //System.out.println(data);
        //System.out.println("Change");
        for(var x : this.listPostion){ // clear position all
            x.setFill(Color.TRANSPARENT);
        }
        //System.out.println("Change!");
        this.btnPrev.setText("+"+ this.indexMain + " ก่อนหน้า");
        this.btnNext.setText("ถัดไป " + "+"+(this.way.size()-1-this.indexMain));
        
        for(var x : data){
            activePosition(this.mapName.get(x));
        }
        
        this.btnHelp.setText(String.format("ราคาตั๋ว %d บาท", this.price.get(indexMain)));
        // </editor-fold>;
    }
    
    private String getHours(int minute){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        return String.format("%.0f:%02d", ceil(minute/60),minute%60);
        // </editor-fold>;
    }
    
    private void activePosition(String name){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        this.listPostion.stream().filter((x) -> (x.getId().equals(name))).forEachOrdered((x) -> {
            x.setFill(Color.DARKBLUE);
        });
        // </editor-fold>;
    }
    
    private List<Circle> addButtonToMap(){ // ปุ่มในแมพ
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        double shiftW = 0.5;
        double shiftH = 0;
        List<Pair<Double,Double>> pos = new ArrayList<>();
        String temp = "65.520,73.260 235.036,304.723 121.695,201.416 356.947,433.041 301.844,307.157 92.098,135.840 419.156,469.535 150.721,279.095 124.776,500.923 510.253,469.535 191.593,466.269 231.954,425.810 329.836,370.099 235.036,358.802 235.036,250.206 235.036,201.231 327.096,109.140 235.036,128.609 419.156,106.243 217.156,51.388 312.297,51.202 400.203,51.017 491.706,108.955 330.749,201.416 484.661,202.923 72.569,341.064 72.587,421.416 419.156,525.396 75.022,271.864 419.156,293.818 419.156,202.738 419.156,384.897";
        String[] t = temp.split(" ");
        for(var x : t){
            String[] TT = x.split(",");
            //System.out.println(TT[0] + " and " + TT[1]);
            pos.add(new Pair <> (Double.parseDouble(TT[0]),Double.parseDouble(TT[1])));
        }
        
        //System.out.println(pos);
        List<Circle> list = new ArrayList<>();
        for(int i=0;i<pos.size();i++){
            Circle circle = new Circle();
            circle.setId(nameStation.get(i));
            circle.setCenterX(pos.get(i).getKey() + shiftW);
            circle.setCenterY(pos.get(i).getValue()+ shiftH);
            circle.setRadius(8.833f);
            //circle.setFill(Color.web("#FFFFFF"));
            circle.setFill(Color.BLUE);
            listPostion.add(circle);
            list.add(circle);
        }
        return list;
        // </editor-fold>;
    }
    
    private void findMin(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        int minTime = 99999999;
        double maxRate = 0;
        //System.out.println("");
        for(var x : this.time){
            int sum = x.stream().reduce(0, (a, b) -> a + b);
            if(sum < minTime){
                minTime = sum;
                this.indexMinTime = this.time.indexOf(x);
            }
        }
        for(var x : this.way){
            int sumRate = 0;
            for(var y : x){
                sumRate += this.Rating.get(y);
            }
            double newSumRate = sumRate/x.size();
            //System.out.println("all rate " + sumRate);
            if(newSumRate > maxRate){
                maxRate = newSumRate;
                this.indexTopWay = this.way.indexOf(x);
            }
        }
        //System.out.println("max : " + maxRate);
        this.btnTopTime.setText("เร็วที่สุด ("+ minTime + " นาที)");
        this.btnTopRate.setText("Rating สูงสุด ("+ String.format("%.2f ดาว", maxRate/5) +" )");
        // </editor-fold>;     
    }
    
    public void show(Pair<Pair<List<List<Integer>>,List<List<Integer>>>,List<Integer>> value,String title,int person_count) throws FileNotFoundException, ClassNotFoundException {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        this.body.setTitle(title);
        this.person_count = person_count;
        var x = value.getKey();
        this.way = x.getKey();
        this.time = x.getValue();
        this.price = value.getValue();
        initList();LoadStation();findMin();
        this.body.show();
        this.indexReviews = this.mapName.getKey(this.mapName.get(this.way.get(this.indexMain%this.way.size()).get(0)));
        chengeReview();
        managerGrid(this.mainGrid,this.way.get(indexMain));
        
        // </editor-fold>;
    }
    
    private void initList(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        this.nameStation.forEach((n) -> {
            this.mapName.put(this.nameStation.indexOf(n), n);
        });
        // </editor-fold>;
    }
}
