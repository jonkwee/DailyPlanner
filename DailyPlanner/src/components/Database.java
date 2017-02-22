package components;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
	Connection c;
	Statement stmt;
	private static int primaryKey = 0;
	
	// Constructor (creates db file if doesn't exist)
	public Database() {
		c = null;
		stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:tasks.db");
			createTable();
			primaryKey = getID();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println(this.getClass());
		    System.exit(0);
		}
	}
	
	/**
	 * Gets last primary key in TASK table
	 * @param  		None
	 * @return      int ID
	 */
	private int getID() throws SQLException {
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery( "SELECT MAX(ID) FROM TASKS" );
	    int id = rs.getInt(1);
	    rs.close();
	    return ++id;
	}
	
	/**
	 * Creates table named TASKS in database with primary key, year, month,
	 * day, time, task as parameters.
	 * @param  		None
	 * @return      None
	 */
	protected void createTable() throws SQLException {
		stmt = c.createStatement();
		String createTableQuery = "CREATE TABLE IF NOT EXISTS TASKS " +
                				  "(ID INT PRIMARY KEY     NOT NULL," +
                				   "YEAR          INT    NOT NULL," +
                				   "MONTH         INT    NOT NULL," +
                				   "DAY           INT    NOT NULL," +
                				   "TIME          TEXT   NOT NULL, " + 
                				   "TASK          TEXT   NOT NULL);"; 
		stmt.executeUpdate(createTableQuery);
	}
	
	/**
	 * Insert data into database
	 * @param  		year, month, day, time, task
	 * @return      None
	 */
	public void insertInto(int year, int month, int day, String time, String task) throws SQLException {
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(String.format("SELECT COUNT(*) AS AMOUNT FROM TASKS WHERE YEAR = %d AND "
				+ "MONTH = %d AND DAY = %d AND TIME = '%s';", year, month, day, time));
		int amount = rs.getInt("AMOUNT");
		String insertQuery;
		if (amount > 0) {
			String dayTask = String.format("%s, %s", getSpecificTask(year, month, day, time), task);
			insertQuery = String.format("UPDATE TASKS SET TASK = '%s' WHERE YEAR = %d AND "
				+ "MONTH = %d AND DAY = %d AND TIME = '%s';", dayTask, year, month, day, time);
		} else {
			insertQuery = String.format("INSERT INTO TASKS (ID, YEAR, MONTH, DAY, TIME, TASK)"
				+ "VALUES (%d,'%d','%d','%d','%s','%s');", primaryKey, year, month, day, time, task);
		}
		stmt.executeUpdate(insertQuery);
		primaryKey++;
	}
	
	/**
	 * Delete specific data from TASK table
	 * @param  		year, month, day, task
	 * @return      None
	 */
	public void deleteTask(int year, int month, int day, String time, String task) throws SQLException {
		stmt = c.createStatement();
		String deleteQuery = String.format("DELETE FROM TASKS WHERE YEAR = %d "
				+ "AND MONTH = %d AND DAY = %d AND TIME = '%s' AND TASK = '%s';", year, month, day, time, task);
		stmt.executeUpdate(deleteQuery);
	}
	
	/**
	 * Deletes all data in TASK table
	 * @param  		None
	 * @return      None
	 */
	public void deleteAll() throws SQLException {
		stmt = c.createStatement();
		String deleteQuery = "DELETE FROM TASKS";
		stmt.executeUpdate(deleteQuery);
	}
	
	/**
	 * Gets all available tasks in a specific date
	 * @param  		year, month, day
	 * @return      ObservableList of Tasks
	 */
	public ObservableList<Tasks> getTasks(int year, int month, int day) throws SQLException {
		stmt = c.createStatement();
		ObservableList<Tasks> returnableList = FXCollections.observableArrayList();
		ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM TASKS WHERE YEAR = %d AND "
				+ "MONTH = %d AND DAY = %d;", year, month, day));
		 while ( rs.next() ) {
	         String time = rs.getString("time");
	         String task  = rs.getString("task");
	         returnableList.add(new Tasks(time, task));
	      }
		 rs.close();
		 
		 return returnableList;
	}
	
	/**
	 * Gets total amount of tasks on a specific day
	 * @param  		year, month, day
	 * @return      Amount of tasks
	 */
	public int getTaskAmount(int year, int month, int day) throws SQLException {
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(String.format("SELECT COUNT(*) AS AMOUNT FROM TASKS WHERE YEAR = %d AND "
				+ "MONTH = %d AND DAY = %d;", year, month, day));
		int count = rs.getInt("AMOUNT");
		return count;	
	}
	
	/**
	 * Gets specific task from a specific day at a specific time
	 * @param  		year, month, day, time
	 * @return      String task
	 */
	public String getSpecificTask(int year, int month, int day, String time) throws SQLException {
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM TASKS WHERE YEAR = %d AND "
				+ "MONTH = %d AND DAY = %d AND TIME = '%s';", year, month, day, time));
		String task = rs.getString("TASK");
		return task;
	}
	
	// FOR DEBUGGING PURPOSES
	public void printAll() throws SQLException {
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TASKS;");
		System.out.printf("%s%10s%10s%10s%10s%10s\n","ID","YEAR","MONTH","DAY","TIME","TASK");
		while (rs.next()) {
			int id = rs.getInt("ID");
			String year = rs.getString("YEAR");
			String month = rs.getString("MONTH");
			String day = rs.getString("DAY");
			String time = rs.getString("TIME");
			String task = rs.getString("TASK");
			System.out.printf("%d%10s%10s%10s%10s%10s\n", id, year, month, day, time, task);
		}
	}
	
	/**
	 * Closes class and all connections to database
	 * @param  		None
	 * @return      None
	 */
	public void close() throws SQLException {
		c.close();
		stmt.close();
	}
	
//	public static void main(String[] args) {
//		Database database = new Database();
//		try {
//			database.printAll();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
