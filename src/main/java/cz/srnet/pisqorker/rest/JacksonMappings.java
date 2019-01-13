package cz.srnet.pisqorker.rest;

import java.util.function.Consumer;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.HumanPlayer;
import cz.srnet.pisqorker.game.Player;
import cz.srnet.pisqorker.game.Players;
import cz.srnet.pisqorker.game.Rules;
import cz.srnet.pisqorker.game.TransferableGame;
import cz.srnet.pisqorker.game.TransferablePlayer;
import cz.srnet.pisqorker.game.TransferablePlayers;
import cz.srnet.pisqorker.game.TransferableRules;
import cz.srnet.pisqorker.users.TransferableUser;
import cz.srnet.pisqorker.users.User;
import cz.srnet.pisqorker.users.Users;

@Component
final class JacksonMappings implements Jackson2ObjectMapperBuilderCustomizer {

	private final @NonNull Users users;

	public JacksonMappings(@NonNull Users users) {
		this.users = users;
	}

	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		deserializer(Rules.class, TransferableRules.class, null, jacksonObjectMapperBuilder);
		serializer(Rules.class, TransferableRules.class, jacksonObjectMapperBuilder);
		serializer(Game.class, TransferableGame.class, jacksonObjectMapperBuilder);
		deserializer(Player.class, TransferablePlayer.class, t -> t.injectUsers(users), jacksonObjectMapperBuilder);
		serializer(HumanPlayer.class, TransferablePlayer.class, jacksonObjectMapperBuilder);
		serializer(User.class, TransferableUser.class, jacksonObjectMapperBuilder);
		deserializer(User.class, TransferableUser.class, t -> t.injectUsers(users), jacksonObjectMapperBuilder);
		deserializer(Players.class, TransferablePlayers.class, null, jacksonObjectMapperBuilder);
		serializer(Players.class, TransferablePlayers.class, jacksonObjectMapperBuilder);
	}

	private <C, T extends TransferableIn<C>> void deserializer(@NonNull Class<C> clazz, @NonNull Class<T> transferable,
			@Nullable Consumer<T> convertCallback, Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder.deserializerByType(clazz, new StdDelegatingDeserializer<>(new Converter<T, C>() {

			@Override
			public C convert(T value) {
				if (convertCallback != null) {
					convertCallback.accept(value);
				}
				return value.transferIn();
			}

			@Override
			public JavaType getInputType(TypeFactory typeFactory) {
				return typeFactory.constructType(transferable);
			}

			@Override
			public JavaType getOutputType(TypeFactory typeFactory) {
				return typeFactory.constructType(clazz);
			}

		}));
	}

	private <C extends TransferableOut<T>, T> void serializer(@NonNull Class<C> clazz, @NonNull Class<T> transferable,
			Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder.serializerByType(clazz, new StdDelegatingSerializer(new Converter<C, T>() {

			@Override
			public T convert(C value) {
				return value.transferOut();
			}

			@Override
			public JavaType getInputType(TypeFactory typeFactory) {
				return typeFactory.constructType(clazz);
			}

			@Override
			public JavaType getOutputType(TypeFactory typeFactory) {
				return typeFactory.constructType(transferable);
			}

		}));
	}

}
