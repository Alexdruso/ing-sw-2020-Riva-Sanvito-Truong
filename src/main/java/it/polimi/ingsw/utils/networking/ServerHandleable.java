package it.polimi.ingsw.utils.networking;

import it.polimi.ingsw.server.ServerConnectionSetupHandler;

public interface ServerHandleable {
    boolean handleTransmittable(ServerConnectionSetupHandler handler);
}
