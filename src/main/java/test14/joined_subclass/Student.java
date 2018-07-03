package test14.joined_subclass;

/**
 * student entity (extends from person)
 * @author zhangqingli
 *
 */
public class Student extends Person {
	private String school;

	public Student() {
		super();
	}
	public Student(String name, Integer age, String school) {
		super(name, age);
		this.school = school;
	}

	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	
	@Override
	public String toString() {
		return "Student [school=" + school + ", getId()=" + getId()
				+ ", getName()=" + getName() + ", getAge()=" + getAge() + "]";
	}
}
