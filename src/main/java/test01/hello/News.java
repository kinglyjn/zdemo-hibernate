package test01.hello;

import java.util.Date;

/**
 * Hibenrate持久化类
 * 
 * 要求：
 * 1. 提供一个无参的构造方法，使hibernate可以使用 Constructor.newInstance() 来实例化持久化类
 * 2. 提供一个标识属性，通常映射为数据库表的主键字段，如果没有该属性，一些功能将不起作用，如 session.saveOrUpdate()
 * 3. 为类的持久化字段声明 set/get 方法，hibernate对javaBeans风格的属性实行持久化
 * 4. 使用非final类，在运行时生成代理是hibernate的一个重要的功能。如果持久化类没有实现任何接口，hibernate使用cglib生成代理，如果使用的是final类，则无法生成cglib代理
 * 5. 重写equals和hashCode方法，如果需要把持久化类的实例放到Set中（当需要用到关联映射时），则应该重写这两个方法
 * 
 * @author zhangqingli
 *
 */
public class News {
	private Integer id;
	private String title;
	private String author;
	private Date date;
	
	public News() {
		super();
	}
	public News(String title, String author, Date date) {
		this.title = title;
		this.author = author;
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", author=" + author
				+ ", date=" + date + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
