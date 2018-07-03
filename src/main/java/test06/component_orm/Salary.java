package test06.component_orm;

/**
 * salary value obeject
 * @author zhangqingli
 *
 */
public class Salary {
	private float monthSalary;
	private float yearSalary;
	private Worker worker;
	
	public Salary() {
		super();
	}
	public Salary(float monthSalary) {
		this.monthSalary = monthSalary;
	}

	public float getMonthSalary() {
		return monthSalary;
	}
	public void setMonthSalary(float monthSalary) {
		this.monthSalary = monthSalary;
	}
	public float getYearSalary() {
		return yearSalary;
	}
	public void setYearSalary(float yearSalary) {
		this.yearSalary = yearSalary;
	}
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	@Override
	public String toString() {
		return "Salary [monthSalary=" + monthSalary + ", yearSalary="
				+ yearSalary + "]";
	}
}
