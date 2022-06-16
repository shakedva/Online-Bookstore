package hac.ex4.listeners;

import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a @WebListener class for session count
 * the @Component is needed only if we INJECT beans
 */
@Component
@WebListener
public class SessionListenerCounter implements HttpSessionListener {
    private final AtomicInteger activeSessions;

    /**
     * creates an active session variable
     */
    public SessionListenerCounter() {
        super();
        activeSessions = new AtomicInteger();
    }

    /**
     * adds a new session and prints the number of current available sessions
     *
     * @param event collects data over the current available sessions
     */
    public void sessionCreated(final HttpSessionEvent event) {
        activeSessions.incrementAndGet();
        System.out.println("SessionListenerCounter +++ Total active session are " + activeSessions.get());
    }

    /**
     * removes a session and prints the number of current available sessions
     *
     * @param event collects data over the current available sessions
     */
    public void sessionDestroyed(final HttpSessionEvent event) {
        activeSessions.decrementAndGet();
        System.out.println("SessionListenerCounter --- Total active session are " + activeSessions.get());
    }
}
