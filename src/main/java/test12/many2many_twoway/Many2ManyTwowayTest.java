package test12.many2many_twoway;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * 单线程 双向n-n映射测试
 * @author zhangqingli
 *
 */
public class Many2ManyTwowayTest {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction tx;
	
	@Before
	public void init() {
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
	}
	
	@After
	public void destroy() {
		tx.commit();
		session.close();
		sessionFactory.close();
	}
	
	/**
	 * 测试插入数据
	 * 
	 */
	@Test
	public void testSave() {
		Category category1 = new Category("category-5");
		Category category2 = new Category("category-6");
		Item item1 = new Item("item-5");
		Item item2 = new Item("item-6");
		
		category1.getItems().add(item1);
		category1.getItems().add(item2);
		category2.getItems().add(item1);
		category2.getItems().add(item2);
		item1.getCategories().add(category1);
		item1.getCategories().add(category2);
		item2.getCategories().add(category1);
		item2.getCategories().add(category2);
		
		session.save(category1);
		session.save(category2);
		session.save(item1);
		session.save(item2);
	}
	
	/**
	 * 测试查询数据
	 * 
	 */
	@Test
	public void testGet() {
		/*
		Category catagory = (Category) session.get(Category.class, 1);
		System.out.println(catagory.getClass()); //xx.Category
		System.out.println(catagory.getItems().getClass()); //xx.PersistentSet（注意延迟加载异常）
		System.out.println(catagory.getItems().iterator().next().getClass()); //xx.Item
		*/
		
		Item item = (Item) session.get(Item.class, 1);
		System.out.println(item.getClass()); //xx.Item
		System.out.println(item.getCategories().getClass()); //PersistentSet
		System.out.println(item.getCategories().iterator().next().getClass()); //xx.Category
	}
}
