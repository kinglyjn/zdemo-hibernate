
hibernate检索策略：
	------------------------
	检索数据时应注意的两个问题：
		1.不浪费内存
			当hibernate从数据库中加载Customer对象时，如果同时加载所有关联的Order对象，而程序实际上仅仅需要访问
			Customer对象，那么这些关联的Order对象就白白浪费了许多内存。
		2.更高效的查询效率
			发送尽可能少的sql语句导数据库。
	------------------------
			
	
	
	类级别的检索策略:
		类级别的可选的检索策略包括立即检索策略和延迟检索，默认为延迟检索（即lazy=true）
		
		*立即检索：立即加载检索方法指定的对象（相应的对象不是代理对象）
		*延迟检索：延迟加载检索方法指定的对象（相应的对象是代理对象，在使用具体的属性时，对代理对向进行初始化）
		
		如果程序架子啊一个对象是为了访问它的属性，可以采取立即检索；
		如果程序加载一个持久化对象的目的仅仅是为了获取它的引用，则可以使用延迟检索（注意不要出现延迟加载异常！）
		
		无论<class>元素的lazy属性是true还是false，session的get方法及query的list方法在类级别总是使用立即加载策略。
		即class元素的lazy属性的配置只影响load方法。若<class>元素的lazy属性为true，session的load方法不会执行查询
		数据表的select语句，仅返回代理类对象的实例，该代理对象有如下特征：
		1. 由hibernate在运行时采用cglib工具动态生成
		2. hibernate创建代理实例时，仅仅初始化其oid属性
		3. 在应用程序第一次访问代理类实例的非oid属性时，hibernate才会初始化代理类实例。
		
		
	一对多和多对多的检索策略：
		在映射文件中，用set元素来配置一对多关联和多对多关联，set元素有lazy和fetch属性。
		*lazy属性主要决定orders集合被初始化的时机，即到底是在加载customer对象时就被初始化，还是程序访问orders集合时
		 才被初始化。
		 	1. n-1或n-n的集合属性	默认使用懒加载的检索策略
		 	2. 可以通过设置set元素的lazy属性来修改默认的检索策略，默认为true，并不建议设置为false
		 	3. lazy还可以设置为extra（增强的延迟检索），该取值会尽可能延迟集合属性的初始化时机
		 	4. 可以使用Hibernate.initialize(customer.getOrders())来强制初始化集合属性
		 	
		*fetch取值为"select"或"subselect"时，决定初始化orders集合的查询语句的形式。
			1. 默认值为"select"，通过正常的方式来初始化set集合
				select xx from t_order o where o.id in(?,?);
			2. 当取值设置值为"subselect"，通过子查询的方式来初始化所有的set集合，子查询作为where子句的in的条件出现。
				select xx from t_order o where o.id in (select id from t_order where customer_id in(?,?))
			   此时lazy属性lazy属性有效，但batch-size属性失效。
			3. 若取值为"join"，则在加载1的一端的对象时，使用迫切左外连接（使用左外连接查询，且把集合属性进行初始化）
			   的方式检索n的一端的集合属性，lazy属性会被忽略，但HQL查询会忽略fetch="join"的设置。
			   
		*set元素的betch-size属性用来为延迟检索策略或立即检索策略设定批量检索的数量，
		 设定一次初始化set集合的个数（默认情况下为fetch="select"，初始化sql语句为 
		 	select xx from t_order o where o.id in(?,?)）。
		 	批量检索能减少select语句的数目，提高延迟检索或立即检索的运行性能。
		 	
		 	
	多对一和一对一关联的检索策略：
		和<set>一样，<many-to-one>元素也有一个lazy属性和一个fetch属性。
		1. lazy取值为proxy和false分别代表对相应的属性采用延迟检索和立即检索
		2. fetch取值为join，表示使用迫切左外连接的方式初始化n关联的1的一端的属性，fetch="join"将忽略lazy属性
		3. batch-size属性设置在1端class元素中
		   <class name="Customer" table="T_CUSTOMER" lazy="true" batch-size="5"> //一次初始化1端代理对象的个数
			
	
		 	
		