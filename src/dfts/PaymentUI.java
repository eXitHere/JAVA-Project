package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Math.ceil;
import java.lang.reflect.Array;
import static java.nio.file.Files.size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
    
    public PaymentUI() throws FileNotFoundException {
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
        HBox.setMargin(btnPay, new Insets(10, 10, 10, 612));
        
        this.hboxBottom.getChildren().addAll(infomationMember,hyperlink,btnPay);
        
        //Payment
        this.vboxPayment.setPrefSize(1080,100 );
        this.vboxPayment.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);");
        this.vboxPayment.setAlignment(Pos.TOP_CENTER);
        
        Label titlePayment = new Label("*หากสมัครสมาชิกจะได้รับส่วนลด 5%");
        titlePayment.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        titlePayment.setStyle("-fx-text-fill:white");
        
        this.vboxPayment.getChildren().add();
        
        this.payment.setGridLinesVisible(true);
        this.payment.getColumnConstraints().add(new ColumnConstraints(400));
        this.payment.getRowConstraints().add(new RowConstraints(30));
        this.payment.add(new Label("First"), 0, 0);
        
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
                }
                else{
                    status_.setText("ไม่เป็นสมาชิก");
                    status_.setTextFill(Color.GRAY);
                }
            }
            else{
                status_.setText("กรุณาป้อนหมายเลขให้ครบ 10 หลัก");
                status_.setTextFill(Color.RED);
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

        this.mainPayment.setTop(this.vboxPayment);
        this.mainPayment.setCenter(this.payment);
        this.body.setTop(this.hboxHeader);
        this.body.setCenter(this.mainPayment);
        this.body.setBottom(this.hboxBottom);
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
    
    /*
    Driver
    */

    private Scene scene;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        PaymentUI payment = new PaymentUI();
        this.scene = new Scene(payment.getBody(),1080,720);
        stage.setScene(scene);
        stage.show();
    }
}
