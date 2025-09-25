package controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles authentication and meeting management.
 */
public class AuthService {
    /** Registered users mapped by email. */
    private final Map<String, User> users = new HashMap<>();

    /** Meetings mapped by meetingId. */
    private final Map<String, Meeting> meetings = new HashMap<>();

    /**
     * Registers a new user with domain-based role validation.
     *
     * @param emailParam user email
     * @param passwordParam user password
     * @return true if registration succeeds, false otherwise
     */
    public boolean register(final String emailParam, final String passwordParam) {
        if (users.containsKey(emailParam)) {
            return false;
        }

        final String role;
        if (emailParam.endsWith("@iitpkd.ac.in")) {
            role = "instructor";
        } else if (emailParam.endsWith("@smail.iitpkd.ac.in")) {
            role = "student";
        } else {
            return false;
        }

        users.put(emailParam, new User(emailParam, passwordParam, role));
        return true;
    }

    /**
     * Logs in a user.
     *
     * @param emailParam user email
     * @param passwordParam user password
     * @return User if login succeeds, null otherwise
     */
    public User login(final String emailParam, final String passwordParam) {
        if (!users.containsKey(emailParam)) {
            return null;
        }
        final User user = users.get(emailParam);
        if (!user.getPassword().equals(passwordParam)) {
            return null;
        }
        return user;
    }

    /**
     * Creates a new meeting (only for instructors).
     *
     * @param userParam logged-in user
     * @return Meeting if created, null if user is not an instructor
     */
    public Meeting createMeeting(final User userParam) {
        if (!"instructor".equals(userParam.getRole())) {
            return null;
        }
        final Meeting meeting = new Meeting(userParam.getEmail());
        meetings.put(meeting.getMeetingId(), meeting);
        return meeting;
    }

    /**
     * Joins a meeting using meeting ID.
     *
     * @param userParam logged-in user
     * @param meetingIdParam meeting ID
     * @return true if joined, false otherwise
     */
    public boolean joinMeeting(final User userParam, final String meetingIdParam) {
        return meetings.containsKey(meetingIdParam);
    }
}