package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

public interface MakeMove {

	@NonNull
	Move to(@NonNull Coordinates xy);

	@NonNull
	Move to(int x, int y);

	@NonNull
	MakeMove as(@NonNull Player player);

}
