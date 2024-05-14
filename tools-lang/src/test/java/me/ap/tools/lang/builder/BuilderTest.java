package me.ap.tools.lang.builder;

import lombok.Builder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuilderTest {
    /** A record that is {@link ToBuilderable} for testing.
     *
     * @param id an {@link Integer}
     * @param description a {@link String}
     */
    @Builder(toBuilder = true)
    public record TestRecord(Integer id, String description)
    implements ToBuilderable<TestRecord.TestRecordBuilder> {
        public static class TestRecordBuilder implements Buildable<TestRecord> {}
    }

    @Test
    void toBuilderable_Builder_Contract() {
        var aRecord = new TestRecord(1, "1");
        assertEquals(aRecord, aRecord.toBuilder().build());
    }

    @Test
    void builderModifiesAttributes() {
        var aRecord = new TestRecord(1, "1");
        assertEquals(2, aRecord.toBuilder().id(2).build().id());
    }
}