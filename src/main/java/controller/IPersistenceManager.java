package controller;

/**
 * Manages loading and saving of application data.
 */
public interface IPersistenceManager {
    void getChatHistory(String senderEmail);

    void getCanvasEvents(String meetingId);
}
