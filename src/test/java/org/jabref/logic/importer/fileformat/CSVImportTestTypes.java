package org.jabref.logic.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.jabref.model.entry.BibEntry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class CSVImportTestTypes {

    @SuppressWarnings("unused")
    private static Stream<String[]> types() {
        return Arrays.stream(new String[][] {
                {"1", "book"},
                {"2", "booklet"},
                {"3", "proceedings"},
                {"5", "inbook"},
                {"6", "inproceedings"},
                {"7", "article"},
                {"8", "manual"},
                {"9", "phdthesis"},
                {"10", "misc"},
                {"13", "techreport"},
                {"14", "unpublished"}});
    }

    @ParameterizedTest
    @MethodSource("types")
    public void importConvertsToCorrectBibType(String csvType, String bibtexType) throws IOException {
        String CSVInput = "BibliographyType,Author,Title,Year,Custom3\n"
                + Integer.parseInt(csvType) + ",\"Max Mustermann\",\"Java tricks\",2016,\"java\"";

        List<BibEntry> bibEntries = new CSVImporter().importDatabase(new BufferedReader(new StringReader(CSVInput)))
                .getDatabase()
                .getEntries();

        BibEntry entry = new BibEntry();
        entry.setField("author", "Max Mustermann");
        entry.setField("keywords", "java");
        entry.setField("title", "Java tricks");
        entry.setField("year", "2016");
        entry.setType(bibtexType);

        Assertions.assertEquals(Collections.singletonList(entry), bibEntries);
    }
}
