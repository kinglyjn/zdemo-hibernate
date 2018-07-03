
继承映射：
	对于面向对象的程序设计语言，继承和多态是两个最基本的概念。hibernate的继承映射可以理解持久化类之间的继承关系。
	例如：人和学生之间，学生继承了人，可以认为学生是一个特殊的人，如果对人查询，学生的实例也将被得到。
	
	hibernate支持三种映射策略，分别是：
		使用subclass进行映射、使用joined-subclass进行映射、使用union-subclass进行映射。
	
	
	使用subclass进行映射
		将域模型中的每一个实体对象映射到一个独立的表中，也就是说不用在关系数据模型中考虑域模型中的继承关系和多态。
		因为父类和子类的实例全部保存在一张表中，因此需要在该表中增加一列来区分每行记录到底是哪个类的实例，这个列
		被称为辨识者列（discriminator）。
		
		在这种映射策略下，使用subclass来映射子类，使用class或subclass的discriminator-value属性指定辨别者列
		的值。所有子类定义的字段都不能有非空约束，因为如果为子类字段添加非空约束，那么父类的实例在那些字段上其实
		是并没有值的，这将引起数据库完整性冲突，导致父类的实例无法保存到数据库中。
		
		缺点：
		1.使用的冗余数据（辨别者列）
		2.子类独有的字段不能有非空约束
		3.若继承的层次较深则数据表的字段也比较多
	
		基本配置示例：
		<class name="Person" table="T_PERSON" discriminator-value="PERSON">
	    	<!-- 映射主键 -->
	        <id name="id" type="java.lang.Integer">
	            <column name="ID" />
	            <generator class="native" />
	        </id>
	        
	        <!-- 映射辨别者列 -->
	        <discriminator column="TYPE" type="string"/>
	        
	        <!-- 映射普通属性 -->
	        <property name="name" type="java.lang.String">
	            <column name="NAME" />
	        </property>
	        <property name="age" type="java.lang.Integer">
	            <column name="AGE" />
	        </property>
	        
	        <!-- 映射子类 -->
			<subclass name="Student" discriminator-value="STUDENT">
				<property name="school" type="string" column="SCHOOL"/>
			</subclass>        
    	</class>
	
	
