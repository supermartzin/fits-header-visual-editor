package cz.muni.fi.fits.gui.services;

import cz.muni.fi.fits.gui.listeners.OutputListener;
import cz.muni.fi.fits.gui.utils.dialogs.ErrorDialog;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO insert description
 *
 * @author Martin Vrábel - © 2015 FITS-HeaderVisualEditor
 * @version 1.0
 */
public class ExecutionService extends Service {

    private final Task _task;

    private OutputListener _outputListener;

    public ExecutionService(final List<String> inputDataArguments,
                            final String engineFilepath,
                            final int numberOfFiles) {
        if (engineFilepath == null)
            throw new IllegalArgumentException("FITS Header Editor Engine JAR filepath is not set");
        if (inputDataArguments == null || inputDataArguments.isEmpty())
            throw new IllegalArgumentException("Input Data arguments are not set!");

        _task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // set unhandled exception handler
                Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
                    ErrorDialog.show("Error", "exception", "Error: " + e.getMessage()); // TODO
                });

                //String line;
                int counter = 0;
                int maxProcessLength = numberOfFiles + 2;

                List<String> arguments = new LinkedList<String>() {
                    {
                        addAll(Arrays.asList("java", "-jar", engineFilepath));
                        addAll(inputDataArguments);
                    }
                };

                // set up and start engine process
                Process process = new ProcessBuilder(arguments)
                        .redirectInput(ProcessBuilder.Redirect.PIPE)
                        .redirectError(ProcessBuilder.Redirect.PIPE)
                        .start();

                ExecutorService executors = Executors.newCachedThreadPool();

                executors.submit(() -> {
                    String line;
                    // process standard output
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        while ((line = br.readLine()) != null) {
                            //updateProgress(++counter, maxProcessLength);

                            // raise onInfo event in registered listener
                            if (_outputListener != null)
                                _outputListener.onInfo(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


                executors.submit(() -> {
                    String line;
                    // process error output
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        while ((line = br.readLine()) != null) {
                            //updateProgress(++counter, maxProcessLength);

                            // raise onError event in registered listener
                            if (_outputListener != null)
                                _outputListener.onError(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                executors.shutdown();


                if (counter < maxProcessLength)
                    updateProgress(1, 1);

                process.waitFor();

                return null;
            }
        };
    }

    public void setOutputListener(OutputListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("Output listener is null");

        _outputListener = listener;
    }

    public Task getTask() {
        return _task;
    }

    @Override
    protected Task createTask() {
        return _task;
    }
}
