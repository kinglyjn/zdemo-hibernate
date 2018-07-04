package test19.session_manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * session工具类
 * @author zhangqingli
 *
 */
public class SessionUtil {
	private static volatile SessionFactory SESSION_FACTORY;
	
	/**
	 * 获取单例的sessionFactory
	 *
	 */
	public static SessionFactory getSessionFactory() {
		if (SESSION_FACTORY==null) {
			synchronized (SessionUtil.class) {
				if (SESSION_FACTORY==null) {
					Configuration cfg = new Configuration().configure();
					ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
					SESSION_FACTORY = cfg.buildSessionFactory(serviceRegistry);
				}
			}
		}
		return SESSION_FACTORY;
	}
	
	/**
	 * 获取当前sesision
	 * 如果配置hibernate.current_session_context_class为thread，那么获取的就是与当前线程绑定的session
	 *
	 */
	public static Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
}
