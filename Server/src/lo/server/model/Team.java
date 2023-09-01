package lo.server.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	
	private String id,name;
	private int year;
	private List<Student>listStudents = new ArrayList<>();
	
	public Team() {
		id = name = "";
	}
	
	public Team(String id, String name, int year,List<Student>students) {
		setId(id);
		setName(name);
		setYear(year);
		setListStudents(students);
	}
	
	public Team setId(String id) {
		this.id = id;
		return this;
	}
	
	public Team setName(String name) {
		this.name = name;
		return this;
	}
	
	public Team setYear(int year) {
		this.year = year;
		return this;
	}
	
	public Team setListStudents(List<Student> students) {
		for(Student student : students)
			this.listStudents.add(student);
		return this;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getYear() {
		return year;
	}
	
	public List<Student> getListEstudents(){
		return listStudents;
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(String.format("\n%s %s (%d)\n", name,id,year));
		
		for(Student student : listStudents)
			stringBuilder.append(student.toString());
		return stringBuilder.toString();
	}
}
