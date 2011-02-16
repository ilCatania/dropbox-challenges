package it.gcatania.dropboxchallenges.fileEvents.model;

import java.io.File;


/**
 * @author gcatania
 */
public abstract class FileSystemData
{

    public static final String SEPARATOR = File.separator;

    public final String fullPath;

    public final String name;

    public final String parentFolder;

    public FileSystemData(String path)
    {
        fullPath = path;
        int lastSeparatorPos = path.lastIndexOf(SEPARATOR);
        parentFolder = path.substring(0, lastSeparatorPos);
        name = path.substring(lastSeparatorPos + 1);
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

    public boolean samePath(FileSystemData other)
    {
        return fullPath.equals(other.fullPath);
    }

    public boolean sameName(String path)
    {
        return path.endsWith(SEPARATOR + name);
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
