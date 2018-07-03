
===================================================================================================
class（用于指定类和表的映射）
	dynamic-update/dynamic-insert: 设置为true表示当更新/保存一个对象时会动态生成一个对应的sql语句，
				sql语句中仅包含需要更新或保存的字段（默认为false）
	select-before-update: 设置为true时在对游离对象进行更新的时候，会先去数据库中查询相关的记录，
				避免无谓的更新（默认为false），建议不设置该属性。
	
	

===================================================================================================
id（映射对象的oid和表的主键）
	hibernate使用对象标识符（oid）来建立内存中的对象和数据库中表记录的对应关系，对象的oid和数据库表的主键相对应。
	hibernate通过标识符生成器来为主键赋值。
	在关系映射文件中，<id>元素用来设置对象标识符，<generator>子元素用来设定标识符生成器。hibernate提供了标识符
	生成器接口（IdentifierGenerator），并提供了各种内置实现。<generator>元素通过class属性来指定使用的标识符生
	成器的全限类名或其缩写名。例如：
	<id name="id" type="java.lang.Integer">
        <column name="ID" />
        <generator class="native" />
    </id>
    
    
    hibernate内置的标识符生成器：
    ----------------------------------------------------------------------------------------------
	increment		由hibernate以递增的方式为代理主键赋值，				会有并发的问题，只适用于单线程
					hibernate会先读取news表中的主键的最大值，			访问数据库的情况
					接下来向news表中插入记录时，就在max(id)
					的基础上递增1
			
	identity		由底层数据库来负责生成对象标识符，它要求				依赖于底层数据库是否支持自动增长的字段
					底层数据库吧主键定义成自动增长的数据类型				类型，包括db2|mysql|MSSQLServer|Sybase等
					
	sequence		利用底层数据库提供的序列来生成对象标识符				要求底层数据库是支持序列的，包括
					<id name="id">									db2|oracle等
						<generator class="sequence">
							<param name="sequence">news_seq</param>
						</generator>
					</id>
	
	hilo			由hibernate按照一定的高低算法*生成对象标				hibernate在持久化一个news对象时，由
					识符，它从数据库的特定表的字段获取high值				hibernate生成主键值，hilo标识符生成器
					<id name="id">									在生成对象标识符时，需要读取并修改HI_TABLE	
						<generator class="hilo">					表中的NEXT_VALUE值。由于hilo的机制不依赖于
							<param name="table">HI_TABLE</param>	底层数据库，因此它适用于所有的数据库系统
							<param name="column">NEXT_VALUE</param>
							<param name="max_lo">10</param>
						</generator>
					</id>
					
	native			依据底层数据库自动生成标识符的支持能力，来选择			很适合跨数据库平台的开发
					使用increment、identity、sequence或hilo标识
					符生成器
	----------------------------------------------------------------------------------------------			
	
	
	
===================================================================================================
property（用于指定类的属性和表的字段的映射）
	type: 指定hibernate映射类型，hibernate映射类型是java类型与sql类型之间的桥梁，如果没有设置该类型，
		  hibernate会根据反射机制识别出持久化类特定属性的java类型，然后自动使用与之对应的默认的hibernate类型。
		  
		  *映射java的时间-日期类型：
		  1. 在java中代表时间和日期的类型包括java.util.Date和java.util.Calendar，此外在jdbc api中还提供
		     了3个sql_date扩展，这三个类分别和标准sql类型中的date、time、timestamp类型对应。
		     在标准sql中，date表示日期，time表示时间，timestamp类型表示时间戳（同时包含日期和时间信息）
		  2. 如何进行映射？
		     因为java.util.Date是父类，所以java.util.Date可以和标准sql的date、time、timestamp类型都对应，
		     所以在设置持久化类的date类型时，设置为java.util.Date类型。
		  3. 如何把java.util.Date映射为数据库的date、time、timestamp类型？
		     可以通过property的type属性进行映射，例如：
		     <property name="date" type="timestamp"> //这里的timestamp是hibernate的数据类型（它是java类型和数据库类型之间的桥梁）
		         <column name="DATE">
		     </property>
		  
		  *映射大对象类型
		  1. 在java中java.lang.String可用于表示长字符串（长度超过255），字节数组byte[]可用于存放图片或文件的二进制数据。
		     此外在jdbc api中还提供了java.sql.Clob和java.sql.Blob类型，它们分别和标准sql中的CLOB和BLOB类型相对应。
		     CLOB(Character Large Object)表示字符串大对象
		     BLOB(Binary Large Object)表示二进制大对象
		  2. 在持久化类中，二进制大对象可以声明为byte[]或java.sql.Blob，字符串可以声明为String或java.sql.Clob
		  3. mysql不支持标准sql的CLOB属性，在mysql中使用TEXT|MEDIUMTEXT|LONGTEXT类型表示长文本数据类型
		
		  		     
	access: 指定hibernate默认的属性访问策略，默认值为property，即使用getter/setter来访问属性。若指定为field，
			hibernate将会忽略getter/setter方法，而通过反射来访问成员变量。
	
	
	length: 指定该属性所映射的数据列的长度。
	scale: 指定该属性所映射数据列的小数位数，对于double、float、decimal等类型的数据有效。
	formula: 设置一个sql表达式，hibernate将根据它来计算出派生属性的值。
			[注1]派生属性：并不是持久化类的所有属性都直接和表的字段相匹配，持久化类的有些属性的值必须在运行时通过
				计算才能得出来，这种属性称之为派生属性。
			[注2]使用formula属性时，formula="(sql)"的英文括号不能少，sql表达式中的列名和表名都应该和数据库相
				对应，如果需要在formula属性中使用参数，可以直接使用where cur.id=id的形式，其中id就是参数，和当前
				持久化对象的id属性相对应的列的id值将作为参数传入。
	
	
	not-null: 射中是否为该属性所映射的数据列添加非空约束（默认为false）
	unique: 设置是否为该属性所映射的数据列添加唯一约束（默认为false）		
	index: 指定一个字符串的索引名称，当系统需要hibernate自动建表时，用于为该属性所映射的数据列创建索引，从而加速
		   对该数据列的查询。
	update: 当为true时，表名当前数据表字段的值不能被修改（默认为false）
	
		   

		   
	