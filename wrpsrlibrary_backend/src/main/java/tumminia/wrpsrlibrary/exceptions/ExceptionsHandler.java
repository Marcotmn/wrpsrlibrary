package tumminia.wrpsrlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // --> 400
	public ErrorPayload handleBadRequest(BadRequestException e) {
		return new ErrorPayload(e.getMessage());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // --> 404
	public ErrorPayload handleNotFound(NotFoundException e) {
		return new ErrorPayload(e.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED) // --> 401
	public ErrorPayload handleUnauthorized(UnauthorizedException e) {
		return new ErrorPayload(e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN) // --> 403
	public ErrorPayload handleAccessDenied(AccessDeniedException e) {
		return new ErrorPayload(e.getMessage());
	}

}
