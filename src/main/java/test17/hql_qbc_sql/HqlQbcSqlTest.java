package test17.hql_qbc_sql;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 单线程hql qbc sql查询测试
 * @author zhangqingli
 * 
 */
public class HqlQbcSqlTest {
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
	
	@Test
	public void initDB() {
		//
	}
	
	/**
	 * 测试按参数位置绑定
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testPositionParam() {
		// 创建query对象
		String hql = "from Emp e where e.salary>? and e.email like ?";
		Query query = session.createQuery(hql);
		
		// 绑定参数
		query.setFloat(0, 800).setString(1, "%keyllo%");
		
		// 执行查询
		List<Emp> emps = query.list();
		System.out.println(emps);
	}
	
	/**
	 * 测试按参数名称绑定
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testNamedParam() {
		String hql = "from Emp e where e.salary>:salary and e.email like :email order by e.salary desc";
		Query query = session.createQuery(hql);
		query.setFloat("salary", 1000).setString("email", "%keyllo%");
		
		List<Emp> emps = query.list();
		System.out.println(emps);
	}
	
	/**
	 * 绑定实体类参数
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testEntityParam() {
		String hql = "from Emp e where e.salary>? and e.email like ? and e.dept=?";
		Query query = session.createQuery(hql);
		
		Dept dept = new Dept();
		dept.setId(1);
		query.setFloat(0, 1000).setString(1, "%keyllo%").setEntity(2, dept); //只要为dept的id赋值即可
		
		List<Emp> emps = query.list();
		System.out.println(emps);
	}
	
	/**
	 * 测试分页查询
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testPageQuery() {
		String hql = "from Emp";
		Query query = session.createQuery(hql);
		 
		final int pageNo = 1;
		final int pageSize = 2;
		query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
		
		List<Emp> emps = query.list();
		System.out.println(emps);
	}
	
	/**
	 * 定义命名查询
	 * 需要事先在hbm.xml文件中定义相关的命名查询语句
	 * 好处：更加便于开发
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testNamedQuery() {
		Query query = session.getNamedQuery("queryBySalaryRange");
		query.setFloat("minSalary", 5).setFloat("maxSalary", 8);
		
		List<Emp> emps = query.list();
		System.out.println(emps);
	}
	
	/**
	 * 测试投影查询
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testFieldQuery() {
		String hql = "select e.email,e.salary,e.dept from Emp e where e.dept=:dept";
		Query query = session.createQuery(hql);
		
		Dept dept = new Dept();
		dept.setId(1);
		query.setEntity("dept", dept);
		
		List<Object[]> results = query.list();
		for (Object[] result : results) {
			System.out.println(result[0] + " " + result[1] + " " + result[2]);
		}
	}
	
	/**
	 * 测试投影查询
	 * 优化方案（将返回结果不放在数组而是放在一个Emp对象中，需要Emp有相应的构造方法）
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testFieldQuery2() {
		String hql = "select new Emp(e.email,e.salary,e.dept) from Emp e where e.dept=:dept";
		Query query = session.createQuery(hql);
		
		Dept dept = new Dept();
		dept.setId(1);
		query.setEntity("dept", dept);
		
		List<Emp> emps = query.list();
		for (Emp emp : emps) {
			System.out.println(emp);
		}
	}
	
	/**
	 * 测试分组查询
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testGroupbyQuery() {
		String hql = "select min(e.salary),max(e.salary),e.dept from Emp e group by e.dept having min(salary)>:minSalary";
		Query query = session.createQuery(hql);
		query.setFloat("minSalary", 8);
		
		List<Object[]> results = query.list();
		for (Object[] result : results) {
			System.out.println(result[0] + " " + result[1] + " " + result[2]);
		}
	}
	
	
	/**
	 * 测试左外连接（返回数组组成的list）
	 * 去重方式：只能通过使用distinct关键字去重
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testLeftJoin() {
		String hql = "select distinct d from Dept d left join d.emps";
		Query query = session.createQuery(hql);
		
		List<Dept> depts = query.list();
		System.out.println(depts);
	}
	
	/**
	 * 测试迫切左外连接（加了fetch关键字，返回Dept实体组成的list集合）（推荐使用！）
	 * 去重方式：1.使用distinct关键字
	 * 			2.使用Set集合过滤
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testLeftJoinFetch() {
		String hql = "select distinct d from Dept d left join fetch d.emps";
		Query query = session.createQuery(hql);
		
		List<Dept> depts = query.list();
		for (Dept dept : depts) {
			System.out.println(dept);
			System.out.println(dept.getEmps());
		}
	}
	
	
	
	
	/**
	 * 测试QBC基本查询
	 * 
	 */
	@Test
	public void testQBCBase() {
		// 创建criteria对象
		Criteria criteria = session.createCriteria(Emp.class);
		
		// 添加查询条件：在QBC查询条件中使用Criteria来表示（通过Restrictions静态方法得到）
		criteria.add(Restrictions.eq("email", "kinglyjn@keyllo.com")).add(Restrictions.gt("salary", 6f));
		
		// 执行查询
		Emp emp = (Emp) criteria.uniqueResult();
		System.out.println(emp);
		System.out.println(emp.getDept());
	}
	
	/**
	 * 测试QBC组合条件查询
	 *
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testQBCAndOr() {
		Criteria criteria = session.createCriteria(Emp.class);
		
		// and
		Conjunction conjunction = Restrictions.conjunction();
		Dept dept = new Dept();
		dept.setId(1);
		conjunction.add(Restrictions.like("name", "张", MatchMode.ANYWHERE))
					.add(Restrictions.eq("dept", dept));
		System.out.println(conjunction); //(name like %部% and dept=Dept [id=1, name=null])
		
		// or
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.gt("salary", 5f))
					.add(Restrictions.isNotNull("email"));
		System.out.println(disjunction); //(salary>5 or email is not null)
		
		criteria.add(conjunction);
		criteria.add(disjunction);
		
		List<Emp> emps = criteria.list();
		System.out.println(emps);
	}
	
	/**
	 * 测试QBC统计查询
	 * 
	 */
	@Test
	public void testQBCProjection() {
		Criteria criteria = session.createCriteria(Emp.class);
		
		//统计查询用Projecttion来表示
		criteria.setProjection(Projections.max("salary"));
		System.out.println(criteria.uniqueResult());
	}
	
	/**
	 * 测试QBC统计查询
	 * 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testQBCOrderby() {
		Criteria criteria = session.createCriteria(Emp.class);
		
		//添加排序
		criteria.addOrder(Order.asc("salary"));
		criteria.addOrder(Order.desc("email"));
		//添加分页
		final int pageSize = 3;
		final int pageNo = 3;
		criteria.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
		
		List<Emp> emps = criteria.list();
		System.out.println(emps);
	}
	
	
	
	/**
	 * 测试本地查询
	 * 支持CUID和复杂查询操作，但具有侵入性
	 * 
	 */
	@Test
	public void testSQLQueryInsert() {
		String sql = "INSERT INTO T_DEPT VALUES(?,?)";
		SQLQuery query = session.createSQLQuery(sql);
		
		query.setInteger(0, 7);
		query.setString(1, "营销部");
		
		int result = query.executeUpdate();
		System.out.println(result); //成功为1
	}
	@Test
	public void testSQLQueryDelete() {
		String sql = "DELETE FROM T_DEPT WHERE ID=?";
		SQLQuery query = session.createSQLQuery(sql);
		
		query.setInteger(0, 7);
		query.executeUpdate();
	}
	@Test
	public void testSQLQueryUpdate() {
		String sql = "UPDATE T_DEPT SET NAME=? WHERE NAME=?";
		SQLQuery query = session.createSQLQuery(sql);
		
		query.setString(0, "营销部");
		query.setString(1, "组织部");
		query.executeUpdate();
	}
	
}
