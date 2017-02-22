package components;

public class Tasks {

	private String time;
	private String task;
	
	// Constructor 
	public Tasks (String time, String task) {
		this.time = time;
		this.task = task;
	}
	
	/**
	 * Returns time component of Task Object
	 * @param  		None
	 * @return      String
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * Returns task component of Task Object
	 * @param  		None
	 * @return      String
	 */
	public String getTask() {
		return task;
	}
	
	/**
	 * Set time of Task Object to param
	 * @param  		String
	 * @return      None
	 */
	public void setTime(String newTime) {
		time = newTime;
	}
	
	/**
	 * Set task of Task Object to param
	 * @param  		String
	 * @return      None
	 */
	public void setTask(String newTask) {
		task = newTask;
	}
}
