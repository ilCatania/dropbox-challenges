package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class DirectoryData extends FileSystemData
{

    public DirectoryData(String path)
    {
        super(path);
    }

    public boolean contains(String path)
    {
        if (path.startsWith(fullPath))
        {
            int myPathLength = fullPath.length();
            if (path.length() == myPathLength)
            {
                return true;
            }
            // must ensure that there is a separator at the end of the containing path. For example, /dir contains
            // /dir/subdir but does not contain /directory
            int minRequiredPathLength = myPathLength + SEPARATOR.length();
            if (path.length() >= minRequiredPathLength)
            {
                return SEPARATOR.equals(path.substring(myPathLength, minRequiredPathLength));
            }
        }
        return false;
    }

    public boolean contains(FileSystemData other)
    {
        return contains(other.fullPath);
    }
}
