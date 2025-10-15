package controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Handles authentication and meeting management.
 */
public class AuthService {
    /** Registered users mapped by email. */
    private final Map<String, UserProfile> users = new HashMap<>();

    /** Meetings mapped by meetingId. */
    private final Map<String, Meeting> meetings = new HashMap<>();

    /**
     * The PasswordEncoder is the modern, standard way to handle password security.
     * We create one instance and reuse it.
     */
    private final PasswordEncoder passwordEncoder;

    public AuthService() {
        // The "work factor" (e.g., 12) determines how strong the hashing is.
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }
    /**
     * Registers a new user with domain-based role validation.
     *
     * @param emailParam user email
     * @param passwordParam user password
     * @return true if registration succeeds, false otherwise
     */
    public boolean register(final String emailParam, final String passwordParam, final String displayNameParam, final String logoUrlParam) {
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

        String hashedPassword = passwordEncoder.encode(passwordParam);
        users.put(emailParam, new UserProfile(emailParam, displayNameParam, hashedPassword, logoUrlParam, role));
        return true;
    }

    /**
     * Logs in a user.
     *
     * @param emailParam user email
     * @param passwordParam user password
     * @return User if login succeeds, null otherwise
     */
    public UserProfile login(final String emailParam, final String passwordParam) {
        if (!users.containsKey(emailParam)) {
            return null;
        }
        final UserProfile user = users.get(emailParam);
        if (!passwordEncoder.matches(passwordParam, user.getPasswordHash())) {
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
    public Meeting createMeeting(final UserProfile userParam) {
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
    public boolean joinMeeting(final UserProfile userParam, final String meetingIdParam) {
        return meetings.containsKey(meetingIdParam);
    }
}