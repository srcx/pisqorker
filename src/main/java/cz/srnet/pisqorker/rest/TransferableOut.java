package cz.srnet.pisqorker.rest;

import org.springframework.lang.NonNull;

public interface TransferableOut<T> {

	@NonNull
	T transferOut();

}
