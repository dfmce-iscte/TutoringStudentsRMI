package gui;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import interfaces.ITutoringServer;

import students.Student;


public class StudentMainGui {
    public static void main(String[] args) {

        try {
            ITutoringServer server = (ITutoringServer) Naming.lookup("TutoringPlatform");
            Student s1 = new Student("s1");

            StudentPlatformGUI gui1 = new StudentPlatformGUI(s1, server);

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
