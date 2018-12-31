package cz.srnet.pisqorker.spring;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.util.StdConverter;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.Rules;
import cz.srnet.pisqorker.game.TransferableGame;
import cz.srnet.pisqorker.game.TransferableRules;

@Component
final class JacksonMappings implements Jackson2ObjectMapperBuilderCustomizer {

	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder.deserializerByType(Rules.class,
				new StdDelegatingDeserializer<>(new StdConverter<TransferableRules, Rules>() {

					@Override
					public Rules convert(TransferableRules value) {
						return value.transferIn();
					}

				}));
		jacksonObjectMapperBuilder.serializerByType(Game.class,
				new StdDelegatingSerializer(new StdConverter<Game, TransferableGame>() {

					@Override
					public TransferableGame convert(Game value) {
						return value.transferOut();
					}

				}));
	}

}
