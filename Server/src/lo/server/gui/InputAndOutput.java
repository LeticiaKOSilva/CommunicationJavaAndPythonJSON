package lo.server.gui;

import javax.swing.JOptionPane;

public class InputAndOutput {
	public static String inputString(String message, String title) {
		return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
	}
	
	public static int inputInt(String message, String title) {
		String values = inputString(message, title);
		try {
			return Integer.parseInt(values);
		}catch(Exception e) {
			return 0;
		}
	}
	
	public static void showMessage(String message, String title) {
		JOptionPane.showConfirmDialog(null, message, title, JOptionPane.DEFAULT_OPTION);
	}
}
