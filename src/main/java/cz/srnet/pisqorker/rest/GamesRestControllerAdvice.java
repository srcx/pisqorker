package cz.srnet.pisqorker.rest;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
final class GamesRestControllerAdvice {

	@ResponseBody
	@ExceptionHandler(GameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@NonNull
	String gameNotFoundHandler(@NonNull GameNotFoundException e) {
		return Objects.requireNonNull(e.getMessage());
	}

}
