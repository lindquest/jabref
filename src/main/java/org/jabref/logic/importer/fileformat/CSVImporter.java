package org.jabref.logic.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.util.FileType;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.BibtexEntryTypes;
import org.jabref.model.entry.EntryType;

import org.apache.commons.csv.*;

/**
 * Importer for OpenOffice CSV files (exported with JabRef).
 */
public class CSVImporter extends Importer {

    private static final Pattern START_PATTERN = Pattern.compile("[.*,]?BibliographyType[,.*]?");

    private static final List<String> FIELDS_TO_REMOVE = Arrays.asList("BibliographyType", "Identifier",
            "Organizations", "Custom1", "Custom2", "Custom3", "Custom4", "Custom5");

    private static final Map<Integer, EntryType> ENTRY_TYPES = createTypesMap();

    // Values from GetOpenOfficeType.format()
    private static Map<Integer, EntryType> createTypesMap() {
        Map<Integer, EntryType> types = new HashMap<>();
        types.put(1, BibtexEntryTypes.BOOK);
        types.put(2, BibtexEntryTypes.BOOKLET);
        types.put(3, BibtexEntryTypes.PROCEEDINGS);
        types.put(5, BibtexEntryTypes.INBOOK);
        types.put(6, BibtexEntryTypes.INPROCEEDINGS);
        types.put(7, BibtexEntryTypes.ARTICLE);
        types.put(8, BibtexEntryTypes.MANUAL);
        // Type of thesis is lost during export, so we guess
        types.put(9, BibtexEntryTypes.PHDTHESIS);
        types.put(10, BibtexEntryTypes.MISC);
        types.put(13, BibtexEntryTypes.TECHREPORT);
        types.put(14, BibtexEntryTypes.UNPUBLISHED);
        return types;
    }

    @Override
    public String getName() {
        return "CSV";
    }

    @Override
    public FileType getFileType() {
        return FileType.OO_LO;
    }

    @Override
    public String getDescription() {
        return "Importer for OpenOffice CSV files.";
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader) throws IOException {
        String str;
        while ((str = reader.readLine()) != null) {
            if (START_PATTERN.matcher(str).find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ParserResult importDatabase(BufferedReader input) throws IOException {
        Objects.requireNonNull(input);

        List<BibEntry> bibItems = new ArrayList<>();
        Iterable<CSVRecord> entries = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(input);

        for (CSVRecord entry : entries) {
            BibEntry bibEntry = new BibEntry();

            Map<String, String> fields = entry.toMap();
            System.out.println(fields.toString());
            fields.keySet().removeAll(FIELDS_TO_REMOVE);
            fields.put("bibtexkey", entry.get("Identifier"));
            fields.put("organization", entry.get("Organizations"));
            fields.put("abstract", entry.get("Custom1"));
            fields.put("comment", entry.get("Custom2"));
            fields.put("keywords", entry.get("Custom3"));
            fields.put("file", entry.get("Custom4"));
            fields.put("key", entry.get("Custom5"));

            bibEntry.setType(ENTRY_TYPES.getOrDefault(Integer.parseInt(entry.get("BibliographyType")), BibtexEntryTypes.MISC));
            bibEntry.setField(fields);
            bibItems.add(bibEntry);
        }

        return new ParserResult(bibItems);
    }
}
