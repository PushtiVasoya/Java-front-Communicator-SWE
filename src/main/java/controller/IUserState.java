package controller;

public interface IUserState {
    boolean isLoggedIn();
    boolean isInstructor();
    boolean canDrawOnCanvas();
    boolean canUseChat();
    String getUserEmail();
}
