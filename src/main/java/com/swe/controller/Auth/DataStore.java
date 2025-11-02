package com.swe.controller.Auth;

import com.swe.controller.Meeting.UserProfile;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DataStore} class maintains separate lists of teachers and students.
 * It provides methods to add users and print stored user information.
 */
public class DataStore {

    /** List of all student profiles. */
    private final List<UserProfile> students;

    /** List of all teacher profiles. */
    private final List<UserProfile> teachers;

    /**
     * Constructs an empty DataStore with separate lists for students and teachers.
     */
    public DataStore() {
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
    }

    /**
     * Returns the list of all stored student profiles.
     *
     * @return list of student profiles
     */
    public List<UserProfile> getStudents() {
        return students;
    }

    /**
     * Returns the list of all stored teacher profiles.
     *
     * @return list of teacher profiles
     */
    public List<UserProfile> getTeachers() {
        return teachers;
    }

    /**
     * Adds a user to the appropriate list based on their role.
     * If the role is not recognized, the user is not added.
     *
     * @param user the user to be added
     */
    public void addUser(final UserProfile user) {
        if (user.getRole().equalsIgnoreCase("teacher")) {
            teachers.add(user);
        } else if (user.getRole().equalsIgnoreCase("student")) {
            students.add(user);
        } else {
            System.out.println("Unknown role, user not added: "
                    + user.getDisplayName());
        }
    }

    /**
     * Prints all teachers and students currently stored in this DataStore.
     */
    public void printAllUsers() {
        System.out.println("Teachers:");
        teachers.forEach(u -> System.out.println(" - " + u.toJson()));

        System.out.println("Students:");
        students.forEach(u -> System.out.println(" - " + u.toJson()));
    }
}
