package test06.component_orm;

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
 * 组合关系对象持久化测试
 * @author zhangqingli
 *
 */
public class ComponentTest {
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
	
	@Test
	public void testSave() {
		Worker worker = new Worker("kinglyjn", new Salary(1234));
		session.save(worker);
	}
	
	@Test
	public void testGet() {
		Worker worker = (Worker) session.get(Worker.class, 1);
		System.out.println(worker.getSalary());
	}
}
