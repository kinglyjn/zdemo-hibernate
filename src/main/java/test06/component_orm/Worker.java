package test06.component_orm;

/**
 * worker entity
 * 
 * @author zhangqingli
 *
 */
public class Worker {
	private Integer id;
	private String name;
	private Salary salary;

	public Worker() {
		super();
	}
	public Worker(String name, Salary salary) {
		this.name = name;
		this.salary = salary;
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

	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", salary=" + salary
				+ "]";
	}
}
