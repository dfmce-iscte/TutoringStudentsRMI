package students;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import interfaces.ITutoringServer;
import interfaces.IAppointment;
import interfaces.IStudent;
import interfaces.ITeacher;

public class StudentMain {
    public static void main(String[] args) {

        try {
            ITutoringServer server = (ITutoringServer) Naming.lookup("TutoringPlatform");

            Scanner scanner = new Scanner(System.in);

            Student s1 = new Student("s1");
            Student s2 = new Student("s2");
            Student s3 = new Student("s3");

            ITeacher tbruno = null;

            while (true) {
                String text = scanner.nextLine();
                if (text.equals("m")) {
                    Map<ITeacher, Set<IAppointment>> teachers = server.search_availability_for_specific_subject("Math");
                    for (Map.Entry<ITeacher, Set<IAppointment>> entry : teachers.entrySet()) {

                        if (entry.getValue().isEmpty()) {
                            System.out.println("Vou para a wainting list" + entry.getKey().to_string());
                            IStudent is1 = s1;
                            IStudent is2 = s2;
                            IStudent is3 = s3;
                            tbruno = entry.getKey();
                            entry.getKey().add_student_to_waiting_list(is1, "Math");
                            entry.getKey().add_student_to_waiting_list(is2, "Math");
                            entry.getKey().add_student_to_waiting_list(is3, "Math");
                        }

                        else {
                            for (IAppointment app : entry.getValue()) {
                                System.out.println("Vou fazer Book " + entry.getKey().to_string());
                                IStudent is1 = s1;
                                System.out.println(app.book_appointment(is1));
                                System.out.println(s1.to_string());
                                break;
                            }
                        }
                    }

                } else if (text.equals("p")) {
                    Map<ITeacher, Set<IAppointment>> teachers = server
                            .search_availability_for_specific_subject("Physics");
                    for (Map.Entry<ITeacher, Set<IAppointment>> entry : teachers.entrySet()) {
                        System.out.println("For teacher: " + entry.getKey());
                        for (IAppointment app : entry.getValue())
                            System.out.println("    " + app.toString());
                    }

                } else if (text.equals("n")) {
                    for (IAppointment app : s1.get_appoiments_notified()) {
                        System.out.println(app.book_appointment(s1));
                        break;
                    }
                    for (IAppointment app : s2.get_appoiments_notified()) {
                        System.out.println(app.book_appointment(s2));
                        break;
                    }
                } else if (text.equals("rems1") && tbruno != null) {
                    tbruno.remove_student_from_waiting_list(s1, "Math");
                }

            }

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
