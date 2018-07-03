
单向n-1关联关系：

		域模型：
		Order--n----------------1-->Customer
			id							id
			name						name
			customer
									 
		关系数据模型：
			ORDER表(ID)
				NAME
				CUSTOMER_ID-------->CUSTOMER表(ID)
										NAME

		
		
		基本配置示例：
        <many-to-one name="customer" class="Customer" column="CUSTOMER_ID"/>
		
		
		
		测试插入数据：
			1. 先插入1的一方，再插入n的一方，测试结果为3条插入语句（推荐）
			2. 先插入n的一方，再插入1的一方，测试结果为3条插入语句，2条更新语句
			   原因是插入n的一方后，此时应用程序不知道关联的外键值，只有当等关联数据插入之后才能根据关联数据的id更新n表外键
		测试查询数据：
			1. System.out.println(order.getClass()); //xx.Order（不是代理对象）
			   System.out.println(order.getCustomer().getClass()); //xx.Customer_$$_jvstddc_1（代理对象）	   
			2. 在使用延迟加载属性之前关闭了session，默认情况下会抛出延迟加载异常，因为这里的
			   Customer关联属性为延迟加载属性，它是一个代理对象，当为这个代理对象初始化时需要
			   查询数据库数据，但此时session已经关闭，无法完成代理对向的初始化工作！   
		测试更新数据：
			1. order.getCustomer().setName("customer-11"); //ok	   
		测试删除数据：
			1. 默认没有配置级联关系的情况下，并且n表中存在1表中被删除数据的引用，则删除1表这条数据将会抛出异常！	   
			   
		
		
		
		
		
		
		
		
		
			   