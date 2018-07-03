
使用joined-subclass进行映射
	采用这种映射策略时，父类实例保存在父类表中，子类实例由父类表和子类表共同存储。因为子类实例也是一个特殊的父类
	实例，因此也必然包含父类实例的属性。于是将子类和父类共有的属性保存在父类表中，将子类独有的字段保存在子类表中
	
	在这种映射策略下，无需使用辨别者列，但需要为每个子类使用key元素映射共有主键。子类增加的属性可以添加非空约束，
	因为子类的属性和父类的属性没有保存在一张表中。
	
	基本配置示例：		
	<class name="Person" table="T_PERSON">
    	<!-- 映射主键 -->
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
        
        <!-- 映射普通属性 -->
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        <property name="age" type="java.lang.Integer">
            <column name="AGE" />
        </property>
        
        <!-- 映射子类 -->
		<joined-subclass name="Student" table="T_STUDENT">
			<key column="STU_ID"/>
			<property name="school" type="string" column="SCHOOL"/>
		</joined-subclass>        
    </class>	
	
