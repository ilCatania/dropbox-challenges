package it.gcatania.dropboxchallenges.fileEvents.model;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;


/**
 * @author gcatania
 */
public class FilePath
{

    private static final String SEPARATOR = File.separator;

    private static final Pattern SEP_PATTERN = Pattern.compile(SEPARATOR);

    public final String fileName;

    public final String[] containingFolders;

    private final int hashCode;

    public FilePath(String pathStr)
    {
        String[] parts = SEP_PATTERN.split(pathStr);
        int numParts = parts.length;
        containingFolders = Arrays.copyOf(parts, numParts - 1);
        fileName = parts[numParts - 1];
        hashCode = pathStr.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return hashCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FilePath)
        {
            FilePath other = (FilePath) obj;
            return other.fileName.equals(fileName) && other.containingFolders.equals(containingFolders);
        }
        return false;
    }

}
