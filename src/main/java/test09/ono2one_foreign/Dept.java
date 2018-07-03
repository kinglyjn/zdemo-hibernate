package test09.ono2one_foreign;

/**
 * dept entity
 * @author zhangqingli
 *
 */
public class Dept {
	private Integer id;
	private String name;
	private Mgr mgr;
	
	public Dept() {
		super();
	}
	public Dept(String name) {
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
	public Mgr getMgr() {
		return mgr;
	}
	public void setMgr(Mgr mgr) {
		this.mgr = mgr;
	}

	@Override
	public String toString() {
		return "Dept [id=" + id + ", name=" + name + "]";
	}
}
