package dfts.testZone;

import dfts.LoadingAdmin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class LoadingAdminDrive extends Application{
    
    public static void main(String[] args) {
        launch(args);
        //load.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoadingAdmin load = new LoadingAdmin();
        load.show();
        LoadingTemp temp = new LoadingTemp();
        Thread newThread = new Thread(new Runnable() {
        @Override
            public void run() {
                temp.init();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        load.hide();
                        temp.show();
                    }
                    });
                }
        });
        newThread.setDaemon(true);
        newThread.start();

    }
}
