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
     * the full path of the filesistem object up to the parent directory.
     */
    public final String parentPath;

    public FileSystemData(String path)
    {
        fullPath = path;
        int lastSeparatorPos = path.lastIndexOf(SEPARATOR);
        if (lastSeparatorPos <= 0)
        {
            // handle case "parent = root"
            parentPath = SEPARATOR;
        }
        else
        {
            parentPath = path.substring(0, lastSeparatorPos);
        }
        name = path.substring(lastSeparatorPos + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return fullPath.hashCode();
    }

    /**
     * @param path the path to check
     * @return true if the element identified by the input path has the same name as this file system data, false
     * otherwise
     */
    public boolean sameName(String path)
    {
        return path.endsWith(SEPARATOR + name);
    }

    /**
     * @param path the path to check
     * @return true if the element identified by the input path has the same parent path as this file system data, false
     * otherwise
     */
    public boolean sameParentPath(String path)
    {
        return path.startsWith(parentPath)
            && path.length() > parentPath.length() + 1
            && !path.substring(parentPath.length() + 1).contains(SEPARATOR);
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
