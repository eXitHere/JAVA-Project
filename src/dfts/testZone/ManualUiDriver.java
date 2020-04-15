package dfts.testZone;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManualUiDriver extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        ManualUi ui = new ManualUi();
        stage.setScene(new Scene(ui.getBody(),1080,720));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }
}
