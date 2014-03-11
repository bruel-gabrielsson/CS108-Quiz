package app;


//import static org.junit.Assert.*;
import models.User;

//import org.junit.Test;

public class AppTest {

	App app = new App();
	
		
	//@Test
	public void test() {
		app.number_users = 1;
		app.number_quizzes = 1;
		
		app.current_user = new User();
		
		/// TESTING
		
		app.current_user.user_name = "Tyler";
		if (app.current_user.fetch()) {
			System.out.println("success");
			String s = app.current_user.challenge_received + app.current_user.date_created.toString() + app.current_user.user_name + Integer.toString(app.current_user.user_id) + app.current_user.quizzes.toString();
			System.out.println(s);
			
			// TEW: test user.save for update 
			app.current_user.am_challenges_sent = 100;
			if(app.current_user.save()) {
				System.out.println("Successful update");
			}else {
				//fail("Not yet implemented");
			}
		}
	}
}
