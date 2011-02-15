package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class FileCreationEvent extends CreationEvent
{

    public FileCreationEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp, path, hash);
    }

    public FileCreationEvent(RawEvent ev)
    {
        super(ev);
    }

}
