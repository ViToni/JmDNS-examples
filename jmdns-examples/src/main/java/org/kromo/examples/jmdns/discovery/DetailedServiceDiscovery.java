package org.kromo.examples.jmdns.discovery;

import java.net.InetAddress;
import java.util.Map;

import javax.jmdns.ServiceListener;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.LoggingDNSListener;

public class DetailedServiceDiscovery extends ServiceDiscovery {

    public DetailedServiceDiscovery( //
            final InetAddress hostAddress, //
            final Map<String, ServiceListener> listener, //
            final Runnable blockingRunnable //
    ) {
        super(hostAddress, listener, blockingRunnable);
    }

    public DetailedServiceDiscovery( //
            final InetAddress hostAddress, //
            final String serviceType, //
            final ServiceListener serviceListener, //
            final Runnable blockingRunnable //
    ) {
        super(hostAddress, serviceType, serviceListener, blockingRunnable);
    }

    @Override
    protected void setupJmDNSImpl(final JmDNSImpl jmdns) {
        // Add a DNSRecord listener
        jmdns.addListener(new LoggingDNSListener(), null);
    }

}