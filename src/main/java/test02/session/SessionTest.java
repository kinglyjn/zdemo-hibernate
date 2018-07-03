package test02.session;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * session单线程测试类
 * @author zhangqingli
 *
 */
public class SessionTest {
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
	 * 测试session.flush()
	 * 
	 */
	@Test
	public void testSessionFlush() {
		News news = (News) session.get(News.class, 1);
		news.setTitle("keyllo project2");
		session.flush(); //在session.flush 和 tx.commit处打断点
	}
	
	
	/**
	 * 测试session.refresh()
	 * 
	 */
	@Test
	public void testSessionRefresh() {
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
		
		//在此处打断点，当代码执行到这一句时，更改数据库对应记录
		//（这里执行结果并未像预测的那样打印出最新更改的那条记录，原因是mysql默认的事务隔离级别是REPEATABLE_READ）
		//会强制发送select语句，以使session缓存中对象的状态和数据库表中对应的记录保持一致
		session.refresh(news); 
		
		System.out.println(news); 
	}
	
	
	/**
	 * 测试session.clear()
	 * 
	 */
	@Test
	public void testSessionClear() {
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
		
		session.clear(); //此处打断点，看控制台打印sql的情况
		
		News news2 = (News) session.get(News.class, 1);
		System.out.println(news2);
	}
	
	
	
	/**
	 * save方法测试
	 * 
	 */
	@Test
	public void testSessionSave() {
		News news = new News("java title21", "kinglyjn", new Date());
		news.setId(100); //在save方法之前设置的id是无效的
		
		System.out.println(news); //打印出来的news的id为null
		session.save(news); //
		System.out.println(news); //打印出来的news的id不为null
		//news.setId(100); //持久化对象的id是不能够被修改的，若修改会抛出异常
	}
	
	/**
	 * persist方法测试
	 * 
	 */
	@Test
	public void testSessionPersist() {
		News news = new News("java title3", "kinglyjn", new Date());
		//news.setId(10);
		
		System.out.println(news); 
		session.persist(news); //
		System.out.println(news); 
	}
	
	/**
	 * 测试映射大数据对象
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testSaveBlob() throws IOException {
		News news = new News("java test book", "kinglyjn", new Date());
		news.setContent("this is book content ...");
		
		String path = this.getClass().getResource("/test02/session").getPath() + "/obj-status.png";
		InputStream is = new FileInputStream(path);
		Blob image = Hibernate.getLobCreator(session).createBlob(is, is.available());
		news.setImage(image); //
		session.save(news); //
	}
	
	/**
	 * 测试大数据对象的获取
	 * @throws SQLException 
	 * 
	 */
	@Test
	public void testGetBlob() throws SQLException {
		News news = (News) session.get(News.class, 4);
		Blob image = news.getImage();
		InputStream is = image.getBinaryStream();
		System.out.println(is);
	}
	
	/**
	 * get/load方法测试
	 * 
	 */
	@Test
	public void testSessionGetAndLoad() {
		News news = (News) session.get(News.class, 1);
		//News news = (News) session.load(News.class, 1);
		System.out.println(news.getClass());
		session.close();
		System.out.println(news);
	}
	
	/**
	 * update方法测试
	 * 更新一个游离对象需要显式调用update方法
	 * 
	 */
	@Test
	public void testSessionUpdate() {
		News news = (News) session.get(News.class, 1);
		
		tx.commit();
		session.close();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		news.setTitle("java project03"); //此时的news处于游离状态
		session.update(news); //将游离状态的news对象转变为持久化状态
	}
	
	/**
	 * saveOrUpdate方法测试
	 * 
	 */
	@Test
	public void testSessionSaveOrUpdate() {
		News news = new News("java project4", "kinglyjn", new Date());
		news.setId(1);
		session.saveOrUpdate(news); //这时候会执行update方法（因为oid不为空）
	}
	
	/**
	 * delete方法测试
	 * 
	 */
	@Test
	public void testSessionDelete() {
		News news = new News(); //测试删除游离对象
		news.setId(2);
		session.delete(news);
		
		//News news = (News) session.get(News.class, 3); //测试持久化对象
		//session.delete(news);
		//System.out.println(news);
	}
	
	/**
	 * evict方法测试
	 * 
	 */
	@Test
	public void testSessionEvict() {
		News news1 = (News) session.get(News.class, 6);
		News news2 = (News) session.get(News.class, 7);
		
		news1.setTitle("java project111");
		news2.setTitle("java project222");
		
		session.evict(news2); //将持久化对象news2从session缓存中移除，tx.commit时只会发送一条关于news1的更新语句
	}
	
	
	/**
	 * doWork方法测试
	 * 使用原生connection操作数据库
	 */
	@Test
	public void testSessionDoWork() {
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				//原生的connection：com.mysql.jdbc.JDBC4Connection@6ea78d3d
				System.out.println(connection); 
				//使用原生connection调用数据库存储过程
				//...
			}
		});
	}
}
