package com.swe.controller.Analytics;

import com.swe.controller.Analytics.MeetAnalytics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MeetAnalyticsTest {
    private MeetAnalytics meetAnalytics;

    @BeforeEach
    void setUp() {
        String[][] cloudData = {
                {"u1", "10:00", "11:00"},
                {"u2", "10:30", "online"},
                {"u3", "11:00", "11:45"}
        };
        meetAnalytics = new MeetAnalytics(cloudData);
    }

    @Test
    void testAnalyticsInitialization() {
        Map<String, String> activeUsers = meetAnalytics.activeUsers();
        assertNotNull(activeUsers, "MeetAnalytics should be initialized");
    }
}



