package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface ITutoringServer extends Remote {

	// public Map<Integer,Set<IAppointment>>
	// search_availability_for_specific_subject(String subject) throws
	// RemoteException;
	public SerializableMap<ITeacher, List<IAppointment>> search_availability_for_specific_subject(String subject)
			throws RemoteException;

	public String to_string() throws RemoteException;
}
