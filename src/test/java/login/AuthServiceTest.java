package login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for AuthService.
 */
class AuthServiceTest {
    /**
     * Tests.
     */
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }

    @Test
    void testRegisterInstructorSuccess() {
        final boolean result = authService.register("teacher@iitpkd.ac.in", "pass123");
        assertTrue(result, "Instructor should be able to register");
    }

    @Test
    void testRegisterStudentSuccess() {
        final boolean result = authService.register("student@smail.iitpkd.ac.in", "pass456");
        assertTrue(result, "Student should be able to register");
    }

    @Test
    void testRegisterInvalidDomainFails() {
        final boolean result = authService.register("random@gmail.com", "pass789");
        assertFalse(result, "Invalid domain should not be allowed");
    }

    @Test
    void testRegisterDuplicateFails() {
        authService.register("teacher@iitpkd.ac.in", "pass123");
        final boolean result = authService.register("teacher@iitpkd.ac.in", "another");
        assertFalse(result, "Duplicate registration should fail");
    }

    @Test
    void testLoginSuccess() {
        authService.register("teacher@iitpkd.ac.in", "pass123");
        final User user = authService.login("teacher@iitpkd.ac.in", "pass123");
        assertNotNull(user, "Login should succeed with correct password");
        assertEquals("instructor", user.getRole(), "Role should be instructor");
    }

    @Test
    void testLoginWrongPasswordFails() {
        authService.register("student@smail.iitpkd.ac.in", "pass456");
        final User user = authService.login("student@smail.iitpkd.ac.in", "wrongpass");
        assertNull(user, "Login should fail with wrong password");
    }

    @Test
    void testCreateMeetingOnlyInstructor() {
        authService.register("teacher@iitpkd.ac.in", "pass123");
        final User instructor = authService.login("teacher@iitpkd.ac.in", "pass123");
        final Meeting meeting = authService.createMeeting(instructor);
        assertNotNull(meeting, "Instructor should be able to create meeting");

        authService.register("student@smail.iitpkd.ac.in", "pass456");
        final User student = authService.login("student@smail.iitpkd.ac.in", "pass456");
        final Meeting studentMeeting = authService.createMeeting(student);
        assertNull(studentMeeting, "Student should not be able to create meeting");
    }

    @Test
    void testJoinMeeting() {
        authService.register("teacher@iitpkd.ac.in", "pass123");
        final User instructor = authService.login("teacher@iitpkd.ac.in", "pass123");
        final Meeting meeting = authService.createMeeting(instructor);

        authService.register("student@smail.iitpkd.ac.in", "pass456");
        final User student = authService.login("student@smail.iitpkd.ac.in", "pass456");

        final boolean joined = authService.joinMeeting(student, meeting.getMeetingId());
        assertTrue(joined, "Student should be able to join with valid meeting ID");

        final boolean invalidJoin = authService.joinMeeting(student, "fake-meeting-id");
        assertFalse(invalidJoin, "Joining with invalid ID should fail");
    }
}
