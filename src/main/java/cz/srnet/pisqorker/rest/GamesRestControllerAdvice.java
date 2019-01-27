package cz.srnet.pisqorker.rest;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cz.srnet.pisqorker.game.ForbiddenPlayerException;
import cz.srnet.pisqorker.game.IllegalCoordinatesException;
import cz.srnet.pisqorker.game.IllegalGameSetupException;
import cz.srnet.pisqorker.game.IllegalGameStateException;
import cz.srnet.pisqorker.game.IllegalPieceException;

@ControllerAdvice
final class GamesRestControllerAdvice {

	@ResponseBody
	@ExceptionHandler(GameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@NonNull
	String gameNotFoundHandler(@NonNull GameNotFoundException e) {
		return Objects.requireNonNull(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(IllegalCoordinatesException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@NonNull
	String illegalCoordinatesHandler(@NonNull IllegalCoordinatesException e) {
		return Objects.requireNonNull(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(IllegalPieceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@NonNull
	String illegalPieceHandler(@NonNull IllegalPieceException e) {
		return Objects.requireNonNull(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(IllegalGameSetupException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@NonNull
	String illegalGameSetupHandler(@NonNull IllegalGameSetupException e) {
		return Objects.requireNonNull(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(ForbiddenPlayerException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@NonNull
	String forbiddenPlayerHandler(@NonNull ForbiddenPlayerException e) {
		return Objects.requireNonNull(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(IllegalGameStateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@NonNull
	String illegalGameStateHandler(@NonNull IllegalGameStateException e) {
		return Objects.requireNonNull(e.getMessage());
	}

}
