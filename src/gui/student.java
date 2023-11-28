import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

public class student {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Teacher, Subject, and Time Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Map<String, Map<String, String[]>> teacherSubjectTimeMap = new HashMap<>();

            // Adding sample data
            Map<String, String[]> subjectsForTeacher1 = new HashMap<>();
            subjectsForTeacher1.put("Math", new String[]{"9:00 AM", "2:00 PM", "4:00 PM"});
            subjectsForTeacher1.put("Physics", new String[]{"10:00 AM", "1:00 PM", "3:00 PM"});
            teacherSubjectTimeMap.put("Teacher 1", subjectsForTeacher1);

            Map<String, String[]> subjectsForTeacher2 = new HashMap<>();
            subjectsForTeacher2.put("English", new String[]{"9:30 AM-10:30AM", "2:30 PM", "4:30 PM"});
            subjectsForTeacher2.put("History", new String[]{"10:30 AM", "1:30 PM", "3:30 PM"});
            subjectsForTeacher2.put("Geography", new String[]{"11:00 AM", "2:00 PM", "4:00 PM"});
            teacherSubjectTimeMap.put("Teacher 2", subjectsForTeacher2);

            Map<String, String[]> subjectsForTeacher3 = new HashMap<>();
            subjectsForTeacher3.put("Biology", new String[]{"9:15 AM", "2:15 PM", "4:15 PM"});
            subjectsForTeacher3.put("Chemistry", new String[]{"10:15 AM", "1:15 PM", "3:15 PM"});
            teacherSubjectTimeMap.put("Teacher 3", subjectsForTeacher3);

            JComboBox<String> teacherDropdown = new JComboBox<>(teacherSubjectTimeMap.keySet().toArray(new String[0]));
            JComboBox<String> subjectDropdown = new JComboBox<>();
            JComboBox<String> timeDropdown = new JComboBox<>();
            timeDropdown.setPreferredSize(new Dimension(200, 20));
            subjectDropdown.setPreferredSize(new Dimension(200, 20));
            teacherDropdown.setPreferredSize(new Dimension(200, 20));
            teacherDropdown.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String selectedTeacher = (String) teacherDropdown.getSelectedItem();
                        Map<String, String[]> subjects = teacherSubjectTimeMap.get(selectedTeacher);

                        subjectDropdown.removeAllItems();
                        timeDropdown.removeAllItems();

                        for (String subject : subjects.keySet()) {
                            subjectDropdown.addItem(subject);
                        }
                    }
                }
            });

            subjectDropdown.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String selectedTeacher = (String) teacherDropdown.getSelectedItem();
                        String selectedSubject = (String) subjectDropdown.getSelectedItem();

                        String[] availableTimes = teacherSubjectTimeMap.get(selectedTeacher).get(selectedSubject);

                        timeDropdown.removeAllItems();

                        for (String time : availableTimes) {
                            timeDropdown.addItem(time);
                        }
                    }
                }
            });

            JButton confirmButton = new JButton("Confirm");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedTeacher = (String) teacherDropdown.getSelectedItem();
                    String selectedSubject = (String) subjectDropdown.getSelectedItem();
                    String selectedTime = (String) timeDropdown.getSelectedItem();

                    JOptionPane.showMessageDialog(frame, "You selected:\nTeacher: " + selectedTeacher +
                            "\nSubject: " + selectedSubject + "\nTime: " + selectedTime);
                }
            });

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Select a Teacher:"));
            panel.add(teacherDropdown, "growx");
            panel.add(new JLabel("Select a Subject:"));
            panel.add(subjectDropdown, "growx");
            panel.add(new JLabel("Select a Time:"));
            panel.add(timeDropdown, "growx");
            panel.add(confirmButton, "span 2, align center");

            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
