package keycloak.events;



import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.adapter.InMemoryUserAdapter;
import org.jboss.logging.Logger;

public class SessionRestrictorEventListenerProvider implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(SessionRestrictorEventListenerProvider.class);

    private final KeycloakSession keycloakSession;

    public SessionRestrictorEventListenerProvider(KeycloakSession session) {
        this.keycloakSession = session;
    }

    @Override
    public void onEvent(Event event) {
    	
    	//log.info("SPI SessionRestrictorEventListenerProvider  LOGIN Event ...");
    	//System.out.println("SPI SessionRestrictorEventListenerProvider  LOGIN Event ...");
    	
        if (EventType.LOGIN.equals(event.getType())) {
        	
        	//log.info("SPI SessionRestrictorEventListenerProvider  LOGIN Event ...");
        	
            RealmModel realm = keycloakSession.getContext().getRealm();
            InMemoryUserAdapter user = new InMemoryUserAdapter(keycloakSession, realm, event.getUserId());
            
            keycloakSession.sessions().getUserSessionsStream(realm, user)/*.getUserSessions(realm, user)*/
                    .forEach(userSession -> {
                        // remove all existing user sessions but the current one (last one wins) ...
                        if (!userSession.getId().equals(event.getSessionId())) {
                            keycloakSession.sessions().removeUserSession(realm, userSession);
                            log.info(String.format("Session Id: %s removed ...", userSession.getId()));
                        }
                    });
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}