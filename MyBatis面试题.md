#####1. #{...} 和 ${...} 的区别
    1. MyBatis将 #{…} 解释为JDBC prepared statement 的一个参数标记。而将 ${…} 解释为字符串替换
    2. 使用$ {…} (字符串替换)时可能会有SQL注入攻击的风险
#####2. 两种使用LIKE的方法
 1. (推荐使用)第一种方法是,在Java代码中添加SQL通配符  
```
01.String wildcardName = "%Smi%";  
02.List<Name> names = mapper.selectLike(wildcardName);  


01.<select id="selectLike">  
02.  select * from foo where bar like #{value}  
03.</select>
```
 2. 第二种方式是在SQL语句中拼接通配符  
```
01.String wildcardName = "Smi";  
02.List<Name> names = mapper.selectLike(wildcardName); 


01.<select id="selectLike">  
02.  select * from foo where bar like '%' || '${value}' || '%'  
03.</select>
```
**重要提示**: 请注意两种方式中 $ 和 # 的使用!
#####3. 如何执行批量插入
```
首先,创建一个简单的insert语句:
01.<insert id="insertName">  
02.  insert into names (name) values (#{value})  
03.</insert>  


然后在Java代码中像下面这样执行批处理插入:
01.List<String> names = new ArrayList<String>();  
02.names.add("Fred");  
03.names.add("Barney");  
04.names.add("Betty");  
05.names.add("Wilma");  
06.  
07.// 注意这里 ExecutorType.BATCH  
08.SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);  
09.try {  
10.  NameMapper mapper = sqlSession.getMapper(NameMapper.class);  
11.  for (String name : names) {  
12.    mapper.insertName(name);  
13.  }  
14.  sqlSession.commit();  
15.} finally {  
16.  sqlSession.close();  
17.}
```
#####4. Xml映射文件中，除了常见的select|insert|updae|delete标签之外，还有哪些标签？
    还有很多其他的标签，<resultMap>、<parameterMap>、<sql>、<include>、<selectKey>，加上动态sql的9个标签，trim|where|set|foreach|if|choose|when|otherwise|bind等，其中<sql>为sql片段标签，通过<include>标签引入sql片段，<selectKey>为不支持自增的主键生成策略标签。
#####5. 最佳实践中，通常一个Xml映射文件，都会写一个Dao接口与之对应，请问，这个Dao接口的工作原理是什么？Dao接口里的方法，参数不同时，方法能重载吗？
    Dao接口，就是人们常说的Mapper接口，接口的全限名，就是映射文件中的namespace的值，接口的方法名，就是映射文件中MappedStatement的id值，接口方法内的参数，就是传递给sql的参数。Mapper接口是没有实现类的，当调用接口方法时，接口全限名+方法名拼接字符串作为key值，可唯一定位一个MappedStatement，举例：com.mybatis3.mappers.StudentDao.findStudentById，可以唯一找到namespace为com.mybatis3.mappers.StudentDao下面id = findStudentById的MappedStatement。在Mybatis中，每一个<select>、<insert>、<update>、<delete>标签，都会被解析为一个MappedStatement对象。
    
    Dao接口里的方法，是不能重载的，因为是全限名+方法名的保存和寻找策略。
    
    Dao接口的工作原理是JDK动态代理，Mybatis运行时会使用JDK动态代理为Dao接口生成代理proxy对象，代理对象proxy会拦截接口方法，转而执行MappedStatement所代表的sql，然后将sql执行结果返回。