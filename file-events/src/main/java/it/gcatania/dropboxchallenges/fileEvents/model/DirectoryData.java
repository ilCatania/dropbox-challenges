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

    public boolean contains(FileSystemData other)
    {
        if (equals(other))
        {
            return true;
        }
        if (other.fullPath.startsWith(fullPath))
        {
            int pathLength = fullPath.length();
            if (other.fullPath.length() == pathLength)
            {
                return true;
            }
            // must ensure that there is a separator at the end of the containing path. For example, /dir contains
            // /dir/subdir but does not contain /directory
            String candidateSeparator = other.fullPath.substring(pathLength, pathLength + SEPARATOR.length());
            return candidateSeparator.equals(SEPARATOR);
        }
        return false;
    }
}
