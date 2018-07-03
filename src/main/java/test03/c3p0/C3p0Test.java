package test03.c3p0;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;

/**
 * c3p0连接池测试
 * @author zhangqingli
 *
 */
public class C3p0Test {

	public static void main(String[] args) {
		//获取sessionFactory、session
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();

		//在事务范围内操作数据库
		Transaction tx = session.beginTransaction();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("connection: " + connection); //connection: com.mchange.v2.c3p0.impl.NewProxyConnection@63144ceb
			}
		});
		
		//提交事务，关闭session和sessionFactory
		tx.commit();
		session.close();
		sessionFactory.close();
	}
}
