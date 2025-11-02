package com.swe.controller.UX;

import com.swe.controller.Analytics.UserAnalytics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Main participants view window with an enhanced modern UI.
 */
public final class ParticipantsView extends JPanel {

    /** Layout gap for spacing. */
    private static final int LAYOUT_GAP = 10;

    /** Title font size. */
    private static final int TITLE_FONT_SIZE = 18;

    /** Button font size. */
    private static final int BUTTON_FONT_SIZE = 14;

    /** Background color. */
    private static final Color BG_COLOR = new Color(250, 252, 255);

    /** Accent color for title and buttons. */
    private static final Color ACCENT_COLOR = new Color(0, 120, 215);

    /**
     * Constructs a styled participants view window.
     *
     * @param parent the parent frame
     */
    public ParticipantsView(final JFrame parent) {
        super(new BorderLayout(LAYOUT_GAP, LAYOUT_GAP));
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        final JLabel titleLabel = new JLabel("Meeting Participants",
                JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN,
                TITLE_FONT_SIZE));
        titleLabel.setForeground(ACCENT_COLOR);
        add(titleLabel, BorderLayout.NORTH);

        final UserAnalytics analytics = new UserAnalytics();
        final ParticipantsListPanel listPanel =
                new ParticipantsListPanel(analytics);
        add(listPanel, BorderLayout.CENTER);

        final JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, BUTTON_FONT_SIZE));
        closeButton.setBackground(ACCENT_COLOR);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setOpaque(true);
        closeButton.addActionListener(event -> parent.dispose());

        final JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        buttonPanel.add(closeButton, gbc);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Entry point for standalone UI testing.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("Participants View");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            final ParticipantsView view = new ParticipantsView(frame);
            frame.add(view);

            frame.setMinimumSize(new java.awt.Dimension(500, 400));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
