package com.swe.controller.UX;

import com.swe.controller.Analytics.UserAnalytics;
import com.swe.controller.Meeting.UserProfile;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

/**
 * A styled and responsive panel that lists all participants
 * with profile pictures, names, and join timestamps.
 */
public class ParticipantsListPanel extends JPanel {

    /** Font size for user name. */
    private static final int NAME_FONT_SIZE = 15;

    /** Font size for join timestamp. */
    private static final int TIME_FONT_SIZE = 12;

    /** Row height for list items. */
    private static final int ROW_HEIGHT = 65;

    /** Background color for list. */
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);

    /** Selection highlight color. */
    private static final Color SELECTION_COLOR = new Color(225, 235, 255);

    /**
     * Constructs the participants list panel.
     *
     * @param analytics the analytics instance used to fetch mock users
     */
    public ParticipantsListPanel(final UserAnalytics analytics) {
        super(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        analytics.fetchUsersFromCloud();
        final List<UserProfile> users = analytics.getAllUsers();

        final DefaultListModel<UserProfile> model = new DefaultListModel<>();
        for (UserProfile user : users) {
            model.addElement(user);
        }

        final JList<UserProfile> userList = new JList<>(model);
        userList.setCellRenderer(new ParticipantRenderer());
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setBackground(BACKGROUND_COLOR);
        userList.setFixedCellHeight(ROW_HEIGHT);

        final JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        final JPanel header = new JPanel(new GridBagLayout());
        header.setOpaque(false);
        final JLabel title = new JLabel("Participants");
        title.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        header.add(title, new GridBagConstraints());

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Custom renderer to display profile photo, user name, and join time.
     */
    private static class ParticipantRenderer
            implements ListCellRenderer<UserProfile> {

        @Override
        public Component getListCellRendererComponent(
                final JList<? extends UserProfile> list,
                final UserProfile value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {

            final JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(8, 10, 8, 10));
            panel.setBackground(isSelected
                    ? SELECTION_COLOR : BACKGROUND_COLOR);

            // Profile Image
            JLabel picLabel = new JLabel();
            picLabel.setPreferredSize(new Dimension(45, 45));
            try {
                final BufferedImage img =
                        ImageIO.read(new URL(value.getLogoUrl()));
                final ImageIcon icon = new ImageIcon(
                        img.getScaledInstance(45, 45,
                                java.awt.Image.SCALE_SMOOTH));
                picLabel.setIcon(icon);
            } catch (Exception e) {
                // fallback default icon
                picLabel.setIcon(new ImageIcon(
                        new BufferedImage(45, 45,
                                BufferedImage.TYPE_INT_RGB)));
            }

            // Name and Join time stacked vertically
            final JPanel textPanel = new JPanel(new GridBagLayout());
            textPanel.setOpaque(false);
            final GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;

            final JLabel nameLabel = new JLabel(value.getDisplayName());
            nameLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN,
                    NAME_FONT_SIZE));
            textPanel.add(nameLabel, gbc);

            gbc.gridy = 1;
            final JLabel timeLabel = new JLabel("Joined at "
                    + value.getJoinTime());
            timeLabel.setFont(new Font("Segoe UI", Font.PLAIN,
                    TIME_FONT_SIZE));
            timeLabel.setForeground(new Color(100, 100, 100));
            textPanel.add(timeLabel, gbc);

            panel.add(picLabel, BorderLayout.WEST);
            panel.add(textPanel, BorderLayout.CENTER);
            return panel;
        }
    }
}
