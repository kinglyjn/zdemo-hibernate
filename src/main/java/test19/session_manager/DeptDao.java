package test19.session_manager;

import org.hibernate.Session;

/**
 * dept dao
 * @author zhangqingli
 *
 */
public class DeptDao {
	
	public void save(Dept dept) {
		Session session = SessionUtil.getSession();
		System.out.println(session.hashCode());
		session.save(dept);
	}
}
