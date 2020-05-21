package net.sourceforge.jabm.util;

import static org.assertj.core.api.Assertions.assertThat;

import junit.framework.TestCase;
import org.junit.Test;

public class SystemPropertiesTest extends TestCase {

    @Test
    public void testThatItReadsSystemProperties() {
        final String config = "/the/test/directory";
        System.setProperty("jabm.config", config);

        final SystemProperties systemProperties = SystemProperties.jabsConfiguration();

        assertThat(systemProperties.get("config")).isEqualTo(config);
    }
}
