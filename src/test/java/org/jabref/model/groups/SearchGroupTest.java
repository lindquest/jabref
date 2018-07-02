package org.jabref.model.groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jabref.model.entry.BibEntry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchGroupTest {


    @Test
    public void containsFindsWordWithRegularExpression() {
        SearchGroup group = new SearchGroup("myExplicitGroup", GroupHierarchyType.INDEPENDENT, "anyfield=rev*", true, true);
        BibEntry entry = new BibEntry();
        entry.addKeyword("review", ',');

        assertTrue(group.contains(entry));
    }

    @Test
    public void containsAllFindEntriesWithRegularExpression() {
        SearchGroup group = new SearchGroup("myExplicitGroup", GroupHierarchyType.INDEPENDENT, "anyfield=tab*", true, true);
        BibEntry entry1 = new BibEntry();
        BibEntry entry2 = new BibEntry();
        List<BibEntry> entries = new ArrayList<>();
        entry1.addKeyword("tab", ',');
        entry2.addKeyword("table", ',');
        entries.add(entry1);
        entries.add(entry2);

        assertTrue(group.containsAll(entries));
    }

    @Test
    public void containsAnyFindAEntryWithRegularExpression() {
        SearchGroup group = new SearchGroup("myExplicitGroup", GroupHierarchyType.INDEPENDENT, "anyfield=var*", true, true);
        BibEntry entry1 = new BibEntry();
        BibEntry entry2 = new BibEntry();
        BibEntry entry3 = new BibEntry();
        List<BibEntry> entries = new ArrayList<>();
        entry1.addKeyword("van", ',');
        entry2.addKeyword("tab", ',');
        entry3.addKeyword("variable", ',');
        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);

        assertTrue(group.containsAny(entries));
    }

    @Test
    public void deepCopyShouldBeEqual() {
        SearchGroup group = new SearchGroup("myExplicitGroup", GroupHierarchyType.INDEPENDENT, "anyfield=var*", true, true);
        AbstractGroup group2 = group.deepCopy();

        assertTrue(group.equals(group2));
    }

    @Test
    public void hashCodeMatchesObjectHash() {
        SearchGroup group = new SearchGroup("myExplicitGroup", GroupHierarchyType.INDEPENDENT, "anyfield=var*", true, true);

        int expected = Objects.hash(group.getName(), group.getHierarchicalContext(), group.getSearchExpression(), group.isCaseSensitive(), group.isRegularExpression());
        assertEquals(expected, group.hashCode());
    }
}
