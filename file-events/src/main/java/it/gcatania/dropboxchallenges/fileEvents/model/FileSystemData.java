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

    public final String fullPath;

    public final String[] pathComponents;

    public final String name;

    public final String[] containingFolders;

    private static String DIRECTORY_HASH = "-";

    public FileSystemData(String path)
    {
        fullPath = path;
        pathComponents = SEP_PATTERN.split(path);
        int numParts = pathComponents.length;
        containingFolders = Arrays.copyOf(pathComponents, numParts - 1);
        name = pathComponents[numParts - 1];
    }

    public static FileSystemData from(String path, String hash)
    {
        return DIRECTORY_HASH.equals(hash) ? new DirectoryData(path) : new FileData(path, hash);
    }

    /**
     * @param path the path to check
     * @return true if this file system data is contained in the input path, false otherwise
     */
    public boolean isContainedIn(String path)
    {
        return fullPath.startsWith(path);
    }

    /**
     * @param other another file system data
     * @return true if this file system data and the input one have the same type, false otherwise
     */
    public abstract boolean sameType(FileSystemData other);

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return fullPath.hashCode();
    }

    public boolean samePathAs(FileSystemData other)
    {
        return fullPath.equals(other.fullPath);
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
            return other.fullPath.equals(fullPath);
        }
        return false;
    }

}
