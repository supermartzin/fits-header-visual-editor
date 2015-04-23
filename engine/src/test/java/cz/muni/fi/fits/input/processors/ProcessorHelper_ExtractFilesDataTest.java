package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Tests for extraction of files input data
 * in {@link CmdArgumentsProcessorHelper} class
 *
 * @author Martin Vrábel
 * @version 1.1
 */
public class ProcessorHelper_ExtractFilesDataTest {

    private static final Path FILE_PATH = Paths.get("test-files.in");
    private static final Path DIR_PATH = Paths.get("test-files_dir.in");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Files.createFile(FILE_PATH);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    public void testExtractFilesData_NullFilePath() throws Exception {
        exception.expect(IllegalArgumentException.class);
        CmdArgumentsProcessorHelper.extractFilesData(null);
    }

    @Test
    public void testExtractFilesData_FilePathDoesNotExists() throws Exception {
        Path path = Paths.get("files_not_exists.in");

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("does not exist");
        CmdArgumentsProcessorHelper.extractFilesData(path.toString());
    }

    @Test
    public void testExtractFilesData_FilePathIsDirectory() throws Exception {
        Files.createDirectory(DIR_PATH);
        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is not a file");
        try {
            CmdArgumentsProcessorHelper.extractFilesData(DIR_PATH.toString());
        }
        finally {
            Files.deleteIfExists(DIR_PATH);
        }
    }

    @Test
    public void testExtractFilesData_FileIsEmpty() throws Exception {
        Collection<File> fitsFiles = CmdArgumentsProcessorHelper.extractFilesData(FILE_PATH.toString());

        assertTrue(fitsFiles.isEmpty());
    }

    @Test
    public void testExtractFilesData_OnlyCommentedPaths() throws Exception {
        List<String> fitsFilesLines = Arrays.asList("# sample1.fits", "#sample2.fits", "### sample3.fits");
        Files.write(FILE_PATH, fitsFilesLines);

        HashSet<File> fitsFiles = new HashSet<>(CmdArgumentsProcessorHelper.extractFilesData(FILE_PATH.toString()));

        assertTrue(fitsFiles.size() == 0);
    }

    @Test
    public void testExtractFilesData_CorrectFile() throws Exception {
        List<String> fitsFilesLines = Arrays.asList("sample1.fits", "#sample2.fits", "sample3.fits", "##sample4.fits", "sample5.fits");
        Files.write(FILE_PATH, fitsFilesLines);

        HashSet<File> fitsFiles = new HashSet<>(CmdArgumentsProcessorHelper.extractFilesData(FILE_PATH.toString()));

        assertTrue(fitsFiles.size() == 3);
    }
}
