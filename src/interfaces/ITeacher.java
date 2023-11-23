package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ITeacher extends Remote {

	// public Set<IAppointment> check_availability(String subject) throws
	// RemoteException;

	public void add_student_to_waiting_list(IStudent student, String subject) throws RemoteException;

	public void remove_student_from_waiting_list(IStudent student, String subject) throws RemoteException;

	public String to_string() throws RemoteException;

	public String get_name() throws RemoteException;
}
