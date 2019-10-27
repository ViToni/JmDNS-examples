package org.kromo.examples.jmdns.discovery;

import java.net.InetAddress;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceListener;
import javax.jmdns.impl.JmDNSImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServiceDiscovery implements Runnable, AutoCloseable {

    private final Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);

    private final InetAddress hostAddr;
    private final Map<String, ServiceListener> listener;
    private final Runnable blockingRunnable;

    private final AtomicBoolean isClosed = new AtomicBoolean();

    private JmDNSImpl jmdnsImpl;

    public ServiceDiscovery( //
            final InetAddress hostAddress, //
            final String serviceType, //
            final ServiceListener serviceListener, //
            final Runnable blockingRunnable //
    ) {
        this( //
                hostAddress, //
                Collections.singletonMap(serviceType, serviceListener), //
                blockingRunnable //
        );
    }

    public ServiceDiscovery( //
            final InetAddress hostAddress, //
            final Map<String, ServiceListener> listener, //
            final Runnable blockingRunnable //
    ) {
        this.hostAddr = Objects.requireNonNull(hostAddress, "'hostAddress' must not be null");
        this.listener = new LinkedHashMap<>( //
                Objects.requireNonNull(listener, "'listener' must not be null") //
        );
        this.blockingRunnable = Objects.requireNonNull(blockingRunnable, "'blockingRunnable' must not be null");
    }

    public void run() {
        try {
            logger.debug("Starting JmDNS on {} ...", hostAddr);

            // Create a JmDNS instance
            jmdnsImpl = (JmDNSImpl) JmDNS.create(hostAddr);

            setupJmDNSImpl(jmdnsImpl);

            listener.forEach( //
                    (serviceType, serviceListener) -> {
                        logger.debug("Adding ServiceListener: {}", serviceType);
                        jmdnsImpl.addServiceListener(serviceType, serviceListener);
                    } //
            );

            logger.debug("Waiting for events...");

            blockingRunnable.run();

        } catch (final Exception e) {
            logger.error("Failure while running JmDNS discovery", e);
        } finally {
            close();
        }
    }

    protected void setupJmDNSImpl(final JmDNSImpl jmdns) {
        // can be extended for further configuration
    }

    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            try {
                jmdnsImpl.close();
            } catch (final Exception e) {
                logger.error("Error while closing JmDNS instance", e);
            }

            logger.info("Shutting down...");
        }
    }

}