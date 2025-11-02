package com.swe.controller.Meeting;

import java.util.UUID;

/**
 * Represents a meeting created by an instructor.
 */
public class MeetingSession {
    /** Unique meeting ID. */
    private final String meetingId;

    /** Email of the instructor who created the meeting. */
    private final String createdBy;

    /** Time the meeting was created. */
    private final long createdAt;

    /**
     * Creates a new meeting with a unique ID.
     *
     * @param createdByParam email of the instructor who created the meeting
     */
    public MeetingSession(final String createdByParam) {
        this.meetingId = UUID.randomUUID().toString(); // generate unique ID
        this.createdBy = createdByParam;
        this.createdAt = System.currentTimeMillis();
    }

    // /**
    //  * Gets the unique meeting ID.
    //  *
    //  * @return meetingId
    //  */
    // public String getMeetingId() {
    //     return meetingId;
    // }
    /**
     * Instead of returning a local ID, this fetches the
     * ongoing meeting details from CloudStorageAdapter
     * and returns that meeting's ID.
     */
    public String getMeetingId() {
        CloudStorageAdapter cloudAdapter = new CloudStorageAdapter();

        try {
            Optional<MeetingSession> ongoingMeeting = cloudAdapter.getOngoingMeeting();
            if (ongoingMeeting.isPresent()) {
                return ongoingMeeting.get().meetingId;
            } else {
                return "No ongoing meeting found.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error fetching ongoing meeting from cloud.";
        }
    }

    /**
     * Takes meetingId as an argument, calls CloudStorageAdapter
     * to fetch details for that meeting, and returns 'createdBy'.
     */
    public String getCreatedBy(String meetingId) {
        CloudStorageAdapter cloudAdapter = new CloudStorageAdapter();

        try {
            String meetingDetails = cloudAdapter.getMeetingDetailsById(meetingId);
            if (meetingDetails.isPresent()) {
                return meetingDetails.get().createdBy;
            } else {
                return "Meeting not found.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error fetching meeting details from cloud.";
        }
    }

    // /**
    //  * Gets the creator of the meeting.
    //  *
    //  * @return createdBy email
    //  */
    // public String getCreatedBy() {
    //     return createdBy;
    // }

    // /**
    //  * Gets the creation time of the meeting.
    //  *
    //  * @return createdAt timestamp
    //  */
    // public long getCreatedAt() {
    //     return createdAt;
    // }

      /**
     * Takes meetingId, creates CloudStorageAdapter instance,
     * fetches meeting details from cloud, returns createdAt field.
     */
    public String getCreatedAt(String meetingId) {
        CloudStorageAdapter cloudAdapter = new CloudStorageAdapter();

        try {
            Optional<MeetingSession> meetingDetails = cloudAdapter.getMeetingDetailsById(meetingId);
            if (meetingDetails.isPresent()) {
                return String.valueOf(meetingDetails.get().createdAt);
            } else {
                return "Meeting not found.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error fetching meeting details from cloud.";
        }
    }
}