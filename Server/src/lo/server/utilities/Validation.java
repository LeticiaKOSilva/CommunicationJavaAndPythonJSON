package lo.server.utilities;

import static lo.server.gui.InputAndOutput.*;
import static lo.server.utilities.Constant.*;

import java.util.List;

import lo.server.model.Student;

public class Validation {
	private static int id = 0;
	
	public static String generatorId() {
		id++;
		return String.format("%d", id);
	}
	
	public static boolean validatingString(String data) {
		return ( data != null && !data.isBlank() ? true : false);
	}
	
	public static boolean validatingInt(int data) {
		return (data > 0 ? true : false);
	}
	
	public static String convertingInt(String matriculation) {
		
		try {
			Integer.parseInt(matriculation);
		}catch(Exception e) {
			showMessage(ERRO_MATRICULATION, TITLE);
			System.exit(0);
		}
		return matriculation;
	}
	
	public static String checksMatriculation(List<Student>students, String value) {
		if(students.isEmpty())
			return convertingInt(value);
		
		while (true) {
			for(Student student : students) {
				if(value.equals(student.getMatriculation())) {
					showMessage(EXISTING_MATRICULATION, TITLE);
					value = checksMatriculation(students,manipulatingString(MESSAGE_MATRICULATION));
				}
			}
			return convertingInt(value);
		}		
	}
	
	public static String manipulatingString(String message) {
		String value = inputString(message, TITLE);
		if(!validatingString(value)) {
			showMessage(ERROR_DATA, TITLE);
			System.exit(0);
		}
		return value;
	}
	
	public static int manipulatingInt(String message) {
		int value = inputInt(message, TITLE);
		if(!validatingInt(value)) {
			showMessage(ERROR_DATA, TITLE);
			System.exit(0);
		}
		return value;
	}
}
