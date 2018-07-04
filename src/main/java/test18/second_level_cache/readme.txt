
概述:
	缓存的概念：
	缓存（cache）是计算机领域非常通用的概念，它介于应用程序和永久性数据（永久性数据源如数据库或硬盘上的文件）之间，
	其作用是降低应用程序直接读写永久性数据存储源的频率，从而提高应用的性能。缓存中的数据是数据存储源中数据的拷贝。
	缓存的物理介质通常是内存。
	
	hibernate提供了两个级别的缓存：
	第一级缓存是session缓存，它是属于事务范围的缓存，是由hibernate管理的。
	第二级缓存是sessionFactory级别的缓存，它是属于进程范围内的缓存。
		第二级缓存又可以分为两种：
		内置二级缓存：hibernate自带的，不可以卸载，通常在hibernate的初始化阶段，hibernate会把映射元数据和预定义
		            的sql语句放到sessionFactory缓存中，映射元数据是映射文件中数据（.hbm.xml文件中的数据）的复
		            制。该内置缓存是只读的。
		外置二级缓存：一个可配置的缓存插件。在默认情况下，sessionFactory不会启用这个缓存插件。外置缓存中的数据是
					数据库数据的复制，外置缓存的物理介质可以是内存或硬盘。
					
	适合放入二级缓存中的数据：
		1.很少被修改，总是被查询的数据放入二级缓存中
		2.不是很重要的，允许出现偶尔的并发问题的数据
	不适合放入二级缓存中的数据：
		1.经常被修改的数据
		2.重要的核心数据（比如财务数据）
		3.与其他应用程序共享的数据
			
			
	
hibernate二级缓存的并发访问策略：
	两个并发的事务同时访问持久化层缓存的相同数据时，就有可能出现各类并发问题。
	hibernate二级缓存可以设定一下4种类型的并发访问策略，每一种访问策略对应一种事务的隔离级别。
	分别是：
	1. 非严格读写（Nonstrict-read-write）
		不保证缓存和数据库数据的一致性。提供Read Uncommited事务隔离级别。对于极少被修改而且允许脏读的数据，
		可以采用这种策略。
	2. 读写型（Read-write）//推荐
		提供Read Commited数据隔离级别。对于经常读但是很少被修改的数据，可以采用这种隔离类型，因为他可以防止脏读。
	3. 事务型（Transactional）
		仅在受管理环境下适用。它提供了Repeatable Read事务隔离级别。对于经常读但又很少被修改的数据，可以采用这种
		隔离类型，因为它可以防止脏读和不可重复读。
	4. 只读型（Read-Only）
		提供Serializable数据隔离级别，对于从来不会被修改的数据，可以采用这种访问策略。
		
	
hibernate二级缓存配置示例：
	1. 加入jar包
		<dependency>
		    <groupId>org.hibernate</groupId>
		    <artifactId>hibernate-ehcache</artifactId>
		    <version>${hibernate.version}</version>
		</dependency>
		
	2.复制/hibernate-release-4.3.11.Final/project/etc/ehcache.xml到当前类路径下
		<diskStore>: 指定一个目录，当ehcache把数据写到磁盘上时将把数据写到这个目录下
		<defaultCache>: 设置缓存的默认过期策略
		<cache>: 设定具体的命名缓存的过期策略，每个命名缓存代表一个缓存区域
		<region>: 一个具有名称的缓存块，可以给每个不同的缓存块设置不同的缓存策略。如果没有设置任何缓存区域，则
				 所有被缓存的对象，都将使用默认的缓存策略，即<defaultCache../>
		hibernate在不同的缓存区域保存不同的类/集合:
			对于类而言，区域的名称是类名，如hb18.second_level_cache.Dept
			对于集合而言，区域的名称是类名加属性名，如hb18.second_level_cache.Dept.emps
		
		<!-- 
	 	name: 设置缓存的名字，它的取值为类的全限名或集合的名字
	 	maxInMemory: 设置基于内存的缓存中可存放的对象最大数目
	 	eternal: 设置对象是否为永久的，true表示永不过期（此时将忽略timeToIdleSeconds和timeToLiveSeconds），默认值是false
	 	timeToIdleSeconds: 设置对象空闲最长时间，以秒为单位，空闲超过这个时间则对象过期，ehcache会把它从缓存中清除，如果设置
	 			 为0，表示对象可以无限期地处于空闲状态
	 	timeToLiveSeconds: 设置对象最长能够生存的时间，超过这个时间则对象过期，如果设置这个值为0，表示对象可以无限期地处于缓存
	 			 中，该属性值必须大于或等于timeToIdleSeconds属性值
	 	overflowToDisk: 设置基于内存的缓存对象数目达到上限之后，是否把溢出的对象写到硬盘的缓存中
	    -->
	    <cache name="hb18.second_level_cache.Emp"
	        maxElementsInMemory="10000"
	        eternal="false"
	        timeToIdleSeconds="300"
	        timeToLiveSeconds="600"
	        overflowToDisk="true"
	    />
	
	3.配置hibernate.cfg.xml
		i) 配置启用hibernate二级缓存
			<property name="cache.use_second_level_cache">true</property>
		ii) 配置二级缓存使用的产品（这是hibernate4.x+的配置方式）
			<property name="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</property>
		iii) 配置对哪些类使用hibernate二级缓存（注意配置在最后）
			<class-cache usage="read-write" class="hb18.second_level_cache.Dept"/>
			实际上也可以在*.hbm.xml中配置对那些类使用二级缓存以及二级缓存的策略，此时配置应写在hbm文件<class>元素下：
			<cache usage="read-write"/>
			
		iv) 集合级别的二级缓存的陪配置
			可以在*.cfg.xml中配置
			<class-cache usage="read-write" class="hb18.second_level_cache.Dept"/>
			<collection-cache usage="read-write" collection="hb18.second_level_cache.Dept.emps"/>	
			
			也可以在*.hbm.xml文件中的<set>集合元素中配置
			<cache usage="read-write"/>
			
			[注意]
			除了上述配置，还需要配置集合中的元素对应的持久化类的也使用二级缓存配置，否则将会多出n条sql查询语句
			
		v) 配置开启查询缓存（查询缓存依赖于hibernate二级缓存）
			在cfg配置文件中设置<property name="cache.use_query_cache">true</property>
	 		并且代码中需要显式调用query.setCacheable(true)方法
		

hibernate时间戳缓存：
	时间戳缓存区域存放了对查询结果相关的表进行CURD操作的时间戳，hibernate通过时间戳缓存区域来判断被缓存的
	查询结果是否过期，其运行是hibernate内部自动完成的，机制如下：
		T1时刻执行查询操作，把查询结果存放在QueryCache区域，记录该区域的时间戳为T1；
		T2时刻对查询结果相关的表进行更新操作，hibernate把T2时刻存放在UpdateTimestampCache区域；
		T3时刻执行查询语句之前，先比较QueryCache区域的时间戳和UpdateTimeStampCache区域的时间戳，
			若T2>T1则丢弃原来存放在QueryCache区域的查询结果，重新到数据库中查询数据，
			若T2<T1则直接从QueryCache中获取查询结果
			
			
			
			
			
			
	