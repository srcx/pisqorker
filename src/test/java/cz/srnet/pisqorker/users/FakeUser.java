package cz.srnet.pisqorker.users;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.HumanPlayer;

public final class FakeUser implements User {

	private @NonNull Optional<Predicate<HumanPlayer>> canPlayAsProvider = Optional.empty();

	public @NonNull FakeUser _canPlayAsProvider(@NonNull Predicate<HumanPlayer> provider) {
		canPlayAsProvider = Optional.of(provider);
		return this;
	}

	@Override
	@NonNull
	public TransferableUser transferOut() {
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public String id() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean canPlayAs(@NonNull HumanPlayer player) {
		return canPlayAsProvider.orElseThrow(() -> new UnsupportedOperationException()).test(player);
	}

}
