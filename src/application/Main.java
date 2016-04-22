package application;
	
import java.io.IOException;
import java.util.List;

import com.domain.JDirectory;
import com.domain.JFile;
import com.domain.JRoot;
import com.services.JDirectoryService;
import com.services.JFileService;
import com.services.JRootService;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	static JFileService fileService = new JFileService();
	static JDirectoryService dirService = new JDirectoryService();
	static JRootService rootService = new JRootService();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		//launch(args);
		
		List<JFile> files = fileService.getFilesForDirectory("C:\\Users\\Adam\\Desktop\\Studia\\CV\\2015_23_10");
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
		}
		
		//fileService.copyFile("C:\\Users\\Adam\\Desktop\\Testowy1\\plik1.txt", "C:\\Users\\Adam\\Desktop\\Testowy2\\skopiowany.txt");
		//fileService.moveFile("C:\\Users\\Adam\\Desktop\\Testowy1\\plik1.txt", "C:\\Users\\Adam\\Desktop\\Testowy2\\plik1.txt");
		//fileService.deleteFile("C:\\Users\\Adam\\Desktop\\Testowy1\\plik2.txt");
		//dirService.copyDirectory("C:\\Users\\Adam\\Desktop\\Testowy1", "C:\\Users\\Adam\\Desktop\\Testowy2\\Testowy1");
		//dirService.moveDirectory("C:\\Users\\Adam\\Desktop\\Testowy1", "C:\\Users\\Adam\\Desktop\\Testowy2\\Testowy1");
		//dirService.deleteDirectory("C:\\Users\\Adam\\Desktop\\Testowy1_kopia");
	}
}
