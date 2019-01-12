package cz.srnet.pisqorker.rest;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.HumanPlayer;
import cz.srnet.pisqorker.game.Rules;
import cz.srnet.pisqorker.game.TransferableGame;
import cz.srnet.pisqorker.game.TransferableHumanPlayer;
import cz.srnet.pisqorker.game.TransferableRules;

@Component
final class JacksonMappings implements Jackson2ObjectMapperBuilderCustomizer {

	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		deserializer(Rules.class, TransferableRules.class, jacksonObjectMapperBuilder);
		serializer(Rules.class, TransferableRules.class, jacksonObjectMapperBuilder);
		serializer(Game.class, TransferableGame.class, jacksonObjectMapperBuilder);
		serializer(HumanPlayer.class, TransferableHumanPlayer.class, jacksonObjectMapperBuilder);
	}

	private <C, T extends TransferableIn<C>> void deserializer(@NonNull Class<C> clazz, @NonNull Class<T> transferable,
			Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder.deserializerByType(clazz, new StdDelegatingDeserializer<>(new Converter<T, C>() {

			@Override
			public C convert(T value) {
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
