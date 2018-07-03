
采用union-subclass元素的继承映射：
	采用此种映射可以实现将每一个实体对象映射到一个独立的表中，子类增加的属性可以添加非空约束，
	即父类实例的数据保存在父类表中，子类实例的数据保存在子类表中，子类表的字段数目等于父类表
	的字段数目加上子类增加字段数目。
	在这种映射策略之下，既不需要鉴别者列，也无需使用key元素来指定共有主键。
	使用union-subclass映射策略时不能使用identify的主键生成策略，因为同一类继承层次中所有实
	体类都需要使用同一个主键种子，即多个持久化实体对应的记录的主键值应该是连续的。受此影响，也
	不该使用native主键生成策略，因为native会根据数据库来选择使用identity还是sequence。
	
	
	基本配置示例：
	<class name="Person" table="T_PERSON">
       	<!-- 映射主键（注意不能使用identity或native主键生成策略） -->
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="hilo" />
        </id>
        
        <!-- 映射普通字段 -->
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        <property name="age" type="java.lang.Integer">
            <column name="AGE" />
        </property>
        
        <!-- 映射子类 -->
		<union-subclass name="Student" table="T_STUDENT">
			<property name="school" type="string" column="SCHOOL"/>
		</union-subclass>        
    </class>
    
    
    
    测试结果：
    --------------------
    测试插入数据
     1. 插入数据的效率较高
     
    测试查询数据
	 1. 查询父类的效率较低，需要用到子查询查询父表和子表组成的联合表
	 2. 查询子类的效率较高，只需要查询子表即可
	 
	测试更新数据
	 1. 更新数据的效率较差！
	 
	测试删除数据
	 1. 删除数据的效率较高
	
	