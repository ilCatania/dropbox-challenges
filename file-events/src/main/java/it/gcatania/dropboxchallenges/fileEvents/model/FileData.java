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
    public boolean sameType(FileSystemData other)
    {
        return other instanceof FileData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileData)
        {
            FileData other = (FileData) obj;
            return hash.equals(other.hash) && super.equals(obj);
        }
        return false;
    }

}
