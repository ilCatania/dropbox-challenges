package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;


/**
 * @author gcatania
 */
public interface FileEvent extends StructuredEvent
{

    FileData getData();
}
