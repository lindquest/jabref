package org.jabref.model.groups;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;

import org.jabref.logic.auxparser.DefaultAuxParser;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.util.DummyFileUpdateMonitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TexGroupTest {

    @Test
    public void containsReturnsTrueForEntryInAux() throws Exception {
        Path auxFile = Paths.get(TexGroupTest.class.getResource("paper.aux").toURI());
        TexGroup group = new TexGroup("paper", GroupHierarchyType.INDEPENDENT, auxFile, new DefaultAuxParser(new BibDatabase()), new DummyFileUpdateMonitor());
        BibEntry inAux = new BibEntry();
        inAux.setCiteKey("Darwin1888");

        assertTrue(group.contains(inAux));
    }

    @Test
    public void containsReturnsTrueForEntryNotInAux() throws Exception {
        Path auxFile = Paths.get(TexGroupTest.class.getResource("paper.aux").toURI());
        TexGroup group = new TexGroup("paper", GroupHierarchyType.INDEPENDENT, auxFile, new DefaultAuxParser(new BibDatabase()), new DummyFileUpdateMonitor());
        BibEntry notInAux = new BibEntry();
        notInAux.setCiteKey("NotInAux2017");

        assertFalse(group.contains(notInAux));
    }

    @Test
    public void deepCopyShouldBeEqual() throws Exception {
        Path auxFile = Paths.get(TexGroupTest.class.getResource("paper.aux").toURI());
        TexGroup group1 = new TexGroup("paper", GroupHierarchyType.INDEPENDENT, auxFile, new DefaultAuxParser(new BibDatabase()), new DummyFileUpdateMonitor());
        AbstractGroup group2 = group1.deepCopy();

        assertTrue(group1.equals(group2));
    }

    @Test
    public void hashCodeMatchesObjectHash() throws Exception {
        Path auxFile = Paths.get(TexGroupTest.class.getResource("paper.aux").toURI());
        TexGroup group = new TexGroup("paper", GroupHierarchyType.INDEPENDENT, auxFile, new DefaultAuxParser(new BibDatabase()), new DummyFileUpdateMonitor());

        int expected = Objects.hash(Objects.hash(group.getName(), group.getDescription(), group.getHierarchicalContext()), auxFile);
        assertEquals(expected, group.hashCode());
    }

    @Test
    public void groupToStringReturnsExpectedText() throws Exception {
        Path auxFile = Paths.get(TexGroupTest.class.getResource("paper.aux").toURI());
        DefaultAuxParser auxParser = new DefaultAuxParser(new BibDatabase());
        DummyFileUpdateMonitor fileMonitor = new DummyFileUpdateMonitor();
        TexGroup group = new TexGroup("paper", GroupHierarchyType.INDEPENDENT, auxFile, auxParser, fileMonitor);
        BibEntry dummyEntry = new BibEntry();
        group.contains(dummyEntry);
        Set<String> keysUsedInAux = auxParser.parse(auxFile).getUniqueKeys();

        String expected = "TexGroup{" +
                "filePath=" + auxFile +
                ", keysUsedInAux=" + keysUsedInAux +
                ", auxParser=" + auxParser +
                ", fileMonitor=" + fileMonitor +
                "} " +
                "AbstractGroup{" +
                "name='" + group.getName() + '\'' +
                ", context=" + group.getHierarchicalContext() +
                ", color=" + group.getColor() +
                ", isExpanded=" + group.isExpanded() +
                ", description=" + group.getDescription() +
                ", iconName=" + group.getIconName() +
                '}';
        assertEquals(expected, group.toString());

    }
}
