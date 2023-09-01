package lo.server;

import static lo.server.gui.InputAndOutput.inputString;
import static lo.server.gui.InputAndOutput.showMessage;
import static lo.server.utilities.Constant.*;
import static lo.server.utilities.Validation.checksMatriculation;
import static lo.server.utilities.Validation.generatorId;
import static lo.server.utilities.Validation.manipulatingInt;
import static lo.server.utilities.Validation.manipulatingString;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lo.server.model.Student;
import lo.server.model.Team;

public class Server {
	
	/*Método responsável por inicializar o programa*/
	public static void main(String[]args) {
		new Server().server(inuput());
	}
	
	/*Responsável por receber e retornar todos os dados das turmas*/
	private static List<Team> inuput() {

		try {
			List<Team>teams = fillingClass();
			return teams;
		}catch(Exception e) {
			showMessage(MESSAGE_CLOSE, TITLE);
		}
		return null;
	}
	
	/*Retorna os dados formatados em uma String*/
	private static String formattingData(List<Team>teams) {
		StringBuilder stringBuilder = new StringBuilder();
		
		for(Team team : teams)
			stringBuilder.append(team.toString());
		return stringBuilder.toString();
	}
	
	/*Preenche os dados da classe Team*/
	private static List<Team> fillingClass() {
		String option = "";
		List<Team> teams = new ArrayList<>();
		List<Student>students;
		do {
			try {
				String name, id;
				int year;
				id = generatorId();
				name = manipulatingString(MESSAGE_NAME_TEAM);
				year = manipulatingInt(MESSAGE_YEAR);
				students = studentData();
				teams.add(new Team(id,name,year,students));
				
			}catch(Exception e) {
				showMessage(ERROR_DATA, TITLE);
			}
			
			option = inputString(MESSAGE_CLOSE_TEAM,TITLE);
			
		}while(option != null && option.equals("1"));
		return teams;
	}
	
	/*Preenche os dados da classe Student*/
	private static List<Student> studentData() {
		String option = "";
		List<Student>students = new ArrayList<>();
		do {
			try {
				String name, matriculation;
				int age;
				matriculation = manipulatingString(MESSAGE_MATRICULATION);
				matriculation = checksMatriculation(students, matriculation);
				name = manipulatingString(MESSAGE_NAME_STUDENT);
				age = manipulatingInt(MESSAGE_AGE);
				students.add(new Student(matriculation,name, age));
			}catch(Exception e) {
				showMessage(ERROR_DATA, TITLE);
			}
			option = inputString(MESSAGE_CLOSE_STUDENT,TITLE);
		}while(option != null && option.equals("1"));
		return students;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject jsonSimple(Team team) {
		JSONObject jsonTeam = new JSONObject();
		jsonTeam.put("nome", team.getName());
		jsonTeam.put("id", team.getId());
		jsonTeam.put("ano",team.getYear());
		
		JSONArray list = new JSONArray();
		List<Student>students = team.getListEstudents();
		for(Student student : students) {
			JSONObject aluJsonObject = new JSONObject();
			aluJsonObject.put("matricula", student.getMatriculation());
			aluJsonObject.put("aluno", student.getName());
			aluJsonObject.put("idade",student.getAge());
			list.add(aluJsonObject);
		}
		jsonTeam.put("Estudantes",list);
		return jsonTeam;
	}
	
	public JSONObject json(List<Team>teams) {
		JSONArray jsonTeams = new JSONArray();
		
		for(Team team : teams) {
			jsonTeams.add(jsonSimple(team));
		}
		
		JSONObject jsonS = new JSONObject();
		jsonS.put("Turmas", jsonTeams);
		return jsonS;
		
	}
	
	public void server(List<Team> teams) {
	    // Ao criar um soquete o código pode disparar exceções
	    try {
	        // Instanciando um soquete em Java
	        @SuppressWarnings("resource")
	        ServerSocket server = new ServerSocket(14003);
	        System.out.println("Servidor aguardando uma conexão");

	        while (true) {
	            // O método accept() bloqueia a execução até que haja uma solicitação de conexão feita por um cliente
	            Socket client = server.accept();
	            OutputStream clientOutput = client.getOutputStream();
	            PrintWriter output = new PrintWriter(new OutputStreamWriter(clientOutput, "utf-8"), true);

	            // Crie o objeto JSON com os dados iniciais e envie para o cliente
	            JSONObject object = json(teams);
	            output.println(object.toJSONString());

	            // Lê os dados enviados pelo cliente
	            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
	            String json = reader.readLine();

	            formattingData(json);
	            reader.close();
	            output.close();
	            client.close();
	        }
	    } catch (Exception e) {
	        System.out.println(e.toString());
	    }
	}
	
	/*Responsável por receber, formatar e printar os dados */
	public void formattingData(String json) {
		String[] jsonModified = json.replaceAll(REGEX_DATA, "").split(" ");
	    StringBuilder stringBuilder = new StringBuilder("Lideres de turma:\n");

	    String currentTurma = null;

	    for (String result : jsonModified) {
	        if (result.startsWith("Turma")) {
	            currentTurma = result;
	        } else if (currentTurma != null && !result.equals(ALUNO_SORTEADO)) {
	            stringBuilder.append(currentTurma + " : " + result + "\n");
	            currentTurma = null;
	        }
	    }
	    showMessage(stringBuilder.toString(), TITLE);
	}


		
}
