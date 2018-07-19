package org.jabref.logic.importer.fileformat;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class CSVImporterTestFiles {

    private static final String FILE_ENDING = ".csv";

    @SuppressWarnings("unused")
    private static Stream<String> fileNames() throws IOException {
        Predicate<String> fileName = name -> name.startsWith("CSVImporterTest")
                && name.endsWith(FILE_ENDING);
        return ImporterTestEngine.getTestFiles(fileName).stream();
    }

    @SuppressWarnings("unused")
    private static Stream<String> nonCSVfileNames() throws IOException {
        Predicate<String> fileName = name -> !name.startsWith("CSVImporterTest");
        return ImporterTestEngine.getTestFiles(fileName).stream();
    }

    @ParameterizedTest
    @MethodSource("fileNames")
    public void testIsRecognizedFormat(String fileName) throws IOException {
        ImporterTestEngine.testIsRecognizedFormat(new CSVImporter(), fileName);
    }

    @ParameterizedTest
    @MethodSource("nonCSVfileNames")
    public void testIsNotRecognizedFormat(String fileName) throws IOException {
        ImporterTestEngine.testIsNotRecognizedFormat(new CSVImporter(), fileName);
    }

    @ParameterizedTest
    @MethodSource("fileNames")
    public void testImportEntries(String fileName) throws Exception {
        ImporterTestEngine.testImportEntries(new CSVImporter(), fileName, FILE_ENDING);
    }
}
