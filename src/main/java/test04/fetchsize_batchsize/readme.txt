
hibernate.jdbc.fetch_size:
	实质是调用Statement.setFetchSize()方法设定jdbc的Statement读取数据的时候每次从数据库取出的记录数。
	例如：一次查询一万条记录，对于 Oracle的jdbc 确定来说，是不会把一万条记录全取出来的，而只会取出
	fetch_size条记录，当遍历完了这fetch_size条记录之后再去数据库取出fetch_size条记录，这样就大大节
	省了无谓的内存消耗。fetch_size设置的越大，读取数据库的次数越少，速度越快；fetch_size设置的越小，
	读数据库的次数越多，速度越慢。Oracle数据库的jdbc驱动默认的fetch_size为10，是一个保守的设定，根据
	测试，当fetch_size设定为50时，性能会提升1倍之多，当fetch_size设置为100，性能还能继续提升20%，
	fetch_size继续增大，性能提升就不显著了，并不是所有的数据库都支持fetch_size特性，例如mysql就不支持。
	
hibernate.jdbc.batch_size:
	设置对数据库进行批量删除，批量更新和批量插入时的批次大小，类似于设置缓冲区大小的意思。batch_size越大，
	批量操作时向数据库发送sql的次数越少，速度就越快。
	测试结果是当batch_size设置为0的时候，使用hibernate对oracle删除一万条记录需要25秒，batch_size设置
	为50的时候，删除仅仅需要5秒，oracle数据库bitch_size设置为30比较合适。
	
	
	