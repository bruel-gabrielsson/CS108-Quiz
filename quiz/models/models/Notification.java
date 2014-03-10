package models;

import database.DBConnector;

public class Notification implements model {

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fetch() {
		// TODO Auto-generated method stub
		return false;
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
