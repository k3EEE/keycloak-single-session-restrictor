package keycloak.events;


import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.jboss.logging.Logger;

public class SessionRestrictorEventListenerProviderFactory  implements EventListenerProviderFactory {

	//private static final Logger log = Logger.getLogger(SessionRestrictorEventListenerProviderFactory.class);
	
    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
    	
    	//log.info("SPI SessionRestrictorEventListenerProviderFactory  Event Listener Created...");

        return new SessionRestrictorEventListenerProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "session-restrictor";
    }
}