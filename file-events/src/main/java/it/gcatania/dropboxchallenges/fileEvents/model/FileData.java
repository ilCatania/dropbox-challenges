package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class FileData extends FileSystemData
{

    public final String hash;

    public FileData(String path, String hash)
    {
        super(path);
        this.hash = hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileData && super.equals(obj))
        {
            FileData other = (FileData) obj;
            return hash.equals(other.hash);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("FileData [fullPath=")
            .append(fullPath)
            .append(", hash=")
            .append(hash)
            .append("]")
            .toString();
    }

}
