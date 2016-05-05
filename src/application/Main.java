package application;
	
import java.io.IOException;
import java.util.List;

import com.controllers.CopyScreenController;
import com.controllers.JCommanderMainController;
import com.controllers.MoveScreenController;
import com.controllers.ProgressBarController;
import com.controllers.RootLayoutController;
import com.domain.JFile;
import com.domain.JRoot;
import com.services.JFileService;
import com.services.JRootService;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	static JFileService fileService = new JFileService();
	static JRootService rootService = new JRootService();
	
    private ObservableList<JFile> jFiles = FXCollections.observableArrayList();
    private ObservableList<JFile> jDirs = FXCollections.observableArrayList();
	
    private Stage primaryStage;
    private Stage progressStage;
    private Stage copyScreenStage;
    private Stage moveScreenStage;
    private Parent root;
    private Parent rootCopy;
    private Parent rootMove;
    private BorderPane rootLayout;
    
    private ProgressBarController progressController;
    private CopyScreenController copyController;
    private MoveScreenController moveController;
    private RootLayoutController rootController;
    
    public void showCopyScreenStage() {
    	copyScreenStage.show();
    }
    
    public void closeCopyScreenStage() {
    	copyScreenStage.close();
    }
    
    public void showProgressStage() {
    	progressStage.show();
    }
    
    public void closeProgressStage() {
    	progressStage.close();
    }
    
    public void showMoveScreenStage() {
    	moveScreenStage.show();
    }
    
    public void closeMoveScreenStage() {
    	moveScreenStage.close();
    }
    
    public CopyScreenController getCopyScreenController() {
    	return this.copyController;
    }
    
    public ProgressBarController getProgressController() {
    	return this.progressController;
    }
    
    public MoveScreenController getMoveScreenController() {
    	return this.moveController;
    }
    
    public RootLayoutController getRootController() {
    	return this.rootController;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JCommander");
        //this.jFiles = fileService.getFilesForDirectory("C:\\Users\\Adam\\Desktop\\Testowy2");
       // this.jDirs = (ObservableList<JDirectory>) dirService.getDirectoriesInDirectory("C:/");

        initRootLayout();
        showJCommanderOverview(); 
    }
    
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/controllers/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            rootController = loader.getController();
            rootController.setMainApp(this);
            primaryStage.show();
            
            FXMLLoader loaderProgress = new FXMLLoader();
            loaderProgress.setLocation(Main.class.getResource("/com/controllers/ProgressBar.fxml"));
			root = loaderProgress.load();
			progressStage = new Stage();
			progressStage.setTitle("Delete");
			progressStage.setScene(new Scene(root, 450, 200));
			//progressStage.show();
			progressController = loaderProgress.getController();
			progressController.setMainApp(this);
			
            FXMLLoader loaderCopyScreen = new FXMLLoader();
            loaderCopyScreen.setLocation(Main.class.getResource("/com/controllers/CopyScreen.fxml"));
			rootCopy = loaderCopyScreen.load();
			copyScreenStage = new Stage();
			copyScreenStage.setTitle("Copy");
			copyScreenStage.setScene(new Scene(rootCopy, 550, 300));
			//progressStage.show();
			copyController = loaderCopyScreen.getController();
			copyController.setMainApp(this);
			
			FXMLLoader loaderMoveScreen = new FXMLLoader();
            loaderMoveScreen.setLocation(Main.class.getResource("/com/controllers/MoveScreen.fxml"));
			rootMove = loaderMoveScreen.load();
			moveScreenStage = new Stage();
			moveScreenStage.setTitle("Move");
			moveScreenStage.setScene(new Scene(rootMove, 500, 250));
			//progressStage.show();
			moveController = loaderMoveScreen.getController();
			moveController.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showJCommanderOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/controllers/JCommanderMain.fxml"));
            AnchorPane jCommanderOverwiew = (AnchorPane) loader.load();
            rootLayout.setCenter(jCommanderOverwiew);
            
            JCommanderMainController controller = loader.getController();
            controller.setMain(this);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public ObservableList<JFile> getJFiles() {
        return this.jFiles;
    }
    
    public ObservableList<JFile> getJDirectories() {
        return this.jDirs;
    }

	
	public static void main(String[] args) throws IOException {
		launch(args);
		
	}
}
