package test07.many2one_oneway;

/**
 * order entity
 * 
 * @author zhangqingli
 *
 */
public class Order {
	private Integer id;
	private String name;
	private Customer customer;

	public Order() {
		super();
	}
	public Order(String name) {
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", customer=" + customer
				+ "]";
	}
}
