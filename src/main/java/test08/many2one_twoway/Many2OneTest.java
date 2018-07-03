package test08.many2one_twoway;

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
 * 单线程n-1双向关联关系映射测试
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
		customer1.getOrders().add(order1);
		customer1.getOrders().add(order2);
		
		//1. 先插入1的一方，再插入n的一方
		//   如果在1方配置inverse=false（默认）测试结果为3条插入语句和2条更新语句（因为1方也要维护关系）
		//   如果在1方配置inverse=true（放弃维护关联关系）测试结果为3条插入语句（推荐）
		session.save(customer1);
		session.save(order1);
		session.save(order2);
		
		//2. 先插入n的一方，再插入1的一方
		//   如果在1方配置inverse=false（默认）测试结果为3条插入语句和4条更新语句（因为两方都要维护关系）
		//	 如果在1方配置inverse=true（放弃维护关联关系）测试结果为3条插入语句和2条更新语句（仅n方维护关系）
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
		Customer customer = (Customer) session.get(Customer.class, 3);
		System.out.println(customer.getClass()); //xx.Customer
		System.out.println(customer.getOrders().getClass()); //org.hibernate.collection.internal.PersistentSet（hibernate的Set实现类，具有延迟加载性）
		
		//session.close();
		System.out.println(customer.getOrders()); //注意延迟加载异常
	}
	
	/**
	 * 测试更新数据
	 * 
	 */
	@Test
	public void testUpdate() {
		Customer customer = (Customer) session.get(Customer.class, 3);
		//
		customer.getOrders().iterator().next().setName("order-1");
		/*
		Order order = new Order("order-121"); //需要设置级联属性 cascade="save-update"
		order.setCustomer(customer);
		customer.getOrders().add(order);
		*/
	}
	
	/**
	 * 测试删除数据
	 *
	 */
	@Test
	public void testDelete() {
		Customer customer1= (Customer) session.get(Customer.class, 5);
		
		//1. 默认没有配置级联关系的情况下，并且n表中存在1表中被删除数据的引用，则删除1表这条数据将会抛出异常！
		session.delete(customer1);
	}
}
