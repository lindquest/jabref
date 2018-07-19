package org.jabref.logic.importer.fileformat;

import org.jabref.logic.util.FileType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CSVImporterTest {

    private CSVImporter importer;

    @BeforeEach
    public void setUp() throws Exception {
        importer = new CSVImporter();
    }

    @Test
    public void testGetFormatName() {
        assertEquals("CSV", importer.getName());
    }

    @Test
    public void testGetCLIId() {
        assertEquals("csv", importer.getId());
    }

    @Test
    public void testsGetExtensions() {
        assertEquals(FileType.OO_LO, importer.getFileType());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Importer for OpenOffice CSV files.", importer.getDescription());
    }
}
