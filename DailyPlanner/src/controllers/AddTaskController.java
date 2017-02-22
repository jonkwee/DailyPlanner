package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddTaskController {

	@FXML
	ComboBox<String> timeOption;
	
	@FXML
	TextField taskInput;
	
	@FXML 
	Button quit;
	
	@FXML
	Button confirm;
	
	
	MainController mainMenu;
	
	
	@FXML
	private void quitWindow() {
		quit.getScene().getWindow().hide();
	}
	
	@FXML
	void addTask() {
		try {
			String task = modifyText(taskInput.getText());
			String time = timeOption.getValue();
			int year = mainMenu.exportDate().getYear();
			int month = mainMenu.exportDate().getMonth();
			int day = mainMenu.exportDate().getDay();
			mainMenu.exportDatabase().insertInto(year, month, day, time, task);
			mainMenu.loadTaskToTable(year, month, day);
			mainMenu.setTextCalendar();
			quitWindow();
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
	
	String modifyText(String task) {
		String ans = task.replace("\'", "\''");
		return ans;
	}
	
	@FXML
	void initialize() {
		for (int i = 0; i <= 23; i++) {
			timeOption.getItems().add(String.format("%d:00", i));
		}
		timeOption.getSelectionModel().selectFirst();
	}
	
	/**
	 * Connects to Main Controller
	 * @param  		None
	 * @return      None
	 */
	public void setController(MainController controller) {
		mainMenu = controller;
	}
	
	
}
