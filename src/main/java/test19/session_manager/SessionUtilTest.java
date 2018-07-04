package test19.session_manager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * 测试sessionUtil
 * @author zhangqingli
 *
 */
public class SessionUtilTest {
	
	@Test
	public void test() {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		System.out.println(session.hashCode());
		
		DeptDao deptDao = new DeptDao();
		Dept dept = new Dept("宣传部");
		deptDao.save(dept);
		deptDao.save(dept);
		deptDao.save(dept);
		
		//若session是由thread来管理的，则session在提交或回滚事务就会被关闭
		tx.commit(); 
		System.out.println(session.isOpen());
	}
}
