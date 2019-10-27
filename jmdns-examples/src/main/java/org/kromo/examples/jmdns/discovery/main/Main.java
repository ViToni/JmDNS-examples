package org.kromo.examples.jmdns.discovery.main;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import org.kromo.examples.jmdns.LoggingServiceListener;
import org.kromo.examples.jmdns.discovery.ServiceDiscovery;
import org.kromo.examples.network.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final NetworkUtils networkUtils = new NetworkUtils();

        // final InetAddress hostIP = networkUtils.getAddress("HOST_NAME");
        final InetAddress hostAddress = networkUtils.getMainAddress();

        logger.info("Using InetAddress: {}", hostAddress);

        final String serviceType = "_http._tcp.local.";
        try (final ServiceDiscovery discovery = new ServiceDiscovery( //
                hostAddress, //
                serviceType, //
                new LoggingServiceListener(), //
                Main::waitUntilKeypressed //
//                Main::waitADay //
        )) {
            discovery.run();
        } catch (final Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected static final void waitUntilKeypressed() {
        try {
            System.in.read();
        } catch (final Exception ignored) {
        }
    }

    protected static final void waitADay() {
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (final InterruptedException e) {
            // restore interrupted state
            Thread.currentThread().interrupt();
        }
    }
}