package test08.many2one_twoway;

import java.util.HashSet;
import java.util.Set;

/**
 * customer entity
 * 
 * @author zhangqingli
 *
 */
public class Customer {
	private Integer id;
	private String name;
	 /*
	 * 关联属性：
	 * 1. 关联属性orders必须为接口类型，因为hibernate使用的是其内置的Set实现，而不是javaSE的Set实现 
	 * 2. 关联属性orders必须初始化，防止空指针异常 
	 */
	private Set<Order> orders = new HashSet<Order>();

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
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}
}
