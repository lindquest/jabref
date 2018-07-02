package org.jabref.model.groups;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.jabref.model.entry.BibEntry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutomaticPersonsGroupTest {

    @Test
    public void createSubgroupsFromAuthorLastname() throws Exception {
        AutomaticPersonsGroup personsGroup = new AutomaticPersonsGroup("Author", GroupHierarchyType.INDEPENDENT, "author");
        BibEntry entry = new BibEntry().withField("author", "John Tailor");

        Set<GroupTreeNode> expected = new HashSet<>();
        expected.add(GroupTreeNode.fromGroup(new WordKeywordGroup("Tailor", GroupHierarchyType.INDEPENDENT, "author", "Tailor", true, ' ', true)));
        assertEquals(expected, personsGroup.createSubgroups(entry));
    }

    @Test
    public void hashCodeMatchesObjectHash() throws Exception {
        AutomaticPersonsGroup personsGroup = new AutomaticPersonsGroup("Author", GroupHierarchyType.INDEPENDENT, "author");

        int expected = Objects.hash(personsGroup.getField());
        assertEquals(expected, personsGroup.hashCode());
    }

    @Test
    public void deepCopyShouldBeEqual() throws Exception {
        AutomaticPersonsGroup group1 = new AutomaticPersonsGroup("Author", GroupHierarchyType.INDEPENDENT, "author");
        AbstractGroup group2 = group1.deepCopy();

        assertTrue(group1.equals(group2));
    }
}
