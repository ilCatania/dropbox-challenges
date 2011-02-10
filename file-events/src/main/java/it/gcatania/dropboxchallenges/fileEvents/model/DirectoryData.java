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
        if (pathComponents.length > other.pathComponents.length)
        {
            return false;
        }
        for (int i = 0; i < pathComponents.length; i++)
        {
            if (!pathComponents[i].equals(other.pathComponents[i]))
            {
                return false;
            }
        }
        return true;
    }

}
