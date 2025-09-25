package controller;

public interface IPersistenceManager {
    void getChatHistory(String senderEmail);

    void getCanvasEvents(String meetingId);
}
