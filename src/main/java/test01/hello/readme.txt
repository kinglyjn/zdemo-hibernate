实体类：
--------------------------------
public class MultiTypeDemo {
	private String id;
	private Integer ii;
	private Short sht;
	private Byte bt;
	private Long lg;
	private Float flt;
	private Double dbl;
	private BigDecimal bdml;
	private Boolean bl;
	private Character c;
	private String str;
	private Date d;
	private Date t;
	private Date ts;
	private Calendar cl;
	private Blob image;		//BLOB	blob mediumblob
	private String text;	        //TEXT	text mediumtext
	private Serializable dog;  //BLOB
	private Class<?> clazz;	//class
	private Locale locale;	//locale
	private TimeZone tz;	//timezone;
	private Currency cy;	//currency

	// getter & setter
	// ...
}




ORM映射:
--------------------------------
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<!-- Generated 2016-5-13 0:53:12 by Hibernate Tools 3.4.0.CR1 -->
<hibernate-mapping>
    <class name="com.demo.coltype.MultiTypeDemo" table="T_MULTITYPE_DEMO">
        <id name="id" type="java.lang.String">
            <column name="ID" />
            <generator class="uuid" />
        </id>
        <property name="ii" type="integer">
            <column name="II" />
        </property>
        <property name="sht" type="short">
            <column name="SHT" />
        </property>
        <property name="bt" type="byte">
            <column name="BT" />
        </property>
        <property name="lg" type="long">
            <column name="LG" />
        </property>
        <property name="flt" type="float">
            <column name="FLT" />
        </property>
        <property name="dbl" type="double">
            <column name="DBL" />
        </property>
        <property name="bdml" type="big_decimal">
            <column name="BDML" />
        </property>
        <property name="bl" type="boolean">
            <column name="BL" />
        </property>
        <property name="c" type="character">
            <column name="C" />
        </property>
        <property name="str" type="string">
            <column name="STR" />
        </property>
        <property name="d" type="date">
            <column name="D" />
        </property>
        <property name="t" type="time">
            <column name="T" />
        </property>
        <property name="ts" type="timestamp">
            <column name="TS" />
        </property>
        <property name="cl" type="calendar">
            <column name="CL" />
        </property>
        <property name="image" type="blob">
            <column name="IMAGE" sql-type="mediumblob"/>
        </property>
        <property name="text" type="text">
            <column name="TEXT" sql-type="mediumtext"/>
        </property>
        <property name="dog" type="serializable">
            <column name="DOG" />
        </property>
        <property name="clazz" type="class">
            <column name="CLAZZ" />
        </property>
        <property name="locale" type="locale">
            <column name="LOCALE" />
        </property>
        <property name="tz" type="timezone">
            <column name="TZ" />
        </property>
        <property name="cy" type="currency">
            <column name="CY" />
        </property>
    </class>
</hibernate-mapping>