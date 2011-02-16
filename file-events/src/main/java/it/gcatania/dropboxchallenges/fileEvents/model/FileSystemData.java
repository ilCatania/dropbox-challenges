package it.gcatania.dropboxchallenges.fileEvents.model;

import java.io.File;


/**
 * @author gcatania
 */
public abstract class FileSystemData
{

    protected static final String SEPARATOR = File.separator;

    public final String fullPath;

    public final String name;

    public static String DIRECTORY_HASH = "-";

    public FileSystemData(String path)
    {
        fullPath = path;
        name = path.substring(path.lastIndexOf(SEPARATOR) + 1);
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
