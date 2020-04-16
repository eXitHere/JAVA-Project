package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;

public class UserUi {
    
    private final Stage dialog = new Stage();
    private final Stage dialogCreate = new Stage();
    private final Stage dialogEdit = new Stage();
    private Stage primaryStage = new Stage();
    private final DropShadow shadow = new DropShadow();
    private Font fontBtn = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 20);
    private int initBy;
    
    public UserUi(Stage primaryStage,int init) throws FileNotFoundException {
        initMain(primaryStage);
        initEdit(primaryStage);
        initCreate(primaryStage);
        this.initBy = init;
    }
    
    public final void initEdit(Stage primaryStage) throws FileNotFoundException{
        DatePicker datePicker = new DatePicker();
        // <editor-fold defaultstate="collapsed" desc="Custom functions">
        datePicker.setConverter(new StringConverter<LocalDate>(){
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MM/dd/yyyy");
            @Override
            public String toString(LocalDate localDate){
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString){
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
            
        });
        
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>(){
            public DateCell call(final DatePicker datePicker){
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        // Must call super
                        super.updateItem(item, empty);
 
                        // Show Weekends in blue color
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
                            this.setTextFill(Color.BLUE);
                        }
                         
                        // Disable all future date cells
                        if (item.isAfter(LocalDate.now())){
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        // </editor-fold>;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        
        this.dialogEdit.setOnHidden((e)->{
            showMain();
        });
        
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid,550,650);
        this.dialogEdit.getIcons().add(new Image(new FileInputStream("src/resources/images/icon.png")));
        this.dialogEdit.setTitle("แก้ไขบัญชีผู้ใช้");
        this.dialogEdit.initModality(Modality.APPLICATION_MODAL);
        //this.dialogEdit.initOwner(primaryStage);
        this.dialogEdit.setResizable(false);
        this.dialogEdit.centerOnScreen();
        //this.dialogCreate.initStyle(StageStyle.UNDECORATED);
        this.dialogEdit.setScene(scene);
        
        grid.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40, 40, 10, 40));
        
        FileInputStream inputstream = new FileInputStream("src/resources/images/logo-182x182.jpg");       
        Image logo = new Image(inputstream);
        ImageView Logo = new ImageView(logo);
        VBox vBoxLogo = new VBox();
        vBoxLogo.setAlignment(Pos.CENTER);
        vBoxLogo.getChildren().add(Logo);
        
        
        Label sceneTitle = new Label("           กรุณาล็อกอินก่อนแก้ไขข้อมูล");
        sceneTitle.setStyle("-fx-text-fill: white;");
        sceneTitle.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 24));
        grid.setMargin(sceneTitle, new Insets(15));

        Label firstName = new Label("ชื่อจริง");
        firstName.setStyle("-fx-text-fill: white;");
        firstName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        TextField firstNameTextField = new TextField();
        firstNameTextField.setPromptText("กรุณาป้อนชื่อจริง");
        firstNameTextField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        
        Label lastName = new Label("นามสกุล");
        lastName.setStyle("-fx-text-fill: white;");
        lastName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        TextField lastNameTextField = new TextField();
        lastNameTextField.setPromptText("กรุณาป้อนนามสกุล");
        lastNameTextField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        
        Label birthDay = new Label("วันเกิด");
        birthDay.setStyle("-fx-text-fill: white;");
        birthDay.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        datePicker.setDayCellFactory(dayCellFactory);
        //datePicker.setPromptText("กรุณาป้อนยืนยันรหัสผ่าน");
        datePicker.setEditable(false);
        //datePicker.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 20));
        
        Label phoneNumber = new Label("เบอร์โทรศัพท์");
        phoneNumber.setStyle("-fx-text-fill: white;");
        phoneNumber.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("กรุณาป้อนเบอร์โทรศัพท์");
        phoneNumberField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        
        Label password = new Label("รหัสผ่าน");
        password.setStyle("-fx-text-fill: white;");
        password.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));

        PasswordField  passwordField = new PasswordField ();
        passwordField.setPromptText("กรุณาป้อนรหัสผ่าน อย่างน้อย 6 หลัก");
        passwordField.setFont(new Font(16));
        
        Label password2 = new Label("ยืนยันรหัสผ่าน");
        password2.setStyle("-fx-text-fill: white;");
        password2.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("กรุณาป้อนยืนยันรหัสผ่าน");
        passwordField2.setFont(new Font(16));
        
        Button btnConfirm = new Button("เข้าสู่ระบบ");
        Button btnCancel = new Button("ปิด");
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btnConfirm,btnCancel);
        
        Font fontBtn = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 20);
        
        btnConfirm.setFont(fontBtn);
        btnConfirm.setPrefSize(120, 40);
        btnConfirm.setStyle("-fx-background-color: linear-gradient(#05C600, #05C600); -fx-text-fill: white;");
        HBox.setMargin(btnConfirm, new Insets(3, 10, 3, 10));
        
        btnCancel.setFont(fontBtn);
        btnCancel.setPrefSize(120, 40);
        btnCancel.setStyle("-fx-background-color: linear-gradient(#A88000, #A88000); -fx-text-fill: white;");
        HBox.setMargin(btnCancel, new Insets(3, 0, 3, 0));
        
        Label actiontarget = new Label();
        actiontarget.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        actiontarget.setVisible(false);
        
        grid.add(vBoxLogo, 0, 0,2,1);
        grid.add(sceneTitle, 0, 1, 2, 1);
        grid.add(phoneNumber, 0, 5);
        grid.add(phoneNumberField, 1, 5);
        grid.add(password, 0, 6);
        grid.add(passwordField, 1, 6);
        grid.add(actiontarget, 1, 7);
        grid.add(hbBtn, 1, 10);
        
        /*
        grid.add(firstName, 0, 2);
        grid.add(firstNameTextField, 1, 2);
        grid.add(lastName, 0, 3);
        grid.add(lastNameTextField, 1, 3);
        grid.add(birthDay, 0, 4);
        grid.add(datePicker, 1, 4);
        
        
        grid.add(password2, 0, 7);
        grid.add(passwordField2, 1, 7);
        
        grid.add(actiontarget, 1, 12);
        */
        btnConfirm.setOnAction((ActionEvent e)->{
            if(btnConfirm.getText().equals("เข้าสู่ระบบ")){
                if(phoneNumberField.getText().length()==10 && passwordField.getText().length()>=6){
                    //System.out.println("phone : " + phoneNumberField.getText());
                    try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.user", phoneNumberField.getText()))); ObjectInputStream oi = new ObjectInputStream(fi);){
                        actiontarget.setVisible(false);
                        User user = (User)oi.readObject();
                        if(user.passwordCheck(passwordField.getText())){
                            ////System.out.println(grid.getChildren().size());
                            grid.getChildren().removeAll(actiontarget,phoneNumber,phoneNumberField,password,passwordField,hbBtn);
                            //System.out.println(grid.getChildren().size());
                            grid.add(firstName, 0, 2);
                            grid.add(firstNameTextField, 1, 2);
                            grid.add(lastName, 0, 3);
                            grid.add(lastNameTextField, 1, 3);
                            grid.add(birthDay, 0, 4);
                            grid.add(datePicker, 1, 4);
                            grid.add(phoneNumber, 0, 5);
                            grid.add(phoneNumberField, 1, 5);
                            grid.add(password, 0, 6);
                            grid.add(passwordField, 1, 6);
                            grid.add(password2, 0, 7);
                            grid.add(passwordField2, 1, 7);
                            grid.add(actiontarget, 1, 7);
                            grid.add(hbBtn, 1, 10);
                            //System.out.println(grid.getChildren().size());
                            phoneNumberField.editableProperty().set(false);
                            btnConfirm.setText("บันทึก");
                            btnCancel.setText("ยกเลิก");
                            sceneTitle.setText(String.format("                      ยินดีต้อนรับ", user.getFirstName()));
                            firstNameTextField.setText(user.getFirstName());
                            lastNameTextField.setText(user.getLastName());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            datePicker.setValue(LocalDate.parse(user.getBirthDay(),formatter));
                            passwordField2.setText(passwordField.getText());
                            fi.close();
                            oi.close();
                        }
                        else{
                            actiontarget.setText("ข้อมูลไม่ถูกต้อง");
                            actiontarget.setStyle("-fx-text-fill:red");
                            actiontarget.setVisible(true);
                        }
                    }
                    catch(Exception EX){
                        System.out.println(EX);
                        //System.out.println("Fail2");
                        actiontarget.setText("ข้อมูลไม่ถูกต้อง");
                        actiontarget.setStyle("-fx-text-fill:red");
                        actiontarget.setVisible(true);
                    }
                }
                else{
                    actiontarget.setText("ข้อมูลไม่ถูกต้อง");
                    actiontarget.setStyle("-fx-text-fill:red");
                    actiontarget.setVisible(true);
                }
            }
            else{
                Pair response = verifyUpdate(firstNameTextField,
                   lastNameTextField,
                   datePicker,
                   phoneNumberField,
                   passwordField,
                   passwordField2);
                if((boolean)response.getKey()){
                    //System.out.println("inject");
                    phoneNumberField.editableProperty().set(true);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(this.dialogEdit);
                    alert.setContentText("ระบบจะปิดหน้าต่างนี้อัตโนมัติและผ่านท่านกลับไปยังหน้าหลัก");
                    alert.setHeaderText(response.getValue().toString());
                    alert.showAndWait();
                    showMain();
                    firstNameTextField.clear();
                    lastNameTextField.clear();
                    passwordField.clear();
                    passwordField2.clear();
                    phoneNumberField.clear();
                    //datePicker.getEditor().clear();
                    grid.getChildren().removeAll(hbBtn,actiontarget,vBoxLogo,sceneTitle,lastName,lastNameTextField,firstName,firstNameTextField,datePicker,birthDay,phoneNumber,phoneNumberField,password,password2,passwordField,passwordField2,btnCancel,btnConfirm);
                    grid.add(vBoxLogo, 0, 0,2,1);
                    grid.add(sceneTitle, 0, 1, 2, 1);
                    grid.add(phoneNumber, 0, 5);
                    grid.add(phoneNumberField, 1, 5);
                    grid.add(password, 0, 6);
                    grid.add(passwordField, 1, 6);
                    grid.add(actiontarget, 1, 7);
                    grid.add(hbBtn, 1, 10);
                    btnConfirm.setText("เข้าสู่ระบบ");
                    btnCancel.setText("ปิด");
                }
                else{
                    //System.out.print("reject ");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(this.dialogEdit);
                    alert.setTitle("DFTS REGISTER");
                    alert.setContentText("เกิดความผิดพลาดในการดำเนินการ");
                    alert.setHeaderText(response.getValue().toString());
                    alert.showAndWait();
                    //System.out.println(response.getValue());
                }
            }
        });
        
        btnCancel.setOnAction((ActionEvent e)->{
            if(btnCancel.getText().equals("ปิด")){
                showMain();
                passwordField.clear();
                phoneNumberField.clear();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(this.dialogEdit);
                alert.setTitle("DFTS REGISTER");
                alert.setContentText("Ok = ยกเลิกการแก้ไขข้อมูล, Calcel = กลับสู่การแก้ไขข้อมูล");
                alert.setHeaderText("ต้องการยกเลิกการลงทะเบียนใช่หรือไม่");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    actiontarget.setVisible(false);
                    showMain();
                    phoneNumberField.editableProperty().set(true);
                    firstNameTextField.clear();
                    lastNameTextField.clear();
                    //datePicker.getEditor().clear();
                    passwordField.clear();
                    passwordField2.clear();
                    phoneNumberField.clear();
                    grid.getChildren().removeAll(hbBtn,actiontarget,vBoxLogo,sceneTitle,lastName,lastNameTextField,firstName,firstNameTextField,datePicker,birthDay,phoneNumber,phoneNumberField,password,password2,passwordField,passwordField2,btnCancel,btnConfirm);
                    grid.add(vBoxLogo, 0, 0,2,1);
                    grid.add(sceneTitle, 0, 1, 2, 1);
                    grid.add(phoneNumber, 0, 5);
                    grid.add(phoneNumberField, 1, 5);
                    grid.add(password, 0, 6);
                    grid.add(passwordField, 1, 6);
                    grid.add(actiontarget, 1, 7);
                    grid.add(hbBtn, 1, 10);
                    btnConfirm.setText("เข้าสู่ระบบ");
                    btnCancel.setText("ปิด");
                } else {

                }
            }
        });
        
        phoneNumberField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(phoneNumberField.getLength()>10){
                phoneNumberField.setText(oldValue);
            }
            if (!newValue.matches("\\d*")) {
                phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        // <editor-fold defaultstate="collapsed" desc="Drop shadow">
            btnConfirm.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnConfirm.setEffect(shadow);
            });

            btnConfirm.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnConfirm.setEffect(null);
            });

            btnCancel.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnCancel.setEffect(shadow);
            });

            btnCancel.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnCancel.setEffect(null);
            });
            // </editor-fold>;

        // </editor-fold>;
        this.dialogEdit.setScene(scene);
    }
    
    public final void initCreate(Stage primaryStage) throws FileNotFoundException{
        DatePicker datePicker = new DatePicker();
        // <editor-fold defaultstate="collapsed" desc="Custom functions">
        datePicker.setConverter(new StringConverter<LocalDate>(){
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MM/dd/yyyy");
            @Override
            public String toString(LocalDate localDate){
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString){
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
        
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>(){
            public DateCell call(final DatePicker datePicker){
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        // Must call super
                        super.updateItem(item, empty);
 
                        // Show Weekends in blue color
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
                            this.setTextFill(Color.BLUE);
                        }
                         
                        // Disable all future date cells
                        if (item.isAfter(LocalDate.now())){
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        // </editor-fold>;
        
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        this.dialogCreate.setOnHidden((e)->{
            showMain();
        });
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid,550,650);
        this.dialogCreate.getIcons().add(new Image(new FileInputStream("src/resources/images/icon.png")));
        this.dialogCreate.setTitle("สร้างบัญชีใหม่");
        this.dialogCreate.initModality(Modality.APPLICATION_MODAL);
        //this.dialogCreate.initOwner(primaryStage);
        this.dialogCreate.setResizable(false);
        this.dialogCreate.centerOnScreen();
        //this.dialogCreate.initStyle(StageStyle.UNDECORATED);
        this.dialogCreate.setScene(scene);
        
        grid.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40, 40, 10, 40));
        
        FileInputStream inputstream = new FileInputStream("src/resources/images/logo-182x182.jpg");       
        Image logo = new Image(inputstream);
        ImageView Logo = new ImageView(logo);
        VBox vBoxLogo = new VBox();
        vBoxLogo.setAlignment(Pos.CENTER);
        vBoxLogo.getChildren().add(Logo);
        grid.add(vBoxLogo, 0, 0,2,1);
        
        Label sceneTitle = new Label("ยินดีต้องรับสู่การเป็นสมาชิกใหม่, กรุณากรอกข้อมูลให้ถูกต้อง");
        sceneTitle.setStyle("-fx-text-fill: white;");
        sceneTitle.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 24));
        grid.setMargin(sceneTitle, new Insets(20));
        
        grid.add(sceneTitle, 0, 1, 2, 1);

        Label firstName = new Label("ชื่อจริง");
        firstName.setStyle("-fx-text-fill: white;");
        firstName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(firstName, 0, 2);

        TextField firstNameTextField = new TextField();
        firstNameTextField.setPromptText("กรุณาป้อนชื่อจริง");
        firstNameTextField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        grid.add(firstNameTextField, 1, 2);

        Label lastName = new Label("นามสกุล");
        lastName.setStyle("-fx-text-fill: white;");
        lastName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(lastName, 0, 3);

        TextField lastNameTextField = new TextField();
        lastNameTextField.setPromptText("กรุณาป้อนนามสกุล");
        lastNameTextField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        grid.add(lastNameTextField, 1, 3);
        
        Label birthDay = new Label("วันเกิด");
        birthDay.setStyle("-fx-text-fill: white;");
        birthDay.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(birthDay, 0, 4);
        
        datePicker.setDayCellFactory(dayCellFactory);
        //datePicker.setPromptText("กรุณาป้อนยืนยันรหัสผ่าน");
        datePicker.setEditable(false);
        //datePicker.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 20));
        grid.add(datePicker, 1, 4);
        
        Label phoneNumber = new Label("เบอร์โทรศัพท์");
        phoneNumber.setStyle("-fx-text-fill: white;");
        phoneNumber.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(phoneNumber, 0, 5);

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("กรุณาป้อนเบอร์โทรศัพท์");
        phoneNumberField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        grid.add(phoneNumberField, 1, 5);
        
        Label password = new Label("รหัสผ่าน");
        password.setStyle("-fx-text-fill: white;");
        password.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(password, 0, 6);

        PasswordField  passwordField = new PasswordField ();
        passwordField.setPromptText("กรุณาป้อนรหัสผ่าน อย่างน้อย 6 หลัก");
        passwordField.setFont(new Font(16));
        grid.add(passwordField, 1, 6);
        
        Label password2 = new Label("ยืนยันรหัสผ่าน");
        password2.setStyle("-fx-text-fill: white;");
        password2.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(password2, 0, 7);
        
        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("กรุณาป้อนยืนยันรหัสผ่าน");
        passwordField2.setFont(new Font(16));
        grid.add(passwordField2, 1, 7);
        
        Button btnConfirm = new Button("สมัครสมาชิก");
        Button btnCancel = new Button("ยกเลิก");
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btnConfirm,btnCancel);
        grid.add(hbBtn, 1, 10);
        
        Font fontBtn = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 20);
        
        btnConfirm.setFont(fontBtn);
        btnConfirm.setPrefSize(120, 40);
        btnConfirm.setStyle("-fx-background-color: linear-gradient(#05C600, #05C600); -fx-text-fill: white;");
        HBox.setMargin(btnConfirm, new Insets(3, 10, 3, 10));
        
        btnCancel.setFont(fontBtn);
        btnCancel.setPrefSize(120, 40);
        btnCancel.setStyle("-fx-background-color: linear-gradient(#A88000, #A88000); -fx-text-fill: white;");
        HBox.setMargin(btnCancel, new Insets(3, 0, 3, 0));
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 12);
        
        btnConfirm.setOnAction((ActionEvent e)->{
            Pair response = verifyCrate(firstNameTextField,
                           lastNameTextField,
                           datePicker,
                           phoneNumberField,
                           passwordField,
                           passwordField2);
            if((boolean)response.getKey()){
                //System.out.println("inject");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(this.dialogCreate);
                alert.setContentText("ระบบจะปิดหน้าต่างนี้อัตโนมัติและผ่านท่านกลับไปยังหน้าหลัก");
                alert.setHeaderText(response.getValue().toString());
                alert.showAndWait();
                showMain();
                firstNameTextField.clear();
                lastNameTextField.clear();
                //datePicker.getEditor().clear();
                passwordField.clear();
                passwordField2.clear();
                phoneNumberField.clear();
            }
            else{
                //System.out.print("reject ");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(this.dialogCreate);
                alert.setTitle("DFTS REGISTER");
                alert.setContentText("เกิดความผิดพลาดในการดำเนินการ");
                alert.setHeaderText(response.getValue().toString());
                alert.showAndWait();
                //System.out.println(response.getValue());
            }
        });
        
        btnCancel.setOnAction((ActionEvent e)->{
            //System.out.println("Click");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(this.dialogCreate);
            alert.setTitle("DFTS REGISTER");
            alert.setContentText("Ok = ยกเลิกการลงทะเบียน, Calcel = กลับสู่การลงทะเบียน");
            alert.setHeaderText("ต้องการยกเลิกการลงทะเบียนใช่หรือไม่");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                firstNameTextField.clear();
                lastNameTextField.clear();
                //datePicker.getEditor().clear();
                passwordField.clear();
                passwordField2.clear();
                phoneNumberField.clear();
                //datePicker.getEditor().clear();
                showMain();
            } else {
                
            }
        });
        
        phoneNumberField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(phoneNumberField.getLength()>10){
                phoneNumberField.setText(oldValue);
            }
            if (!newValue.matches("\\d*")) {
                phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        // <editor-fold defaultstate="collapsed" desc="Drop shadow">
            btnConfirm.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnConfirm.setEffect(shadow);
            });

            btnConfirm.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnConfirm.setEffect(null);
            });

            btnCancel.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnCancel.setEffect(shadow);
            });

            btnCancel.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnCancel.setEffect(null);
            });
            // </editor-fold>;
        
        // </editor-fold>;
    }
    
    private Pair<Boolean,String> verifyUpdate(TextField n1,TextField n2,DatePicker n3,TextField n4,TextField n5,TextField n6){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /*
        -Firstname
        -Lastname
        -DatePicker
        -Phone
        -Password1
        -Password2
        */
        if(n1.getText().isEmpty() || n1.getText().isBlank()) return new Pair<>(false,"กรุณากรอกชื่อจริง");
        if(n2.getText().isEmpty() || n2.getText().isBlank()) return new Pair<>(false,"กรุณากรอกนามสกุล");
        if(n3.getValue() == null)                            return new Pair<>(false,"กรุณากรอกวันเกิด");
        if(n4.getText().isEmpty() || n4.getText().isBlank()
                || n4.getText().length() < 10)               return new Pair<>(false,"กรุณากรอกเบอร์โทรศัพท์");
        if(n5.getText().isEmpty() || n5.getText().isBlank()) return new Pair<>(false,"กรุณากรอกรหัสผ่าน");
        if(n6.getText().isEmpty() || n6.getText().isBlank()) return new Pair<>(false,"กรุณากรอกรหัสผ่าน (ยืนยัน)");
        if(!n5.getText().equals(n6.getText()))               return new Pair<>(false,"การยืนยันรหัสผ่านไม่ถูกต้อง");
        if(n5.getText().length() < 6 || n6.getText().length() <6) return new Pair<>(false,"รหัสผ่านต้องมีความยาวมากกว่า 6 ตัวอักษร");
        
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat birthday =new SimpleDateFormat("MM/dd/yyyy");
        Date oldDate = null;
        try{
            Date old = formatter.parse(n3.getValue().toString());
            oldDate = old;
            //System.out.println(n3.getValue());
            long diff = new Date().getTime() - old.getTime() ;
            int diffDays = (int) (diff / (24 * 1000 * 60 * 60));
            //System.out.println("Diff daty " + diffDays);
            if (diffDays < 12*365) return new Pair<>(false,"วันเกิดไม่ถูกต้อง ต้องมีอายุมากกว่า 12 ปีถึงจะสมัครสมาชิกได้");
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }
        User user = null;
        try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.user", n4.getText()))); ObjectInputStream oi = new ObjectInputStream(fi);){
            user = (User)oi.readObject();
        }
        catch(Exception ex){}
        user.update(n1.getText(), n2.getText(), n4.getText(), birthday.format(oldDate), n5.getText());
        try(FileOutputStream f = new FileOutputStream(new File(String.format("src/resources/data/%s.user",n4.getText())));ObjectOutputStream o = new ObjectOutputStream(f);){
            o.writeObject(user); 
            System.out.println("Update success!");
        }
        catch (Exception e){
            System.out.println(e);
            return new Pair<>(false,"ระบบผิดพลาด");
        }     
        
        // </editor-fold>;
        return new Pair<>(true,String.format("บันทึกข้อมูลเสร็จเสร็จ"));
    }
    
    private Pair<Boolean,String> verifyCrate(TextField n1,TextField n2,DatePicker n3,TextField n4,TextField n5,TextField n6){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /*
        -Firstname
        -Lastname
        -DatePicker
        -Phone
        -Password1
        -Password2
        */
        if(n1.getText().isEmpty() || n1.getText().isBlank()) return new Pair<>(false,"กรุณากรอกชื่อจริง");
        if(n2.getText().isEmpty() || n2.getText().isBlank()) return new Pair<>(false,"กรุณากรอกนามสกุล");
        if(n3.getValue() == null)                            return new Pair<>(false,"กรุณากรอกวันเกิด");
        if(n4.getText().isEmpty() || n4.getText().isBlank()
                || n4.getText().length() < 10)               return new Pair<>(false,"กรุณากรอกเบอร์โทรศัพท์");
        if(n5.getText().isEmpty() || n5.getText().isBlank()) return new Pair<>(false,"กรุณากรอกรหัสผ่าน");
        if(n6.getText().isEmpty() || n6.getText().isBlank()) return new Pair<>(false,"กรุณากรอกรหัสผ่าน (ยืนยัน)");
        if(!n5.getText().equals(n6.getText()))               return new Pair<>(false,"การยืนยันรหัสผ่านไม่ถูกต้อง");
        if(n5.getText().length() < 6 || n6.getText().length() <6) return new Pair<>(false,"รหัสผ่านต้องมีความยาวมากกว่า 6 ตัวอักษร");
        
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat birthday =new SimpleDateFormat("MM/dd/yyyy");
        Date oldDate = null;
        try{
            Date old = formatter.parse(n3.getValue().toString());
            oldDate = old;
            //System.out.println(n3.getValue());
            long diff = new Date().getTime() - old.getTime() ;
            int diffDays = (int) (diff / (24 * 1000 * 60 * 60));
            //System.out.println("Diff daty " + diffDays);
            if (diffDays < 12*365) return new Pair<>(false,"วันเกิดไม่ถูกต้อง ต้องมีอายุมากกว่า 12 ปีถึงจะสมัครสมาชิกได้");
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }
        
        try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.user", n4.getText()))); ObjectInputStream oi = new ObjectInputStream(fi);){
            return new Pair<>(false,String.format("หมายเลข %s มีในระบบแล้ว", n4.getText()));
        }
        catch(Exception ex){
            User newUser = new User(n1.getText(), n2.getText(), n4.getText(), birthday.format(oldDate), n5.getText());
            try(FileOutputStream f = new FileOutputStream(new File(String.format("src/resources/data/%s.user",n4.getText())));ObjectOutputStream o = new ObjectOutputStream(f);){
                o.writeObject(newUser); 
                System.out.println("new user : " + newUser);
            }
            catch (Exception e){
                System.out.println("Create user fail!");
                return new Pair<>(false,"ระบบผิดพลาด");
            }     
        }
        
        // </editor-fold>;
        return new Pair<>(true,String.format("การสมัครสมาชิกเสร็จสิ้น\nยินดีต้อนรับคุณ %s %s", n1.getText(), n2.getText()));
    }
    
    public final void initMain(Stage primaryStage){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        this.primaryStage = primaryStage;
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane,450,180);
        HBox hbox = new HBox();
        HBox hboxTop = new HBox();
        
        try{
            Label infomation = new Label("เลือกเมนูที่ต้องการ");
            infomation.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 28));
            infomation.setStyle("-fx-text-fill: linear-gradient(#0065D3, #0B509B);");
            hbox.setMargin(infomation, new Insets(40, 10, 3, 10));
            this.dialog.setTitle("บัญชีผู้ใช้งาน");
            this.dialog.initModality(Modality.APPLICATION_MODAL);
            this.dialog.initOwner(primaryStage);
            this.dialog.setResizable(false);
            this.dialog.centerOnScreen();
            this.dialog.getIcons().add(new Image(new FileInputStream("src/resources/images/icon.png")));
            this.dialog.initStyle(StageStyle.UNDECORATED);
            this.dialog.setScene(scene);

            Button btnCreate = new Button("สร้างบัญชีใหม่");
            Button btnEdit = new Button("แก้ไขบัญชี");
            Button btnClose = new Button("ปิด");

            btnCreate.setFont(fontBtn);
            btnCreate.setPrefSize(120, 40);
            btnCreate.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
            hbox.setMargin(btnCreate, new Insets(3, 10, 3, 10));

            btnEdit.setFont(fontBtn);
            btnEdit.setPrefSize(120, 40);
            btnEdit.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
            hbox.setMargin(btnEdit, new Insets(3, 10, 3, 10));

            btnClose.setFont(fontBtn);
            btnClose.setPrefSize(120, 40);
            btnClose.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
            hbox.setMargin(btnClose, new Insets(3, 10, 3, 10));

            hboxTop.getChildren().addAll(infomation);
            hbox.getChildren().addAll(btnCreate,btnEdit,btnClose);
            mainPane.setTop(hboxTop);
            mainPane.setCenter(hbox);
            mainPane.setStyle("-fx-background-color: linear-gradient(#EAEAEA, #A5A5A5);-fx-border-color: black");
            hbox.setAlignment(Pos.CENTER);
            hboxTop.setAlignment(Pos.CENTER);
            
            btnCreate.setOnAction((ActionEvent e)->{
                showCreate();
            });
            
            btnEdit.setOnAction((ActionEvent e)->{
                showEdit();
            });
            
            btnClose.setOnAction((ActionEvent e)->{
                this.dialog.close();
            });
            // <editor-fold defaultstate="collapsed" desc="Drop shadow">
            btnCreate.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnCreate.setEffect(shadow);
            });

            btnCreate.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnCreate.setEffect(null);
            });

            btnEdit.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnEdit.setEffect(shadow);
            });

            btnEdit.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnEdit.setEffect(null);
            });

            btnClose.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                btnClose.setEffect(shadow);
            });

            btnClose.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                btnClose.setEffect(null);
            });
            // </editor-fold>;
        }
        catch(Exception e){
            System.out.println(e);
        }
        this.dialog.setScene(scene);
        // </editor-fold>;
    }
    
    public void show(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        this.dialog.show();
        // </editor-fold>;
    }
    
    public void showEdit(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        if(this.initBy==0){
            this.dialog.hide();
            this.dialogEdit.show();
            this.primaryStage.hide();
        }
        else if(this.initBy==1){
            this.dialog.hide();
            this.dialogEdit.show();
        }
        // </editor-fold>;
    }
    
    public void showCreate(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        if(this.initBy==0){
            this.dialog.hide();
            this.dialogCreate.show();
            this.primaryStage.hide();
        }
        else if(this.initBy==1){
            this.dialog.hide();
            this.dialogCreate.show();
        }
        // </editor-fold>;
    }
    
    public void showMain(){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        if(this.initBy==0){
            this.primaryStage.show();
            this.dialogCreate.hide();
            this.dialogEdit.hide();
        }
        else if(this.initBy==1){
            this.dialogCreate.hide();
            this.dialogEdit.hide();
        }
        // </editor-fold>;
    }
    
}
