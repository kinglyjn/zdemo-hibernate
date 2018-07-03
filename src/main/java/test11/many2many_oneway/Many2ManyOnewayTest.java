package test11.many2many_oneway;

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
 * 单线程 单向n-n映射测试
 * @author zhangqingli
 *
 */
public class Many2ManyOnewayTest {
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
	 * 测试保存
	 * 
	 */
	@Test
	public void testSave() {
		Category category1 = new Category("category-1");
		Category category2 = new Category("category-2");
		Item item1 = new Item("item-1");
		Item item2 = new Item("item-2");
		
		category1.getItems().add(item1);
		category1.getItems().add(item2);
		category2.getItems().add(item1);
		category2.getItems().add(item2);
		
		session.save(category1);
		session.save(category2);
		session.save(item1);
		session.save(item2);
	}
	
	/**
	 * 测试查询
	 * 
	 */
	@Test
	public void testGet() {
		Category category = (Category) session.get(Category.class, 1);
		System.out.println(category.getClass()); //xx.Category
		System.out.println(category.getItems().getClass()); //xx.PersistentSet
		System.out.println(category.getItems());
	}
}
