package students;

import java.awt.List;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import interfaces.IAppointment;
import interfaces.IStudent;
import interfaces.ITeacher;

public class Student extends UnicastRemoteObject implements IStudent {

	private Set<IAppointment> studentAppointments;
	private Set<IAppointment> appoimentsNotified;
	private Map<String, ITeacher> studentWaitingList;
	private String name;

	public Student(String name) throws RemoteException {
		super();
		// this.student_appointments = new TreeSet<IAppointment>(new
		// AppointmentComparator());
		this.studentAppointments = new HashSet<IAppointment>();
		this.name = name;
		this.appoimentsNotified = new HashSet<IAppointment>();
		this.studentWaitingList = new HashMap<String, ITeacher>();
	}

	@Override
	public void appointmentAvailable(IAppointment appointments) throws RemoteException {
		appoimentsNotified.add(appointments);
		System.out.println("Student: " + name + "\n\tAppointment available: " + appointments.to_string());
	}

	@Override
	public void addAppointment(IAppointment appointment) throws RemoteException {
		studentAppointments.add(appointment);
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	public Set<IAppointment> getStudentAppointments() {
		return studentAppointments;
	}

	public Set<IAppointment> getAppoimentsNotified() throws RemoteException {
		return appoimentsNotified;
	}

	public void addStudentToWaitingList(String subject, ITeacher teacher) throws RemoteException {
		studentWaitingList.put(subject, teacher);
	}

	@Override
	public Map<String, ITeacher> getStudentWaitingList() throws RemoteException {
		return studentWaitingList;
	}

	public void removeStudentFromWaitingList(String subject,ITeacher teacherName) throws RemoteException {
		System.out.println("Subject: " + subject + " Teacher: " + teacherName.getName());
		for (Map.Entry<String, ITeacher> entry : studentWaitingList.entrySet()) {
			System.out.println("Subject: " + entry.getKey() + " Teacher: " + entry.getValue().getName());
			if (entry.getKey().equals(subject) && entry.getValue().equals(teacherName)) {
				studentWaitingList.remove(subject, teacherName);
				System.out.println("REMOVEDDDDDDDDDD");
				return;
			}
		}
	}



	@Override
	public String to_string() {
		return "Student [number of student_appointments=" + studentAppointments.size() + "]" + " name: " + name;
	}
}
