package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnector;

public class Announcement implements model {

	public int app_id = -1;
	public String announcement_name;
	public String announcement;

	private DBConnector connector;

	public Announcement() {
		connector = new DBConnector();
	}

	@Override
	public boolean save() {

		String[] insertStmt = new String[1];
		insertStmt[0] = "INSERT INTO app(announcement_name, announcement,  display_yn) VALUES( "
				+ "\""
				+ announcement_name
				+ "\", \""
				+ announcement
				+ "\", \""
				+ 1 + "\")";
		System.out.println("user insert: " + insertStmt[0]);
		int result = connector.updateOrInsert(insertStmt);
		if (result < 0) {
			System.err
					.println("There was an error in the INSERT call to the APP table");
			return false;
		}
		return true;
	}

	/**
	 * Grab the latest displayable announcement
	 */
	@Override
	public boolean fetch() {
		String msgQuery = "SELECT * FROM app WHERE display_yn = '1' ORDER BY timestamp";
		ResultSet rs = connector.query(msgQuery);
		try {
			while (rs.next()) {
				this.app_id = rs.getInt("app_id");
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
		if (announcement_id == -1) {
			error = "No annoucnement to delete (announcement_id = -1)";
			return false;
		}
		String[] deleteAnnouncement = new String[1];

		// Delete from announcement
		deleteAnnouncement[0] = "DELETE FROM app WHERE app_id = " + app_id;

		// Delete from the database
		int result = connector.updateOrInsert(deleteAnnouncement);
		if (result < 0) {
			System.err
					.println("There was an error in the DELETE call on an app");
			error = "There was an error in the DELETE call on an app";
			return false;
		}
		return true;
	}

	// TEW: This was using quiz instead of app and appears to have been copied
	// from getAllQuizzes
	// UPdated to use app
	public static ArrayList<Announcement> getAllAnnouncements() {
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		DBConnector connector = new DBConnector();

		// Populate announcements list
		String app_query = "SELECT * FROM app WHERE display_yn = 1 ORDER BY time_stamp";
		ResultSet rs = connector.query(app_query);
		try {
			while (rs.next()) {
				Announcement ann = new Announcement();
				ann.announcement_name = rs.getString("announcement_name");
				ann.announcement = rs.getString("announcement");
				ann.announcement = rs.getInt("app_id");
				announcements.add(ann);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return announcements;
	}
}
