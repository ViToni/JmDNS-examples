/*
 * Have to use this package otherwise the interface DNSListener is not visible
 */
package javax.jmdns.impl;

import javax.jmdns.impl.DNSCache;
import javax.jmdns.impl.DNSEntry;
import javax.jmdns.impl.DNSListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingDNSListener implements DNSListener {
    private final Logger logger = LoggerFactory.getLogger(LoggingDNSListener.class);

    @Override
    public void updateRecord(final DNSCache dnsCache, final long now, final DNSEntry record) {
        logger.trace("Update DNSRecord: {}, now: {}", record, now);
    }
}
