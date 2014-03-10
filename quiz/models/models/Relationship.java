package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class Relationship implements model {
	public String error = null;
	
	public int relationship_id = -1;
	public int user_id = -1;
	public int friend_id = -1;
	public String date_created = null;
	public int request_status = -1;
	// TEW: request status: -1 = Sent, 0 = Rejected, 1 = Accepted
	
	public static final int relationship_notification_type_id = 3;
	
	private DBConnector connector;

	Relationship() {
		connector = new DBConnector();
	}
	
	/*
	 * TEW: When making a new relationship, populate the user_id and friend_id on a relationship and call save.
	 * DO NOT edit the relationship_id directly when trying to create a new relationship. If a relationship_id 
	 * exists this function will perform an update instead of an insert.
	 */
	@Override
	public boolean save() {
		// If the relationship_id is populated, the we want to try and update the relationship table
		if(relationship_id >= 0){
			String[] updateStmt = new String[1];
			updateStmt[0] = "UPDATE relationship SET user_id = " + user_id + ", " +
					"friend_id = " + friend_id + ", " + 
					"request_status = " + request_status + " " +
					"WHERE relationship_id = " + relationship_id;
			int result = connector.updateOrInsert(updateStmt);
			if(result < 0){
				System.err.println("There was an error in the UPDATE call to the RELATIONSHIP table");
				error = "There was an error in the UPDATE call to the RELATIONSHIP table";
				return false;
			}
			return true;
		} else {
			// We don't have a relationship_id, so we are trying to create a new relationship
			String[] insertStmt = new String[3];
				
				// Insert into relationship both ways
				insertStmt[0] = "INSERT INTO relationship(user_id, friend_id, date_created) VALUES(" +
				user_id + ", " + friend_id + ", NOW())";
				
				insertStmt[1] = "INSERT INTO relationship(user_id, friend_id, date_created) VALUES(" +
						friend_id + ", " + user_id + ", NOW())";
				
				// Get the user sending the friend request's user_name
				User user = new User();
				user.user_id = user_id;
				if(!user.fetch()){
					System.err.println("Cannot fetch User for user_id = " + user_id +" when inserting into relationship");
					error = "Cannot fetch User for user_name = " + user.user_name +" when inserting into relationship";
					return false;
				}
				
				// Insert into notification
				insertStmt[2] = "INSERT INTO notification(user_id, notification_type_id, relationship_id, "
						+ "notification_text) VALUES(" +  friend_id + ", " + relationship_notification_type_id + ", " 
						+ " LAST_INSERT_ID(), " + "'" + user.user_name + " has sent you a friend request!')";
			int result = connector.updateOrInsert(insertStmt);
			if(result < 0){
				System.err.println("There was an error in the INSERT call to the RELATIONSHIP table");
				error = "There was an error in the INSERT call to the RELATIONSHIP table";
				return false;
			}
			return true;
		}
	}

	@Override
	public boolean fetch() {
		if(relationship_id == -1){
			System.err.println("Cannot fetch relationship for relationship_id = -1");
			error = "Cannot fetch relationship for relationship_id = -1";
		}
		String relationshipQuery = "SELECT * FROM relationship WHERE relationship_id = " + relationship_id;
		System.out.println(relationshipQuery);
		ResultSet rs = null;
		rs = connector.query(relationshipQuery);
		try {
			rs.beforeFirst();
			if (rs.next()) {
				this.user_id = rs.getInt("user_id");
				this.date_created = rs.getString("date_created");
				this.friend_id = rs.getInt("friend_id");
				this.request_status = rs.getInt("request_status");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			error = "Error getting information from database on relationship_id = " + relationship_id + "when fetching";
			return false;
		}
		System.out.println("relationship.user_id = "+user_id);
		return true;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean destroy() {
		// Check that we have a relationship to delete
		if(relationship_id == -1) {
			//Set error message
			System.err.println("There is no relationship_id to delete");
			return false;
		}
		
		// Get the corresponding relationship on the table
		int corresponding_relationship_id = this.getCorrespondingRelationship();
		
		// Now that we have both relationship_id's we can call delete
		String[] deleteRelationship = new String[2];
		
			// Delete any notifications associated with either relationship
			deleteRelationship[0] = "DELETE FROM notification WHERE relationship_id IN(" 
					+ relationship_id + ", " + corresponding_relationship_id + ")";
			
			// Delete from relationship table
			deleteRelationship[1] = "DELETE FROM relationship WHERE relationship_id IN(" 
					+ relationship_id + ", " + corresponding_relationship_id + ")";
		
		// Delete from the database
		int result = connector.updateOrInsert(deleteRelationship);
		if(result < 0){
			error = "Error deleteing relationship from the database";
			System.err.println("Error deleteing relationship from the database");
			return false;
		}
				
		return true;
	}
	
	/*
	 * TEW: This method will accept a relationship. It will update both of the rows in the relationship table
	 * to have a request_status = 1. It assumes a valid relationship_id is populated on this model.
	 */
	public boolean isAccepted() {
		// Check for illegal inputs
		int corresponding_relationship_id = this.getCorrespondingRelationship();
		if(relationship_id == -1) {
			System.err.println("No relationship_id found to accept in isAccepted()");
			error = "No relationship found to accept";
			return false;
		}
		if(corresponding_relationship_id < 0){
			System.err.println("No corresponding relationship found in isAccepted()");
			error = "No corresponding relationship found to accept";
			return false;
		}
		
		// Write update statement to update request_status
		String[] acceptRelationship = new String[1];
		acceptRelationship[0] = "UPDATE relationship SET request_status = 1 WHERE "
				+ "relationship_id IN(" + relationship_id + ", " + corresponding_relationship_id + ")";
		
		
		// Execute update statement
		int result = connector.updateOrInsert(acceptRelationship);
		if(result < 0){
			System.err.println("Error accepting relationship in database");
			error = "Error accepting relationship in database";
			return false;
		}
		return true;
	}

	/*
	 * TEW: This method will reject a relationship. It will update both of the rows in the relationship table
	 * to have a request_stats = 0. As of now, once a relationship has been rejected, there is no way to 
	 * resend a friend reqeust. 
	 */
	public boolean isRejected() {
		// Check for illegal inputs
		int corresponding_relationship_id = this.getCorrespondingRelationship();
		if(relationship_id == -1) {
			System.err.println("No relationship_id found to accept in isAccepted()");
			error = "No relationship found to accept";
			return false;
		}
		if(corresponding_relationship_id < 0){
			System.err.println("No corresponding relationship found in isAccepted()");
			error = "No corresponding relationship found to accept";
			return false;
		}
		
		// Write update statement to update request_status
		String[] acceptRelationship = new String[1];
		acceptRelationship[0] = "UPDATE relationship SET request_status = 0 WHERE "
				+ "relationship_id IN(" + relationship_id + ", " + corresponding_relationship_id + ")";
		
		
		// Execute update statement
		int result = connector.updateOrInsert(acceptRelationship);
		if(result < 0){
			System.err.println("Error accepting relationship in database");
			error = "Error accepting relationship in database";
			return false;
		}
		return true;
	}
	
	/*
	 * TEW: This is a helper method to assist in getting the corresponding relationship in our double
	 * entry relationship system. It requires fetch to have been called since it uses user_id and friend_id.
	 */
	private int getCorrespondingRelationship() {
		int corresponding_relationship_id = -1;
		String getCorrespondingRelationship = "SELECT relationship_id FROM relationship WHERE "
				+ "user_id = " + this.friend_id + " AND friend_id = " + this.user_id;
		ResultSet rs = null;
		System.out.println("Getting corresponding relationship:" +getCorrespondingRelationship);
		rs = connector.query(getCorrespondingRelationship);
		try{
			rs.beforeFirst();
			if(rs.next()){
				corresponding_relationship_id = rs.getInt("relationship_id");
			} else{
				System.err.println("No corresponding relationship was found to delete!");
				error = "No corresponding relationship was found to delete!";
				return -1;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			error = "Error accessing relationship table";
			return -1;
		}
		return corresponding_relationship_id;
	}
}
