package test13.subclass;

import java.util.List;

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
 * 单线程subclass映射测试
 * @author zhangqingli
 *
 */
public class SubclassTest {
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
	
	/**
	 * 测试插入数据
	 * 
	 */
	@Test
	public void testSave() {
		Person person = new Person("小娟", 20);
		Student student = new Student("张三", 21, "清华大学");
		session.save(person);
		session.save(student);
	}
	
	/**
	 * 测试查询数据
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testQuery() {
		/*
		List<Person> persons = session.createQuery("from Person").list();
		for (Person person : persons) {
			System.out.println(person.getClass()); //类型既有Person也有Student
		}
		*/
		
		List<Student> students = session.createQuery("from Student").list();
		for (Student student : students) {
			System.out.println(student.getClass()); //类型只有Student
		}
	}
	
}
