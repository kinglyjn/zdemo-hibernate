package test17.hql_qbc_sql;

/**
 * emp entity
 * @author zhangqingli
 *
 */
public class Emp {
	private Integer id;
	private String name;
	private Float salary;
	private String email;
	private Dept dept;
	
	public Emp() {
		super();
	}
	public Emp(String name, Float salary, String email) {
		this.name = name;
		this.salary = salary;
		this.email = email;
	}
	public Emp(String email, Float salary, Dept dept) {
		this.email = email;
		this.salary = salary;
		this.dept = dept;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getSalary() {
		return salary;
	}
	public void setSalary(Float salary) {
		this.salary = salary;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Emp [id=" + id + ", name=" + name + ", salary=" + salary
				+ ", email=" + email + "]";
	}
}
