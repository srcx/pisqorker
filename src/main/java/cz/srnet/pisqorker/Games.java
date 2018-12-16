package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

public interface Games {

	@NonNull
	Game newGame(@NonNull Rules rules);

}
