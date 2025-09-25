package controller;

/**
 * Defines the state of the user in the system, handling different actions.
 */
public interface IUserState {
    boolean isLoggedIn();

    boolean isInstructor();

    boolean canDrawOnCanvas();

    boolean canUseChat();

    String getUserEmail();
}
