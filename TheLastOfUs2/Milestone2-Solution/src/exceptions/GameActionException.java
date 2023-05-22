package exceptions;

public abstract class GameActionException extends Exception {

	public GameActionException() {
	}
//test
	public GameActionException(String message) {
		super(message);
	}

}
