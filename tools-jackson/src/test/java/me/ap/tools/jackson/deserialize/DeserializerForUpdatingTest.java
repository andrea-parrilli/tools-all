package me.ap.tools.jackson.deserialize;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import me.ap.tools.lang.builder.Buildable;
import me.ap.tools.lang.builder.ToBuilderable;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeserializerForUpdatingTest {
    private DeserializerForUpdating updater;

    DeserializerForUpdatingTest() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.updater = new DeserializerForUpdating(mapper);
    }

    @Builder(toBuilder = true)
    public record TestRecord(Integer id, String name)
    implements ToBuilderable<TestRecord.TestRecordBuilder> {
        public static class TestRecordBuilder implements Buildable<TestRecord> {}
    }

    @Test
    void deserializeForUpdate_String() throws JsonProcessingException {
        TestRecord original = new TestRecord(1, "1");
        String json = """
                {"id":44}
                """;

        TestRecord updated = updater.updateFromJson(original, json);

        assertEquals(44, updated.id());
    }

    @Test
    void deserializeForUpdate_Stream() throws IOException {
        TestRecord original = new TestRecord(1, "1");
        String json = """
                {"id":44}
                """;
        var jsonStream = new ByteArrayInputStream(json.getBytes());
        TestRecord updated = updater.updateFromJson(original, jsonStream);

        assertEquals(44, updated.id());
    }
}