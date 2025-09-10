package login;

/**
 * Acts as the frontend integration layer for testing purposes.
 */
public class FrontendIntegration {
    /** Service handling authentication and meeting logic. */
    private final AuthService authService = new AuthService();

    /**
     * Simulated frontend register button.
     *
     * @param email user email
     * @param password user password
     * @return result message
     */
    public String onRegister(final String email, final String password) {
        final boolean success = authService.register(email, password);
        if (success) {
            return "Registration Successful";
        } else {
            return "Registration Failed";
        }
    }

    /**
     * Simulated frontend login button.
     *
     * @param email user email
     * @param password user password
     * @return User if login succeeds, null otherwise
     */
    public User onLogin(final String email, final String password) {
        return authService.login(email, password);
    }

    /**
     * Simulated frontend create meeting button.
     *
     * @param user instructor user
     * @return meeting ID or error message
     */
    public String onCreateMeeting(final User user) {
        final Meeting meeting = authService.createMeeting(user);
        if (meeting != null) {
            return meeting.getMeetingId();
        } else {
            return "Error: Only instructors can create meetings!";
        }
    }

    /**
     * Simulated frontend join meeting button.
     *
     * @param user user joining the meeting
     * @param meetingId meeting ID
     * @return result message
     */
    public String onJoinMeeting(final User user, final String meetingId) {
        final boolean joined = authService.joinMeeting(user, meetingId);
        if (joined) {
            return "Joined Successfully";
        } else {
            return "Invalid Meeting ID";
        }
    }
}
