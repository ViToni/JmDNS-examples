package org.kromo.examples.jmdns.registration.main;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import org.kromo.examples.jmdns.LoggingServiceListener;
import org.kromo.examples.network.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final NetworkUtils networkUtils = new NetworkUtils();

        final InetAddress hostAddress = networkUtils.getMainAddress();

        logger.info("Using InetAddress: {}", hostAddress);

        // create JmDNS instance
        final JmDNS jmdns = JmDNS.create(hostAddress);
        try {

            // add a service listener
            jmdns.addServiceListener("_http._tcp.local.", new LoggingServiceListener());

            // just a little delay so that everything is set up
            TimeUnit.SECONDS.sleep(5);

            final ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "example", 4567, "path=index.html");
            jmdns.registerService(serviceInfo);

        } finally {
            jmdns.close();
        }
    }

}