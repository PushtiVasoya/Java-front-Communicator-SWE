package controller;

import java.util.UUID;

/**
 * Represents a meeting created by an instructor.
 */
public class Meeting {
    /** Unique meeting ID. */
    private final String meetingId;

    /** Email of the instructor who created the meeting. */
    private final String createdBy;

    /**
     * Creates a new meeting with a unique ID.
     *
     * @param createdByParam email of the instructor who created the meeting
     */
    public Meeting(final String createdByParam) {
        this.meetingId = UUID.randomUUID().toString(); // generate unique ID
        this.createdBy = createdByParam;
    }

    /**
     * Gets the unique meeting ID.
     *
     * @return meetingId
     */
    public String getMeetingId() {
        return meetingId;
    }

    /**
     * Gets the creator of the meeting.
     *
     * @return createdBy email
     */
    public String getCreatedBy() {
        return createdBy;
    }
}
