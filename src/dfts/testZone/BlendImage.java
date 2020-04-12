package dfts.testZone;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.FileStore;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/** Blend a coke can and a pepsi can to find the difference. */
public class BlendImage extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Image coke = new Image(new FileInputStream("src/resources/images/ticket2thblue .png"));

        Image pepsi = new Image(
            "http://icons.iconarchive.com/icons/michael/coke-pepsi/256/Pepsi-Can-icon.png"
        );

        ImageView bottom = new ImageView(coke);
        ImageView top = new ImageView(pepsi);
        top.setLayoutX(100);
        
        top.setBlendMode(BlendMode.SRC_OVER);

        Group blend = new Group(
                bottom,
                top,
                new Label("Hello")
        );

        HBox layout = new HBox(10);
        layout.getChildren().addAll(
                blend
        );
        layout.setPadding(new Insets(10));
        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}