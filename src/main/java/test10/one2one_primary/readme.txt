
1-1主键映射：
	一端的主键生成器使用foreign策略，表明根据"对方"的主键来生成自己的主键，自己并不能独立生成主键。
	<param>子元素指定当前持久化类的哪个属性作为对方。
	
	采用foreign主键生成器策略的一端需增加one-to-one元素映射关联属性，其one-to-one属性还应增加
	constrained="true"属性，另一端增加one-to-one属性映射关联关系。
	
	contrained约束是指为当前持久化类对应的数据库表的主键添加一个外键约束，指向被关联的对象（“对方”）
	所对应的数据库表主键。
	
	
	基本配置示例：
	
		主键被动方:（mgr）
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="foreign">
            	<param name="property">dept</param>
            </generator>
        </id>
        ...
        <one-to-one name="dept" class="Dept" constrained="true"/>
        
        主键主动方:（dept）
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
        ...
        <one-to-one name="mgr" class="Mgr"/>
        
        
     
    测试结果：
    	测试插入：
    		无论先插入哪一个都不会有多余的update语句，因为如果先插入主键被动方的话，此时session会等待主键
    		主动方先插入数据，插入完成之后，主键被动方再插入数据。因此无论插入的顺序怎么样，都不会有多余update
    	
    	测试查询：
    		查询主键主动方会使用左外连接查询，一次性初始化主键主动方和主键被动方，不会有延迟加载异常
    		查询主键被动方会使用延迟加载，使用关系属性注意可能出现延迟加载异常	
    	
    	测试更新：（略）
    	测试删除：（略）

	
    		
    		   
	
	