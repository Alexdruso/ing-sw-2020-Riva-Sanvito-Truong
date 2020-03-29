package it.polimi.ingsw.observer;

/**
 * This interface can be implemented by all classes that represent an Observer
 * @param <T> the type of the message that is to be received when the Observer is updated
 */
public interface Observer<T> {
    /**
     * This method is called by the Observable to update the observer
     * @param message the message to be received
     */
    void update(T message);
}
