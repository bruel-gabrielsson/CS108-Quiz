package questions;

import models.Question;

public class PictureResponse extends Question {
	private static final String type = "PictureResponse";

	@Override
	public boolean save() {
		return true;
	}

	@Override
	public boolean fetch() {
		return true;
	}

	@Override
	public boolean update() {
		return true;
	}

	@Override
	public boolean destroy() {
		return true;
	}

}
