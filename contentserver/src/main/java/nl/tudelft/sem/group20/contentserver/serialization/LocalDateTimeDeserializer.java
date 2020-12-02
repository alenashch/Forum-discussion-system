package nl.tudelft.sem.group20.contentserver.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctxt) throws
        IOException {

        return LocalDateTime.parse(parser.getValueAsString(),
            DateTimeFormatter.ISO_DATE_TIME);
    }
}