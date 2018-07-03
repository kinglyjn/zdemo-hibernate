
连接池（c3p0）配置信息：
	hibernate.c3p0.max_size				数据库连接池的最大连接数
	hibernate.c3p0.min_size				数据库连接池的最小连接数
	hibernate.c3p0.acquire_increment 	当数据库连接池中的连接耗尽时，同一时刻获取多少个数据库连接
	hibernate.c3p0.max_statements	 	缓存statement对象的数量
	hibernate.c3p0.timeout				数据库连接池中连接对象在多长时间没有使用过后，就应该被销毁
	hibernate.c3p0.idle_test_period	 	表示连接池检测线程多长时间检测一次池内的所有连接对象是否超时，
										连接池本身不会把自己从连接池中移除，而是专门有一个线程按照一
										定的时间间隔来做这件事，这个线程通过比较连接对象最后一次使用
										时间和当前的时间差来和timeout做比对，今儿决定是否销毁这个连
										接对象。
	
