package test09.ono2one_foreign;

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
 * 单线程1-1唯一外键映射测试
 * @author zhangqingli
 *
 */
public class One2OneForeignkeyTest {
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
		Dept dept = new Dept("dept-2");
		Mgr mgr = new Mgr("mgr-2");
		
		dept.setMgr(mgr);
		mgr.setDept(dept);
		
		//先保存1的一方，再保存伪n的一方
		session.save(dept);
		session.save(mgr);
	}
	
	/**
	 * 测试查询
	 * 
	 */
	@Test
	public void testGet() {
		/*
		Dept dept = (Dept) session.get(Dept.class, 1); //使用的是左外连接查询，会将dept和mgr都进行初始化
		System.out.println(dept.getClass()); //xx.Dept
		System.out.println(dept.getMgr().getClass()); //xx.Mgr
		System.out.println(dept);
		System.out.println(dept.getMgr());
		*/
		
		Mgr mgr = (Mgr) session.get(Mgr.class, 1);
		System.out.println(mgr.getClass()); //Mgr
		System.out.println(mgr.getDept().getClass()); //xx.Dept_$$_jvst703_0（代理对象，注意延迟加载异常）
		System.out.println(mgr.getDept());
	}
	
	/**
	 * 测试更新
	 * 
	 */
	@Test
	public void testUpdate() {
		Dept dept = (Dept) session.get(Dept.class, 1);
		dept.getMgr().setName("zhangsan");
	}
	
	/**
	 * 测试删除
	 * 需要配置级联删除属性
	 * 
	 */
	@Test
	public void testDelete() {
		Dept dept = (Dept) session.get(Dept.class, 1);
		session.delete(dept);
	}
}
