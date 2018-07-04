package test19.session_manager;

import java.util.HashSet;
import java.util.Set;

/**
 * dept entity
 * @author zhangqingli
 *
 */
public class Dept {
	private int id;
	private String name;
	private Set<Emp> emps = new HashSet<>();
	
	public Dept() {
		super();
	}
	public Dept(String name) {
		super();
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Emp> getEmps() {
		return emps;
	}
	public void setEmps(Set<Emp> emps) {
		this.emps = emps;
	}
	
	@Override
	public String toString() {
		return "Dept [id=" + id + ", name=" + name + "]";
	}
}
