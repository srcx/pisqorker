package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public interface Games {

	@NonNull
	Game newGame(@NonNull Rules rules);

}
