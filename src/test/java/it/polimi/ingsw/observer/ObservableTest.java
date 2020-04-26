package it.polimi.ingsw.observer;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ObservableTest {

    class LoggingObserver implements LambdaObserver {
        LinkedHashMap<Integer, Object> calls = new LinkedHashMap<>();
        public void foo1(Object o){
            calls.put(1, o);
        }

        public void foo2(Object o){
            calls.put(2, o);
        }

        public void foo3(Object o){
            calls.put(3, o);
        }

        void clearCalls(){
            calls = new LinkedHashMap<>();
        }
    }

    @Test
    void behavioralTest() {
        LambdaObservable<Object> myObservable = new LambdaObservable<>();
        LoggingObserver observer1 = new LoggingObserver();
        LoggingObserver observer2 = new LoggingObserver();

        Object message = new Object();

        myObservable.addObserver(observer1, (obs, obj) -> ((LoggingObserver)obs).foo1(obj));
        myObservable.addObserver(observer2, (obs, obj) -> ((LoggingObserver)obs).foo2(obj));

        //Same observer, called twice so this should register only foo3
        myObservable.addObserver(observer2, (obs, obj) -> ((LoggingObserver)obs).foo3(obj));

        myObservable.notify(message);

        assertEquals(1, observer1.calls.size());
        assertEquals(1, observer2.calls.size());

        assertEquals(message, observer1.calls.get(1));
        assertFalse(observer1.calls.containsKey(2));
        assertFalse(observer1.calls.containsKey(3));

        assertEquals(message, observer2.calls.get(3));
        assertFalse(observer2.calls.containsKey(1));
        assertFalse(observer2.calls.containsKey(2));

        myObservable.removeObserver(observer1);
        myObservable.removeObserver(observer2);

        message = new Object();

        Object finalMessage = message;
        Thread t = new Thread( () -> myObservable.notify(finalMessage, true)); //Should get stuck
        t.start();

        assertTrue(t.isAlive());

        observer1.clearCalls();
        myObservable.addObserver(observer1, (obs, obj) -> ((LoggingObserver)obs).foo1(obj));

        Awaitility.await().until(() -> !t.isAlive());

        assertEquals(1, observer1.calls.size());
        assertEquals(message, observer1.calls.get(1));

        //we are reasonably sure it works :)
    }
}