package fr.diginamic.demo_jdbc.exceptions;

/**
 *
 */
public class TechnicalException extends RuntimeException {

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public TechnicalException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}

}
