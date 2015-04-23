package cz.muni.fi.fits.utils.loaders;

import cz.muni.fi.fits.exceptions.ConfigurationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class for loading configuration of FITS Header Editor program
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class ConfigurationLoader {

    /**
     * Loads configuration of FITS Header Editor from provided <code>properties</code>
     *
     * @param properties                {@link Properties} object with user defined properties
     * @return                          map of <code>key:value</code> configuration pairs
     * @throws ConfigurationException   when there some error occurs during configuration loading
     */
    public static Map<String, String> loadConfiguration(Properties properties) throws ConfigurationException {
        Map<String, String> configuration = new HashMap<>();

        String outputWriter = properties.getProperty("output.writer");
        if (outputWriter == null)
            throw new ConfigurationException("Cannot find property 'output.writer' in properties file");

        switch (outputWriter) {
            case "ConsoleOutputWriter":
                configuration.put("outputWriter", "console");
                break;

            case "FileOutputWriter":
                String outputFilepath = properties.getProperty("output.file");
                if (outputFilepath == null)
                    throw new ConfigurationException("Cannot find property 'output.file' in properties file");
                configuration.put("outputWriter", "file");
                configuration.put("outputFile", outputFilepath);
                break;

            case "FileConsoleOutputWriter":
                outputFilepath = properties.getProperty("output.file");
                if (outputFilepath == null)
                    throw new ConfigurationException("Cannot find property 'output.file' in properties file");
                configuration.put("outputWriter", "file-console");
                configuration.put("outputFile", outputFilepath);
                break;

            default:
                throw new ConfigurationException("Unknown value for 'output.writer' property");
        }

        return configuration;
    }
}
