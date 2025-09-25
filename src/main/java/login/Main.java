package login;

/**
 * Entry point for running the simulation.
 */
public class Main {
    /**
     * Main method to simulate user actions.
     *
     * @param args program arguments
     */
    public static void main(final String[] args) {
        final FrontendIntegration frontend = new FrontendIntegration();

        // Register demo users
        System.out.println(frontend.onRegister("teacher@iitpkd.ac.in", "pass123"));
        System.out.println(frontend.onRegister("student@smail.iitpkd.ac.in", "pass456"));

        // Login users
        final User instructor = frontend.onLogin("teacher@iitpkd.ac.in", "pass123");
        final User student = frontend.onLogin("student@smail.iitpkd.ac.in", "pass456");

        // Instructor creates meeting
        if (instructor != null) {
            final String meetingId = frontend.onCreateMeeting(instructor);
            System.out.println("Meeting ID: " + meetingId);

            // Student joins meeting
            if (student != null) {
                System.out.println(frontend.onJoinMeeting(student, meetingId));
            }
        }
    }
}
