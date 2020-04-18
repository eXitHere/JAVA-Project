package dfts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class AdminSite {
    
    private List<Pair<String,String>> userAdmin = new ArrayList<>();
    private List<String> saleDetil = new ArrayList<>();
    
    private Stage login = new Stage();
    private Scene sceneLogin;
    private Stage body = new Stage();
    private Scene scene;
    private Stage primary;
    private String nameDisplay="Thana";
    private List<String> btnName = Arrays.asList("Dashboard","จัดการผู้ใช้งาน","จัดการสถานี");
    private SaleManager saleMG;
    private GridPane gridUserDisplay = new GridPane();
    private UserManager userMG;
    private User userSelected;
    private TableView tableSale = new TableView();
    
    public AdminSite(Stage primary) throws FileNotFoundException{
        readDatabaseSale();
        ScrollPane scrollPane = new ScrollPane(); //หน้าหลัก
        BorderPane userPane = new BorderPane();   //จัดการผู้ใช้งาน
        BorderPane stationPane = new BorderPane();   //จัดการผู้ใช้งาน
        
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefSize(1060, 720);
        VBox vbox = new VBox();
        scrollPane.setContent(vbox);
        
        this.body.getIcons().add(new Image(new FileInputStream("src/resources/images/icon.png")));
        this.body.initModality(Modality.APPLICATION_MODAL);
        this.primary = primary;
        //read
        readDatabase();
        //addAdmin(new Pair<>("eiei","bibi"));
        //deleteAdmin(new Pair<>("eiei","bibi"));

        BorderPane pane = new BorderPane();
        /*------------VBOX_Control--------------*/
        VBox vboxController = new VBox();
        vboxController.setPrefSize(220, 720);
        vboxController.setPadding(new Insets(10));
        vboxController.setStyle("-fx-background-color: linear-gradient(#272945, #1B1E3A);");
        vboxController.setAlignment(Pos.TOP_CENTER);
        
        Label title = new Label(String.format("Welcome : %s", nameDisplay));
        title.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 24));
        title.setStyle("-fx-text-fill:black");
        title.setWrapText(false);
        VBox.setMargin(title, new Insets(20,10,20,10));
        vboxController.getChildren().add(title);
        // <editor-fold defaultstate="collapsed" desc="RGB">
        Thread randomeColor = new Thread(() -> {
                try{
                    while(true){
                        title.setTextFill(Color.color(Math.abs(Math.random()), Math.abs(Math.random()), Math.abs(Math.random())));
                        TimeUnit.MILLISECONDS.sleep(500);
                        //System.out.println("Do");
                    }
                }catch(Exception e){e.printStackTrace();};
                Platform.runLater(() -> {
                });
            });
        randomeColor.start();
        // </editor-fold>;
        // <editor-fold defaultstate="collapsed" desc="Button adding">
        for(var x : this.btnName){
            Button btn = new Button(String.format(x));
            btn.setPrefSize(260, 60);
            btn.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 18));
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: transparent; -fx-border-width: 0;");
            VBox.setMargin(btn, new Insets(5));
            btn.setOnAction(btnLeftClickEvent);
            btn.setId(x);
            btn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btn.setEffect(new DropShadow());
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: transparent, white;; -fx-border-width: 1, 1;");
            });

            btn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btn.setEffect(null);
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: transparent; -fx-border-width: 0;");
            });
            btn.setOnAction((e)->{
                switch(this.btnName.indexOf(x)){
                    case 0: pane.setCenter(scrollPane); //Dashboard
                        break;
                    case 1: pane.setCenter(userPane);   //จัดการผู้ใช้งาน
                        break;
                    case 2: pane.setCenter(stationPane);   //จัดการผู้ใช้งาน
                        break;
                }
            });
            vboxController.getChildren().add(btn);
        }
        // </editor-fold>;
        /*--------------------------------------*/
        
        /*------------VBOX_main-----------------*/
        vbox.getChildren().add(getUserInfo());
        vbox.getChildren().add(getAnalysis());
        /*--------------------------------------*/
        
        /*------------ User All -----------------*/
        userPane.setLeft(getUserAll());
        userPane.setCenter(displayUser());
        /*--------------------------------------*/
        
        //this.vboxStation.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);");
        //this.vboxStation.setStyle("-fx-border-color: white, Black;; -fx-border-width: 1;");
        this.vboxStation.setPrefSize(490, 700);
        this.vboxStation.setMaxSize(490, 700);
        this.vboxStation.setMinSize(490, 700);
        this.vboxStation.setPadding(new Insets(10));
        
        //this.vboxReadReview.setStyle("-fx-background-color: Red;");
        //this.vboxReadReview.setStyle("-fx-border-color: white -fx-border-width: 1;");
        this.vboxReadReview.setPrefSize(320, 700);
        this.vboxReadReview.setMaxSize(320, 700);
        this.vboxReadReview.setMinSize(320, 700);
        this.vboxReadReview.setPadding(new Insets(10));
        HBox.setMargin(this.vboxReadReview, new Insets(0,0,0,10));
        this.vboxReadReview.setAlignment(Pos.TOP_CENTER);
        
        /*---------------Station----------------*/
        HBox hboxMain = new HBox();
        hboxMain.getChildren().addAll(this.vboxStation,this.vboxReadReview);
        hboxMain.setPadding(new Insets(10));
        stationPane.setLeft(getStationAll());
        stationPane.setCenter(hboxMain);
        //stationPane.setRight();
        /*--------------------------------------*/
        
        pane.setLeft(vboxController);
        pane.setCenter(scrollPane);
        this.scene = new Scene(pane,1280,720);
        this.body.setScene(scene);
        this.body.setOnHidden((e)->{
            randomeColor.stop();
            primary.show();
        });
    }
    
    private VBox vboxReadReview = new VBox();
    
    private VBox vboxStation = new VBox();
    private void changeStation(String name){
        try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.station", name))); ObjectInputStream oi = new ObjectInputStream(fi)){
            Station pr1;
            pr1 = (Station) oi.readObject();
            this.vboxStation.getChildren().clear();
            //this.vboxStation.getChildren().add();
            try{
                ImageView imageView = new ImageView(new Image(new FileInputStream(new File(pr1.getPathImage()))));
                VBox.setMargin(imageView, new Insets(10,10,10,10));
                VBox body = new VBox();
                body.getChildren().add(imageView);
                Label lbName = new Label("สถานี " + pr1.getName());
                Label lbPersen = new Label(String.format("การใช้บริการสถานีนี้ %.2f %%", pr1.getPersen()));
                TextArea lbReview = new TextArea(pr1.getReview());
                lbName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 25));
                lbPersen.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
                lbReview.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
                lbName.setStyle("-fx-text-fill:Blue;");
                lbPersen.setStyle("-fx-text-fill:Black;");
                lbReview.setStyle("-fx-text-fill:Black;");
                lbReview.setMinSize(400, 200);
                lbReview.setPrefSize(400, 200);
                lbReview.setMaxSize(400, 200);
                //lbReview.setAlignment(Pos.TOP_LEFT);
                HBox rateBox = new HBox();
                rateBox.setAlignment(Pos.CENTER);
                ImageView imView = pr1.setRateImage();
                HBox.setMargin(imView, new Insets(5));
                rateBox.getChildren().addAll(imView);
                body.getChildren().addAll(lbName,rateBox,lbPersen,lbReview);
                body.setAlignment(Pos.TOP_CENTER);
                //System.out.println("Created!");
                Button btnSave = new Button("บันทึก");
                btnSave.setPrefSize(200, 60);
                btnSave.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 18));
                btnSave.setStyle("-fx-background-color: linear-gradient(#272945, #1B1E3A);; -fx-text-fill: white");
                VBox.setMargin(btnSave, new Insets(20,20,20,20));
                btnSave.setOnAction((e)->{
                    pr1.setReview(lbReview.getText());
                    try(FileOutputStream f = new FileOutputStream(new File(String.format("src/resources/data/%s.station", name)));ObjectOutputStream o = new ObjectOutputStream(f);){
                        o.writeObject(pr1);
                        //System.out.println("Update!");
                    }
                    catch(Exception a){a.printStackTrace();}
                });
                this.vboxStation.setAlignment(Pos.TOP_CENTER);
                this.vboxStation.getChildren().addAll(body,btnSave);
                
                
                btnSave.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    btnSave.setEffect(new DropShadow());
                });
                btnSave.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    btnSave.setEffect(null);
                });
            }
            catch(Exception ex){
                System.out.println(ex);
            }
            //System.out.println(pr1);
        }
        catch(Exception e){e.printStackTrace();}
        
        /* Change review !*/
        this.vboxReadReview.getChildren().clear();
        ScrollPane scrollpane = new ScrollPane();
        //scrollpane.setStyle("-fx-background-color: black;");
        //scrollpane.setStyle("-fx-border-color: white, Black;; -fx-border-width: 1;");
        scrollpane.setPrefHeight(620);
        Label title = new Label("รีวิวสถานี " + name);
        try{
            title.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 24));
        }catch(Exception e){}
        title.setStyle("-fx-text-fill:linear-gradient(#272945, #1B1E3A);");
        //title.setPrefWidth(380);
        VBox.setMargin(title, new Insets(20,10,20,10));
        this.vboxReadReview.getChildren().addAll(title,scrollpane);
        int ch;
        String temp="";
        VBox AllReview = new VBox();
        scrollpane.setContent(AllReview);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        try(FileReader in = new FileReader(String.format("src/resources/data/%s.review",name));){
            while ((ch=in.read())!=-1){
                if(ch=='|'){
                    //System.out.println(temp);
                    VBox tempVBox = new VBox();
                    tempVBox.setPrefSize(265, 100);
                    tempVBox.setPadding(new Insets(10));
                    tempVBox.setStyle("-fx-border-color: white, Black;; -fx-border-width: 1;");
                    VBox.setMargin(tempVBox, new Insets(10));
                    String[] spilt = temp.split("___");
                    Label phone_ = new Label("เบอร์โทรศัพท์: " + spilt[0]);
                    Hyperlink review_ = new Hyperlink(spilt[1]);
                    Label rating_ = new Label("ความพึงพอใจ: " + spilt[2] + " (/2)");
                    Label date_ = new Label("วันที่: " + spilt[3]);
                    phone_.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
                    phone_.setStyle("-fx-text-fill:black;");
                    review_.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
                    //review_.setStyle("-fx-text-fill:black;");
                    rating_.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
                    rating_.setStyle("-fx-text-fill:black;");
                    date_.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
                    date_.setStyle("-fx-text-fill:black;");
                    
                    phone_.setPadding(new Insets(5));
                    review_.setPadding(new Insets(5));
                    rating_.setPadding(new Insets(5));
                    date_.setPadding(new Insets(5));
                    
                    review_.setOnAction((e)->{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("DFTS");
                        alert.setGraphic(null);
                        alert.initOwner(this.body);
                        alert.setContentText("ความคิดเห็นของ: " + spilt[0]);
                        alert.setHeaderText(spilt[1]);
                        alert.showAndWait();
                    });
                    
                    tempVBox.getChildren().addAll(phone_,review_,rating_,date_);
                    temp ="";
                    AllReview.getChildren().add(tempVBox);
                }
                else{
                    temp+=(char)(ch);
                }
            }    
        }
        catch(Exception e){}
    }
    
    
    private ListView<Label> listStation;
    private ObservableList<Label> entriesStation = FXCollections.observableArrayList();
    private VBox getStationAll(){
        this.vboxStation.setPadding(new Insets(20));
        VBox vbox = new VBox();
        File file = new File("src/resources/data/");
        List<String> listStation = new ArrayList<>();
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.toLowerCase().endsWith(".station")){
                    return true;
                } else {
                    return false;
                }
            }
        });
        for(File f:files){
            String[] name = ((String)f.getName()).split(".station");
            listStation.add(name[0]);
        }
        
        this.listStation = new ListView<>();
        listStation.forEach((x) -> {
            //list.getItems().add(initLabelGetUser(x,18,"Black"));
            this.entriesStation.add(initLabelGetUser(x,18,"Black"));
        });
        
        this.listStation.setPrefWidth(200);
        this.listStation.setPrefHeight(540);
        VBox.setVgrow(this.listStation, Priority.ALWAYS);
        
        // <editor-fold defaultstate="collapsed" desc="SearchBox">
        Label title = initLabelGetUser("ค้นหา ชื่อสถานี",18,"Black");
        VBox.setMargin(title, new Insets(10,5,10,5));
        TextField search = new TextField();
        search.setPromptText("กรุณาป้อนชื่อสถานี");
        VBox.setMargin(search, new Insets(0,5,10,5));
        
        this.listStation.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Label> ov, Label old_val, Label new_val) -> {
            Label selectedItem = this.listStation.getSelectionModel().getSelectedItem();
            int index = this.listStation.getSelectionModel().getSelectedIndex();
            try{
                changeStation(selectedItem.getText());
            }
            catch(Exception e){}
        });
        
        FilteredList<Label> filteredData = new FilteredList<>(entriesStation, s -> true);
        search.textProperty().addListener(obs->{
            String filter = search.getText().toUpperCase(); 
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.getText().toUpperCase().contains(filter));
            }
        });
        
        this.listStation.setItems(filteredData);
        
        try{
            search.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        }catch(FileNotFoundException e){e.printStackTrace();}
        // </editor-fold>;
        
        Button update = new Button("อัพเดทข้อมูล");
        update.setPrefSize(200, 60);
        try{update.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 18));}catch(Exception e){}
        update.setStyle("-fx-background-color: linear-gradient(#272945, #1B1E3A);; -fx-text-fill: white; -fx-border-color: transparent; -fx-border-width: 0;");
        VBox.setMargin(update, new Insets(10,0,10,0));
        vbox.getChildren().addAll(title,search,this.listStation,update);
        vbox.setPadding(new Insets(10));
        
        
        update.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            update.setEffect(new DropShadow());
        });
        update.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            update.setEffect(null);
        });
                
        return vbox;
        
    }
    
    private Label initLabelGetUser(String txt,int size,String color){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        Label temp = new Label(txt);
        temp.setAlignment(Pos.CENTER);
        try{
            temp.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), size));
        }
        catch(FileNotFoundException e){}
        temp.setStyle("-fx-text-fill:"+ color +";");
        // </editor-fold>;
        return temp;
    }
    
    private ObservableList<Label> entries = FXCollections.observableArrayList();
    
    private HBox displayUser() throws FileNotFoundException{
        /*-----------------UI USER------------------*/
        Label firstName = new Label("ชื่อจริง");
        firstName.setStyle("-fx-text-fill: black;");
        firstName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        TextField firstNameTextField = new TextField();
        //firstNameTextField.setPromptText("กรุณาป้อนชื่อจริง");
        firstNameTextField.setId("0");
        firstNameTextField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        
        Label lastName = new Label("นามสกุล");
        lastName.setStyle("-fx-text-fill: black;");
        lastName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        TextField lastNameTextField = new TextField();
        //lastNameTextField.setPromptText("กรุณาป้อนนามสกุล");
        lastNameTextField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        lastNameTextField.setId("1");
        
        Label birthDay = new Label("วันเกิด");
        birthDay.setStyle("-fx-text-fill: black;");
        birthDay.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        TextField birthDayField = new TextField();
        //birthDayField.setPromptText("กรุณาป้อนวันเกิด");
        birthDayField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        birthDayField.setId("2");
        
        Label phoneNumber = new Label("เบอร์โทรศัพท์");
        phoneNumber.setStyle("-fx-text-fill: black;");
        phoneNumber.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));

        TextField phoneNumberField = new TextField();
        //phoneNumberField.setPromptText("กรุณาป้อนเบอร์โทรศัพท์");
        phoneNumberField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        phoneNumberField.setId("3");
        
        Label password = new Label("รหัสผ่าน");
        password.setStyle("-fx-text-fill: black;");
        password.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        TextField  passwordField = new TextField();
        //passwordField.setPromptText("กรุณาป้อนรหัสผ่าน อย่างน้อย 6 หลัก");
        passwordField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        passwordField.setId("4");
        
        this.gridUserDisplay.add(firstName, 0, 0);
        this.gridUserDisplay.add(firstNameTextField, 1, 0);
        this.gridUserDisplay.add(lastName, 0, 1);
        this.gridUserDisplay.add(lastNameTextField, 1, 1);
        this.gridUserDisplay.add(birthDay, 0, 2);
        this.gridUserDisplay.add(birthDayField, 1, 2);
        this.gridUserDisplay.add(phoneNumber, 0, 3);
        this.gridUserDisplay.add(phoneNumberField, 1, 3);
        this.gridUserDisplay.add(password, 0,4);
        this.gridUserDisplay.add(passwordField, 1, 4);
        
        this.gridUserDisplay.setVgap(10);
        this.gridUserDisplay.setHgap(10);
        this.gridUserDisplay.setPadding(new Insets(30));
        
        Button btnSave = new Button("บันทึก");
        btnSave.setPrefSize(200, 60);
        btnSave.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 18));
        btnSave.setStyle("-fx-background-color: linear-gradient(#272945, #1B1E3A); -fx-text-fill: white;");
        VBox.setMargin(btnSave, new Insets(10));
        btnSave.setOnAction(btnLeftClickEvent);
        btnSave.setId("บันทึก");
        btnSave.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSave.setEffect(new DropShadow());
        });

        btnSave.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSave.setEffect(null);
        });
        
        phoneNumberField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(phoneNumberField.getLength()>10){
                phoneNumberField.setText(oldValue);
            }
            if (!newValue.matches("\\d*")) {
                phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        btnSave.setOnAction((e)->{  
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DFTS REGISTER");
            alert.setContentText("เบอร์โทร " + this.userSelected.getPhoneNumber());
            alert.setHeaderText("ต้องการบันทึกข้อมูล");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if(firstNameTextField.getText().isEmpty()||
                    lastNameTextField.getText().isEmpty() ||
                    phoneNumberField.getText().isEmpty()  ||
                    passwordField.getText().isEmpty()     ||
                    birthDayField.getText().isEmpty()) return;
                this.userSelected.update(firstNameTextField.getText(), lastNameTextField.getText(), phoneNumberField.getText(), birthDayField.getText(), passwordField.getText());
                try(FileOutputStream f = new FileOutputStream(new File(String.format("src/resources/data/%s.user",this.userSelected.getPhoneNumber())));ObjectOutputStream o = new ObjectOutputStream(f);){
                    o.writeObject(this.userSelected); 
                    System.out.println("Update success!");
                }
                catch (Exception x){
                    System.out.println(x);
                }     
            }
            
            
        });
        
        Button btnDelete = new Button("ลบ");
        btnDelete.setPrefSize(200, 60);
        btnDelete.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 18));
        btnDelete.setStyle("-fx-background-color: linear-gradient(#272945, #1B1E3A); -fx-text-fill: white;");
        VBox.setMargin(btnDelete, new Insets(10));
        btnDelete.setOnAction(btnLeftClickEvent);
        btnDelete.setId("ลบ");
        btnDelete.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnDelete.setEffect(new DropShadow());
        });

        btnDelete.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnDelete.setEffect(null);
        });
        
        btnDelete.setOnAction((e)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DFTS REGISTER");
            alert.setContentText("เบอร์โทร " + this.userSelected.getPhoneNumber());
            alert.setHeaderText("ต้องการลบข้อมูล");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if(!phoneNumberField.getText().isEmpty()){
                    userMG.RemoveUser(phoneNumberField.getText());
                    int selectedIdx = this.listUser.getSelectionModel().getSelectedIndex();
                    if (selectedIdx != -1) {
                        int newSelectedIdx = (selectedIdx == this.listUser.getItems().size() - 1) ? 
                        selectedIdx - 1 : selectedIdx;
                        //System.out.println("Index remove " + this.listUser.getSelectionModel().getSelectedItem());
                        this.entries.remove(this.listUser.getSelectionModel().getSelectedItem());
                        //System.out.println("Removed" + itemToRemove);
                        this.listUser.getSelectionModel().select(newSelectedIdx);
                    }
                }
            }
        });
        
        this.gridUserDisplay.add(btnSave, 0, 5);
        this.gridUserDisplay.add(btnDelete, 1, 5);
        /*------------------------------------------*/
        
        /*
        --------------V-box---------------------------
        */
        VBox vboxSubMain = new VBox();
        
        Label label = new Label("ประวัติการใช้บริการ");
        label.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
 
        this.tableSale.setEditable(false);
 
        TableColumn phoneCol = new TableColumn("เบอร์โทรศัพท์");
        TableColumn stationsCol = new TableColumn("สถานี");
        TableColumn countCol = new TableColumn("จำนวนตั๋ว");
        TableColumn dateCol = new TableColumn("วันที่ซื้อ");
        TableColumn priceCol = new TableColumn("ราคาตั๋ว");
        
        this.tableSale.getColumns().addAll(phoneCol, stationsCol,countCol,dateCol,priceCol);
        TableColumn startCol = new TableColumn("ต้นทาง");
        TableColumn stopCol = new TableColumn("ปลายทาง");

        stationsCol.getColumns().addAll(startCol, stopCol);
        
        phoneCol.setCellValueFactory(new PropertyValueFactory<Sale, String>("phone"));
        startCol.setCellValueFactory(new PropertyValueFactory<Sale, String>("start"));
        stopCol.setCellValueFactory(new PropertyValueFactory<Sale, String>("stop"));
        countCol.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("count"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Sale, String>("date"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Sale, Double>("price"));
        
        
        VBox detailReport = new VBox();
        detailReport.setPrefSize(330, 700); //820
        detailReport.setMinSize(330, 700);
        detailReport.setMaxSize(330, 700);
        detailReport.setStyle(" -fx-border-color: Green, white;; -fx-border-width: 1, 1;");
        detailReport.setAlignment(Pos.TOP_CENTER);
        
        Label T1 = initLabelGetUser("รายงานการใช้บริการรายบุคคล", 24, "Black");
        T1.setPadding(new Insets(20));
        
        Label T2 = initLabelGetUser("การใช้บริการทั้งหมด", 18, "Black");
        T2.setPadding(new Insets(10));
        
        T_2.setPadding(new Insets(10,0,20,0));
        
        Label T3 = initLabelGetUser("รายได้ทั้งหมด", 18, "Black");
        T2.setPadding(new Insets(10));
        
        T_3.setPadding(new Insets(10,0,20,0));
        
        Label T4 = initLabelGetUser("การใช้บริการครั้งล่าสุด", 18, "Black");
        T4.setPadding(new Insets(10));
        
        T_4.setPadding(new Insets(10,0,20,0));
        
        detailReport.getChildren().addAll(T1,T2,T_2,T3,T_3,T4,T_4);
        /*
        ----------------------------------------------
        */
        vboxSubMain.setPrefSize(480, 700); //820
        vboxSubMain.setMinSize(480, 700);
        vboxSubMain.setMaxSize(480, 700);
        //this.gridUserDisplay.setMaxSize(860, 720);
        //this.gridUserDisplay.setPrefSize(860, 720);
        //vboxSubMain.setStyle("-fx-background-color: ORANGE; -fx-text-fill: white;");
        vboxSubMain.getChildren().addAll(this.gridUserDisplay,this.tableSale);
        //vboxSubMain.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(this.gridUserDisplay, new Insets(10,10,10,10));
        
        HBox hboxMain = new HBox();
        hboxMain.getChildren().addAll(vboxSubMain,detailReport);
        HBox.setMargin(vboxSubMain, new Insets(10,10,10,10));
        HBox.setMargin(detailReport, new Insets(10,0,10,0));
        return hboxMain;
    }
    /*
    Detail report
    */
    private Label T_2 = initLabelGetUser("- ครั้ง", 18, "Black");
    private Label T_3 = initLabelGetUser("- บาท", 18, "Black");
    private Label T_4 = initLabelGetUser("-", 18, "Black");
    
    private void changeUserDisplay(User user){
        this.userSelected = user;
        //System.out.println("User " + user);
        List<Node> node = this.gridUserDisplay.getChildren();
        for(var x : node){
            if(x instanceof TextField)
            if(x.getId().equals("0"))      if(user!=null) ((TextField)x).setText(user.getFirstName());   else ((TextField)x).setText("");
            else if(x.getId().equals("1")) if(user!=null) ((TextField)x).setText(user.getLastName());    else ((TextField)x).setText("");
            else if(x.getId().equals("2")) if(user!=null) ((TextField)x).setText(user.getBirthDay());    else ((TextField)x).setText("");
            else if(x.getId().equals("3")) if(user!=null) ((TextField)x).setText(user.getPhoneNumber()); else ((TextField)x).setText("");
            else if(x.getId().equals("4")) if(user!=null) ((TextField)x).setText(user.getPassword());    else ((TextField)x).setText("");
        }
        this.tableSale.setItems(this.saleMG.getDataByPhone(((Label)this.listUser.getSelectionModel().getSelectedItem()).getText()));
        this.T_2.setText(String.format("%.0f ครั้ง", this.saleMG.getDetailByPhone().get(1)));
        this.T_3.setText(String.format("%.2f บาท", this.saleMG.getDetailByPhone().get(0)));
        this.T_4.setText(String.format("%s", this.saleMG.getDateNeares()));
        //System.out.println("Pass");
    }
    
    private ListView<Label> listUser;
    private VBox getUserAll() throws FileNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        VBox vbox = new VBox();
        this.userMG = new UserManager();
        this.listUser = new ListView<>();
        this.userMG.getPhoneAll().forEach((x) -> {
            //list.getItems().add(initLabelGetUser(x,18,"Black"));
            this.entries.add(initLabelGetUser(x,18,"Black"));
        });
        
        this.listUser.setPrefWidth(200);
        this.listUser.setPrefHeight(600);
        VBox.setVgrow(this.listUser, Priority.ALWAYS);
        
        // <editor-fold defaultstate="collapsed" desc="SearchBox">
        Label title = initLabelGetUser("ค้นหา (เบอร์โทรศัพท์)",18,"Black");
        VBox.setMargin(title, new Insets(10,5,10,5));
        TextField search = new TextField();
        search.setPromptText("กรุณาป้อนเบอร์โทรศัพท์");
        VBox.setMargin(search, new Insets(0,5,10,5));
        
        this.listUser.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Label> ov, Label old_val, Label new_val) -> {
            Label selectedItem = this.listUser.getSelectionModel().getSelectedItem();
            int index = this.listUser.getSelectionModel().getSelectedIndex();
            try{
                //System.out.println("Do1");
                //System.out.println(userMG.getInfoUser(selectedItem.getText()));
                changeUserDisplay(this.userMG.getInfoUser(selectedItem.getText()));
            }
            catch(Exception e){}
        });
        
        FilteredList<Label> filteredData = new FilteredList<>(entries, s -> true);
        search.textProperty().addListener(obs->{
            String filter = search.getText(); 
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.getText().contains(filter));
            }
        });
        
        this.listUser.setItems(filteredData);
        
        try{
            search.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        }catch(FileNotFoundException e){e.printStackTrace();}
        // </editor-fold>;
        
        vbox.getChildren().addAll(title,search,this.listUser);
        vbox.setPadding(new Insets(10));
        return vbox;
        // </editor-fold>;
    }
    
    private HBox getAnalysis(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        HBox hbox = new HBox();
        var x = saleMG.getMonthUse();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Month");
        LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("การใช้บริการแสดงตามเดือนและการเป็นสมาชิก");
        //System.out.println(x);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("สมาชิก");
        series1.getData().add(new XYChart.Data("Jan", x.getKey().get(0)));
        series1.getData().add(new XYChart.Data("Feb", x.getKey().get(1)));
        series1.getData().add(new XYChart.Data("Mar", x.getKey().get(2)));
        series1.getData().add(new XYChart.Data("Apr", x.getKey().get(3)));
        series1.getData().add(new XYChart.Data("May", x.getKey().get(4)));
        series1.getData().add(new XYChart.Data("Jun", x.getKey().get(5)));
        series1.getData().add(new XYChart.Data("Jul", x.getKey().get(6)));
        series1.getData().add(new XYChart.Data("Aug", x.getKey().get(7)));
        series1.getData().add(new XYChart.Data("Sep", x.getKey().get(8)));
        series1.getData().add(new XYChart.Data("Oct", x.getKey().get(9)));
        series1.getData().add(new XYChart.Data("Nov", x.getKey().get(10)));
        series1.getData().add(new XYChart.Data("Dec", x.getKey().get(11)));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("ไม่เป็นสมาชิก");
        series2.getData().add(new XYChart.Data("Jan", x.getValue().get(0)));
        series2.getData().add(new XYChart.Data("Feb", x.getValue().get(1)));
        series2.getData().add(new XYChart.Data("Mar", x.getValue().get(2)));
        series2.getData().add(new XYChart.Data("Apr", x.getValue().get(3)));
        series2.getData().add(new XYChart.Data("May", x.getValue().get(4)));
        series2.getData().add(new XYChart.Data("Jun", x.getValue().get(5)));
        series2.getData().add(new XYChart.Data("Jul", x.getValue().get(6)));
        series2.getData().add(new XYChart.Data("Aug", x.getValue().get(7)));
        series2.getData().add(new XYChart.Data("Sep", x.getValue().get(8)));
        series2.getData().add(new XYChart.Data("Oct", x.getValue().get(9)));
        series2.getData().add(new XYChart.Data("Nov", x.getValue().get(10)));
        series2.getData().add(new XYChart.Data("Dec", x.getValue().get(11)));
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("ทั้งหมด");
        series3.getData().add(new XYChart.Data("Jan", x.getValue().get(0) + x.getKey().get(0)));
        series3.getData().add(new XYChart.Data("Feb", x.getValue().get(1) + x.getKey().get(1)));
        series3.getData().add(new XYChart.Data("Mar", x.getValue().get(2) + x.getKey().get(2)));
        series3.getData().add(new XYChart.Data("Apr", x.getValue().get(3) + x.getKey().get(3)));
        series3.getData().add(new XYChart.Data("May", x.getValue().get(4) + x.getKey().get(4)));
        series3.getData().add(new XYChart.Data("Jun", x.getValue().get(5) + x.getKey().get(5)));
        series3.getData().add(new XYChart.Data("Jul", x.getValue().get(6) + x.getKey().get(6)));
        series3.getData().add(new XYChart.Data("Aug", x.getValue().get(7) + x.getKey().get(7)));
        series3.getData().add(new XYChart.Data("Sep", x.getValue().get(8) + x.getKey().get(8)));
        series3.getData().add(new XYChart.Data("Oct", x.getValue().get(9) + x.getKey().get(9)));
        series3.getData().add(new XYChart.Data("Nov", x.getValue().get(10) + x.getKey().get(10)));
        series3.getData().add(new XYChart.Data("Dec", x.getValue().get(11) + x.getKey().get(11)));
        lineChart.getData().addAll(series1, series2, series3);
        lineChart.setMaxSize(600, 340);
        lineChart.setStyle("@font-face {font-family: 'myFont';src: url('src/resources/fonts/PrintAble4U_Regular.ttf') format('truetype');};-fx-font:myFont;");
        
        var y =  saleMG.getDayUse();
        CategoryAxis xAxis_ = new CategoryAxis();
        NumberAxis yAxis_ = new NumberAxis();
        BarChart<String, Number> sbc = new BarChart<>(xAxis_, yAxis_);
        XYChart.Series<String, Number> series1_ = new XYChart.Series<>();
        sbc.setTitle("การใช้บริการทั้งหมดแบ่งตามวันที่");
        xAxis_.setLabel("วันที่");
        xAxis_.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturay")));
        yAxis_.setLabel("จำนวนการใช้บริการ");
        series1_.setName("การใช้บริการ");
        series1_.getData().add(new XYChart.Data<>( "Sunday", y.get(0)));
        series1_.getData().add(new XYChart.Data<>("Monday", y.get(1)));
        series1_.getData().add(new XYChart.Data<>("Tuesday", y.get(2)));
        series1_.getData().add(new XYChart.Data<>("Wednesday", y.get(3)));
        series1_.getData().add(new XYChart.Data<>("Thursday", y.get(4)));
        series1_.getData().add(new XYChart.Data<>("Friday", y.get(5)));
        series1_.getData().add(new XYChart.Data<>("Saturay", y.get(6)));
        sbc.getData().addAll(series1_);
        sbc.setMaxSize(500, 340);
        sbc.setStyle("@font-face {font-family: 'myFont';src: url('src/resources/fonts/PrintAble4U_Regular.ttf') format('truetype');};-fx-font:myFont;");
        
        hbox.getChildren().addAll(lineChart,sbc);
        hbox.setPadding(new Insets(50,20,20,20));
        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefSize(1060, 340);
        return hbox;
        // </editor-fold>;
    }
    
    private HBox getUserInfo(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        HBox hbox = new HBox();
        var userUse = this.saleMG.getMemberUse();
        //System.out.println(userUse);
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
            new PieChart.Data("เป็นสมาชิก", userUse.getKey()),
            new PieChart.Data("ไม่เป็นสมาชิก", userUse.getValue()));
        PieChart chartUser = new PieChart(pieChartData);
        chartUser.setStyle("@font-face {font-family: 'myFont';src: url('src/resources/fonts/PrintAble4U_Regular.ttf') format('truetype');};-fx-font:myFont;");
        chartUser.setLabelLineLength(10);
        chartUser.setTitle("การใช้บริการ");
        chartUser.setMaxSize(500, 340);
        
        
        var count = saleMG.getListCount();
        //System.out.println(count);
        ObservableList<PieChart.Data> pieChartData_ =
            FXCollections.observableArrayList(
            new PieChart.Data("1", count.get(0)),
            new PieChart.Data("2", count.get(1)),
            new PieChart.Data("3", count.get(2)),
            new PieChart.Data("4", count.get(3)),
            new PieChart.Data("5", count.get(4)),
            new PieChart.Data("6", count.get(5)),
            new PieChart.Data("7", count.get(6)),
            new PieChart.Data("8", count.get(7)),
            new PieChart.Data("9", count.get(8)),
            new PieChart.Data("10", count.get(9)));
        PieChart chartCount = new PieChart(pieChartData_);
        chartCount.setStyle("@font-face {font-family: 'myFont';src: url('src/resources/fonts/PrintAble4U_Regular.ttf') format('truetype');};-fx-font:myFont;");
        //chartCount.setLabelLineLength(20);
        chartCount.setClockwise(true);
        chartCount.setTitle("การซื้อตั๋ว");
        chartCount.setLabelsVisible(Boolean.TRUE);
        chartCount.setMaxSize(500, 340);
        hbox.getChildren().addAll(chartUser,chartCount);
        hbox.setPadding(new Insets(20));
        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefSize(1060, 340);
        return hbox;
        // </editor-fold>;
    }
    
    private EventHandler<ActionEvent> btnLeftClickEvent = new EventHandler<ActionEvent>(){
        @Override
        // <editor-fold defaultstate="collapsed" desc="Button event">
        public void handle(ActionEvent event) {
            Button btn = (Button)event.getSource();
            System.out.println(btn.getId());
        }
        // </editor-fold>;
    };
    
    private void addAdmin(Pair<String,String> user){
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        this.userAdmin.add(user);
        String out = user.getKey()+","+user.getValue()+"|";
        try(FileWriter fw=new FileWriter("src/resources/data/admin.bin",true);){  
            for (int i = 0; i < out.length(); i++) 
                fw.write(out.charAt(i)+0xF);
        }
        catch(Exception e){ System.out.println("File not found"); }
        System.out.println("new");
        for(var x : this.userAdmin){
            System.out.println(x.getKey() + " -- " + x.getValue());
        }
        // </editor-fold>;
    }
    
    private void deleteAdmin(Pair<String,String> user){
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        String out ="";
        for(var x : this.userAdmin){
            if(!(x.getKey().equals(user.getKey()) && x.getValue().equals(user.getValue())))
                out+=x.getKey()+","+x.getValue()+"|";
        }
        
        try(FileWriter fw=new FileWriter("src/resources/data/admin.bin");){  
            for (int i = 0; i < out.length(); i++) 
                fw.write(out.charAt(i)+0xF); 
        }
        catch(Exception e){ System.out.println("File not found"); }
        // </editor-fold>;
    }
    
    private void readDatabase(){ // Admin user
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        this.userAdmin = new ArrayList<>();
        int ch;
        String temp ="";
        try(FileReader in = new FileReader("src/resources/data/admin.bin");){
            while ((ch=in.read())!=-1){
                //System.out.print((char)(ch-0xF));
                if(ch=='|'+0xF){
                    //System.out.println(temp);
                    String[] spilt = temp.split(",");
                    this.userAdmin.add(new Pair<>(spilt[0x0],spilt[0x1]));
                    temp ="";
                }
                else{
                    temp+=(char)(ch-0xF);
                }
            }    
        } 
        catch (Exception e){ System.out.println(e); } 
        for(var x : this.userAdmin){
            System.out.println(x.getKey() + " -- " + x.getValue());
        }
        // </editor-fold>;
    }
    
    private void readDatabaseSale(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        String line;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/resources/data/", "sale.bin") , Charset.forName("UTF-8"))) {
            while ((line = reader.readLine()) != null ) {
                this.saleDetil.add(line);
            }
        }catch (IOException e) {
            System.err.println(e);
        }
        //System.out.println("DOO");
        this.saleMG = new SaleManager(this.saleDetil);
        //System.out.println("Pass");
        // </editor-fold>;
    }
    
    public void authen() throws FileNotFoundException{
        this.body.show();
        /*
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        this.login.getIcons().add(new Image(new FileInputStream("src/resources/images/icon.png")));
        this.login.initModality(Modality.APPLICATION_MODAL);
        this.login.setResizable(false);
        this.login.setTitle("Login");
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setVgap(15);
        pane.setHgap(15);
        this.sceneLogin = new Scene(pane,280,200);
        Label title = new Label("Admin site");
        title.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        title.setStyle("-fx-text-fill:black");
        Label _user = new Label("Username");
        _user.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        _user.setStyle("-fx-text-fill:black");
        Label _pass = new Label("Password");
        _pass.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        _pass.setStyle("-fx-text-fill:black");
        TextField user = new TextField();
        PasswordField pass = new PasswordField();
        
        Button btnLogin = new Button("Login");
        btnLogin.setPrefSize(160, 40);
        btnLogin.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 24));
        btnLogin.setStyle("-fx-background-color: linear-gradient(#94D800, #78A300); -fx-text-fill: white;");
        
        pane.add(title, 0, 0,2,1);
        pane.setHalignment(title, HPos.CENTER);
        pane.add(_user, 0, 1);
        pane.add(user,1,1);
        pane.add(_pass, 0, 2);
        pane.add(pass, 1, 2);
        pane.add(btnLogin, 0, 3,2,1);
        pane.setHalignment(btnLogin, HPos.CENTER);
        this.login.setScene(this.sceneLogin);
        this.login.show();
        
        this.login.setOnHidden((e)->{
            this.primary.show();
        });
        
        btnLogin.setOnAction((e)->{
            if(pass.getText().isEmpty() || user.getText().isEmpty()) return;
            for(var x : this.userAdmin){
                if(x.getKey().equals(user.getText()) && x.getValue().equals(pass.getText())){
                    show();
                    nameDisplay = x.getKey();
                    this.login.hide();
                    this.primary.hide();
                    break;
                }
            }
        });
        
        btnLogin.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnLogin.setEffect(new DropShadow());
        });
                
        btnLogin.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnLogin.setEffect(null);
        });
        // </editor-fold>;
        */
    }
    
    public void show(){
        this.body.show();
    }
}
