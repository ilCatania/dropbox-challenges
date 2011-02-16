package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class DirectoryCreationEvent extends CreationEvent
{

    public DirectoryCreationEvent(long timeStamp, String path)
    {
        super(timeStamp, path);
    }

    public DirectoryCreationEvent(RawEvent ev)
    {
        super(ev);
    }

}
