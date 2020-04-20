package dfts;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Pair; 

public class DFTS extends Application{
    //add do hahaasd
    private final HashMap<String, BorderPane> mainPage = new HashMap<>();
    private final BorderPane mainBorder = new BorderPane();
    private final Scene mainScene = new Scene(mainBorder,1280,720);
    private List<String> nameStation = new ArrayList<>();
    private List<String> nameRailway = new ArrayList<>();
    private final List<List<String>> subRailway = new ArrayList<>();
    private final List<List<String>> timeRailway = new ArrayList<>();
    
    private String startAt = "";
    private String stopAt = "";
    private final MyMap<String,Circle> listPostion = new MyMap<>();
    private final List<Object> listInMain = new ArrayList<>();
    private VBox nodeInMain = new VBox(); 
    
    private Loading load;
    
    private MapCode mapCode;
    
    private MapAndTime mapTime;

    private Stage mainStage;
    
    private DropShadow shadow = new DropShadow();
    
    private UserUi userUi;
    
    private int adminClick = 0;
    
    private TimeTableUi timeTableUi;
    
    private AboutMeUI aboutMeUi;
    
    private StationUI statusUi;
    
    private LoadingAdmin loadingAdmin = new LoadingAdmin();
    
    public static void main(String[] args) {
        //System.out.println("Hello world");
        launch(args);
    }

    public VBox addLVBox() throws FileNotFoundException { // Main left
        Font font = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 24);
        VBox vbox = new VBox();
        //vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setPrefSize(200, 720);
        vbox.setStyle("-fx-background-color: #FFFFFF;");
        
        try{
            //System.out.println(System.getProperty("user.dir")); //get path working
            FileInputStream inputstream = new FileInputStream("src/resources/images/logo-182x182.jpg");       
            Image logo = new Image(inputstream);
            ImageView Logo = new ImageView(logo);
            Logo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if(this.adminClick==2){
                    this.loadingAdmin.show();
                    this.admin = new AdminSite(mainStage);
                    Thread ld = new Thread(()->{
                        this.admin.loadDBAll();
                        this.admin.init();
                        Platform.runLater(() -> {
                            this.loadingAdmin.hide();
                            this.admin.authen();
                        });
                    });
                    ld.setDaemon(true);
                    ld.start();
                    
                    this.mainStage.hide();
                    this.adminClick = 0;
                }
                this.adminClick++;
            });
            VBox.setMargin(Logo, new Insets(2, 10, 10, 10));
            vbox.getChildren().add(Logo);
        }
        catch (FileNotFoundException ex){
            System.out.println("Can't load logo");
        }    
        
        Button btnHome = new Button("จองตั๋ว");
        btnHome.setPrefSize(180, 60);
        btnHome.prefWidthProperty().bind(vbox.widthProperty());
        btnHome.setFont(font);
        btnHome.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        VBox.setMargin(btnHome, new Insets(10,10, 3, 10));
        
        Button btnMap = new Button("สถานี");
        btnMap.setFont(font);
        btnMap.setPrefSize(180, 60);
        btnMap.prefWidthProperty().bind(vbox.widthProperty());
        btnMap.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        VBox.setMargin(btnMap, new Insets(3, 10, 3, 10));   
        
        Button btnTime = new Button("ตารางเวลา");
        btnTime.setFont(font);
        btnTime.setPrefSize(180, 60);
        btnTime.prefWidthProperty().bind(vbox.widthProperty());
        btnTime.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        VBox.setMargin(btnTime, new Insets(3, 10, 3, 10));
        
        Button btnUser = new Button("บัญชีผู้ใช้งาน");
        btnUser.setFont(font);
        btnUser.setPrefSize(180, 60);
        btnUser.prefWidthProperty().bind(vbox.widthProperty());
        btnUser.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        VBox.setMargin(btnUser, new Insets(3, 10, 3, 10));
        
        Button btnManual = new Button("วิธีใช้งาน");
        btnManual.setFont(font);
        btnManual.setPrefSize(180, 60);
        btnManual.prefWidthProperty().bind(vbox.widthProperty());
        btnManual.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        VBox.setMargin(btnManual, new Insets(50, 10, 3, 10));
        
        Button btnAboutme = new Button("ผู้จัดทำ");
        btnAboutme.setFont(font);
        btnAboutme.setPrefSize(180, 60);
        btnAboutme.prefWidthProperty().bind(vbox.widthProperty());
        btnAboutme.setStyle("-fx-background-color: linear-gradient(#A51E22, #BC0D1B); -fx-text-fill: white;");
        VBox.setMargin(btnAboutme, new Insets(3, 10, 3, 10));
        
        vbox.getChildren().addAll(btnHome, btnMap, btnTime,btnUser,btnManual,btnAboutme);
        
        //Event Zone
        //Click event
        /*
        btnHome.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Home Clicked!");
            }
        });
        */
        
        btnHome.setOnAction((ActionEvent e) -> {
            //System.out.println("Home Clicked!");
            mainPage.remove("main");
            try{
                mainPage.put("main",addHomePane());
            }catch(Exception x){};
            this.mainBorder.setCenter(mainPage.get("main"));
        });
        
        btnMap.setOnAction((ActionEvent e) -> {
            //System.out.println("Map Clicked!");
            mainPage.remove("station");
            try{
                mainPage.put("station",addStationPane());
            }catch(Exception x){};
            this.mainBorder.setCenter(mainPage.get("station"));
            
        });
        
        btnTime.setOnAction((ActionEvent e) -> {
            //System.out.println("Time Clicked!");
            mainPage.remove("time");
            try{
                mainPage.put("time",addTimePane());
            }catch(Exception x){};
            this.mainBorder.setCenter(mainPage.get("time"));
        });
        
        btnUser.setOnAction((ActionEvent e) -> {
            //System.out.println("User Clicked!");
            try{
                this.userUi = new UserUi(this.mainStage,0);
                this.userUi.show();
            }
            catch(Exception ex){
                System.out.println("Have something wrong!\n"+ex);
            }
            //this.mainBorder.setCenter(mainPage.get("time"));
        });
        
        btnAboutme.setOnAction((ActionEvent e) -> {
            mainPage.remove("aboutme");
            try{
                mainPage.put("aboutme",addAboutMePane());
            }catch(Exception x){};
            this.mainBorder.setCenter(mainPage.get("aboutme"));
        });
        
        btnManual.setOnAction((e)->{
            mainPage.remove("manual");
            try{
                mainPage.put("manual",addManualPane());
            }catch(Exception x){};
            this.mainBorder.setCenter(mainPage.get("manual"));
        });
        //Drop Shadow
        // <editor-fold defaultstate="collapsed" desc="Event shadow">
        
        btnHome.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnHome.setEffect(shadow);
        });
                
        btnHome.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnHome.setEffect(null);
        });
        
        btnMap.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnMap.setEffect(shadow);
        });
                
        btnMap.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnMap.setEffect(null);
        });
        
        btnTime.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnTime.setEffect(shadow);
        });
                
        btnMap.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnTime.setEffect(null);
        });
        
        btnUser.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnUser.setEffect(shadow);
        });
                
        btnUser.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnUser.setEffect(null);
        });
        btnManual.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnManual.setEffect(shadow);
        });
                
        btnManual.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnManual.setEffect(null);
        });
        btnAboutme.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnAboutme.setEffect(shadow);
        });
                
        btnAboutme.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnAboutme.setEffect(null);
        });
        // </editor-fold>;
        
        this.mainStage.setOnShown((e)->{ // Clear
           //System.out.println("reset");
           mainPage.remove("main");
            try{
                mainPage.put("main",addHomePane());
            }catch(Exception x){};
            this.mainBorder.setCenter(mainPage.get("main"));
        });
        
        return vbox;
    }
    
    private AdminSite admin;
    public void loadShow(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        load.show();
            Thread newThread = new Thread(new Runnable() {
            @Override
                public void run() {
                    try {
                        Thread.sleep((int)Math.random()%2000+500);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            load.hide();
                        }
                        });
                    }
            });
            newThread.start();
        // </editor-fold>;
    }
    
    public BorderPane addHomePane() throws FileNotFoundException{ // Home page
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        this.listInMain.clear();
        BorderPane body = new BorderPane();
        HBox subTop = new HBox();
        HBox subCenter = new HBox();
        VBox subCenterRigth = new VBox();
        Label infomation = new Label("กรุณาเลือกสถานีต้นทาง สถานทีปลายทาง และจำนวนสมาชิกที่ต้องการซื้อตั๋ว");
        Label infomation2 = new Label("(คลิ๊กเมาส์ซ้ายเพื่อระบุสถานีเริ่มต้น / คลิ๊กเมาส์ขวาเพื่อระบุสถานีปลายทาง)");
        StackPane stackPane = new StackPane();
        body.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        
        BorderPane mapButtonPane = new BorderPane();
        try{
            //System.out.println(System.getProperty("user.dir")); //get path working
            FileInputStream inputstream = new FileInputStream("src/resources/images/Map-560x560.jpg");       
            Image mapIM = new Image(inputstream);
            ImageView map = new ImageView(mapIM);
            mapButtonPane.getChildren().addAll(addButtonToMap()); //BorderPane and method return list<btn>
            stackPane.getChildren().add(map);                     //Stackpane
            stackPane.getChildren().add(mapButtonPane);
            subCenter.getChildren().add(stackPane);               //Borderpane main
        }
        catch (FileNotFoundException ex){
            System.out.println("Can't load logo");
        } 
        
        subTop.setAlignment(Pos.CENTER);
        subTop.setPadding(new Insets(30,0,0,0));
       
        // TOP RIGHT BOTTOM LEFT
        subCenter.setPadding(new Insets(50,50,0,50));
        subCenterRigth.setPadding(new Insets(0,50,0,80));
        //subCenterRigth.setAlignment(Pos.CENTER);
        subCenterRigth.setAlignment(Pos.TOP_LEFT);
        
        infomation.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 25));
        infomation.setStyle("-fx-text-fill: white;");
        infomation2.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        infomation2.setStyle("-fx-text-fill: white;");
        //infomation.setPrefWidth(100);
        subTop.getChildren().addAll(infomation);
        
        
        //Setting combobox
        ComboBox combo_Start = new ComboBox();
        ComboBox combo_StartStation = new ComboBox();
        ComboBox combo_Stop = new ComboBox();
        ComboBox combo_Stop_Station = new ComboBox();
        ComboBox combo_Count = new ComboBox();
        Label lbStart = new Label("สถานีต้นทาง");
        lbStart.setId("lb_start");
        Label lbStop = new Label("สถานีปลายทาง");
        lbStop.setId("lb_stop");
        Label lbCount = new Label("จำนวนผู้เดินทาง");
        lbCount.setId("lb_count");
        Button btnConfirm = new Button("ค้นหา");
        btnConfirm.setId("btn_confirm");
        
        lbStart.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        lbStart.setStyle("-fx-text-fill: white;");
        lbStop.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        lbStop.setStyle("-fx-text-fill: white;");
        lbCount.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        lbCount.setStyle("-fx-text-fill: white;");
        
        Integer person_count[] = {1,2,3,4,5,6,7,8,9,10};
        
        combo_Count.getItems().addAll(person_count);
        combo_Count.setPrefSize(300, 40);
        combo_Count.setMinSize(300, 40);
        VBox.setMargin(combo_Count, new Insets(5, 0, 20, 0));  
        combo_Count.getEditor().setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 300));
        combo_Count.prefWidthProperty().bind(subCenterRigth.widthProperty());
        combo_Count.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF);-fx-font-size: 17.0");
        combo_Count.setId("cb_count");
        combo_Count.getSelectionModel().selectFirst();
        
        combo_Start.getItems().addAll(nameRailway);
        combo_Start.setPrefSize(300, 40);
        combo_Start.setMinSize(300, 40);
        VBox.setMargin(combo_Start, new Insets(5, 0, 10, 0)); 
        combo_Start.getEditor().setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 300));
        combo_Start.prefWidthProperty().bind(subCenterRigth.widthProperty());
        combo_Start.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF);-fx-font-size: 17.0");
        combo_Start.setPromptText("เลือกสายรถไฟ");
        combo_Start.setId("cb_start");
        
        combo_StartStation.setPrefSize(300, 40);
        combo_StartStation.setMinSize(300, 40);
        VBox.setMargin(combo_StartStation, new Insets(0, 0, 20, 0)); 
        combo_StartStation.getEditor().setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 300));
        combo_StartStation.prefWidthProperty().bind(subCenterRigth.widthProperty());
        combo_StartStation.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF);-fx-font-size: 17.0");
        combo_StartStation.setPromptText("เลือกสถานีต้นทาง");
        combo_StartStation.setId("cb_start_station");
        
        combo_Stop.setMinSize(300, 40);
        combo_Stop.setPrefSize(300, 40);
        VBox.setMargin(combo_Stop, new Insets(5, 0, 10, 0));  
        combo_Stop.getEditor().setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 300));
        combo_Stop.prefWidthProperty().bind(subCenterRigth.widthProperty());
        combo_Stop.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF);-fx-font-size: 17.0");
        combo_Stop.setPromptText("เลือกสายรถไฟ");
        combo_Stop.setId("cb_stop");
        combo_Stop.getItems().addAll(nameRailway);
        
        //combo_Stop_Station.getItems().addAll(nameStation);
        combo_Stop_Station.setMinSize(300, 40);
        combo_Stop_Station.setPrefSize(300, 40);
        VBox.setMargin(combo_Stop_Station, new Insets(0, 0, 20, 0));  
        combo_Stop_Station.getEditor().setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 300));
        combo_Stop_Station.prefWidthProperty().bind(subCenterRigth.widthProperty());
        combo_Stop_Station.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF);-fx-font-size: 17.0");
        combo_Stop_Station.setPromptText("เลือกสถานีปลายทาง");
        combo_Stop_Station.setId("cb_stop_station");
        
        btnConfirm.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 30));
        btnConfirm.setPrefSize(140, 40);
        btnConfirm.prefWidthProperty().bind(subCenterRigth.widthProperty());
        btnConfirm.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: linear-gradient(#042A5A, #0B509B);;");
        VBox.setMargin(btnConfirm, new Insets(10, 10, 10, 10));   //#042A5A, #0B509B
        
        //subCenterRigth.getChildren().addAll(lbStart,combo_Start,lbStop,combo_Stop,lbCount,combo_Count, btnConfirm);

        subCenterRigth.getChildren().addAll(lbStart,combo_Start);
        
        subCenter.getChildren().add(subCenterRigth);
        body.setTop(subTop);
        body.setCenter(subCenter);
        
        DropShadow shadow = new DropShadow();
       
        //Event zone
        combo_Start.setOnAction(new EventHandler<ActionEvent>(){ // 1
            @Override public void handle(ActionEvent e){
                try{
                if(combo_Start.getValue() != null){
                    manageMark(2, (String) combo_Start.getValue());
                }
                }catch(Exception ex){};
            }
        });
        
        combo_StartStation.setOnAction(new EventHandler<ActionEvent>(){ // 2
            @Override public void handle(ActionEvent e){
               try{
               if(combo_StartStation.getValue().equals(stopAt)){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("DFTS Station Choosing Error");
                    alert.setContentText("กรุณาทำการเลือกสถานีใหม่อีกรอบ");
                    alert.setHeaderText("เกิดความผิดพลาดในการดำเนินการ");
                    alert.showAndWait();
                    //startAt= (String) combo_StartStation.getValue();
                    combo_StartStation.getSelectionModel().select((combo_Stop_Station.getItems().indexOf(combo_StartStation.getValue())+1)%combo_StartStation.getItems().size());
                    //startAt = (String) combo_StartStation.getValue();
               }
               else if(combo_StartStation.getValue() != null ){
                   manageMark(3, (String) combo_StartStation.getValue());
               }
               }catch(Exception ex){}
            }
        });
        
        combo_Stop.setOnAction(new EventHandler<ActionEvent>(){ // 3
            @Override public void handle(ActionEvent e){
                try{
                if(combo_Stop.getValue() != null){
                    manageMark(4, (String) combo_Stop.getValue());
                }
                }catch(Exception ex){};
            }
        });
        
        combo_Stop_Station.setOnAction(new EventHandler<ActionEvent>(){ // 4
            @Override public void handle(ActionEvent e){
                try{
                if(combo_Stop_Station.getValue().equals(startAt)){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("DFTS Station Choosing Error");
                    alert.setContentText("กรุณาทำการเลือกสถานีใหม่อีกรอบ");
                    alert.setHeaderText("เกิดความผิดพลาดในการดำเนินการ");
                    alert.showAndWait();
                    //System.out.println("Do");
                    combo_Stop_Station.getSelectionModel().select((combo_StartStation.getItems().indexOf(combo_StartStation.getValue())+1)%combo_StartStation.getItems().size());
                    //stopAt= (String) combo_Stop_Station.getValue();
               }
               else if(combo_Stop_Station.getValue() != null){
                   //System.out.println("new Mark");
                   manageMark(5, (String) combo_Stop_Station.getValue());
               }
               }catch(Exception ex){}
            }
        });
        
        combo_Count.setOnAction(new EventHandler<ActionEvent>(){ // 5
            @Override public void handle(ActionEvent e){
                    
            }
        });
        
        btnConfirm.setOnAction((ActionEvent e) -> {
            //System.out.println("btnConfirm Clicked!");
            //System.out.println(startAt + " " + stopAt);
            if(startAt == null || stopAt == null || startAt.equals(stopAt) || stopAt.equals("/") || startAt.equals("/") || startAt.equals("") || stopAt.equals("")){
                //System.out.println("Start : " + startAt);
                //System.out.println("Stop : "+ stopAt);
                //combo_Stop_Station.getSelectionModel().select(0);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("DFTS Station Choosing Error");
                alert.setContentText("กรุณาทำการเลือกสถานีใหม่อีกรอบ");
                alert.setHeaderText("เกิดความผิดพลาดในการดำเนินการ");
                alert.showAndWait();
            }
            else{
                //mapCode.getPath(startAt,stopAt);
                try{
                    this.mapCode = new MapCode(this.nameStation,this.nameRailway,this.subRailway,this.timeRailway);
                    //System.out.println("1");
                    this.mapTime = new MapAndTime(this.mainStage,this.nameStation);
                    //System.out.println("2");
                    this.mainStage.hide();
                    this.mapTime.show(this.mapCode.getPath(this.startAt,this.stopAt),"จาก " + startAt + " ไปยัง " + stopAt,(Integer)combo_Count.getValue());
                    loadShow();
                    //System.out.println("3");
                }
                catch (Exception ex) {
                    System.out.println("Error? no!" + ex);
                }
                
            }
            
        });
        
        combo_Start.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            combo_Start.setEffect(shadow);
        });
                
        combo_Start.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            combo_Start.setEffect(null);
        });

        combo_StartStation.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            combo_StartStation.setEffect(shadow);
        });
                
        combo_StartStation.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            combo_StartStation.setEffect(null);
        });
        
        combo_Stop.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            combo_Stop.setEffect(shadow);
        });
                
        combo_Stop.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            combo_Stop.setEffect(null);
        });
        
        combo_Stop_Station.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            combo_Stop_Station.setEffect(shadow);
        });
                
        combo_Stop_Station.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            combo_Stop_Station.setEffect(null);
        });

        combo_Count.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            combo_Count.setEffect(shadow);
        });
                
        combo_Count.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            combo_Count.setEffect(null);
        });
        
        btnConfirm.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnConfirm.setEffect(shadow);
        });
                
        btnConfirm.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnConfirm.setEffect(null);
        });
        
        listInMain.add(combo_Count);
        listInMain.add(combo_Start);
        listInMain.add(combo_StartStation);
        listInMain.add(combo_Stop);
        listInMain.add(combo_Stop_Station);
        listInMain.add(lbCount);
        listInMain.add(lbStart);
        listInMain.add(lbStop);
        listInMain.add(btnConfirm);
        nodeInMain = subCenterRigth;
        return body;
        
                // </editor-fold>;
    }
    
    public Object getNodeInMain(String id){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        for(var x : nodeInMain.getChildren()){
            if(x.getId().equals(id)){
                return x;
            }
        }
        return null;
        // </editor-fold>;
    }
    
    public Object getNodeInList(String id){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        for(var x : listInMain){
            if(x instanceof ComboBox){
                if(((ComboBox) x).getId().equals(id)){
                    return (ComboBox)x;
                }
            }
            else if(x instanceof Label){
                if(((Label) x).getId().equals(id)){
                    return (Label)x;
                }
            }
            else if(x instanceof Button){
                if(((Button) x).getId().equals(id)){
                    return (Button)x;
                }
            }
        }
        return null;
        // </editor-fold>;
    }
    
    public int getIndexName(String name){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        //System.out.println(name);
        if(name != null){
            for(var x : subRailway){
                for(var X : x){
                    if(X.equals(name)){
                        return subRailway.indexOf(x);
                    }
                }
            }
        }
        return -1;
        // </editor-fold>;
    }
    
    public boolean isNameRailWay(String name){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        return (nameRailway.indexOf(name) != -1);
        // </editor-fold>;
    }
    
    public boolean addToMain(List<String> obList,String status,boolean isStart,boolean noti){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        try{
        if(noti && stopAt.equals(status) || status.equals("" )|| startAt.equals(status)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("DFTS Station Choosing Error");
            alert.setContentText("กรุณาทำการเลือกสถานีใหม่อีกรอบ");
            alert.setHeaderText("เกิดความผิดพลาดในการดำเนินการ");
            alert.showAndWait();
            return false;
        }
        for(var ob : obList){
            Object body = getNodeInList(ob);
            if(getNodeInMain(ob)==null && status != null){
                if(body instanceof ComboBox){
                    nodeInMain.getChildren().add((ComboBox)body);
                }
                else if(body instanceof Label) nodeInMain.getChildren().add((Label)body);
                else if(body instanceof Button) nodeInMain.getChildren().add((Button)body);
            }
            if(isStart && (body instanceof ComboBox && ((ComboBox) body).getId().equals("cb_start_station") ||
                           body instanceof Label && ((Label) body).getId().equals("lb_stop"))){
                ComboBox tempStart = (ComboBox) getNodeInMain("cb_start");
                //System.out.println("temp Start " + tempStart);
                if(tempStart != null){
                    if(isNameRailWay(status)){
                        //System.out.println("Name");
                        int indexSub = nameRailway.indexOf(status);
                        ((ComboBox) body).getItems().clear();
                        ((ComboBox) body).getItems().addAll(subRailway.get(indexSub));
                        startAt = "/";
                        markStart(startAt);
                    }else if(body instanceof Label && ((Label) body).getId().equals("lb_stop")){
                        startAt = status;
                        markStart(startAt);
                    }
                    else{
                        //System.out.println("Sub");
                        int indexSub = getIndexName(status);
                        tempStart.getSelectionModel().select(indexSub);
                        ((ComboBox) body).getItems().clear();
                        ((ComboBox) body).getItems().addAll(subRailway.get(indexSub));
                        ((ComboBox) body).getSelectionModel().select(status);
                        startAt = status;
                        markStart(startAt);
                    }
                }
            }
            else if(body instanceof ComboBox &&((ComboBox) body).getId().equals("cb_stop_station") ||
                    body instanceof Label &&((Label) body).getId().equals("lb_count")){
                ComboBox tempStop = (ComboBox) getNodeInMain("cb_stop");
                if(tempStop != null){
                    if(isNameRailWay(status)){
                        int indexSub = nameRailway.indexOf(status);
                        ((ComboBox) body).getItems().clear();
                        ((ComboBox) body).getItems().addAll(subRailway.get(indexSub));
                        stopAt = "/";
                        markStop(stopAt);
                    }
                    else if(body instanceof Label &&((Label) body).getId().equals("lb_count")){
                        stopAt = status;
                        markStop(stopAt);
                    }
                    else{
                        int indexSub = getIndexName(status);
                        tempStop.getSelectionModel().select(indexSub);
                        ((ComboBox) body).getItems().clear();
                        ((ComboBox) body).getItems().addAll(subRailway.get(indexSub));
                        ((ComboBox) body).getSelectionModel().select(status);
                        stopAt = status;
                        markStop(stopAt);
                    }
                }
            }
        }
        
        //System.out.println("Success!");
        }
        catch(Exception ex){}
        return true;
        // </editor-fold>;
    }
    
    public void manageMark(int state,String status){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        if(status == null) return; // Flush!
        List<String> queue = new ArrayList<>();
        switch(state){
            case 0:
                    queue.addAll(Arrays.asList("cb_start_station","lb_stop","cb_stop"));
                    addToMain(queue,status,true,true);
                break;
            case 1:
                    queue.addAll(Arrays.asList("cb_start_station","cb_start_station","lb_stop","cb_stop","cb_stop_station","lb_count","cb_count","btn_confirm"));
                    addToMain(queue,status,false,true);
                break;
            case 2:
                    queue.addAll(Arrays.asList("cb_start_station"));
                    addToMain(queue,status,true,false);
                break;
            case 3:
                    queue.addAll(Arrays.asList("lb_stop","cb_stop"));
                    addToMain(queue,status,true,false);
                    //System.out.println("case 3");
                break;
            case 4:
                    queue.addAll(Arrays.asList("cb_stop_station"));
                    addToMain(queue,status,true,false);
                break;
            case 5:
                    queue.addAll(Arrays.asList("lb_count","cb_count","btn_confirm"));
                    addToMain(queue,status,true,false);
                break;
        }
        // </editor-fold>;
    }
    
    public void markStart(String at){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        for(Circle x : listPostion.values()){
            if(x.getFill() != Color.TRANSPARENT && (!listPostion.getKey(x).equals(startAt) &&
                                                    !listPostion.getKey(x).equals(stopAt))){
                x.setFill(Color.TRANSPARENT);
            }
            
        }
        if(!at.equals("/")) listPostion.get(at).setFill(Color.web("#F6EC1F"));
        // </editor-fold>;
    }
    
    public void markStop(String at){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        for(Circle x : listPostion.values()){
            if(x.getFill() != Color.TRANSPARENT && (!listPostion.getKey(x).equals(startAt) &&
                                                    !listPostion.getKey(x).equals(stopAt))){
                x.setFill(Color.TRANSPARENT);
            }
        }
        if(!at.equals("/")) listPostion.get(at).setFill(Color.web("#F68A1F"));
        // </editor-fold>;
    }
    
    public List<Circle> addButtonToMap(){ // ปุ่มในแมพ
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        double shiftH = 1;
        double shiftW = 27.88;
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
            String name = nameStation.get(i);
            circle.setCenterX(pos.get(i).getKey() + shiftH);
            circle.setCenterY(pos.get(i).getValue()+ shiftW);
            circle.setRadius(8.833f);
            //circle.setFill(Color.web("#FFFFFF"));
            circle.setFill(Color.TRANSPARENT);
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY && startAt != null && !startAt.equals(name)) {
                    manageMark(0,name);
                }
                else if (e.getButton() == MouseButton.SECONDARY && stopAt != null && !stopAt.equals(name)) {
                    manageMark(1,name);
                }
            });
            circle.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                if(!startAt.equals(name) && !stopAt.equals(name))
                    circle.setFill(Color.GRAY);
            });

            circle.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                if(startAt != name && stopAt != name)
                    circle.setFill(Color.TRANSPARENT);
            });
            listPostion.put(name,circle);
            list.add(circle);
        }
        return list;
        // </editor-fold>;
    }
    
    public BorderPane addStationPane(){  // Map Page
        this.statusUi = new StationUI(this.nameStation,this.mainStage);
        return statusUi.getBody();
    }
    
    public BorderPane addTimePane(){ //Time Page
        return timeTableUi.getBody();
    }   
    
    public BorderPane addAboutMePane(){
        BorderPane body = new BorderPane();
        this.aboutMeUi = new AboutMeUI(this.mainStage);
        body.setCenter(this.aboutMeUi.getBody());
        return body;
    }
    
    public BorderPane addManualPane() throws FileNotFoundException{
        BorderPane body = new BorderPane();
        ManualUi ui = new ManualUi(this.mainStage);
        body.setCenter(ui.getBody());
        return body;
    }
    
    public BorderPane addMainPane() throws FileNotFoundException{
        BorderPane mainPane = new BorderPane();
        mainPage.put("main",addHomePane());
        mainPage.put("station",addStationPane());
        mainPage.put("time",addTimePane());
        mainPage.put("aboutme",addAboutMePane());
        mainPage.put("manual",addManualPane());
        //mainPane.setCenter(combo_box);
        return mainPane;
    }
    
    private void readData() throws FileNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        String line;
        List<String> bufReturn;
        bufReturn = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/resources/data/", "data.txt") , Charset.forName("UTF-8"))) {
            while ((line = reader.readLine()) != null ) {
              //separate all csv fields into string array
                //String[] lineVariables = line.split(","); 
                //System.out.println(Arrays.toString(lineVariables));
                bufReturn.add(line);
            }
        }catch (IOException e) {
            System.err.println(e);
        }
        nameStation = Arrays.asList(bufReturn.get(0).split(","));
        nameRailway = Arrays.asList(bufReturn.get(1).split(","));
        int lastIndex = 2;
        for (String nameRailway1 : nameRailway) {
            subRailway.add(Arrays.asList(bufReturn.get(lastIndex).split(",")));
            lastIndex++;
        }
        for (String nameRailway1 : nameRailway) {
            timeRailway.add(Arrays.asList(bufReturn.get(lastIndex).split(",")));
            lastIndex++;
        }
        //</editor-fold>;
    }
    
    @Override
    public void init() throws Exception{
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        readData();
        mkdir();
        this.mainStage = stage;
        this.load = new Loading(stage);
        this.aboutMeUi = new AboutMeUI(stage);
        this.timeTableUi = new TimeTableUi(this.nameStation,this.nameRailway,this.subRailway,this.timeRailway);
        this.mainBorder.setLeft(addLVBox());
        this.mainBorder.setCenter(addMainPane());
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.getIcons().add(new Image(new FileInputStream("src/resources/images/icon.png")));
        stage.setTitle("DFTS");
        stage.setScene(mainScene);
        this.mainBorder.setCenter(mainPage.get("main")); // set main show!
        stage.show();

        //mapTime.show(mapCode.getPath("Lobster","Avocado"));
        //stage.hide();
        
        
        /*-----Debug-----*/
        /*try{
            AdminSite admin = new AdminSite(this.mainStage);
           admin.authen(); 
        }catch(Exception e){e.printStackTrace();};*/
    }
    
    
    private void mkdir(){
        File file = new File("C:\\DFTS_Project");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created! (C:\\DFTS_Project)");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }
    
    
    @Override
    public void stop() throws Exception {
        super.stop(); 
        System.exit(0);
    }
}
