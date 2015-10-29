package cz.muni.fi.fits.gui.services;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO insert description
 *
 * @author Martin Vrábel - © 2015 FITS-HeaderVisualEditor
 * @version 1.0
 */
public class ExecutionTask extends Task<Void> {

    private final List<String> _inputDataArguments;
    private final int _numberOfFiles;
    private final String _engineFilepath;

    public ExecutionTask(List<String> inputDataArguments, int numberOfFiles, String engineFilepath) {
        _inputDataArguments = inputDataArguments;
        _numberOfFiles = numberOfFiles;
        _engineFilepath = engineFilepath;
    }

    @Override
    protected Void call() throws Exception {
        if (_inputDataArguments == null || _inputDataArguments.isEmpty())
            throw new RuntimeException("Input Data arguments are not set!");

        //File file = new File(MainApp.class.getResource("/engine/engine.jar").toURI());

        List<String> arguments = new LinkedList<>();
        arguments.add("java");
        arguments.add("-jar");
        arguments.add(_engineFilepath);
        arguments.addAll(_inputDataArguments);

        Process process = new ProcessBuilder(arguments)
                                .inheritIO()
                                .redirectError(ProcessBuilder.Redirect.PIPE)
                                .redirectInput(ProcessBuilder.Redirect.PIPE)
                                .start();

        String line;
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = br.readLine()) != null) {
                updateProgress(++counter, _numberOfFiles + 2);
                System.out.println(line);
            }
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            while ((line = br.readLine()) != null) {
                updateProgress(++counter, _numberOfFiles + 2);
                System.err.println(line);
            }
        }

        updateProgress(_numberOfFiles + 2, _numberOfFiles + 2);

        process.waitFor();
        return null;
    }
}
