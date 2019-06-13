package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressGUI {
	
	private FXMLLoader loader;
	private Stage stage;
	
	public void start() {
		try {
			loader = new FXMLLoader(getClass().getResource("ProgressGui.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("ASDF");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		stage.close();
	}
	
	public ProgressGUIController getController() {
		return loader.getController();
	}
	
}
