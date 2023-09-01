package lo.server.model;

public class Student {
	
	private String matriculation,name;
	private int age;
	
	public Student() {
		matriculation = name = "";
	}
	
	public Student(String matriculation, String name, int age) {
		setMatriculation(matriculation);
		setName(name);
		setAge(age);
	}
	
	public Student setMatriculation(String matriculation) {
		this.matriculation = matriculation;
		return this;
	}
	
	public Student setName(String name) {
		this.name = name;
		return this;
	}
	
	public Student setAge(int age) {
		this.age = age;
		return this;
	}
	
	public String getMatriculation() {
		return matriculation;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public String toString() {
		return String.format("- %s, %d anos, matrícula %s\n",
				name,age,matriculation);
	}
}
