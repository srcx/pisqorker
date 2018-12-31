package cz.srnet.pisqorker.game.transfer;

import org.springframework.lang.NonNull;

public interface TransferableIn<T> {

	@NonNull
	T transferIn();

}
