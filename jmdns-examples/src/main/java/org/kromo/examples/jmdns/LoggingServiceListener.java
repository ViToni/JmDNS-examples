package org.kromo.examples.jmdns;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingServiceListener implements ServiceListener {
    private static final Logger logger = LoggerFactory.getLogger(LoggingServiceListener.class);

    @Override
    public void serviceAdded(ServiceEvent event) {
        logger.info("Service added: {}", event.getInfo());
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        logger.info("Service removed: {}", event.getInfo());
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        logger.info("Service resolved: {}", event.getInfo());
    }

}
