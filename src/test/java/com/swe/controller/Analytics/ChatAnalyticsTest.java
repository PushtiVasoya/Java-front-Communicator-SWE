package com.swe.controller.Analytics;

import org.junit.jupiter.api.Test;

class ChatAnalyticsTest {

    @Test
    void manualTestChatAnalytics() {
        // Sample chat data
        String[][] chatArray = {
                {"u1", "c1", "u2", "Hi!", ""},
                {"u2", "c2", "u1", "Hello!", "img.png"},
                {"u3", "c3", "u1", "Need help?", ""}
        };

        // Dummy AI API endpoint
        String aiUrl = "https://example.com/ai/analyzeChat";

        // Create ChatAnalytics instance
        ChatAnalytics analytics = new ChatAnalytics(chatArray, aiUrl);

        // Call AI API (will likely return an error for dummy URL)
        System.out.println("AI Response :");
        System.out.println(analytics.callAI());

        // Convert chat data to JSON and print
        String json = analytics.convertToJson(chatArray);
        System.out.println("\nJSON Output :");
        System.out.println(json);
    }
}


