
hibernate自身提供了三种管理session的方法，在hibernate.cfg.xml配置文件中指定session的管理方式即可，
配置方式为：<property name="hibernate.current_session_context_class">thread</property>
可选值包括：
	thread: session对象的生命周期和本地线程绑定，hibernate按照以下规则把session对象与本地线程绑定：
			i) 当threadA第一次调用sessionFactory对象的getCurrentSession()方法时，该方法会创建一个
			   新的session（sessionA）对象，并把该对象与threadA绑定，并将sessionA返回。
			ii) 当threadA再次调用sessionFactory对象的getCurrentSession()方法时，该方法将直接返回
			   sessionA对象。
			iii) 当threadA提交sessionA对象关联的事务时，hibernate将会自动flush sesisonA对象的缓存，
			   然后提交事务，关闭sessionA对象；当threadA撤销对sessionA对象关联的事务时，也会自动关闭
			   sessionA对象。
			iv) 若threadA再次调用sessionFactory对象的getCurrentSession()方法时，该方法又会创建一个
			   新的session（sessionB）对象，把该对象和threadA绑定，并将sessionB返回  
			v) 若session是由thread来管理的，则session在提交或回滚事务就会被关闭   
	jta*: session对象的生命周期和jta事务绑定
	managed: hibernate委托程序来管理session对象的生命周期
		
	
	
	