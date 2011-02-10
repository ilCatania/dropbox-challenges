package it.gcatania.dropboxchallenges.fileEvents.model;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;


/**
 * @author gcatania
 */
public abstract class FileSystemData
{

    private static final String SEPARATOR = File.separator;

    private static final Pattern SEP_PATTERN = Pattern.compile(SEPARATOR);

    public final String actualPath;

    protected final String[] pathComponents;

    private static String DIRECTORY_HASH = "-";

    public FileSystemData(String path)
    {
        actualPath = path;
        String[] parts = SEP_PATTERN.split(path);
        int numParts = parts.length;
        pathComponents = Arrays.copyOf(parts, numParts - 1);
    }

    public static FileSystemData from(String path, String hash)
    {
        return DIRECTORY_HASH.equals(hash) ? new DirectoryData(path) : new FileData(path, hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return actualPath.hashCode();
    }

    public boolean samePathAs(FileSystemData other)
    {
        return actualPath.equals(other.actualPath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileSystemData)
        {
            FileSystemData other = (FileSystemData) obj;
            return other.actualPath.equals(actualPath);
        }
        return false;
    }

}
