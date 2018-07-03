package test10.one2one_primary;

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
 * 单线程1-1主键映射测试
 * @author zhangqingli
 *
 */
public class One2OnePrimarykeyTest {
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
		Dept dept = new Dept("dept-1");
		Mgr mgr = new Mgr("mgr-1");
		
		dept.setMgr(mgr);
		mgr.setDept(dept);
		
		session.save(mgr);
		session.save(dept);
	}
	
	/**
	 * 测试查询
	 *
	 */
	@Test
	public void testGet() {
		/*
		Dept dept = (Dept) session.get(Dept.class, 1); //使用左外连接查询（不会出现延迟加载异常）
		System.out.println(dept.getClass()); //xx.Dept
		System.out.println(dept.getMgr().getClass());//xx.Mgr
		System.out.println(dept.getMgr());
		*/
		
		Mgr mgr = (Mgr) session.get(Mgr.class, 1);
		System.out.println(mgr.getClass()); //xx.Mgr
		System.out.println(mgr.getDept().getClass()); //xx.Dept_$$_jvst74a_0（注意可能出现懒加载异常）
	}
}
