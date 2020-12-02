package nl.tudelft.sem.group20.contentserver.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator,
                          SerializerProvider serializers) throws
        IOException {

        generator.writeString("" + value.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}