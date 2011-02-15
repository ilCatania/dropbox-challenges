package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;


/**
 * @author gcatania
 */
public interface DirectoryEvent extends StructuredEvent
{

    DirectoryData getData();
}
