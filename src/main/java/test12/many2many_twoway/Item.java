package test12.many2many_twoway;

import java.util.HashSet;
import java.util.Set;

/**
 * item entity
 * 
 * @author zhangqingli
 *
 */
public class Item {
	private Integer id; 
	private String name;
	private Set<Category> categories = new HashSet<Category>();
	
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
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + "]";
	}
}
