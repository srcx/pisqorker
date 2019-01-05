package cz.srnet.pisqorker.rest;

import org.springframework.lang.NonNull;

public interface TransferableIn<T> {

	@NonNull
	T transferIn();

}
