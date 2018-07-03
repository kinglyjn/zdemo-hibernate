package test11.many2many_oneway;

/**
 * item entity
 * 
 * @author zhangqingli
 *
 */
public class Item {
	private Integer id; 
	private String name;
	
	public Item() {
		super();
	}
	public Item(String name) {
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
		return "Item [id=" + id + ", name=" + name + "]";
	}
}
