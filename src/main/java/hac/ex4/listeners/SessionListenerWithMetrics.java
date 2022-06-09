package hac.ex4.listeners;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@EnableWebMvc
@WebListener
public class SessionListenerWithMetrics implements HttpSessionListener {

    private final AtomicInteger activeSessions;
    public SessionListenerWithMetrics() {
        super();
        activeSessions = new AtomicInteger();
    }
    public int getTotalActiveSession() {
        return activeSessions.get();
    }
    @Override
    public void sessionCreated(final HttpSessionEvent event) {
        activeSessions.incrementAndGet();
        System.out.println("Active sessions: " + getTotalActiveSession());
    }
    @Override
    public void sessionDestroyed(final HttpSessionEvent event) {
        activeSessions.decrementAndGet();
    }
}