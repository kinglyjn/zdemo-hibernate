映射组成关系：
	hibernate将持久化类的属性划分为两种：
		实体类型（entity）：有oid，可以被单独持久化，有独立的生命周期
		值类型（value）：没有oid，不能被单独持久化，生命周期需要依赖于所属的持久化类对象的生命周期
		
本实验ORM对应关系：
	Worker(id name salary)-------------WORKER(ID NAME MONTH_SALARY YEAR_SALARY)
		
	
