
基本配置示例：
	n方：
    <many-to-one name="customer" class="Customer" column="CUSTOMER_ID"/>
        
	1方：
    <set name="orders" table="T_ORDER" inverse="true" cascade="delete,save-update" orderby="ID">
        <key column="CUSTOMER_ID"/>
        <one-to-many class="Order" />
    </set>

	说明:
		inverse属性：
			在hibernate中通过对inverse属性来决定双向关联关系的哪一方来维护表与表之间的关联关系。
			insert=false的为主控方，inverse=true的为被控方，由主动方来维护关联关系。在没有设置
			默认情况下，两方都维护关联关系。
			
			在n-1关系中，将n方设置为主控方有助于性能的改善（如果要国家元首记住全国人民的名字是不太
			可能的，但要全国人民记住国家元首的名字则要容易的多）
			
			在n-1关系中，若将1方设置为主控方，则会额外多出update语句，而且插入时也无法同时插入外键列，
			因而无法为外键添加非空约束。
		
		cascade属性：（建议不设置该属性，建议使用手工的方式来处理）
			none: 当session操作当前对象时，忽略其他关联的对象（默认）
			persist: 当通过session的persist方法来保存当前对象时，会级联保存所有关联的和新建的临时对象
			save-update: 当通过session的save、update、saveOrUpdate方法来保存和更新当前对象时，级联
						 保存或更新所有关联的新建的临时对象，并且级联更新所有关联的游离对象
			delete: 当通过session的delete方法来删除当前对象时，会级联删除所有关联的对象
			delete-orphan: 删除所有和当前对象接触关联关系的对象
			all-delete-orphan: 包含delete和delete-orphan行为
			all: 包含save-update、persist、merge、delete、lock、replicate、evict、refresh行为
			//
			merge: 当通过session的merge方法来保存当前对象时，会级联融合所有关联的游离对象
			lock: 当通过session的lock方法将当前游离对象加入到session缓存中时，会把所有关联的游离对象也
				  加入到session缓存中
			replicate: 当通过session的replicate方法复制当前对象时，会级联复制所有关联的对象
			evict: 当通过session的evict方法从session缓存中清除对象时，会级联清除所有关联的对象
			refresh: 当通过session的refresh方法从数据库中刷新数据到session缓存中时，会级联刷新所有关联的对象
		
		order-by属性：
			hibernate通过select语句，当数据库中检索集合对象时，利用order-by子句进行排序
			order-by属性中可以加入sql函数
	
	
	
测试结果：
	测试插入数据
		1. 先插入1的一方，再插入n的一方
			如果在1方配置inverse=false（默认）测试结果为3条插入语句和2条更新语句（因为1方也要维护关系）
			如果在1方配置inverse=true（放弃维护关联关系）测试结果为3条插入语句（推荐）
		2. 先插入n的一方，再插入1的一方
			如果在1方配置inverse=false（默认）测试结果为3条插入语句和4条更新语句（因为两方都要维护关系）
			如果在1方配置inverse=true（放弃维护关联关系）测试结果为3条插入语句和2条更新语句（仅n方维护关系）
	
	测试查询数据
		1. System.out.println(customer.getClass()); //xx.Customer
		   System.out.println(customer.getOrders().getClass()); //org.hibernate.collection.internal.PersistentSet（hibernate的Set实现类，具有延迟加载性）
		2. 注意延迟加载异常
	
	测试更新数据
		1. customer.getOrders().iterator().next().setName("order-1"); //ok
	
	测试删除数据
		1. 默认没有配置级联关系的情况下，并且n表中存在1表中被删除数据的引用，则删除1表这条数据将会抛出异常！
	
	
	
	
	
	
	
	
	