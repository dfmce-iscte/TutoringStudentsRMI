package students;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

import interfaces.IAppointment;
import interfaces.IStudent;

public class Student extends UnicastRemoteObject implements IStudent {

	private Set<IAppointment> student_appointments;
	private Set<IAppointment> appoiments_notified;
	private String name;

	protected Student(String name) throws RemoteException {
		super();
		// this.student_appointments = new TreeSet<IAppointment>(new
		// AppointmentComparator());
		this.student_appointments = new HashSet<IAppointment>();
		this.name = name;
		this.appoiments_notified = new HashSet<IAppointment>();
	}

	@Override
	public void appointment_available(IAppointment appointments) throws RemoteException {
		appoiments_notified.add(appointments);
		System.out.println("Student: " + name + "\n\tAppointment available: " + appointments.to_string());
	}

	@Override
	public void add_appointment(IAppointment appointment) throws RemoteException {
		student_appointments.add(appointment);
	}

	@Override
	public String get_name() throws RemoteException {
		return name;
	}

	@Override
	public String to_string() {
		return "Student [number of student_appointments=" + student_appointments.size() + "]" + " name: " + name;
	}

	public Set<IAppointment> get_student_appointments() {
		return student_appointments;
	}

	public Set<IAppointment> get_appoiments_notified() throws RemoteException {
		return appoiments_notified;
	}

}
