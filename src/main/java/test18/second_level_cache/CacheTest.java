package test18.second_level_cache;

import java.util.List;

import org.hibernate.Query;
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
 * 
 * @author zhangqingli
 * 
 */
public class CacheTest {
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
	public void destory() {
		tx.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void initDB() {
		//
	}
	
	/**
	 * 测试一级缓存
	 * 测试结果：只向数据库发送一条sql查询语句（因为有一级缓存即session事务级别的缓存）
	 * 
	 */
	@Test
	public void testFirstLevelCache() {
		Dept dept = (Dept) session.get(Dept.class, 1);
		System.out.println(dept);
		
		dept = (Dept) session.get(Dept.class, 1);
		System.out.println(dept);
	}
	
	/**
	 * 测试二级缓存（类缓存）
	 * 
	 */
	@Test
	public void testSecondLevelCache() {
		Dept dept = (Dept) session.get(Dept.class, 1);
		System.out.println(dept);
		
		session.close();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		dept = (Dept) session.get(Dept.class, 1);
		System.out.println(dept);
	}
	
	/**
	 * 测试二级缓存（集合缓存）
	 * 
	 */
	@Test
	public void testSecondLevelCache2() {
		Dept dept = (Dept) session.get(Dept.class, 1);
		System.out.println(dept);
		System.out.println(dept.getEmps());
		
		session.close();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		dept = (Dept) session.get(Dept.class, 1);
		System.out.println(dept);
		System.out.println(dept.getEmps());
	}
	
	
	/**
	 * 测试查询缓存
	 * 
	 * 在cfg配置文件中设置<property name="cache.use_query_cache">true</property>
	 * 并且代码中需要调用query的query.setCacheable(true)方法
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testSecondLevelCacheWithQuery() {
		String hql = "FROM Emp";
		Query query = session.createQuery(hql);
		query.setCacheable(true);
		
		List<Emp> emps = query.list();
		System.out.println(emps);
		
		emps = query.list();
		System.out.println(emps);
	}
	
}
