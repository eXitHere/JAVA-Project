package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Math.ceil;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import static java.nio.file.Files.size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class PaymentUI extends Application{
    private BorderPane body = new BorderPane();
    private GridPane payment = new GridPane();
    private BorderPane mainPayment = new BorderPane();
    private VBox vboxPayment = new VBox();
    private HBox hboxHeader = new HBox();
    private HBox hboxBottom = new HBox();
    private List<String> member = new ArrayList<>();
    private Pair<String,String> otp;
    //payment

    public PaymentUI() {
    }
    
    
    public PaymentUI(String _item,int _count,int _price) throws FileNotFoundException {
        loadMember();
        this.hboxHeader.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        this.payment.setStyle("-fx-border-color: blue; -fx-border-width: 1 1 1 1;");
        this.hboxBottom.setStyle("-fx-border-color: blue; -fx-border-width: 1 1 1 1;");
        
        this.hboxHeader.setPadding(new Insets(10));
        this.hboxHeader.setAlignment(Pos.CENTER_RIGHT);
        this.hboxHeader.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);");
        
        this.hboxBottom.setPadding(new Insets(0));
        this.hboxBottom.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);");
        
        Label infomation = new Label("เบอร์โทรศัพท์");
        infomation.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        infomation.setStyle("-fx-text-fill:white");
        HBox.setMargin(infomation, new Insets(8,10,0,5));
        
        TextField phoneField = new TextField();
        phoneField.setPromptText("กรุณากรอกเบอร์โทรศัพท์");
        phoneField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        phoneField.setAlignment(Pos.CENTER);
        phoneField.setPrefSize(200, 35);
        HBox.setMargin(phoneField, new Insets(5,0,0,0));
        
        Label status = new Label("สถานะ : ");
        status.setVisible(false);
        status.setStyle("-fx-text-fill:white");
        status.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 16));
        HBox.setMargin(status, new Insets(10,0,0,10));
        
        Label status_ = new Label("...");
        status_.setPrefSize(200, 35);
        status_.setVisible(false);
        status_.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 16));
        HBox.setMargin(status_, new Insets(10,0,0,2));
        
        Button btnBack = new Button("ย้อนกลับ");
        btnBack.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        btnBack.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        btnBack.setPrefSize(120, 35);
        HBox.setMargin(btnBack, new Insets(5, 0, 0, 395));
        
        this.hboxHeader.getChildren().addAll(infomation,phoneField,status,status_,btnBack);
        
        //Botton
        Label infomationMember = new Label("*หากสมัครสมาชิกจะได้รับส่วนลด 5%");
        infomationMember.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        infomationMember.setStyle("-fx-text-fill:white");
        HBox.setMargin(infomationMember, new Insets(20,2,10,10));
        
        Hyperlink hyperlink = new Hyperlink("-สมัครสมาชิก-"); 
        hyperlink.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        hyperlink.setTextFill(Color.BEIGE);
        HBox.setMargin(hyperlink, new Insets(17,0,10,0));
        
        Button btnPay = new Button("จ่ายเงิน");
        btnPay.setStyle("-fx-background-color: linear-gradient(#148400, #23CC00); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);");
        btnPay.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        btnPay.setPrefSize(150, 40);
        HBox.setMargin(btnPay, new Insets(10, 10, 10, 10));
        
        Label otpLabel = new Label("รหัสอ้างอิง: ABCD");
        otpLabel.setVisible(false);
        otpLabel.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        otpLabel.setStyle("-fx-text-fill:white");
        otpLabel.setPrefSize(100, 20);
        HBox.setMargin(otpLabel, new Insets(20,5,10,342));
        
        TextField otpField = new TextField();
        otpField.setVisible(false);
        otpField.setPromptText("OTP");
        otpField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        otpField.setAlignment(Pos.CENTER);
        otpField.setPrefSize(140, 35);
        HBox.setMargin(otpField, new Insets(13,10,10,0));
        
        this.hboxBottom.getChildren().addAll(infomationMember,hyperlink,otpLabel,otpField,btnPay);
        
        //Payment
        this.vboxPayment.setPrefSize(1080,180 );
        //this.vboxPayment.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);");
        this.vboxPayment.setAlignment(Pos.TOP_CENTER);
        
        Label titlePayment = new Label("\nบริษัท Delicious Food Transit System (DFTS) จำกัด");
        titlePayment.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 25));
        titlePayment.setStyle("-fx-text-fill:black");
        
        Label subTitlePayment1 = new Label("\n345/12 ถ. Market เขต Metro 90165");
        subTitlePayment1.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        subTitlePayment1.setStyle("-fx-text-fill:black");
        
        //\n\nโทร 01-234-567 โทรสาร 0-3131-3262 เลขประจำตัวผู้เสียภาษี 4910472763
        
        Label subTitlePayment2 = new Label("\nโทร 01-234-567 โทรสาร 0-3131-3262 เลขประจำตัวผู้เสียภาษี 4910472763");
        subTitlePayment2.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        subTitlePayment2.setStyle("-fx-text-fill:black");
        
        Label subTitlePayment3 = new Label("\nใบแจ้งการชำระเงิน");
        subTitlePayment3.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        subTitlePayment3.setStyle("-fx-text-fill:black");
        
        this.vboxPayment.getChildren().addAll(titlePayment,subTitlePayment1,subTitlePayment2,subTitlePayment3);
        
        this.payment.setGridLinesVisible(true);
        this.payment.getColumnConstraints().add(new ColumnConstraints(800));
        this.payment.getColumnConstraints().add(new ColumnConstraints(168));
        this.payment.getColumnConstraints().add(new ColumnConstraints(100));
        this.payment.getRowConstraints().add(new RowConstraints(40));
        this.payment.getRowConstraints().add(new RowConstraints(320));
        this.payment.getRowConstraints().add(new RowConstraints(40));
        
        Label nameList = new Label("รายการ");
        nameList.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        nameList.setStyle("-fx-text-fill:black");
        GridPane.setHalignment(nameList, javafx.geometry.HPos.CENTER);
        this.payment.add(nameList, 0, 0);
        
        Label countList = new Label("จำนวน");
        countList.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        countList.setStyle("-fx-text-fill:black");
        countList.setAlignment(Pos.CENTER);
        GridPane.setHalignment(countList, javafx.geometry.HPos.CENTER);
        this.payment.add(countList, 1, 0);
        
        Label pricetList = new Label("ราคา");
        pricetList.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        pricetList.setStyle("-fx-text-fill:black");
        pricetList.setAlignment(Pos.CENTER);
        GridPane.setHalignment(pricetList, javafx.geometry.HPos.CENTER);
        this.payment.add(pricetList, 2, 0);
        
        Label itemsName = new Label(String.format("\n     %s", _item));
        itemsName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        itemsName.setStyle("-fx-text-fill:black");
        itemsName.setAlignment(Pos.CENTER);
        //GridPane.setHalignment(itemsName, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(itemsName, javafx.geometry.VPos.TOP);
        this.payment.add(itemsName, 0, 1);
        
        Label itemsCount = new Label(String.format("\n%d", _count));
        itemsCount.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        itemsCount.setStyle("-fx-text-fill:black");
        itemsCount.setAlignment(Pos.CENTER);
        GridPane.setHalignment(itemsCount, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(itemsCount, javafx.geometry.VPos.TOP);
        this.payment.add(itemsCount, 1, 1);
        
        Label itemsPrice = new Label(String.format("\n%.2f", _price*1.00));
        itemsPrice.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        itemsPrice.setStyle("-fx-text-fill:black");
        itemsPrice.setAlignment(Pos.CENTER);
        GridPane.setHalignment(itemsPrice, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(itemsPrice, javafx.geometry.VPos.TOP);
        this.payment.add(itemsPrice, 2, 1);
        
        Label total_name = new Label("รวม\n");
        total_name.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        total_name.setStyle("-fx-text-fill:black");
        GridPane.setHalignment(total_name, javafx.geometry.HPos.CENTER);
        this.payment.add(total_name, 0, 2);
        
        Label total_Count = new Label("-\n");
        total_Count.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        total_Count.setStyle("-fx-text-fill:black");
        GridPane.setHalignment(total_Count, javafx.geometry.HPos.CENTER);
        this.payment.add(total_Count, 1, 2);
        
        Label total_Price = new Label(String.format("%.2f\n", _price*_count*1.00));
        total_Price.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        total_Price.setStyle("-fx-text-fill:black");
        GridPane.setHalignment(total_Price, javafx.geometry.HPos.CENTER);
        this.payment.add(total_Price, 2, 2);
        
        // <editor-fold defaultstate="collapsed" desc="pay click event">
        otp = getOTP();
        btnPay.setDisable(true);
        btnPay.setOnAction((ActionEvent e)->{
            if(btnPay.getText().equals("จ่ายเงิน")){
                otpLabel.setText(String.format("รหัสอ้างอิง: %s", otp.getKey()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("(เป็นระบบจำลอง)");
                alert.setHeaderText("กรอก OTP อันนี้ " + otp.getValue() +"  (รหัสอ้างอิง: " + otp.getKey()+")");
                alert.show();
                btnPay.setText("ส่งใหม่");
                btnPay.setStyle("-fx-background-color: linear-gradient(#FFCE00, #FFCE00); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);");
                otpLabel.setVisible(true);
                otpField.setVisible(true);
            }
            else if(btnPay.getText().equals("ส่งใหม่")){
                otpField.clear();
                otpField.setPromptText("OTP");
                otp = getOTP();
                otpLabel.setText(String.format("รหัสอ้างอิง: %s", otp.getKey()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("(เป็นระบบจำลอง)");
                alert.setHeaderText("กรอก OTP อันนี้ " + otp.getValue() +"  (รหัสอ้างอิง: " + otp.getKey()+")");
                alert.show();
            }
            else{
                if(otp.getValue().equals(otpField.getText())){
                    System.out.println("Pass");
                }
                else{
                    otpField.clear();
                    otpField.setPromptText("OTP ไม่ถูกต้อง");
                }
            }
        });
        // </editor-fold>;
        
        otpField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.length()<6){
                if(otpField.getLength()==0){
                    btnPay.setDisable(false);
                    btnPay.setText("ส่งใหม่");
                    btnPay.setStyle("-fx-background-color: linear-gradient(#FFCE00, #FFCE00); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);");
                }
                else if(otpField.getLength()!=5){
                    btnPay.setDisable(true);
                }
                else{
                    btnPay.setDisable(false);
                    btnPay.setText("ยืนยัน");
                    btnPay.setStyle("-fx-background-color: linear-gradient(#148400, #23CC00); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);");
                }
            }
            else{
                otpField.setText(oldValue);
            }
        });
        
        // <editor-fold defaultstate="collapsed" desc="HyperLink">
        hyperlink.setOnAction((ActionEvent e)->{
            System.out.println("User show");
        });
        // </editor-fold>;
        
        // <editor-fold defaultstate="collapsed" desc="phone field event">
        phoneField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(phoneField.getLength()>10){
                phoneField.setText(oldValue);
            }
            else if (!newValue.matches("\\d*")) {
                phoneField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            
            if(phoneField.getLength()==1){
                status.setVisible(true);
                status_.setVisible(true);
            }
            else if(newValue.length()==0){
                status.setVisible(false);
                status_.setVisible(false);
            }
            else if(phoneField.getLength()==10){
                btnPay.setDisable(false);
                boolean isMember = false;
                for(var x : this.member){
                    if(phoneField.getText().equals(x)){
                        isMember = true;
                        break;
                    }
                }
                if(isMember){
                    status_.setText("เป็นสมาชิก");
                    status_.setTextFill(Color.GREENYELLOW);
                    total_Price.setText(String.format("%.2f\n", _price*_count*.95));
                    itemsName.setText(String.format("\n     %s\n\n     %s", _item,"ส่วนลดจากการเป็นสมาชิก"));
                    //itemsCount.setText(String.format("\n%d\n\n%d", _count,_count));
                    itemsPrice.setText(String.format("\n%.2f\n\n-%.2f",_price*1.00 , _price*_count*.05));
                    total_Price.setTextFill(Color.BLUEVIOLET);
                }
                else{
                    status_.setText("ไม่เป็นสมาชิก");
                    status_.setTextFill(Color.GRAY);
                    total_Price.setText(String.format("%.2f\n", _price*_count*1.00));
                    itemsName.setText(String.format("\n     %s\n\n", _item));
                    //itemsCount.setText(String.format("\n%d", _count));
                    itemsPrice.setText(String.format("\n%.2f",_price*1.00 ));
                    total_Price.setTextFill(Color.BLACK);
                }
            }
            else{
                btnPay.setDisable(true);
                status_.setText("กรุณาป้อนหมายเลขให้ครบ 10 หลัก");
                status_.setTextFill(Color.RED);
                total_Price.setText(String.format("%.2f\n", _price*_count*1.00));
                itemsName.setText(String.format("\n     %s\n\n", _item));
                //itemsCount.setText(String.format("\n%d", _count));
                itemsPrice.setText(String.format("\n%.2f",_price*1.00 ));
                total_Price.setTextFill(Color.BLACK);
            }
        });
        // </editor-fold>;

        // <editor-fold defaultstate="collapsed" desc="Drop shodow">
        DropShadow shadow = new DropShadow();
        
        btnBack.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnBack.setEffect(shadow);
        });
                
        btnBack.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnBack.setEffect(null);
        });
        
        btnPay.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnPay.setEffect(shadow);
        });
                
        btnPay.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnPay.setEffect(null);
        });
        // </editor-fold>;

        BorderPane.setMargin(this.mainPayment, new Insets(5));
        this.mainPayment.setTop(this.vboxPayment);
        this.mainPayment.setCenter(this.payment);
        this.body.setTop(this.hboxHeader);
        this.body.setCenter(this.mainPayment);
        this.body.setBottom(this.hboxBottom);
    }
    
    private void generateTicket(){
        
    }
    
    private void loadMember() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        this.member = new ArrayList<>(); // Clear list
        File file = new File("src/resources/data/");
        File[] files = file.listFiles(new FilenameFilter() {
             
            @Override
            public boolean accept(File dir, String name) {
                if(name.toLowerCase().endsWith(".user")){
                    return true;
                } else {
                    return false;
                }
            }
        });
        for(File f:files){
            //System.out.println(f.getName());
            String[] name = ((String)f.getName()).split(".user");
            this.member.add(name[0]);
        }
        // </editor-fold>;
    }
    
    public BorderPane getBody(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        return this.body;
        // </editor-fold>;
    }
    
    private Pair<String,String> getOTP(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String OTP = buffer.toString();
        buffer = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String OTPRef = buffer.toString();
        // </editor-fold>;
        return new Pair<String,String>(OTPRef,OTP);
    }
    
    /*
    Driver
    */

    private Scene scene;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        PaymentUI payment = new PaymentUI("ก๋วยเตี๋ยว",2,120);
        this.scene = new Scene(payment.getBody(),1080,720);
        stage.setScene(scene);
        stage.show();
        
        
        }
}
