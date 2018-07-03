package test01.hello;

import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

/**
 * NewsTest
 * @author zhangqingli
 *
 */
public class NewsTest {
	
	@Test
	public void test01() {
		//1. 创建sessionFactory
		//ServiceRegistry是hibernate4.x新添加的对象，hibernate的任何配置和服务都需要在该对象中注册后才能生效
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		SessionFactory sf = cfg.buildSessionFactory(serviceRegistry);
		
		//2. 获取sesison
		Session session = sf.openSession();
		
		//3. 在事务之中操作数据库
		session.beginTransaction();//
		News news = new News("java", "ghoslin", new Date());
		session.save(news);
        session.getTransaction().commit();//
        
        //4. 关闭session和sessionFactory
        session.close();
        sf.close();
	}
}
