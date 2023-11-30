package students;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
	private Map<String, ArrayList<ITeacher>> studentWaitingList;
	private String name;

	public Student(String name) throws RemoteException {
		super();
		
		this.studentAppointments = new HashSet<IAppointment>();
		this.name = name;
		this.appoimentsNotified = new HashSet<IAppointment>();
		this.studentWaitingList = new HashMap<String, ArrayList<ITeacher>>();
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
		if (!studentWaitingList.containsKey(subject)) {
			studentWaitingList.put(subject, new ArrayList<ITeacher>());
		}
		studentWaitingList.get(subject).add(teacher);
	}

	@Override
	public Map<String, ArrayList<ITeacher>> getStudentWaitingList() throws RemoteException {
		return studentWaitingList;
	}

	public void removeStudentFromWaitingList(String subject,ITeacher teacherName) throws RemoteException {
		System.out.println("Subject: " + subject + " Teacher: " + teacherName.getName());
		if (studentWaitingList.containsKey(subject)) {
			ArrayList<ITeacher> teachers = studentWaitingList.get(subject);
			for (ITeacher teacher : teachers) {
				if (teacher.getName().equals(teacherName.getName())) {
					teachers.remove(teacher);
					break;
				}
			}
		}
	}

	public void deleteAllNotifications(){
		appoimentsNotified.clear();
	}



	@Override
	public String to_string() {
		return "Student [number of student_appointments=" + studentAppointments.size() + "]" + " name: " + name;
	}
}
