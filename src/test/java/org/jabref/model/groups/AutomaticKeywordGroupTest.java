package org.jabref.model.groups;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.jabref.model.entry.BibEntry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutomaticKeywordGroupTest {

    @Test
    public void createSubgroupsForTwoKeywords() throws Exception {
        AutomaticKeywordGroup keywordsGroup = new AutomaticKeywordGroup("Keywords", GroupHierarchyType.INDEPENDENT, "keywords", ',', '>');
        BibEntry entry = new BibEntry().withField("keywords", "A, B");

        Set<GroupTreeNode> expected = new HashSet<>();
        expected.add(GroupTreeNode.fromGroup(new WordKeywordGroup("A", GroupHierarchyType.INCLUDING, "keywords", "A", true, ',', true)));
        expected.add(GroupTreeNode.fromGroup(new WordKeywordGroup("B", GroupHierarchyType.INCLUDING, "keywords", "B", true, ',', true)));
        assertEquals(expected, keywordsGroup.createSubgroups(entry));
    }

    @Test
    public void hashCodeMatchesObjectHash() throws Exception {
        AutomaticKeywordGroup keywordsGroup = new AutomaticKeywordGroup("Keywords", GroupHierarchyType.INDEPENDENT, "keywords", ',', '>');

        int expected = Objects.hash(keywordsGroup.getKeywordDelimiter(), keywordsGroup.getField());
        assertEquals(expected, keywordsGroup.hashCode());
    }

    @Test
    public void deepCopyShouldBeEqual() throws Exception {
        AutomaticKeywordGroup group1 = new AutomaticKeywordGroup("Keywords", GroupHierarchyType.INDEPENDENT, "keywords", ',', '>');
        AbstractGroup group2 = group1.deepCopy();

        assertTrue(group1.equals(group2));
    }
}
