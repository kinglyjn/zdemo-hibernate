package test07.many2one_oneway;

/**
 * customer entity
 * 
 * @author zhangqingli
 *
 */
public class Customer {
	private Integer id;
	private String name;

	public Customer() {
		super();
	}
	public Customer(String name) {
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}
}
