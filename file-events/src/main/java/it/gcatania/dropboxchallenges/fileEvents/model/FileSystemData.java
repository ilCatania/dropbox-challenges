package it.gcatania.dropboxchallenges.fileEvents.model;

import java.io.File;


/**
 * abstract wrapper class for file system paths.
 * @author gcatania
 */
public abstract class FileSystemData
{

    /**
     * file system separator.
     */
    public static final String SEPARATOR = File.separator;

    /**
     * the full path of the filesystem object.
     */
    public final String fullPath;

    /**
     * the name of the filesystem object.
     */
    public final String name;

    /**
     * the full path of the filesistem object up to the parent folder.
     */
    public final String parentFolder;

    public FileSystemData(String path)
    {
        fullPath = path;
        int lastSeparatorPos = path.lastIndexOf(SEPARATOR);
        if (lastSeparatorPos <= 0)
        {
            // handle case "parent = root"
            parentFolder = SEPARATOR;
        }
        else
        {
            parentFolder = path.substring(0, lastSeparatorPos);
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return fullPath;
    }

}
