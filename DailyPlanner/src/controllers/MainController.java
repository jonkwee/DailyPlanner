package controllers;

import java.sql.SQLException;

import components.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {
	
	// Overall
	@FXML
	TabPane tabPane;
	
	// In Month Tab
	@FXML
	Button monthLeft;
	
	@FXML
	Button monthRight;
	
	@FXML
	Button exit;
	
	@FXML
	Label monthDisplay;
	
	@FXML
	Label yearDisplay;
	
	@FXML
	GridPane calendar;
	
	// In Week Tab
	@FXML
	Button weekLeft;
	
	@FXML
	Button weekRight;
	
	@FXML
	Label weekDisplay;
	
	// In Days Tab
	@FXML
	TableView<Tasks> timeTaskTable; 
	
	@FXML
	TableColumn<Tasks,String> dayTimeColumn;
	
	@FXML
	TableColumn<Tasks,String> dayTaskColumn;
	
	@FXML
	Label taskMonthDisplay;
	
	@FXML
	Label taskDayDisplay;
	
	@FXML
	Button add;
	
	@FXML
	Button delete;
	
	@FXML
	Button deleteAll;
	
	@FXML
	Button dayLeft;
	
	@FXML
	Button dayRight;
	
	
	
	private Date date = new Date();
	private Database database = new Database();
	
	// Helpers
	/**
	 * Returns reference of Date Object from Main Controller
	 * @param  		None
	 * @return      Date Object
	 */
	public Date exportDate() {
		return date;
	}

	/**
	 * Returns reference of Database Object from Main Controller
	 * @param  		None
	 * @return      Database Object
	 */
	public Database exportDatabase() {
		return database;
	}
	
	
	// For Month Tab
	@FXML
	public void clickNextMonth() {
		date.setNextMonth();
		monthDisplay.setText(date.monthToString());
		yearDisplay.setText(date.yearToString());
		taskMonthDisplay.setText(date.monthToString());
		setTextCalendar();
	}
	
	@FXML
	public void clickPreviousMonth() {
		date.setPreviousMonth();
		monthDisplay.setText(date.monthToString());
		yearDisplay.setText(date.yearToString());
		taskMonthDisplay.setText(date.monthToString());
		setTextCalendar();
	}
	
	/**
	 * Create Pane for calendar
	 * @param  		None
	 * @return      Pane with two Labels
	 */
	private Pane createPane() {
		Pane pane = new Pane();
		Label textLabel = new Label();
		Label taskLabel = new Label();
		textLabel.getStyleClass().add("dateLabel");
		taskLabel.getStyleClass().add("taskLabel");
		textLabel.relocate(66, 0);
		taskLabel.relocate(18, 29);
		pane.getChildren().add(textLabel);
		pane.getChildren().add(taskLabel);
		return pane;
	}

	
	private void setPaneEvent(Pane pane) {
		pane.setOnMouseClicked(event -> {
			Label l = (Label) pane.getChildren().get(0);
			String clickedDay = l.getText();
			taskDayDisplay.setText(clickedDay);
			taskMonthDisplay.setText(date.monthToString());
			date.setDay(Integer.parseInt(clickedDay));
			loadTaskToTable(date.getYear(),date.getMonth(),date.getDay());
			changeTabs();
		});
	}
	
	private void clearPaneEvent(Pane pane) {
		pane.setOnMouseClicked(null);
	}
	
	/**
	 * Fills GridPane with Panes
	 * @param  		None
	 * @return      None
	 */
	private void fillCalendar() {
		for (int c = 0; c < 6; c++) {
			for (int r = 0; r < 7; r++) {
				Pane pane = createPane();
				calendar.add(pane, r, c);
			}
		}
	}

	/**
	 * Set the days of the current month on the calendar interface
	 * @param  		None
	 * @return      None
	 */
	public void setTextCalendar() {
		ObservableList<Node> calendarChildrens = calendar.getChildren();
		int first = date.getFirstDayOfMonth();
		int last = date.getLastDayOfMonth();
		
		for (int index = 1, dateNumber = 1; index < 43; index++) {
			Pane pane = (Pane) calendarChildrens.get(index);
			Label textLabel = (Label) pane.getChildren().get(0);
			Label taskLabel = (Label) pane.getChildren().get(1);
			if ((index >= first + 1) && (index < last + first + 1)) {
				textLabel.setText("" + dateNumber);
				taskLabel.setText((updateTaskCalendarHelper(dateNumber) == 0)? "" : "" + updateTaskCalendarHelper(dateNumber) + " tasks");
				setPaneEvent(pane);
				dateNumber++;
			} else {
				clearPaneEvent(pane);
				textLabel.setText("");
				taskLabel.setText("");
			}
		}
	}
	
	/**
	 * Get the amount of tasks on a specific day
	 * @param  		None
	 * @return      int
	 */
	public int updateTaskCalendarHelper(int day) {
		try {
			return database.getTaskAmount(date.getYear(), date.getMonth(), day);
		} catch (SQLException e) {
			System.out.println(this.getClass());
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return 0;
	}
	
	/**
	 * Change to the last tab
	 * @param  		None
	 * @return      None
	 */
	private void changeTabs() {
		// How to get reference of pane by clicking
		tabPane.getSelectionModel().selectLast();
	}
	
	@FXML
	private void clickPreviousDay() {
		date.setPreviousDay();
		taskDayDisplay.setText(date.dayToString());
		taskMonthDisplay.setText(date.monthToString());
		monthDisplay.setText(date.monthToString());
		yearDisplay.setText(date.yearToString());
		setTextCalendar();
		loadTaskToTable(date.getYear(),date.getMonth(),date.getDay());
	}
	
	@FXML
	private void clickNextDay() {
		date.setNextDay();
		taskDayDisplay.setText(date.dayToString());
		taskMonthDisplay.setText(date.monthToString());
		monthDisplay.setText(date.monthToString());
		yearDisplay.setText(date.yearToString());
		setTextCalendar();
		loadTaskToTable(date.getYear(),date.getMonth(),date.getDay());
	}
	
	@FXML
	private void exitWindow() {
		exit.getScene().getWindow().hide();
	}
	
	// For Day Tab
	/**
	 * Loads tasks from specific date from database to table
	 * @param  		None
	 * @return      Observable List of tasks
	 */
	public void loadTaskToTable(int year, int month, int day) {
		ObservableList<Tasks> tasks = null;
		
		try {
			tasks = database.getTasks(year, month, day);
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.out.println(this.getClass());
		      System.exit(0);
		}
		
		timeTaskTable.setItems(tasks);
	}
	
	@FXML
	private void deleteTask() {
		int row = timeTaskTable.getSelectionModel().getSelectedIndex();
		String time = dayTimeColumn.getCellData(row);
		String task = dayTaskColumn.getCellData(row);
		
		try {
			database.deleteTask(date.getYear(), date.getMonth(), date.getDay(), time, task);
			loadTaskToTable(date.getYear(), date.getMonth(), date.getDay());
			setTextCalendar();
		} catch (SQLException e) {
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		     System.out.println(this.getClass());
		     System.exit(0);
		}	
	}
	
	@FXML
	private void deleteAll() {
		try {
			database.deleteAll();
			loadTaskToTable(date.getYear(), date.getMonth(), date.getDay());
			setTextCalendar();
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.out.println(this.getClass());
		    System.exit(0);	
		}
	}
	
	private void setLabelsDayTab(String month, String day) {
		taskMonthDisplay.setText(month);
		taskDayDisplay.setText(day);
	}
	
	@FXML
	void openAddInterface() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("AddTaskGUI.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			AddTaskController second = (AddTaskController) loader.getController();
			second.setController(this);
			Stage secondStage = new Stage();
			Scene scene = new Scene(root);
			secondStage.setScene(scene);
			secondStage.show();	
		} catch ( Exception e ) {
			System.out.println(this.getClass());
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );   	
		}
	}
	
	
	
	@FXML
	private void initialize() {
		// initialize Month Tab
		monthDisplay.setText(date.monthToString());
			// initialize days of month
		fillCalendar();
		setTextCalendar();
		
		// initialize Day Tab
		setLabelsDayTab(date.monthToString(),date.dayToString());
		dayTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
		dayTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
		
//		try { 
//			database.insertInto(date.getYear(), date.getMonth(), date.getDay(), "9:00", "TO DO");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		loadTaskToTable(date.getYear(),date.getMonth(),date.getDay());

	}
	
	
	
}
