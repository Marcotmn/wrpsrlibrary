package tumminia.wrpsrlibrary.exceptions;

import lombok.Getter;

@Getter

@SuppressWarnings("serial")
public class AccessDeniedException extends RuntimeException {

	public AccessDeniedException(String message) {
		super(message);
	}

}
