package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

public interface Game {

	@NonNull
	Moves moves();

	@NonNull
	GameState state();

}
