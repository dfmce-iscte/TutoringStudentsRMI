package gui;

import javax.swing.*;

import interfaces.IAppointment;
import interfaces.ITeacher;
import interfaces.ITutoringServer;

import students.Student;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class StudentPlatformGUI {
    private JFrame frame;
    private Student student;
    private ITutoringServer server;

    public StudentPlatformGUI(Student student, ITutoringServer server) {
        this.student = student;
        this.server = server;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                studentGui();
            }
        });

    }

    public void studentGui() {
        String name = "";
        try {
            name = student.getName();
        } catch (Exception e) {
            // TODO: handle exception
        }
        frame = new JFrame("Student Platform: " + name);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
       
       

        // Create panels for different functionalities
        JPanel scheduledAppointmentsPanel = new JPanel(new BorderLayout());

        DefaultListModel<IAppointment> appointmentsListModel = new DefaultListModel<>();
        JList<IAppointment> appointmentsList = new JList<>(appointmentsListModel);

        appointmentsList.setCellRenderer(new AppointmentListCellRenderer());
        JScrollPane appointmentsScrollPane = new JScrollPane(appointmentsList);
        //JTextArea scheduledAppointmentsTextArea = new JTextArea(15, 50);
        //scheduledAppointmentsTextArea.setEditable(false);
        //JScrollPane scheduledAppointmentsScrollPane = new JScrollPane(scheduledAppointmentsTextArea);
        scheduledAppointmentsPanel.add(new JLabel("Scheduled Appointments"), BorderLayout.NORTH);
        scheduledAppointmentsPanel.add(appointmentsScrollPane, BorderLayout.CENTER);

        JPanel waitingListPanel = new JPanel(new BorderLayout());
        JTextArea waitingListTextArea = new JTextArea(15, 50);
        waitingListTextArea.setEditable(false);
        JScrollPane waitingListScrollPane = new JScrollPane(waitingListTextArea);
        waitingListPanel.add(new JLabel("Waiting List"), BorderLayout.NORTH);
        waitingListPanel.add(waitingListScrollPane, BorderLayout.CENTER);

        JButton joinWaitingListButton = new JButton("Join Waiting List");

        JButton bookAppointmentButton = new JButton("Book Appointment");

        
        JPanel notificationsPanel = new JPanel(new BorderLayout());
        JTextArea notificationsTextArea = new JTextArea(15, 50);
        notificationsTextArea.setEditable(false);
        JScrollPane notificationsScrollPane = new JScrollPane(notificationsTextArea);
        notificationsPanel.add(new JLabel("Notifications"), BorderLayout.NORTH);
        // notificationsPanel.add(appointmentsScrollPane, BorderLayout.CENTER);
        notificationsPanel.add(notificationsScrollPane, BorderLayout.CENTER);

        // Create a tabbed pane to organize different functionalities
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Scheduled Appointments", scheduledAppointmentsPanel);
        tabbedPane.addTab("Waiting List", waitingListPanel);
        tabbedPane.addTab("Notifications", notificationsPanel);

        // Add the 'Join Waiting List' and 'Book Appointment' buttons at the bottom of
        // the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(joinWaitingListButton);
        buttonPanel.add(bookAppointmentButton);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for the 'Join Waiting List' button
        joinWaitingListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] items = {"SELECT ONE OF THE OPTIONS"};
                JComboBox<String> subjectDropdown = new JComboBox<>(items);
                try {
                    System.out.println("subjects" + server.getAllSubjects());
                    for (String subject : server.getAllSubjects()) {
                        subjectDropdown.addItem(subject);
                    }
                } catch (RemoteException e1) {
                    System.out.println("error");
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                JComboBox<String> teacherDropdown = new JComboBox<>();

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Select Subject:"));
                panel.add(subjectDropdown);
                panel.add(new JLabel("Select Teacher:"));
                panel.add(teacherDropdown);

                subjectDropdown.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        teacherDropdown.removeAllItems();
                        String selectedSubject = (String) subjectDropdown.getSelectedItem();
                        System.out.println("selected subject" + selectedSubject);
                        try {
                            for (ITeacher teacher : server.searchAvailabilityForSpecificSubject(selectedSubject)
                                    .keySet()) {
                                teacherDropdown.addItem(teacher.getName());

                            }
                        } catch (RemoteException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });

                int result = JOptionPane.showConfirmDialog(frame, panel, "Join Waiting List",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION && !subjectDropdown.getSelectedItem().equals("SELECT ONE OF THE OPTIONS")) {
                    String selectedSubject = (String) subjectDropdown.getSelectedItem();
                    String selectedTeacher = (String) teacherDropdown.getSelectedItem();

                    waitingListTextArea.append("Teacher: " + selectedTeacher + ", Subject: " + selectedSubject + "\n");
                    try {
                        student.addStudentToWaitingList(selectedSubject, server.getTeacherByName(selectedTeacher));
                        ITeacher teacher = server.getTeacherByName(selectedTeacher);
                        teacher.addStudentToWaitingList(student, selectedSubject);

                    } catch (RemoteException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        // Action listener for the 'Book Appointment' button
        bookAppointmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> subjectDropdown = new JComboBox<>();
                subjectDropdown.addItem("SELECT ONE OF THE OPTIONS");
                try {
                    for (String subject : server.getAllSubjects()) {
                        subjectDropdown.addItem(subject);
                    }
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                JComboBox<String> teacherDropdown = new JComboBox<>();
                teacherDropdown.setSize(100, 100);

                JComboBox<String> timeDropdown = new JComboBox<>();
                timeDropdown.setSize(100, 100);

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Select Subject:"));
                panel.add(subjectDropdown);
                panel.add(new JLabel("Select Teacher:"));
                panel.add(teacherDropdown);
                panel.add(new JLabel("Select Time:"));
                panel.add(timeDropdown);

                subjectDropdown.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        teacherDropdown.removeAllItems();
                        timeDropdown.removeAllItems();
                        String selectedSubject = (String) subjectDropdown.getSelectedItem();
                        Map<ITeacher, Set<IAppointment>> availableApp;
                        try {
                            System.out.println("selected subject" + selectedSubject);
                            availableApp = server.searchAvailabilityForSpecificSubject(selectedSubject);
                            for (ITeacher teacher : availableApp.keySet()) {

                                if (!availableApp.get(teacher).isEmpty()) {
                                    teacherDropdown.addItem(teacher.getName());

                                }
                            }
                        } catch (RemoteException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                    }
                });

                teacherDropdown.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        timeDropdown.removeAllItems();
                        String selectedTeacher = (String) teacherDropdown.getSelectedItem();
                        String selectedSubject = (String) subjectDropdown.getSelectedItem();

                        try {
                            for (IAppointment appointment : server.getAppointmentsForSubjectAndTeacher(selectedTeacher,
                                    selectedSubject)) {
                                timeDropdown.addItem(appointment.to_string());
                                System.out.println();
                            }
                        } catch (RemoteException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });

                int result = JOptionPane.showConfirmDialog(frame, panel, "Book Appointment",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION && !subjectDropdown.getSelectedItem().equals("SELECT ONE OF THE OPTIONS")) {
                    String selectedTeacher = (String) teacherDropdown.getSelectedItem();
                    String selectedSubject = (String) subjectDropdown.getSelectedItem();
                    String selectedAppointment = (String) timeDropdown.getSelectedItem();
                    try {
                        ITeacher teacher = server.getTeacherByName(selectedTeacher);
                        IAppointment appointment = teacher.getAppointment(selectedAppointment);
                        appointment.bookAppointment(student);
                        appointmentsListModel.addElement(appointment);
                        System.out.println("Appointment booked: " + appointment.to_string() + "\n");
                    } catch (RemoteException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    //scheduledAppointmentsTextArea.append("Teacher: " + selectedTeacher + ", Subject: "
                      //      + selectedSubject + ", Time: " + selectedAppointment + "\n");
                    
                }

            }
        });
        // Add the 'Remove from Waiting List' button to the waiting list panel
        JButton removeFromWaitingListButton = new JButton("Remove from Waiting List");
        // Action listener for removing a student from the waiting list
        removeFromWaitingListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> subjectDropdown = new JComboBox<>();
                // Populate subjectDropdown and teacherDropdown based on existing waiting list
                // data

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Select Appointment:"));
                panel.add(subjectDropdown);
                try {
                    Map<String, ArrayList<ITeacher>> waitingList = student.getStudentWaitingList();
                    for (String subject : waitingList.keySet()) {
                        for (ITeacher teacher : waitingList.get(subject)) {
                            subjectDropdown.addItem(subject + " " + teacher.getName());
                        }

                    }
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                int result = JOptionPane.showConfirmDialog(frame, panel, "Remove from Waiting List",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String selectedSubject = (String) subjectDropdown.getSelectedItem();
                    String subjectName = selectedSubject.split(" ")[0];
                    String teacherName = selectedSubject.split(" ")[1];


                    try {

                        ITeacher teacher = server.getTeacherByName(teacherName);
                        System.out.println(student.getStudentWaitingList().size());
                        student.removeStudentFromWaitingList(subjectName, teacher);
                        System.out.println(student.getStudentWaitingList().size());
                        teacher.removeStudentFromWaitingList(student, subjectName);
                        waitingListTextArea.setText("");
                        for (String subject : student.getStudentWaitingList().keySet()) {
                            for (ITeacher teacher1 : student.getStudentWaitingList().get(subject)) {
                                waitingListTextArea.append("Subject: " + subject + ", Teacher: "
                                        + teacher1.getName() + "\n");
                            }

                        }

                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        notificationsTextArea.setText("");
                        for (IAppointment appointment : student.getAppoimentsNotified()) {
                            System.out.println("Appointment available: " + appointment.to_string() + "\n");
                            notificationsTextArea.append("Appointment available: " + appointment.to_string() + "\n");
                        }
                        //student.getAppoimentsNotified().clear();
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        JButton deleteAllNotificationsButton = new JButton("Delete All Notifications");
        deleteAllNotificationsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                student.deleteAllNotifications();
                notificationsTextArea.setText("");
            }
        });
        notificationsPanel.add(deleteAllNotificationsButton, BorderLayout.SOUTH);
        waitingListPanel.add(removeFromWaitingListButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    static class AppointmentListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (renderer instanceof JLabel && value instanceof IAppointment) {
                IAppointment appointment = (IAppointment) value;
                try {
                    ((JLabel) renderer).setText(appointment.to_string());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return renderer;
        }
    }
}
