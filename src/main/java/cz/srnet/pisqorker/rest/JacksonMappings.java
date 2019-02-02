package cz.srnet.pisqorker.rest;

import java.util.Objects;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

import cz.srnet.pisqorker.game.Coordinates;
import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.HumanPlayer;
import cz.srnet.pisqorker.game.Player;
import cz.srnet.pisqorker.game.Players;
import cz.srnet.pisqorker.game.Rules;
import cz.srnet.pisqorker.game.TransferableCoordinates;
import cz.srnet.pisqorker.game.TransferableGame;
import cz.srnet.pisqorker.game.TransferablePlayer;
import cz.srnet.pisqorker.game.TransferablePlayers;
import cz.srnet.pisqorker.game.TransferableRules;
import cz.srnet.pisqorker.users.TransferableUser;
import cz.srnet.pisqorker.users.User;

@Component
final class JacksonMappings implements Jackson2ObjectMapperBuilderCustomizer {

	private final @NonNull AutowireCapableBeanFactory beanFactory;

	public JacksonMappings(@NonNull AutowireCapableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		both(Rules.class, TransferableRules.class, jacksonObjectMapperBuilder);

		serializer(Game.class, TransferableGame.class, jacksonObjectMapperBuilder);

		serializer(HumanPlayer.class, TransferablePlayer.class, jacksonObjectMapperBuilder);
		deserializer(Player.class, TransferablePlayer.class, jacksonObjectMapperBuilder);

		both(User.class, TransferableUser.class, jacksonObjectMapperBuilder);

		both(Players.class, TransferablePlayers.class, jacksonObjectMapperBuilder);

		both(Coordinates.class, TransferableCoordinates.class, jacksonObjectMapperBuilder);
	}

	private <C extends TransferableOut<T>, T extends TransferableIn<C>> void both(@NonNull Class<C> clazz,
			@NonNull Class<T> transferable, Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		serializer(clazz, transferable, jacksonObjectMapperBuilder);
		deserializer(clazz, transferable, jacksonObjectMapperBuilder);
	}

	private <C, T extends TransferableIn<C>> void deserializer(@NonNull Class<C> clazz, @NonNull Class<T> transferable,
			Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder.deserializerByType(clazz, new StdDelegatingDeserializer<>(new Converter<T, C>() {

			@Override
			public C convert(T value) {
				beanFactory.autowireBean(Objects.requireNonNull(value));
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
