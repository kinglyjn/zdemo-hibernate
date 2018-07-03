
域模型

	域模型：
		Category--n--------------------------------------------n-->Item
			id														id
			name													name
			items									
									 
	关系数据模型：
		CATEGORY(ID)--1-----n--CATEGORY_ITEM(C_ID,I_ID)--n----1--ITEM(ID)
			NAME													NAME
			
			
	基本配置示例：
		Category方:
        <set name="items" table="T_CATEGORY_ITEM">
            <key>
                <column name="C_ID" />
            </key>
            <many-to-many class="Item" column="I_ID" />
        </set>
        
        
    