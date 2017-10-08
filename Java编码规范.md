# Java编码规范

### 前言

#### 1.1 术语说明

1. 术语class可表示一个普通类，枚举类，接口或是annotation类型(`@interface`)
2. 术语comment只用来指代实现的注释(implementation comments)，我们不使用“documentation comments”一词，而是用Javadoc。

### 源文件

#### 2.1 文件名

源文件以其最顶层的类名来命名，大小写敏感，文件扩展名为`.java`。

#### 2.2 文件编码:UTF-8

源文件编码格式为UTF-8(包括java\html\js等所有资源文件)。

#### 2.3 特殊字符

是使用实际的Unicode字符(比如∞)，还是使用等价的Unicode转义符(比如\u221e)，取决于哪个能让代码更易于阅读和理解。

```java
String unitAbbrev = "μs";                               // 赞，即使没有注释也非常清晰
String unitAbbrev = "\u03bcs"; // "μs"                  // 允许，但没有理由要这样做
String unitAbbrev = "\u03bcs"; // Greek letter mu, "s"  // 允许，但这样做显得笨拙还容易出错
String unitAbbrev = "\u03bcs";                          // 很糟，读者根本看不出这是什么
```

Tip: 永远不要由于害怕某些程序可能无法正确处理非ASCII字符而让你的代码可读性变差。当程序无法正确处理非ASCII字符时，它自然无法正确运行， 你就会去fix这些问题的了。(言下之意就是大胆去用非ASCII字符，如果真的有需要的话)

### 源文件结构

一个源文件包含(按顺序地)：

1. 许可证或版权信息(如有需要)
2. package语句
3. import语句
4. 一个顶级类(只有一个)

以上每个部分之间用一个空行隔开。

#### 3.1 许可证或版权信息

如果一个文件包含许可证或版权信息，那么它应当被放在文件最前面。

#### 3.2 package语句

package语句不换行

#### 3.3 import语句

1. import不要使用通配符；即，不要出现类似这样的import语句：`import java.util.*;`
2. import语句不换行

#### 3.4 类声明

1. 只有一个顶级类声明
2. 重载：永不分离; 当一个类有多个构造函数，或是多个同名方法，这些函数/方法应该按顺序出现在一起，中间不要放进其它函数/方法。

### 格式

#### 4.1 大括号

##### 4.1.1 使用大括号(即使是可选的)

大括号与`if, else, for, do, while`语句一起使用，即使只有一条语句(或是空)，也应该把大括号写上。

##### 4.1.2 非空块：K & R 风格

对于非空块和块状结构，大括号遵循Kernighan和Ritchie风格 ([Egyptian brackets](http://www.codinghorror.com/blog/2012/07/new-programming-jargon.html)):

- 左大括号前不换行
- 左大括号后换行
- 右大括号前换行
- 如果右大括号是一个语句、函数体或类的终止，则右大括号后换行; 否则不换行。例如，如果右大括号后面是else或逗号，则不换行。

示例：

```java
return new MyClass() {
  @Override public void method() {
    if (condition()) {
      try {
        something();
      } catch (ProblemException e) {
        recover();
      }
    }
  }
};
```

##### 4.1.3 空块：可以用简洁版本

一个空的块状结构里什么也不包含，大括号可以简洁地写成`{}`，不需要换行。例外：如果它是一个多块语句的一部分(if/else 或 try/catch/finally) ，即使大括号内没内容，右大括号也要换行。

示例：

```java
void doNothing() {}
```

#### 4.2 一行一个语句

每个语句后要换行。

#### 4.3 列限制：80或100

一个项目可以选择一行80个字符或100个字符的列限制，除了下述例外，任何一行如果超过这个字符数限制，必须自动换行。

例外：

1. 不可能满足列限制的行(例如，Javadoc中的一个长URL，或是一个长的JSNI方法参考)。
2. `package`和`import`语句。
3. 注释中那些可能被剪切并粘贴到shell中的命令行。

#### 4.4 用小括号来限定组：推荐

除非作者和reviewer都认为去掉小括号也不会使代码被误解，或是去掉小括号能让代码更易于阅读，否则我们不应该去掉小括号。 我们没有理由假设读者能记住整个Java运算符优先级表。

#### 4.5 具体结构

##### 4.5.1 枚举类

枚举常量间用逗号隔开，换行可选。

没有方法和文档的枚举类可写成数组初始化的格式：

```java
private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS }
```

由于枚举类也是一个类，因此所有适用于其它类的格式规则也适用于枚举类。

##### 4.5.2 变量声明

###### 4.5.2.1  每次只声明一个变量

不要使用组合声明，比如`int a, b;`。

###### 4.5.2.2 需要时才声明，并尽快进行初始化

不要在一个代码块的开头把局部变量一次性都声明了(这是c语言的做法)，而是在第一次需要使用它时才声明。 局部变量在声明时最好就进行初始化，或者声明后尽快进行初始化。

##### 4.5.3 数组

数组初始化可以写成块状结构，比如，下面的写法都是OK的：

```java
new int[] {
  0, 1, 2, 3 
}

new int[] {
  0,
  1,
  2,
  3
}

new int[] {
  0, 1,
  2, 3
}

new int[]
    {0, 1, 2, 3}
```

##### 4.5.4 switch语句

术语说明：switch块的大括号内是一个或多个语句组。每个语句组包含一个或多个switch标签(`case FOO:`或`default:`)，后面跟着一条或多条语句。

###### 4.5.4.1 Fall-through：注释

在一个switch块内，每个语句组要么通过`break, continue, return`或抛出异常来终止，要么通过一条注释来说明程序将继续执行到下一个语句组， 任何能表达这个意思的注释都是OK的(典型的是用`// fall through`)。这个特殊的注释并不需要在最后一个语句组(一般是`default`)中出现。示例：

```java
switch (input) {
  case 1:
  case 2:
    prepareOneOrTwo();
    // fall through
  case 3:
    handleOneTwoOrThree();
    break;
  default:
    handleLargeNumber(input);
}
```

###### 4.5.4.2 default的情况要写出来

每个switch语句都包含一个`default`语句组，即使它什么代码也不包含。

##### 4.5.5 注解(Annotations)

注解紧跟在文档块后面，应用于类、方法和构造函数，一个注解独占一行。这些换行不属于自动换行(第4.5节，自动换行)，因此缩进级别不变。例如：

```java
@Override
@Nullable
public String getNameIfPresent() { ... }
```

例外：单个的注解可以和签名的第一行出现在同一行。例如：

```java
@Override public int hashCode() { ... }
```

应用于字段的注解紧随文档块出现，应用于字段的多个注解允许与字段出现在同一行。例如：

```java
@Partial @Mock DataLoader loader;
```

参数和局部变量注解没有特定规则。

##### 4.5.6 注释

###### 4.5.6.1 块注释风格

块注释与其周围的代码在同一缩进级别。它们可以是`/* ... */`风格，也可以是`// ...`风格。对于多行的`/* ... */`注释，后续行必须从`*`开始， 并且与前一行的`*`对齐。以下示例注释都是OK的。

```java
/*
 * This is          // And so           /* Or you can
 * okay.            // is this.          * even do this. */
 */
```

注释不要封闭在由星号或其它字符绘制的框架里。

##### 4.5.7 Modifiers

类和成员的modifiers如果存在，则按Java语言规范中推荐的顺序出现。

```java
public protected private abstract static final transient volatile synchronized native strictfp
```

### 命名约定

#### 5.1 对所有标识符都通用的规则

标识符只能使用ASCII字母和数字，因此每个有效的标识符名称都能匹配正则表达式`\w+`。

#### 5.2 标识符类型的规则

##### 5.2.1 包名

包名全部小写，连续的单词只是简单地连接起来，不使用下划线。

##### 5.2.2 类名

类名都以`UpperCamelCase`风格编写。

类名通常是名词或名词短语，接口名称有时可能是形容词或形容词短语。现在还没有特定的规则或行之有效的约定来命名注解类型。

测试类的命名以它要测试的类的名称开始，以`Test`结束。例如，`HashTest`或`HashIntegrationTest`。

##### 5.2.3 方法名

方法名都以`lowerCamelCase`风格编写。

方法名通常是动词或动词短语。

下划线可能出现在JUnit测试方法名称中用以分隔名称的逻辑组件。一个典型的模式是：`test_`，例如`testPop_emptyStack`。 并不存在唯一正确的方式来命名测试方法。

避免用含义不清的动词如`process`、`handle`等为方法命名，因为这些动词并没有说明要具体做什么。

##### 5.2.4 常量名

常量名命名模式为`CONSTANT_CASE`，全部字母大写，用下划线分隔单词。那，到底什么算是一个常量？

每个常量都是一个静态final字段，但不是所有静态final字段都是常量。在决定一个字段是否是一个常量时， 考虑它是否真的感觉像是一个常量。例如，如果任何一个该实例的观测状态是可变的，则它几乎肯定不会是一个常量。 只是永远不打算改变对象一般是不够的，它要真的一直不变才能将它示为常量。

```java
static final int NUMBER = 5;
static final ImmutableList<String> NAMES = ImmutableList.of("Ed", "Ann");
static final Joiner COMMA_JOINER = Joiner.on(',');  // because Joiner is immutable
static final SomeMutableType[] EMPTY_ARRAY = {};
enum SomeEnum { ENUM_CONSTANT }
```

这些名字通常是名词或名词短语。

##### 5.2.5 非常量字段名

非常量字段名以`lowerCamelCase`风格编写。

这些名字通常是名词或名词短语。

##### 5.2.6 参数名

参数名以`lowerCamelCase`风格编写。

参数应该避免用单个字符命名。

##### 5.2.7 局部变量名

局部变量名以`lowerCamelCase`风格编写，比起其它类型的名称，局部变量名可以有更为宽松的缩写。

虽然缩写更宽松，但还是要避免用单字符进行命名，除了临时变量和循环变量。

即使局部变量是final和不可改变的，也不应该把它示为常量，自然也不能用常量的规则去命名它。

##### 5.2.8 类型变量名

类型变量可用以下两种风格之一进行命名：

- 单个的大写字母，后面可以跟一个数字(如：E, T, X, T2)。
- 以类命名方式(5.2.2节)，后面加个大写的T(如：RequestT, FooBarT)。

#### 5.3 驼峰式命名法(CamelCase)

[驼峰式命名法](http://zh.wikipedia.org/wiki/%E9%A7%9D%E5%B3%B0%E5%BC%8F%E5%A4%A7%E5%B0%8F%E5%AF%AB)分大驼峰式命名法(`UpperCamelCase`)和小驼峰式命名法(`lowerCamelCase`)。 有时，我们有不只一种合理的方式将一个英语词组转换成驼峰形式，如缩略语或不寻常的结构(例如"IPv6"或"iOS")。Google指定了以下的转换方案。

名字从`散文形式`(prose form)开始:

1. 把短语转换为纯ASCII码，并且移除任何单引号。例如："Müller’s algorithm"将变成"Muellers algorithm"。
2. 把这个结果切分成单词，在空格或其它标点符号(通常是连字符)处分割开。
   - 推荐：如果某个单词已经有了常用的驼峰表示形式，按它的组成将它分割开(如"AdWords"将分割成"ad words")。 需要注意的是"iOS"并不是一个真正的驼峰表示形式，因此该推荐对它并不适用。
3. 现在将所有字母都小写(包括缩写)，然后将单词的第一个字母大写：
   - 每个单词的第一个字母都大写，来得到大驼峰式命名。
   - 除了第一个单词，每个单词的第一个字母都大写，来得到小驼峰式命名。
4. 最后将所有的单词连接起来得到一个标识符。

示例：

```
Prose form                Correct               Incorrect
------------------------------------------------------------------
"XML HTTP request"        XmlHttpRequest        XMLHTTPRequest
"new customer ID"         newCustomerId         newCustomerID
"inner stopwatch"         innerStopwatch        innerStopWatch
"supports IPv6 on iOS?"   supportsIpv6OnIos     supportsIPv6OnIOS
"YouTube importer"        YouTubeImporter
                          YoutubeImporter*

```

加星号处表示可以，但不推荐。

Note：在英语中，某些带有连字符的单词形式不唯一。例如："nonempty"和"non-empty"都是正确的，因此方法名`checkNonempty`和`checkNonEmpty`也都是正确的。

### 编程实践

#### 6.1 @Override：能用则用

只要是合法的，就把`@Override`注解给用上。

#### 6.2 捕获的异常：不能忽视

除了下面的例子，对捕获的异常不做响应是极少正确的。(典型的响应方式是打印日志，或者如果它被认为是不可能的，则把它当作一个`AssertionError`重新抛出。)

如果它确实是不需要在catch块中做任何响应，需要做注释加以说明(如下面的例子)。

```
try {
  int i = Integer.parseInt(response);
  return handleNumericResponse(i);
} catch (NumberFormatException ok) {
  // it's not numeric; that's fine, just continue
}
return handleTextResponse(response);

```

例外：在测试中，如果一个捕获的异常被命名为`expected`，则它可以被不加注释地忽略。下面是一种非常常见的情形，用以确保所测试的方法会抛出一个期望中的异常， 因此在这里就没有必要加注释。

```
try {
  emptyStack.pop();
  fail();
} catch (NoSuchElementException expected) {
}

```

#### 6.3 静态成员：使用类进行调用

使用类名调用静态的类成员，而不是具体某个对象或表达式。

```
Foo aFoo = ...;
Foo.aStaticMethod(); // good
aFoo.aStaticMethod(); // bad
somethingThatYieldsAFoo().aStaticMethod(); // very bad

```

#### 6.4 Finalizers: 禁用

极少会去重载`Object.finalize`。

Tip：不要使用finalize。如果你非要使用它，请先仔细阅读和理解[Effective Java](http://books.google.com/books?isbn=8131726592) 第7条款：“Avoid Finalizers”，然后不要使用它。

#### 6.5 使用自己的异常类型

任何应用程序中，都创建自己的异常类型，在这些应用程序中抛出的任何异常都会用笔者创建的异常类接住。这让代码能通过 log 追朔异常来自代码的哪一部分或者这是完全出乎意料的异常类型。

#### 6.6 明确方法的功能，一个方法仅完成一个功能

方法功能太多，会增加方法的复杂度和依赖关系，不利于程序阅读和将来的持续维护，无论是方法还是类设计都应符合单一职责原则。

不要设计多用途面面俱到的方法。多功能集于一身的方法，很可能使方法的理解、测试、维护等变得困难。

非调度方法应减少或防止控制参数，尽量只使用数据参数。

#### 6.7 方法参数不能超过5个

参数太多影响代码阅读和使用，为减少参数，首先要考虑这些参数的合理性，保持方法功能单一、优化方法设计，如果参数确实无法减少，可以将多个参数封装成一个类（对象），同时考虑在新的类（对象）中增加相应的行为，以期更符合OOP。

避免设计多参数方法，不使用的参数从接口中去掉。目的减少方法间接口的复杂度。

示例：

```java
//如下函数构造不太合理
int addOrSub(int a, int b, int flag) {
  if(flag == 0) {
    return (a + b);
  } else {
    return (a - b);
  }
}

//不如分为如下两个函数清晰
int add(int a, int b) {
  return (a + b);
}

int sub(int a, int b) {
  return (a - b);
}
```

#### 6.8资源释放

数据库操作、IO操作等需要关闭对象必须在try -catch-finally 的finally中close()，如果有多个IO对象需要关闭，需要分别对每个对象的close()方法进行try-catch,防止一个IO对象关闭失败其他IO对象都未关闭。推荐做法如下：

```java
Connection jdbcConnection = null;
Statement stmt = null;
try {
	........
} catch (SQLException e) {
	........
} finally {
    if (stmt != null) {
	  try {
      	stmt.close();
	  } catch (SQLException e) {
        logger.log(Level.WARNING, "异常说明", e);
	  }
    }
	if (jdbcConnection != null) {
      try {
      	jdbcConnection.close();
      } catch (SQLException e) {
      	logger.log(Level.WARNING, "异常说明", e);
      }
    }
}
```

#### 6.9 禁止循环中创建新线程

尽量使用线程池

#### 6.10 精确计算

在进行精确计算时(例如:货币计算)避免使用float和double，浮点数计算都是不精确的，必须使用BigDecimal或将浮点数运算转换为整型运算。

#### 6.11 合法性检查

同一项目组应明确规定对接口方法参数的合法性检查应由方法的调用者负责还是由接

口方法本身负责，缺省是由方法调用者负责

#### 6.12 减少方法本身或方法间的递归调用

递归调用特别是方法间的递归调用（如A->B->C->A，影响程序的可理解性；递归调用一般都占用较多的系统资源（如栈空间）；递归调用对程序的测试有一定影响。故除非为某些算法或功能的实现方便，应减少没必要的递归调用。

#### 6.13 打印规范

Stable或以上的版本不在程序中使用System.out.print、System.exit、printStackTrace。

#### 6.14 把Warning干掉

每一个Warning背后都有一个故事，泛型，未被使用的Class引用，未被使用的private方法，未被使用的field，class cast，序列化等等。搞懂一个Warning背后的故事，优雅的解决掉一个Warning。如果实在是搞不定，注释SuppressWarning能够暴力的帮助你。

### Javadoc

基本的注释量为代码量的15%，注释主要描述方法的作用与逻辑，而不是方法名称或者变量名称的简单翻译。注释量不能太多也不能太少，简单优雅的代码比注释更直接易懂，注释会骗人，代码不会，注释不正确比没有注释糟糕很多很多，一大堆无用的注释浪费空间，浪费脑力，不如没有注释。如果一段代码需要你很多很多的注释才能解释清楚，那么请重写那段代码，直到简短注释甚至无需注释。

#### 7.1 格式

Javadoc块的基本格式如下所示：``

```java
/**
 * Multiple lines of Javadoc text are written here,
 * wrapped normally...
 */
public int method(String p1) { ... }
```

或者是以下单行形式：``

```java
/** An especially short bit of Javadoc. */
```

基本格式总是OK的。当整个Javadoc块能容纳于一行时(且没有Javadoc标记@XXX)，可以使用单行形式。

标准的Javadoc标记按以下顺序出现：`@param`, `@return`, `@throws`, `@deprecated`, 前面这4种标记如果出现，描述都不能为空。 

#### 7.2 哪里需要使用Javadoc

至少在每个public类及它的每个public和protected成员处使用Javadoc，以下是一些例外：

##### 7.2.1 给别人调用的接口要注释清楚

包括Interface、非Java Bean的public方法以及工具类里面的public static方法。关注做什么(不是怎么做)，输入输出，特殊情况处理(比如null或不合法参数)，异常情况。这种为块级(Block)注释，一般用/** comments */格式。

##### 7.2.2 特殊的算法简洁清楚地注释

特殊的算法体现的是作者当时解决问题的思路和算法，具有业务上或者算法上的特殊性，需要备注一下，以防忘记或者其他维护同事难以理解。通常用单行注释即可，特别长的用/* comments */ 格式。

##### 7.2.3 具有特殊控制意义或者业务意义的变量简洁注释

主要是帮助阅读代码的时候帮助理解。格式和第二条相同。

##### 7.2.4 例外：不言自明的方法

对于简单明显的方法如`getFoo`，Javadoc是可选的(即，是可以不写的)。这种情况下除了写“Returns the foo”，确实也没有什么值得写了。

##### 7.2.5 例外：重载

如果一个方法重载了超类中的方法，那么Javadoc并非必需的。

#### 7.3 删掉IDE帮忙自动生成的无用注释

`TODO Auto-generated method stud`等等之流的，请删掉。

