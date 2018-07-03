package test09.ono2one_foreign;

/**
 * mgr entity
 * @author zhangqingli
 *
 */
public class Mgr {
	private Integer id;
	private String name;
	private Dept dept;

	public Mgr() {
		super();
	}
	public Mgr(String name) {
		this.name = name;
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
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Mgr [id=" + id + ", name=" + name + "]";
	}
}
