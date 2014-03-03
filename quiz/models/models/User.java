package models;

import java.util.ArrayList;
import java.sql.*; 
import java.security.*;

import database.DBConnector;
import app.App;

/**
 * 
 * @author rickardbruelgabrielsson
 * User class
 * 
 * RELATIONS (Should always be specified):
 * HAS-MANY: QUIZZES
 * 
 * FETCH: BASED ON user_name
 */
public class User implements model {
	
	public int user_id;
	public String date_created;
	public String user_name = null;
	public int message_received;
	public int challenge_received;
	
	public ArrayList<Quiz> quizzes = null;
	public ArrayList<String> friends = null;
	public ArrayList<Message> messages = null;
	
	private boolean is_admin = false;
	
	private DBConnector connector = null;
	
	private static final String ALGORITHM = "SHA";
	private static final int SALT_LENGTH = 32;
	
	/**
	 * 
	 */
	public User() {
		connector = new DBConnector();
		
	}
	
	// Through relationship?
	public void friendRequest(int friend_id) {
		
	}
	
	// Checks if user is an admin 
	public boolean isAdming() {
		
		return true;
	}
	
	@Override
	public boolean save() {
		// Write to database
		return true;
	}
	
	@Override
	public boolean fetch() {

		if (this.user_name == null) {
			return false;
		}
		
		connector.openConnection();
		
		// populate all the fields
		String query = "SELECT * FROM user WHERE user_name = '"+ this.user_name +"'";
		ResultSet rs = connector.query(query);
		try {
			if (rs.next()) {
				this.user_id = rs.getInt("user_id");
				this.date_created = rs.getString("date_created");
				this.user_name = rs.getString("user_name");
				this.message_received = rs.getInt("message_recived"); // SPELLING ERROR!!!!
				this.challenge_received = rs.getInt("challenge_received");
				this.is_admin = rs.getInt("is_admin") == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// also populate the this.quizzes list with quizzes whose user_id == this.user_id
		String quiz_query = "SELECT quiz_id FROM quiz WHERE creator_id = '" + this.user_id + "'";
		rs = null;
		rs = connector.query(quiz_query);
		this.quizzes = new ArrayList<Quiz>();
		try {
			while(rs.next()) {
				int quiz_id = rs.getInt("quiz_id");
				Quiz temp_quiz = new Quiz();
				temp_quiz.quiz_id = quiz_id;
				if (temp_quiz.fetch()) {
					this.quizzes.add(temp_quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		// Populate Friends ID list
		String friends_id_query = "SELECT * FROM relationship WHERE user_id = '" + this.user_id + "' ORDER BY date_created";
		rs = null;
		rs = connector.query(friends_id_query);
		ArrayList<Integer> friendIDs = new ArrayList<Integer>();
		try {
			while(rs.next()) {
				int friend_id = rs.getInt("friend_id");
				friendIDs.add(friend_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		// Translate friends ID list to friends username
		this.friends = new ArrayList<String>();
		for (Integer friend_id : friendIDs) {
		
			String friends_query = "SELECT * FROM user WHERE user_id = '" + friend_id + "'";
			rs = null;
			rs = connector.query(friends_query);
			try {
				while(rs.next()) {
					String friend_name = rs.getString("user_name");
					this.friends.add(friend_name);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		connector.closeConnection();
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
	
	public boolean fetchMessages() {
		
		connector.openConnection();
		
		// Populate Messages list
		String message_id_query = "SELECT * FROM message WHERE to_user_id = '" + this.user_id + "' ORDER BY time_sent";
		ResultSet rs = connector.query(message_id_query);
		this.messages = new ArrayList<Message>();
		try {
			while(rs.next()) {
				int message_id = rs.getInt("message_id");
				Message new_msg = new Message();
				new_msg.message_id = message_id;
				if (new_msg.fetch()) {
					this.messages.add(new_msg);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connector.closeConnection();
		return true;
	}
	
	public boolean signIn(String password) {
		if (this.user_name == null) {
			return false;
		}	
		
		return true;
	}
	
	/**
	  Returns a secure random salt as a string,
	  to be called once per User, upon account
	  creation
	 */
	public String generateSalt() {
		 return hexToString(new SecureRandom().generateSeed(SALT_LENGTH));
	}
	
	/**
	 Given a User's plain text password and salt,
	 returns the hash of that users password. To
	 be used upon account creation (to generate the
	 hash to be stored) and login (for comparison
	 with stored hash).  Returns null upon internal
	 error.
	 */
	public String hashPassword(String password, String salt) {
		try {		
			String toHash = salt + password;
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			byte[] bytes = md.digest(toHash.getBytes());
			return hexToString(bytes);
			
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	/**
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	private String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
}
