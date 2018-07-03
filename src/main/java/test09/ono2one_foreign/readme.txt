
1-1唯一外键映射基本配置示例：

	伪n方 [mgr]
	<many-to-one name="dept" class="Dept" column="DEPT_ID" unique="true"/>
	
	1方 [dept]
	<one-to-one name="mgr" class="Mgr" property-ref="dept"/>
		
	[注]property-ref用来指定 
		from dept left join mgr on dept.id=mgr.dept_id
	   	连接查询中 连接条件列所对应的对方属性（mgr.dept所对应的列为mgr.dept_id）	