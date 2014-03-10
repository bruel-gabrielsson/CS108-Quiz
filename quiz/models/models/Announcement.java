package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnector;

public class Announcement implements model {
	
	public String announcement_name;
	public String announcement;
	
	private DBConnector connector;
	
	public Announcement() {
		connector = new DBConnector();
	}
	
	@Override
	public boolean save() {

		String[] insertStmt = new String[1];
		insertStmt[0] = "INSERT INTO app(announcement_name, announcement,  display_yn) VALUES( " +
		"\"" + announcement_name + "\", \"" + announcement + "\", \"" + 1 +"\")";
		System.out.println("user insert: " + insertStmt[0]);
		int result = connector.updateOrInsert(insertStmt);
		if(result < 0){
			System.err.println("There was an error in the INSERT call to the APP table");
			return false;
		}
		return true;
	}
	
	/**
	 * Grab the latest displayable announcement
	 */
	@Override
	public boolean fetch() {				
		String msgQuery = "SELECT * FROM app WHERE display_yn = '1'";
		ResultSet rs = connector.query(msgQuery);
		try {
			while(rs.next()) {
				this.announcement_name = rs.getString("announcement_name");
				this.announcement = rs.getString("announcement");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}		
		
		return true;
	}
	
	@Override
	public boolean update() {
		// Update fields to database
		return true;
	}
	
	@Override
	public boolean destroy() {
		// Destroy the column from the database
		
		// Set all the quizzes user_id to null or something similar to indicate that user has been destroyed
		return true;
	}
	
	
	public static ArrayList<Announcement> getAllAnnouncements() {
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		DBConnector connector = new DBConnector();
		
		// Populate quizzes list
		String quiz_query = "SELECT * FROM quiz ORDER BY quiz_id";
		ResultSet rs = connector.query(quiz_query);
		try {
			while(rs.next()) {
				Announcement ann = new Announcement();
				ann.announcement_name = rs.getString("announcement_name");
				ann.announcement = rs.getString("announcement");
				announcements.add(ann);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return announcements;
	}
}
