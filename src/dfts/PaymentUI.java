package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.imageio.ImageIO;

public class PaymentUI {
    private BorderPane body = new BorderPane();
    private GridPane payment = new GridPane();
    private BorderPane mainPayment = new BorderPane();
    private VBox vboxPayment = new VBox();
    private HBox hboxHeader = new HBox();
    private HBox hboxBottom = new HBox();
    private List<String> member = new ArrayList<>();
    private Pair<String,String> otp;
    private String __start,__stop;
    private int __count,__price;
    private String user_Display = "";
    private Stage primary;
    private boolean isClickUserUi = false;
    private Stage primary_mat; //MAP AND TIME
    private String phone;
    private double totalPrice;
    //payment
    
    public PaymentUI(Stage primary,Stage primary_mat,String start,String stop,int price,int count) throws FileNotFoundException {
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        this.primary_mat = primary_mat;
        this.primary = primary;
        this.__start = start;
        this.__stop = stop;
        this.__count = count;
        this.__price = price;
        //System.out.println("debug :::: " + this.__start);
        String _item = String.format("ตั๋วจากสถานี %s ไปยัง %s", start,stop);
        int _price = price;
        int _count = count;
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
        
        Button btnBack = new Button("ยกเลิิก");
        btnBack.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        btnBack.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        btnBack.setPrefSize(120, 35);
        HBox.setMargin(btnBack, new Insets(5, 0, 0, 395));
        
        // <editor-fold defaultstate="collapsed" desc="Close form">
        btnBack.setOnAction((ActionEvent ex)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(this.primary_mat);
            alert.setTitle("DFTS REGISTER");
            alert.setContentText("ระบบจะพากลับไปยังหน้าหลัก และไม่บันทึก");
            alert.setHeaderText("ต้องการยกเลิกการจ่ายเงิน");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                this.primary_mat.close();
            }
            
        });
        // </editor-fold>;
        
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
                if(phoneField.getLength()==0){
                    btnPay.setDisable(true);
                    status_.setText("กรุณาป้อนหมายเลขให้ครบ 10 หลัก");
                    status_.setTextFill(Color.RED);
                    total_Price.setText(String.format("%.2f\n", _price*_count*1.00));
                    itemsName.setText(String.format("\n     %s", _item));
                    //itemsCount.setText(String.format("\n%d", _count));
                    itemsPrice.setText(String.format("\n%.2f",_price*1.00 ));
                    totalPrice = price*_count*1.00;
                    total_Price.setTextFill(Color.BLACK);
                    return;
                }
                hyperlink.setDisable(true);
                otpLabel.setText(String.format("รหัสอ้างอิง: %s", otp.getKey()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("(เป็นระบบจำลอง)");
                alert.setHeaderText("กรอก OTP อันนี้ " + otp.getValue() +"  (รหัสอ้างอิง: " + otp.getKey()+")");
                alert.show();
                btnPay.setText("ส่งใหม่");
                btnPay.setStyle("-fx-background-color: linear-gradient(#FFCE00, #FFCE00); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);");
                otpLabel.setVisible(true);
                otpField.setVisible(true);
                phoneField.editableProperty().set(false);
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
                    //System.out.println("Pass");
                    try{
                        if(status_.getText().equals("เป็นสมาชิก")){
                            User user = null;
                            try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.user", phoneField.getText()))); ObjectInputStream oi = new ObjectInputStream(fi);){
                                user = (User)oi.readObject();
                            }
                            this.user_Display = String.format("%s %s", user.getFirstName(),user.getLastName());
                        } 
                        else{
                            this.user_Display = "Guest";
                        }
                        this.phone = phoneField.getText();
                        generateTicket();
                    }
                    catch(Exception ex){
                        System.out.println("Have something wrong!"+ex);
                    }
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
            //System.out.println("User show");
            try{
                UserUi userUi = new UserUi(this.primary,1);
                userUi.show();
                this.isClickUserUi = true;
                phoneField.clear();
                totalPrice = price*_count*1.00;
                total_Price.setText(String.format("%.2f\n", _price*_count*1.00));
                itemsName.setText(String.format("\n     %s", _item));
                //itemsCount.setText(String.format("\n%d", _count));
                itemsPrice.setText(String.format("\n%.2f",_price*1.00 ));
                total_Price.setTextFill(Color.BLACK);
                //System.out.println("--> : " + this.isClickUserUi);
                //System.out.println("\n\n\n");
            }
            catch(FileNotFoundException ex){
                System.out.println("Have something wrong in Payment\n"+ex);
            }
        });
        // </editor-fold>;
        
        // <editor-fold defaultstate="collapsed" desc="phone field event">
        phoneField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            if(this.isClickUserUi && !newValue.equals("")){
                loadMember();
                this.isClickUserUi = false;
                //System.out.println("Loading");
                
            }
            if(phoneField.getLength()>10){
                phoneField.setText(oldValue);
            }
            else if (!newValue.matches("\\d*")) {
                phoneField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            
            if(newValue.length()==0){
                status.setVisible(false);
                status_.setVisible(false);
            }
            else if(phoneField.getLength()==10){
                //System.out.println("Do " + this.member.size());
                btnPay.setDisable(false);
                boolean isMember = false;
                for(var x : this.member){
                    //System.out.println(x + " and " + phoneField.getText());
                    if(phoneField.getText().equals(x)){
                        isMember = true;
                        //System.out.println("Yes");
                        break;
                    }
                }
                if(isMember){
                    status_.setText("เป็นสมาชิก");
                    status_.setTextFill(Color.GREENYELLOW);
                    totalPrice = price*_count*0.95;
                    total_Price.setText(String.format("%.2f\n", _price*_count*.95));
                    itemsName.setText(String.format("\n     %s\n\n     %s", _item,"ส่วนลดจากการเป็นสมาชิก"));
                    //itemsCount.setText(String.format("\n%d\n\n%d", _count,_count));
                    itemsPrice.setText(String.format("\n%.2f\n\n-%.2f",_price*1.00 , _price*_count*.05));
                    total_Price.setTextFill(Color.BLUEVIOLET);
                }
                else{
                    status_.setText("ไม่เป็นสมาชิก");
                    status_.setTextFill(Color.GRAY);
                    totalPrice = price*_count*1.00;
                    total_Price.setText(String.format("%.2f\n", _price*_count*1.00));
                    itemsName.setText(String.format("\n     %s", _item));
                    //itemsCount.setText(String.format("\n%d", _count));
                    itemsPrice.setText(String.format("\n%.2f",_price*1.00 ));
                    total_Price.setTextFill(Color.BLACK);
                }
            }
            else{
                status.setVisible(true);
                status_.setVisible(true);
                btnPay.setDisable(true);
                status_.setText("กรุณาป้อนหมายเลขให้ครบ 10 หลัก");
                status_.setTextFill(Color.RED);
                totalPrice = price*_count*1.00;
                total_Price.setText(String.format("%.2f\n", _price*_count*1.00));
                itemsName.setText(String.format("\n     %s", _item));
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
        //generateTicket();
        // </editor-fold>;
    }
    
    public Label getLabel(String txt) throws FileNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="init label">
        Label body = new Label(txt);
        body.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        body.setStyle("-fx-text-fill:black");
        return body;
        // </editor-fold>;
    }
    
    //private List<Group> list = new ArrayList<>();
    private Button lbutton, rButton;
    private StackPane stackPane = new StackPane();
    
    private void generateTicket() throws FileNotFoundException{
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        generateQRcode qr = new generateQRcode();
        ImageView bgTickle = new ImageView(new Image(new FileInputStream("src/resources/images/ticket2thblue.png")));
        //list.add(qr.getQRCode("Hello"));
        //list.add(qr.getQRCode("world"));
       
        for(int i=0;i<this.__count;i++){
            bgTickle = new ImageView(new Image(new FileInputStream("src/resources/images/ticket2thblue.png")));
            bgTickle.setBlendMode(BlendMode.SRC_OVER);
            bgTickle.setLayoutX(-262);
            bgTickle.setLayoutY(-105);
            //System.out.println(i);
            //System.out.println("Ticket : " + String.format("ticket_from_%s_to_%s_Number_%d",this.__start,this.__stop,i));
            ImageView QR = new ImageView(new Image(qr.getQRCode(String.format("ticket_from_%s_to_%s_Number_%d",this.__start,this.__stop,i))));
            
            Label start = getLabel(String.format("%s", this.__start));
            start.setMaxSize(180, 100);
            start.setLayoutX(-225.83);
            start.setLayoutY(-28.277);
            
            Label stop = getLabel(String.format("%s", this.__stop));
            stop.setMaxSize(180, 100);
            stop.setLayoutX(-225.83);
            stop.setLayoutY(55);
            
            Label name = getLabel(String.format("%s",this.user_Display));
            name.setMaxSize(180, 100);
            name.setLayoutX(-225.83);
            name.setLayoutY(142);
            
            Label price = getLabel(String.format("%.2f บาท",this.__price*1.00));
            price.setMaxSize(180, 100);
            price.setLayoutX(-225.83);
            price.setLayoutY(222);
            
            Group group = new Group(bgTickle,QR,start,stop,name,price);
            stackPane.getChildren().add(group);
        }
        
        lbutton = new Button("<");
        rButton = new Button(">");
        
        lbutton.setStyle("-fx-background-color: linear-gradient(#FFCE00, #FFCE00); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        lbutton.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        lbutton.setPrefSize(30, 420);
        HBox.setMargin(lbutton, new Insets(55,20,0,20));
        
        rButton.setStyle("-fx-background-color: linear-gradient(#FFCE00, #FFCE00); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        rButton.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        rButton.setPrefSize(30, 420);
        HBox.setMargin(rButton, new Insets(55,20,0,20));
        
        HBox hboxCenter = new HBox();
        hboxCenter.setPadding(new Insets(0,0,0,0));
        hboxCenter.setAlignment(Pos.TOP_CENTER);
        
        lbutton.setOnAction(e -> {
            ObservableList<Node> childs = this.stackPane.getChildren();
            if (childs.size() > 1) {
               //
               Node topNode = childs.get(childs.size()-1);
               topNode.toBack();
           }
        });
        rButton.setOnAction(e -> {
            ObservableList<Node> childs = this.stackPane.getChildren();
            if (childs.size() > 1) {
               //
               Node topNode = childs.get(0);
               topNode.toFront();
            }
        });

        hboxCenter.getChildren().addAll(lbutton,stackPane,rButton);
        
        HBox hboxSave = new HBox();
        hboxSave.setAlignment(Pos.CENTER);
        hboxSave.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        hboxSave.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);");
        
        Button btnSaveAll = new Button("บันทึกทั้งหมด");
        btnSaveAll.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        btnSaveAll.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        btnSaveAll.setPrefSize(220, 60);
        HBox.setMargin(btnSaveAll, new Insets(20,10,10,10));
        btnSaveAll.setOnAction((e)->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyyhhmmss");
            //System.out.println(String.format("Payment_from_%s_to_%s_%s",this.__start,this.__stop,simpleDateFormat.format(new Date())));
            saveAsPng(takeShotPayment(),String.format("Payment_from_%s_to_%s_%s",this.__start,this.__stop,simpleDateFormat.format(new Date())));
            for(int i=0;i<__count;i++){
                //System.out.println(String.format("%d : Ticket_from_%s_to_%s_%s",i,this.__start,this.__stop,simpleDateFormat.format(new Date())));
                saveAsPng(takeShotTicket(i),String.format("Ticket_%d_from_%s_to_%s_%s",i,this.__start,this.__stop,simpleDateFormat.format(new Date())));
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(this.primary_mat);
            alert.setContentText("อยู่ใน C:/DFTS_PROJECT");
            alert.setHeaderText("บันทึกใบเสร็จและตั๋วทั้งหมด เสร็จแล้ว");
            alert.showAndWait();
        });
        
        Button btnSave1 = new Button("บันทึกเฉพาะตั๋ว (ทั้งหมด) ");
        btnSave1.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        btnSave1.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        btnSave1.setPrefSize(220, 60);
        HBox.setMargin(btnSave1, new Insets(20,10,10,10));
        btnSave1.setOnAction((e)->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyyhhmmss");
            for(int i=0;i<__count;i++){
                //System.out.println(String.format("%d : Ticket_from_%s_to_%s_%s",i,this.__start,this.__stop,simpleDateFormat.format(new Date())));
                saveAsPng(takeShotTicket(i),String.format("Ticket_%d_from_%s_to_%s_%s",i,this.__start,this.__stop,simpleDateFormat.format(new Date())));
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(this.primary_mat);
            alert.setContentText("อยู่ใน C:/DFTS_PROJECT");
            alert.setHeaderText("บันทึกตั๋วทั้งหมด เสร็จแล้ว");
            alert.showAndWait();
        });
        
        Button btnSave2 = new Button("บันทึกเฉพาะตั๋ว (ใบนี้) ");
        btnSave2.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        btnSave2.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        btnSave2.setPrefSize(220, 60);
        HBox.setMargin(btnSave2, new Insets(20,10,10,10));
        btnSave2.setOnAction((e)->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyyhhmmss");
            saveAsPng(takeShotTicket(-1),String.format("Ticket_from_%s_to_%s_%s",this.__start,this.__stop,simpleDateFormat.format(new Date())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(this.primary_mat);
            alert.setContentText("อยู่ใน C:/DFTS_PROJECT");
            alert.setHeaderText("บันทึกเฉพาะตั๋วใบนี้ เสร็จแล้ว");
            alert.showAndWait();
        });
        
        Button btnSave3 = new Button("บันทึกเฉพาะใบเสร็จ");
        btnSave3.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        btnSave3.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        btnSave3.setPrefSize(220, 60);
        HBox.setMargin(btnSave3, new Insets(20,10,10,10));
        btnSave3.setOnAction((ActionEvent e)->{
            //System.out.println("!!!debug :::: " + this.__start);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyyhhmmss");
            System.out.println(String.format("Payment_from_%s_to_%s_%s",this.__start,this.__stop,simpleDateFormat.format(new Date())));
            saveAsPng(takeShotPayment(),String.format("Payment_from_%s_to_%s_%s",this.__start,this.__stop,simpleDateFormat.format(new Date())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(this.primary_mat);
            alert.setContentText("อยู่ใน C:/DFTS_PROJECT");
            alert.setHeaderText("บันทึกใบเสร็จ เสร็จแล้ว");
            alert.showAndWait();
        });
        
        
        hboxSave.getChildren().addAll(btnSaveAll,btnSave1,btnSave2,btnSave3);
        
        HBox hbox = new HBox();
        hbox.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        hbox.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);");
        Label title = new Label("                                                  ขอบคุณที่ใช้บริการ\n\n                               บริษัท Delicious Food Transit System (DFTS) จำกัด");
        title.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 22));
        title.setStyle("-fx-text-fill:white");
        title.setAlignment(Pos.CENTER);
        title.setPrefSize(900, 100);
        
        Button btnClose = new Button("ปิด");
        btnClose.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: linear-gradient(#FFFFFF, #FFFFFF);-fx-border-color: white; -fx-border-width: 1 1 1 1;");
        btnClose.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        btnClose.setPrefSize(180, 60);
        HBox.setMargin(btnClose, new Insets(20,10,10,10));
        
        // <editor-fold defaultstate="collapsed" desc="Close form">
        btnClose.setOnAction((ActionEvent ev)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(this.primary_mat);
            alert.setTitle("DFTS REGISTER");
            alert.setContentText("ระบบจะพากลับไปยังหน้าหลัก");
            alert.setHeaderText("ต้องการปิดหน้าต่างนี้ใช่หรือไม่");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                this.primary_mat.close();
            }
        });
        // </editor-fold>;
        
        hbox.getChildren().addAll(title,btnClose);
        
        /*
        ------------------- Write to DB ---------------------
        */
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        //System.out.println(String.format("%s_%s_%s_%d_%s_%.2f", this.phone,this.__start,this.__stop,this.__count,date.format(new Date()),this.totalPrice));
        SaveToDB(String.format("%s_%s_%s_%d_%s_%.2f", this.phone,this.__start,this.__stop,this.__count,date.format(new Date()),this.totalPrice));
        /*
        ------------------------------------------------------
        */
        this.body.setTop(hbox);
        this.body.setCenter(hboxCenter);
        this.body.setBottom(hboxSave);
        // </editor-fold>;
        // <editor-fold defaultstate="collapsed" desc="Drop shodow">
        DropShadow shadow = new DropShadow();
        
        btnClose.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnClose.setEffect(shadow);
        });
                
        btnClose.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnClose.setEffect(null);
        });
       
        btnSaveAll.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSaveAll.setEffect(shadow);
        });
                
        btnSaveAll.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSaveAll.setEffect(null);
        });

        btnSave1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSave1.setEffect(shadow);
        });
                
        btnSave1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSave1.setEffect(null);
        });

        btnSave2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSave2.setEffect(shadow);
        });
                
        btnSave2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSave2.setEffect(null);
        });

        btnSave3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSave3.setEffect(shadow);
        });
                
        btnSave3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSave3.setEffect(null);
        });

        
        // </editor-fold>;
    }
    
    private void SaveToDB(String out){
        //out += "\r\n";
        // <editor-fold defaultstate="collapsed" desc="Compiled code">
        try(FileWriter fw=new FileWriter("src/resources/data/sale.bin",true);){  
            for (int i = 0; i < out.length(); i++) 
                fw.write(out.charAt(i));
            fw.write(System.getProperty( "line.separator" ));
        }
        catch(Exception e){
            
        }
        // </editor-fold>;
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
            //System.out.println("member : " + name[0]);
        }
        // </editor-fold>;
    }
    
    public BorderPane getBody(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        return this.body;
        // </editor-fold>;
    }
    
    private Node takeShotTicket(int index){
        if(index == -1){
            ObservableList<Node> childs = this.stackPane.getChildren();
            Node topNode = childs.get(childs.size()-1);
            return topNode;
        }
        else{
            ObservableList<Node> childs = this.stackPane.getChildren();
            return childs.get(index);
        }
        
    }
    
    private BorderPane takeShotPayment(){
        return this.mainPayment;
    }
    
    private static final void saveAsPng(final Node NODE, final String FILE_NAME) {
        final WritableImage SNAPSHOT = NODE.snapshot(new SnapshotParameters(), null);
        final String        NAME     = FILE_NAME.replace("\\.[a-zA-Z]{3,4}", "");
        final File          FILE     = new File("C:\\DFTS_PROJECT\\" + NAME + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(SNAPSHOT, null), "png", FILE);
        } catch (IOException exception) {
            // handle exception here
        }
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
}
