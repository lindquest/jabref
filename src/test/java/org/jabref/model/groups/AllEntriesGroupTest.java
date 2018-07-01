package org.jabref.model.groups;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllEntriesGroupTest {

    @Test
    public void deepCopyShouldBeEqual() throws Exception {
        AllEntriesGroup group1 = new AllEntriesGroup("All entries");
        AbstractGroup group2 = group1.deepCopy();

        assertTrue(group1.equals(group2));
    }
}
