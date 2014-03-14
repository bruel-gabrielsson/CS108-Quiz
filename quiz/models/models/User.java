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
	public String error = null;
	
	public int user_id = -1; // TEW: initialize to -1 for SAVE method
	public String date_created;
	public String user_name = null;
	public int message_received;
	public int challenge_received;
	public int am_created_quizzes;
	public int am_taken_quizzes;
	public int am_challenges_sent;
	public int am_messages_sent;
	public int am_number_friends;
	
	public ArrayList<Quiz> quizzes = null;
	public ArrayList<String> friends = null;
	public ArrayList<Message> messages = null;
	public ArrayList<Notification> notifications = null;
	public ArrayList<History> friendActivity = null;
	
//	private boolean is_admin = false;

	public int is_admin =  0; // TEW: changed is_admin to int because it has to be an INT in SQL (a bool in SQL is just a tinyint that can be 0 or 1)
	public String password = null; // TEW: added because password cannot be null when creating a new user
	public String salt = null; // TEW: added because salt cannot be null when creating a new user
	private static final int superuser_id = 1;
	
	
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
	public boolean isAdmin() {
		if(is_admin == 1) return true;
		return false;	
	}
	
	// TEW: implemented save function.
	@Override
	public boolean save() {
		// If the user_id is populated, the we want to try and update the user table
		if(user_id >= 0){
			String[] updateStmt = new String[1];
			updateStmt[0] = "UPDATE user SET user_name = \"" + user_name+ "\", " +
					"password = \"" + password + "\", " + 
					"salt = \"" + salt + "\", " + 
					"message_received = " + message_received + ", " +
					"challenge_received = " + challenge_received + ", " +
					"is_admin = " + is_admin + ", " +
					"am_created_quizzes = " + am_created_quizzes + ", " +
					"am_taken_quizzes = " + am_taken_quizzes + ", " +
					"am_challenges_sent = " + am_challenges_sent + ", " +
					"am_messages_sent = " + am_messages_sent + ", " +
					"am_number_friends = " + am_number_friends + " " +
					"WHERE user_id = " + user_id;
			System.out.println("user update: " + updateStmt[0]);
			int result = connector.updateOrInsert(updateStmt);
			if(result < 0){
				System.err.println("There was an error in the UPDATE call to the USER table");
				return false;
			}
			return true;
		} else {
			// We don't have a user_id, so we are trying to create a new user
			// All fields besides the ones we are inserting into will be automatically set
			String[] insertStmt = new String[1];
			insertStmt[0] = "INSERT INTO user(date_created, user_name, password, salt, is_admin) VALUES( NOW(), " +
			"\"" + user_name + "\", \"" + password + "\", \"" + salt + "\", " + is_admin +")";
			System.out.println("user insert: " + insertStmt[0]);
			int result = connector.updateOrInsert(insertStmt);
			if(result < 0){
				System.err.println("There was an error in the INSERT call to the USER table");
				return false;
			}
			return true;
		}
	}
	
	@Override
	public boolean fetch() {
		this.error = null;
		
		if (this.user_name == null && this.user_id == -1) {
			this.error = "Username (and user_id) was not specified";
			return false;
		}
				
		ResultSet rs = null;
		
		// populate all the fields
		if (this.user_name != null) {
			String query = "SELECT * FROM user WHERE user_name = '"+ this.user_name +"'";
			rs = connector.query(query);
			System.out.println(this.user_name);
		} else if (this.user_id != -1) {
			String query = "SELECT * FROM user WHERE user_id = '"+ this.user_id +"'";
			rs = connector.query(query);
			System.out.println(this.user_id);
		}
		
		System.out.println(rs);
		
		try {
			if (rs.next()) {
				this.user_id = rs.getInt("user_id");
				this.date_created = rs.getString("date_created");
				this.user_name = rs.getString("user_name");
				this.password = rs.getString("password");
				this.salt = rs.getString("salt");
				this.message_received = rs.getInt("message_received"); 
				this.challenge_received = rs.getInt("challenge_received");
				this.is_admin = rs.getInt("is_admin");
				this.am_created_quizzes = rs.getInt("am_created_quizzes");
				this.am_taken_quizzes = rs.getInt("am_taken_quizzes");
				this.am_challenges_sent = rs.getInt("am_challenges_sent");
				this.am_messages_sent = rs.getInt("am_messages_sent");
				this.am_number_friends = rs.getInt("am_number_friends");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// also populate the this.quizzes list with quizzes whose user_id == this.user_id
		String quiz_query = "SELECT quiz_id FROM quiz WHERE creator_id = '" + this.user_id + "' ORDER BY date_created DESC";
		/*TEW: Order the user's quizzes by the date the were created starting from the most recent*/
		rs = null;
		rs = connector.query(quiz_query);
		this.quizzes = new ArrayList<Quiz>();
		
		try {
			while(rs.next()) {
				int quiz_id = rs.getInt("quiz_id");
				Quiz temp_quiz = new Quiz();
				
				temp_quiz.quiz_id = quiz_id;
				temp_quiz.creator_id = this.user_id; // Mapping back
				if (temp_quiz.fetch()) {
					this.quizzes.add(temp_quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (fetchMessages()) {
			// fetches the messages automatically for now
		}
			
		return true;
	}
	
	@Override
	public boolean update() {
		// Update fields to database
		return true;
	}
	
	// TEW: Implemented destroy method
	@Override
	public boolean destroy() {
		// TEW: If no user is logged in return false and set the error message field? (will we be making an error message field?)
		if(user_id == -1) return false;
		String[] deleteUser = new String[10];
		
			// Delete from Notification for any notifications received
			deleteUser[0] = "DELETE FROM notification WHERE user_id = " + user_id;
			
			// Delete from Notification for friend requests sent
			deleteUser[1] = "DELETE FROM notification where relationship_id IN(" +
								" SELECT relationship_id FROM relationship" +
								" WHERE friend_id = " + user_id + 
								" OR user_id = " + user_id + ")";
			
			// Delete from Notification for challenges sent
			deleteUser[2] = "DELETE FROM notification where challenge_id IN(" +
								" SELECT challenge_id FROM challenge " +
								" WHERE to_user_id = " + user_id +
								" OR from_user_id = " + user_id + ")";
			
			// Delete from Notification for messages sent
			deleteUser[3] = "DELETE FROM notification WHERE message_id IN(" +
								" SELECT message_id FROM message" +
								" WHERE to_user_id = " + user_id +
								" OR from_user_id = " + user_id + ")";
			
			// Delete from relationships
			deleteUser[4] = "DELETE FROM relationship WHERE user_id = " + user_id + " OR " + "friend_id = " + user_id;
					
			// Delete from messages
			deleteUser[5] = "DELETE FROM message WHERE to_user_id = " + user_id + " OR from_user_id = " + user_id;
			
			// Delete from challenge
			deleteUser[6] = "DELETE FROM challenge WHERE to_user_id = " + user_id + " OR " + "from_user_id = " + user_id;
		
			// Delete from history
			deleteUser[7] = "DELETE FROM history WHERE user_id = " + user_id;
			
			// Update any quizzes they wrote to have the superuser's user_id of 1
			deleteUser[8] = "UPDATE quiz SET creator_id = " + superuser_id + " WHERE creator_id = " + user_id;
			
			// Delete from the user table
			deleteUser[9] = "DELETE FROM user WHERE user_id = " + user_id;
	
		// Delete from the database
		int result = connector.updateOrInsert(deleteUser);
		if(result < 0){
			System.err.println("There was an error in the DELETE call on a user_id");
			return false;
		}
		return true;
	}
	
	public boolean fetchFriends() {
		// Populate Friends ID list
		String friends_id_query = "SELECT * FROM relationship WHERE user_id = '" + this.user_id + "' AND request_status = 1 ORDER BY date_created";
		ResultSet rs = connector.query(friends_id_query);
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
		
		return true;
	}
	
	public boolean fetchMessages() {
				
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
		
		return true;
	}
	
	public boolean fetchNotifications() {
		
		String notif_query = "SELECT * FROM notification WHERE user_id = '" + this.user_id + "'";
		ResultSet rs = connector.query(notif_query);
		this.notifications = new ArrayList<Notification>();
		try {
			while(rs.next()) {
				
				// Only add unviewed / active notifications
				if (rs.getInt("is_viewed") == 1) continue;
				
				Notification notif = new Notification();
				notif.notification_id = rs.getInt("notification_id");
				notif.user_id = rs.getInt("user_id");
				notif.notification_type_id = rs.getInt("notification_type_id");
				notif.message_id = rs.getInt("message_id");
				notif.challenge_id = rs.getInt("challenge_id");
				notif.relationship_id = rs.getInt("relationship_id");
				notif.is_viewed = rs.getInt("is_viewed");
				notif.notification_text = rs.getString("notification_text");
				
				this.notifications.add(notif);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean signIn(String password) {
		if (this.user_name == null) {
			return false;
		}	
		
		String query = "SELECT password, salt FROM user WHERE user_name = '" + this.user_name + "'";
		ResultSet rs = connector.query(query);
		try {
			if (rs.next()) {
				String stored_password = rs.getString("password");
				String salt = rs.getString("salt");
				
				String hashed = hashPassword(password, salt);
				if (hashed.equals(stored_password)) return this.fetch();
			}
		} catch (SQLException e) {
			return false;
		}
		
		return false;
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
	
	/*
	 * Light-weight fetch method to see if user exists
	 */
	public Boolean exists() {
		if (this.user_name != null) {
			String query = "SELECT * FROM user WHERE user_name = '"+ this.user_name +"'";
			ResultSet rs = connector.query(query);
			try {
				return rs.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		DBConnector connector = new DBConnector();
		
		// Populate Messages list
		String user_query = "SELECT * FROM user ORDER BY user_id";
		ResultSet rs = connector.query(user_query);
		try {
			while(rs.next()) {
				User user = new User();
				user.user_name = rs.getString("user_name");
				user.date_created = rs.getString("date_created");
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public boolean isFriendWith(int friend_id) {
		boolean isFriend = false;
		
		String friends_id_query = "SELECT * FROM relationship WHERE user_id = '" + this.user_id + "' AND request_status = 1 ORDER BY date_created";
		ResultSet rs = connector.query(friends_id_query);
		try {
			while(rs.next()) {
				if (friend_id == rs.getInt("friend_id")) {
					isFriend = true;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isFriend;
	}
	
	public boolean fetchFriendActivity() {
		String query = "SELECT * FROM history WHERE user_id "
				+ "IN(SELECT friend_id FROM relationship WHERE user_id = "
				+ user_id +") ORDER BY timestamp DESC";
		
		ResultSet rs = connector.query(query);
		friendActivity = new ArrayList<History>();
		try {
			while (rs.next()) {
				History hist = new History();
				hist.history_id = rs.getInt("history_id");
				if (hist.fetch()) {
					friendActivity.add(hist);
				} else {
					System.out.println("Error: History not found");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}