package controller;

/**
 * Represents a user of the system (instructor or student).
 */
public class User {
    /** Email of the user. */
    private final String email;

    /** Password of the user. */
    private final String password;

    /** Role of the user: "instructor" or "student". */
    private final String role;

    /**
     * Constructs a User.
     *
     * @param emailParam user email
     * @param passwordParam user password
     * @param roleParam user role
     */
    public User(final String emailParam, final String passwordParam, final String roleParam) {
        this.email = emailParam;
        this.password = passwordParam;
        this.role = roleParam.toLowerCase();
    }

    /**
     * Gets the email of the user.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password of the user.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the role of the user.
     *
     * @return role
     */
    public String getRole() {
        return role;
    }
}
