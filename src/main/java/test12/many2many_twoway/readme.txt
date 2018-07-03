
n-n双向关联关系映射：
	*两端都要使用集合属性（set），集合属性应增加key子元素用以映射外键列，集合元素里还需要增加many-to-many子元素关联实体类
	*在双向n-n关联的两边都需要指定连接表的表名和外键列的列名，两个集合元素set的table属性必须指定且必须完全相同，set元素的
	 两个子元素key和many-to-many都必须指定column属性，其中key和many-to-many分别指定本持久化类和关联类在连接表中的外键名，
	 因此两边的key与many-to-many的column属性交叉相同。
	*对于双向n-n关联，必须把其中一端的inverse属性设置为true，否则两端都维护关联关系可能会造成主键冲突。
     
        
基本配置示例：
	Category方：
	<set name="items" table="T_CATEGORY_ITEM" inverse="true">
        <key>
            <column name="C_ID" />
        </key>
        <many-to-many class="Item" column="I_ID"/>
    </set>
    
    Item方：
    <set name="categories" table="T_CATEGORY_ITEM">
        <key>
            <column name="I_ID" />
        </key>
       <many-to-many class="Category" column="C_ID"/>
    </set>