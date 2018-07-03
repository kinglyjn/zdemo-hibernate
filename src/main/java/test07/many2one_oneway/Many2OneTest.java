package test07.many2one_oneway;

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
 * 单线程单向n-1关联关系映射测试
 * @author zhangqingli
 *
 */
public class Many2OneTest {
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
	 */
	@Test
	public void testSave() {
		Order order1 = new Order("order-1");
		Order order2 = new Order("order-2");
		Customer customer1 = new Customer("customer-1");
		
		order1.setCustomer(customer1);
		order2.setCustomer(customer1);
		
		//1. 先插入1的一方，再插入n的一方，测试结果为3条插入语句（推荐）
		/*
		session.save(customer1);
		session.save(order1);
		session.save(order2);
		*/
		
		//2. 先插入n的一方，再插入1的一方，测试结果为3条插入语句，2条更新语句
		//（原因是插入n的一方后，此时应用程序不知道关联的外键值，只有当等关联数据插入之后才能根据关联数据的id更新n表外键）
		/*
		session.save(order1);
		session.save(order2);
		session.save(customer1);
		*/
	}
	
	/**
	 * 测试查询数据
	 * 
	 */
	@Test
	public void testGet() {
		Order order = (Order) session.get(Order.class, 1);
		System.out.println(order.getClass()); //xx.Order（不是代理对象）
		System.out.println(order.getCustomer().getClass()); //xx.Customer_$$_jvstddc_1（代理对象）
		
		//session.close(); //默认情况下会抛出延迟加载异常，因为这里的Customer关联属性为延迟加载属性(lazy=proxy)，它是一个代理对象，当为这个代理对象初始化时需要查询数据库数据，但此时session已经关闭，无法完成代理对向的初始化工作！
		System.out.println(order.getCustomer()); //默认延迟加载（注意延迟加载异常）
	}
	
	/**
	 * 测试更新
	 * 
	 */
	@Test
	public void testUpdate() {
		Order order = (Order) session.get(Order.class, 1);
		order.getCustomer().setName("customer-11");
	}
	
	/**
	 * 测试删除
	 *
	 */
	@Test
	public void testDelete() {
		Customer customer1= (Customer) session.get(Customer.class, 1);
		
		//1. 默认没有配置级联关系的情况下，并且n表中存在1表中被删除数据的引用，则删除1表这条数据将会抛出异常！
		session.delete(customer1);
	}
}
