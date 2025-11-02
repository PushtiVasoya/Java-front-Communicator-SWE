package com.swe.controller.Meeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for UserProfile.
 * This class validates the constructors, getters, setters,
 * equals, hashCode, toString, and JSON serialization/deserialization.
 */
class UserProfileTest {

    /**
     * A sample UserProfile object for testing.
     */
    private UserProfile user1;

    /**
     * A second UserProfile object, manually set to be equal to user1.
     */
    private UserProfile user2;

    /**
     * The Jackson mapper for testing serialization/deserialization.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Minimum UUID length for validation.
     */
    private static final int MIN_UUID_LENGTH = 10;

    /**
     * Sets up common UserProfile objects for testing.
     */
    @BeforeEach
    void setUp() {
        user1 = new UserProfile(
                "test@example.com",
                "Test User",
                "hashed_password_123",
                "http://example.com/img.png",
                "instructor"
        );

        // Manually create user2 to test equals/hashCode.
        // We must manually set the fields to match,
        // since the constructor generates a random UUID.
        user2 = new UserProfile();
        user2.setUserId(user1.getUserId()); // Manually set ID to be the same
        user2.setEmail("test@example.com");
        user2.setDisplayName("Test User");
        user2.setPasswordHash("hashed_password_123");
        user2.setLogoUrl("http://example.com/img.png");
        user2.setRole("instructor");
    }

    @Test
    @DisplayName("Test Constructor and Getters")
    void testConstructorAndGetters() {
        assertNotNull(user1.getUserId()); // Check that UUID was generated
        assertTrue(user1.getUserId().length() > MIN_UUID_LENGTH); // Check it's a real UUID
        assertEquals("test@example.com", user1.getEmail());
        assertEquals("Test User", user1.getDisplayName());
        assertEquals("hashed_password_123", user1.getPasswordHash());
        assertEquals("http://example.com/img.png", user1.getLogoUrl());
        assertEquals("instructor", user1.getRole());
    }

    @Test
    @DisplayName("Test Default Constructor")
    void testDefaultConstructor() {
        final UserProfile user = new UserProfile();
        assertNotNull(user);
        assertEquals(null, user.getUserId()); // Fields should be null
    }

    @Test
    @DisplayName("Test Setters")
    void testSetters() {
        final UserProfile user = new UserProfile();
        user.setDisplayName("New Name");
        user.setLogoUrl("http://new.com/new.jpg");

        assertEquals("New Name", user.getDisplayName());
        assertEquals("http://new.com/new.jpg", user.getLogoUrl());

        // Test package-private setters
        user.setUserId("manual-id");
        user.setEmail("manual@email.com");
        user.setRole("student");
        user.setPasswordHash("manual-hash");

        assertEquals("manual-id", user.getUserId());
        assertEquals("manual@email.com", user.getEmail());
        assertEquals("student", user.getRole());
        assertEquals("manual-hash", user.getPasswordHash());
    }

    @Test
    @DisplayName("Test Equals and HashCode")
    void testEqualsAndHashCode() {
        // Test for equality (user1 and user2 were manually matched in setUp)
        assertEquals(user1, user2);

        // Test for hash code consistency
        assertEquals(user1.hashCode(), user2.hashCode());

        // Test for inequality (user3 will have a different UUID)
        final UserProfile user3 = new UserProfile(
                "test@example.com",
                "Test User",
                "hashed_password_123",
                "http://example.com/img.png",
                "instructor"
        );
        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());

        // Test inequality with different field
        user2.setDisplayName("A Different Name");
        assertNotEquals(user1, user2);

        // Test inequality with null
        assertNotEquals(null, user1);

        // Test inequality with different object type
        assertNotEquals("a string", user1);
    }

    @Test
    @DisplayName("Test toString Method Security")
    void testToString() {
        final String userString = user1.toString();
        assertTrue(userString.contains("displayName='Test User'"));
        assertTrue(userString.contains("role='instructor'"));
        assertTrue(userString.contains("userId='" + user1.getUserId() + "'"));
        // Security Check: Ensure password hash is NOT in the toString output
        assertFalse(userString.contains("hashed_password_123"));
        assertFalse(userString.contains("passwordHash"));
    }

    @Test
    @DisplayName("Test JSON Serialization (Write)")
    void testJsonSerialization() throws Exception {
        final String json = objectMapper.writeValueAsString(user1);

        // Check that camelCase keys are used, as per @JsonProperty
        assertTrue(json.contains("\"userId\":\"" + user1.getUserId() + "\""));
        assertTrue(json.contains("\"email\":\"test@example.com\""));
        assertTrue(json.contains("\"displayName\":\"Test User\""));
        assertTrue(json.contains("\"role\":\"instructor\""));
        assertTrue(json.contains("\"logoUrl\":\"http://example.com/img.png\""));
        assertTrue(json.contains("\"passwordHash\":\"hashed_password_123\""));
    }

    @Test
    @DisplayName("Test JSON Deserialization (Read)")
    void testJsonDeserialization() throws Exception {
        // JSON string using the camelCase keys
        final String json = "{"
                + "\"userId\":\"user-999\","
                + "\"email\":\"json@example.com\","
                + "\"displayName\":\"JSON User\","
                + "\"role\":\"student\","
                + "\"logoUrl\":\"http://json.com/pic.png\","
                + "\"passwordHash\":\"a_hash_that_can_be_read\""
                + "}";

        final UserProfile user = objectMapper.readValue(json, UserProfile.class);

        assertEquals("user-999", user.getUserId());
        assertEquals("json@example.com", user.getEmail());
        assertEquals("JSON User", user.getDisplayName());
        assertEquals("student", user.getRole());
        assertEquals("http://json.com/pic.png", user.getLogoUrl());
        assertEquals("a_hash_that_can_be_read", user.getPasswordHash());
    }
}