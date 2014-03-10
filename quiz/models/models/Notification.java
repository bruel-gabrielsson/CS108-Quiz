package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class Notification implements model {

	public String error = null;
	
	public int notification_id = -1;
	public int user_id = -1;
	public int notification_type_id = -1;
	public int message_id = -1;
	public int challenge_id = -1;
	public int relationship_id = -1;
	public int is_viewed = 0;
	public String notification_text = null;
	
	private DBConnector connector;

	public Notification() {
		connector = new DBConnector();
	}
	
	@Override
	public boolean save() {
		if (notification_id >= 0){
			String[] updateStmt = new String[1];
			updateStmt[0] = "UPDATE notification SET is_viewed = " + this.is_viewed + " " +
					"WHERE notification_id = " + notification_id;
			int result = connector.updateOrInsert(updateStmt);
			if(result < 0){
				System.err.println("There was an error in the UPDATE call to the RELATIONSHIP table");
				error = "There was an error in the UPDATE call to the RELATIONSHIP table";
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean fetch() {
		if(notification_id == -1){
			System.err.println("Cannot fetch notification for notification_id = -1");
			error = "Cannot fetch notification for notification_id = -1";
		}
		String notifyQuery = "SELECT * FROM notification WHERE notification_id = " + notification_id;
		ResultSet rs = null;
		rs = connector.query(notifyQuery);
		try {
			rs.beforeFirst();
			if (rs.next()) {
				this.user_id = rs.getInt("user_id");
				this.notification_type_id = rs.getInt("notification_type_id");
				this.message_id = rs.getInt("message_id");
				this.challenge_id = rs.getInt("challenge_id");
				this.relationship_id = rs.getInt("relationship_id");
				this.is_viewed = rs.getInt("is_viewed");
				this.notification_text = rs.getString("notification_text");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			error = "Error getting information from database on notification_id = " + notification_id + "when fetching";
			return false;
		}
		
		return true;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean destroy() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
