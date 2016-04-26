package application;
	
import java.io.IOException;
import java.util.List;

import com.controllers.JCommanderMainController;
import com.domain.JFile;
import com.domain.JRoot;
import com.services.JFileService;
import com.services.JRootService;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	static JFileService fileService = new JFileService();
	static JRootService rootService = new JRootService();
	
    private ObservableList<JFile> jFiles = FXCollections.observableArrayList();
    private ObservableList<JFile> jDirs = FXCollections.observableArrayList();
	
    private Stage primaryStage;
    private BorderPane rootLayout;

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
            primaryStage.show();
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
		
		/*List<JFile> files = fileService.getFilesForDirectory("C:\\Users\\Adam\\Desktop\\Studia\\CV\\2015_23_10");
		for(JFile file : files) {
			System.out.println(file.getName()+ " , " + file.getSize() + " , " + file.getFileTime());
		}
		System.out.println("\n");
		List<JDirectory> dirs = dirService.getDirectoriesInDirectory("C:\\Users\\Adam\\Desktop\\Lab1\\Lab1\\Lab1");
		for(JDirectory dir : dirs) {
			System.out.println(dir.getName());
		}
		
		System.out.println("\n");
		List<JRoot> roots = rootService.getSystemRoots();
		for(JRoot root: roots) {
			System.out.println(root.getPath());
		}*/
		
		//fileService.copyFile("C:\\Users\\Adam\\Desktop\\Testowy1\\plik1.txt", "C:\\Users\\Adam\\Desktop\\Testowy2\\skopiowany.txt");
		//fileService.moveFile("C:\\Users\\Adam\\Desktop\\Testowy1\\plik1.txt", "C:\\Users\\Adam\\Desktop\\Testowy2\\plik1.txt");
		//fileService.deleteFile("C:\\Users\\Adam\\Desktop\\Testowy1\\plik2.txt");
		//dirService.copyDirectory("C:\\Users\\Adam\\Desktop\\Testowy1", "C:\\Users\\Adam\\Desktop\\Testowy2\\Testowy1");
		//dirService.moveDirectory("C:\\Users\\Adam\\Desktop\\Testowy1", "C:\\Users\\Adam\\Desktop\\Testowy2\\Testowy1");
		//dirService.deleteDirectory("C:\\Users\\Adam\\Desktop\\Testowy1_kopia");
	}
}
