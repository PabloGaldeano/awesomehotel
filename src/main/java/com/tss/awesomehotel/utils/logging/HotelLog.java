package com.tss.awesomehotel.utils.logging;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HotelLog
{
    /**
     * Dictionary of all of the loggers created
     */
    private static volatile HashMap<String, Logger> fileLoggers = new HashMap<>();

    /**
     * The logs folder location relative to {@link #homeDirFromConstant}
     */
    private static final String logsFolder = "Starsol".concat(File.separator).concat("logs").concat(File.separator);

    /**
     * The default log extension
     */
    private static final String logsExtension = ".log";

    /**
     * The absolute path where the logs will be placed
     */
    private static final String homeDirFromConstant = "user.home";

    /**
     * The formatter to use to format the logs.
     */
    private static HotelLogFormatter logFormatter = null;

    /**
     * This method will retrieve the logger associate with the file received by
     * parameters, if the logger exist in the map, it will return it but if it does
     * not a Logger instance will be created using the {@link #logFormatter}.
     *
     * @param logFile The name of the file to get teh logger for.
     * @return The Logger instance, null otherwise
     */
    private static synchronized Logger getLoggerFor(String logFile)
    {
        Logger logger = null;
        if (logFile != null)
        {
            logger = fileLoggers.get(logFile);
            if (logger == null)
            {
                logger = saveLoggerForFile(logFile);
            }
        }
        return logger;
    }

    private static Logger saveLoggerForFile(String logFile)
    {
        Logger fileLogger;
        try
        {
            fileLogger = generateLoggerFor(logFile);
            fileLoggers.put(logFile, fileLogger);
        } catch (Exception ex)
        {
            ex.printStackTrace(System.err);
            fileLogger = null;
        }
        return fileLogger;
    }

    private static Logger generateLoggerFor(String logFile) throws IOException
    {
        Logger fileLogger = Logger.getLogger(logFile);
        fileLogger.addHandler(generateFileHandlerFor(logFile));
        return fileLogger;
    }

    private static FileHandler generateFileHandlerFor(String logFile) throws IOException
    {
        File logFolderBase = new File(retrieveLogsPath());
        File testFile = new File(logFolderBase, logFile.concat(logsExtension));
        if (!testFile.exists())
        {
            testFile.getParentFile().mkdirs();
            testFile.createNewFile();
        }
        FileHandler logFileHandler = new FileHandler(testFile.getCanonicalPath(), true);

        logFileHandler.setFormatter((logFormatter == null) ? new HotelLogFormatter() : logFormatter);

        return logFileHandler;
    }

    private static String retrieveLogsPath()
    {
        return System.getProperty(homeDirFromConstant).concat(File.separator).concat(logsFolder);
    }

    /**
     * This method will close the handler associated with the file given only if it
     * exists and it is not null.
     *
     * @param key The name of the file whose logger wants to be released.
     */
    public static void releaseLog(String key)
    {
        synchronized (fileLoggers)
        {
            if (key != null && fileLoggers.containsKey(key))
            {
                Handler[] handlers = fileLoggers.remove(key).getHandlers();
                for (Handler handler : handlers)
                {
                    handler.close();
                }
            }
        }
    }

    /**
     * This method is used to log a composed message in the given file, normally a set of message and exception.
     *
     * @param fileName
     * @param level
     * @param errorMessage
     * @param ex
     */
    public static void logComposedMessageInFile(String fileName, Level level, String errorMessage, Throwable ex)
    {
        Logger logger = getLoggerFor(fileName);

        logger.log(level, errorMessage, new Object[]
                {ex});
    }


    /**
     * Method used to log a message in the file by using its logger
     *
     * @param fileName
     * @param level
     * @param message
     */
    public static void logMessageInFile(String fileName, Level level, String message)
    {
        Logger logger = getLoggerFor(fileName);
        logger.log(level, message);
    }

}
