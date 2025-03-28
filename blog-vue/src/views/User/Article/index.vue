<script setup>
import MarkdownViewer from './components/MarkdownViewer.vue'
import TopArticleCard from '@/views/User/Home/components/TopArticleCard.vue';
import { ref } from 'vue'

const mdContent = ref(`
# JVM

## 类的生命周期

类的生命周期分为: 加载→连接(验证→准备→解析)→初始化→使用→卸载

### 加载

加载阶段是指类加载器根据类的全限定名通过不同的渠道(本地,网络......)以二进制的方式获取字节码信息,类加载器在加载完类之后,java虚拟机会将字节码中的信息保存到方法区中,之后java虚拟机会创建一个InstanceKlass对象,保存类的所有信息(基本信息,常量池,字段,方法,虚方法表等),包括一些特定功能如多态的信息,同时java虚拟机还会再堆中生成一份与方法区中数据类似的java.lang.Class对象用于获取类的信息和存储静态字段的数据(JDK8之后)

#### 为什么要多创建一份对象在堆区中,直接用方法区中的不行吗?

1. InstanceKlass是由cpp编写的,一般难以直接操作
2. 控制开发者的访问权限,InstanceKlass中的有一部分数据开发者不需要用到(安全性)

### 连接

#### 验证

验证字节码文件是否满足java虚拟机规范

1. 文件格式的校验,比如文件开头是否是cafababe,主次版本号是否满足需求
2. 元信息验证,比如类需要有父类
3. 验证程序执行指令的语义
4. 验证符号引用,比如是否访问了其他类的private

#### 准备

准备阶段为静态遍历分配内存空间并设置初始值,这里的初始值并非开发人员定义的初始值,一般默认为0 引用数据类型为null 布尔类型为false
当加上final之后java虚拟机默认为常量,在一开始就赋值为开发者定义的初始值

#### 解析

将常量池中的符号引用替换为直接引用,符号引用指在字节码文件中使用编号来访问常量池的内容(cp_#66),直接引用使用内存中的地址值进行访问具体数据

### 初始化

初始化阶段会执行静态代码块中的代码,并为静态遍历赋值,同时执行clinit部分的字节码指令

类初始化的方式

1. 访问一个类的静态变量或者静态方法的时候,但fianl修饰的常量是不会触发初始化的

2. 调用Class.forName(String className)的时候

3. new

4. 执行类的main方法



## 类加载器的分类(JDK8之前)

### 启动类加载器Bootstrap

负责加载java中最核心的类,底层由cpp实现,默认加载java安装目录下的*/jre/lib下的文件  一般来说无法利用java代码获取到Bootstrap
可以通过-Xbootclasspath/a:jar包目录/jar包名 进行拓展,通过启动类加载器去加载指定jar包

### 拓展类加载器Extension

允许拓展java中比较通用的类,底层由java实现,默认加载java安装目录下/jre/lib/ext下的jar
可以通过-Djava.ext.dirs=jar包目录进行拓展

### 应用程序类加载器Application

加载应用使用的类,加载classpath下的类文件

### 双亲委派

双亲委派的作用:

1. 保证类加载的安全性,通过双亲委派机制可以避免核心代码被替换,如java.lang.String,确保核心类库的完整性和安全性
2. 避免重复加载而导致的资源浪费

双亲委派遵循的机制是,**自底向上的查找类是否被加载过,再自顶向下的进行加载**,其中启动类加载器是拓展类加载器的父类加载器,拓展类加载器是应用程序类加载器的父类加载器

![image-20240924201237394](C:\Users\十八\AppData\Roaming\Typora\typora-user-images\image-20240924201237394.png)

### 如何打破双亲委派机制

1. 自定义类加载器,继承ClassLoader并重写里面的loadClass方法
2. DriverManager利用SPI机制实现Application加载指定jar,SPI中利用了线程上下文类加载器(Application)去加载类并创建对象
3. OSGI模块化

## 类加载器的分类(JDK8之后)

在JDK8之后,启动类加载器使用java进行编写Bootstrap被BootClassLoader,但是为了保持统一依然无法通过java代码获取到启动类加载器

![image-20240924202242808](C:\Users\十八\AppData\Roaming\Typora\typora-user-images\image-20240924202242808.png)

拓展类加载器被替换为了平台类加载器(PlatformClassLoader)

## 运行时数据区

## 代码展示
 \`\`\`java
 public static void main(String[] args) {
         long max=0;
         for (long i = 10000000; i >0 ; i--) {
             if(get(i)){
                 max= i;
                 break;
             }
         }
         System.out.println(max);
     }
     public static boolean get(long n){
         long []arr=new long[100000];//获取n的位数
         arr[0]=n/1000000%10;
         arr[1]=n/100000%10;
         arr[2]=n/10000%10;
         arr[3]=n/1000%10;
         arr[4]=n/100%10;
         arr[5]=n/10%10;
         arr[6]=n%10;
         for (int i = 7; i < arr.length; i++) {
             long number =arr[i-1]+arr[i-2]+arr[i-3]+arr[i-4]+arr[i-5]+arr[i-6]+arr[i-7];
             //当有一个数都大于我们要寻找的数时,那他再加上前面我们的几位数肯定会比我们要找的数大,这里就直接停止寻找返回false了
             if(number>n)return  false;
             if(number==n)return true;//说明这个数在我们这个数列当中,满足题干
             arr[i]=number;//将前七位总和的数添加进去
         }
         return false;
     }
 \`\`\`
### 程序计数器(线程不共享)

用于记录当前要执行的字节码指令的地址,可以控制程序指令的进行,实现分支,跳转,异常等,同时在多线程的情况下,jvm也可以通过程序计数器记录cpu切换前的指令
每个线程的程序计数器都只存储一个固定长度的内存地址,程序计数器是不会发生内存溢出的

### JAVA虚拟机栈(线程不共享)

java虚拟机伴随着线程的创建而创建,在线程销毁的时候进行回收,每一个线程执行的方法不尽相同所以java虚拟机的线程是不共享的

用于保存java中实现的方法,每一个方法在执行的时候都会创建一个栈帧用于**存储局部变量表,操作数栈,动态链接等内容**

栈帧的局部变量表是一个数组,数组中的每一个位置称为槽,局部变量表保存的内容用:实例方法的this对象(0号位置),方法的参数,方法体中定义的局部变量,long和double占两个,其他占一个,为了节省空间,局部变量中的槽是可以复用的,一旦某个局部变量不生效当前槽就可以被再次利用,如图下只用了7个槽,this(1),q(2),m(1),a(1),b(1),当实例块执行完之后a和b的槽会被再次利用i(1),j(2)


**操作数栈**是栈帧中的虚拟机在执行指令过程中用来存放临时数据的区域,在编译的时候就可以确定操作数栈的最大深度,从而在执行的时候可以正确的分配内存

当类的字节码指令引用了其他类的属性或者方法的时候,需要将符号引用转换为对应的运行时常量池中的内存地址,动态链接就保存了编号到运行时常量池的内存地址的映射关系

java虚拟机栈内存可能会有内存溢出的情况(死递归),JVM默认会有一个栈的内存大小(Linux:1MB  BSD:1MB  Solaris:1MB  Windows:基于当前操作系统的默认值)
想要修改java虚拟机栈的大小可以通过虚拟机参数-Xss(大小必须是1024倍)

### 本地方法栈(线程不共享)

加上了native,由cpp实现的方法,用于存储本地方法调用时的局部变量、操作数栈、返回值等信息。管理本地方法调用的生命周期，包括方法的入口、参数传递、方法的执行和退出。当 JVM 需要调用使用非 Java 语言（通常是 C 或 C++）实现的库或函数时，本地方法栈会被使用。

### 方法区(线程共享)

方法区是一个虚拟概念,hotspot的设计:
JDK7以及之前的版本将方法区放在堆区域的永久代空间中,堆的大小由虚拟机参数来控制,可以使用-XX:MaxPermSize= 调整内存大小
JDK8及以后将方法区放在元空间中,元空间位于操作系统的直接内存,默认情况下可以一直分配内存空间 -XX:MaxMetaspaceSize= 对元空间大小进行限制

方法区中存放了所有类的基本信息,一般称之为instanceKlass对象,在类加载阶段完成,常量池存放的是字节码中的常量池内容,字节码文件通过编号查表的方式找到常量,这种常量池称之为静态常量池,当常量池加载到内存中之后,可以通过内存地址快速定位到常量池的内容,这种常量池称之为运行时常量池

### 字符串常量池

已知String,intern()方法可以将字符串放入到字符串常量池中,那来完成下面的判断把(doge)  ps:intern()会将第一次遇到的字符串的引用放入字符串常量池



答案是true   false   true  false

1. 在创建对象d的时候,会在字符串常量池开辟一块内存给d,所以d.inner()得到的也是常量池中的"ddd",注意,d并没有在堆中
2. 创建了一个String对象a在堆上,同时在常量池中添加了"aaa",a.intern得到的是常量池中的引用,a得到的是堆中的引用
3. StringBuilder().append()有点特别,每调用一次就会在字符串常量池中添加这一次的数据,上面就是在字符串常量池中分别添加了"think"和"123",所以最后得到的"think123"在字符串常量池中是没有的,而不在字符串常量池中的数据被intern()会在常量池中添加这个字符串("think123")的引用,所以是true
4. 同上3所述

### 堆(线程共享)

java程序中最大的内存区域,创建出来的对象都放在堆上,栈上的局部变量表中可以存放堆上对象的引用,静态变量也可以存放堆对象的引用,通过静态变量实现对象在线程之间共享
堆是会爆内存的,堆空间中有三个值,分别是used(当前已经使用的堆内存),total(java虚拟机分配的堆内存),max(java虚拟机库分配的最大内存),但堆内存不一定指used=total=max,详见垃圾回收机制

可以使用-Xmx和-Xms修改max的最大值和初始的total

### 直接内存

直接内存的使用解决了java堆中对象如果不在使用要回收,回收会影响对象的创建和使用,和IO操作,如读取文件,在之前需要将文件读取到内存再复制到java堆中,而现在直接放入直接内存减小了开销,可以使用-XX:MaxDirectMemorySize= 修改大小

## 垃圾回收

java中映引入自动的垃圾回收(GC)机制,垃圾回收器主要负责对堆上的内存进行回收

**自动垃圾回收机制**是自动根据对象是否使用虚拟机来进行回收对象

- 优点: 降低程序员的是实现难度,降低对象回收带来的bug
- 缺点: 程序员无法控制内存回收的及时性

**手动垃圾回收机制**

- 优点: 回收及时性高,由程序员把控回收的时机
- 缺点: 编辑容易出现空指针,重复释放,内存泄漏等问题

### 方法区的回收

方法区中能回收的内容主要是不再使用的类,需要同时满足下面三个条件

1. 该类的所有实例对象都已经被回收,在堆中不存在任何该列的实例对象以及子对象
2. 加载该类的类加载器已经被回收
3. 该类的对应的java.lang.Class对象没有在任何地方被引用

### 堆的回收

- 引用计数法

  引用计数法会为每个对象维护一个引用计数器,当对象被引用的时候+1 取消引用-1

  缺陷: 维护会对系统性能造成影响  会存在循环引用的问题 ab相互引用的时候会出现无法回收对象的问题

- 可达性分析法

  可达性分析将对象分为两类: 垃圾回收的根对象(GC Root) 和 普通对象,对象和对象之间存在引用关系

  GC Root:

  1. 线程Thread对象,引用线程栈帧中的方法参数 局部变量等
  2. 系统类加载器加载的java.lang.Class对象
  3. 监视器对象 用来保存同步锁synchronized关键字的持有对象
  4. 本地方法调用时使用的全局对象

  ### 软引用

  软引用指一个对象只有软引用关联到她的时候,当程序内存不足就会将软引用中的数据进行回收  被软引用关联的对象在被回收时，相关的软引用会被放入 ReferenceQueue 中，以便开发者可以监测这些对象的回收并进行相应操作。

  ### 弱引用

  大体和软引用相同,区别是弱引用包含的对象在垃圾回收的时候不管内存是否够不够都会被直接回收

### 垃圾回收算法

- **标记清除算法**

  标记阶段 将所有存活的对象进行标记,java中使用可达性分析算法 从GC Root开始通过引用链遍历出所有存活对象

  清除阶段 从内存中删除没有标记的非存活对象

  缺点:

  1. 碎片化问题

     内存是联系的,在对象被删除之后内存中会出现很多的小空间,但如果需要一个很大的空间,这些小单元无法分配

  2. 分配速度慢

     内存碎片的存在使得系统要维护一个空闲链表,每次分配空间都会遍历查找直到找到合适的

- **复制算法**

  准备两块空间From和To 每次在对象分配阶段 只能使用其中一块空间(From)

  在垃圾回收GC阶段,将From中存活对象复制到To空间,再互换From和To的名字 之后清除To空间

  优点:

  1. 吞吐量高

     只需要遍历一次存活对象,但是需要对对象进行移动 所以效率不如标记清除算法

  2. 不会发生碎片化

  缺点:

  1. 内存使用率很低 每次只能让一半的内存空间来创建对象使用

- **标记整理算法**

  标记阶段 将所有存活的对象进行标记,java中使用可达性分析算法 从GC Root开始通过引用链遍历出所有存活对象

  整理对象 将存活的对象移动到堆的一端 清理掉存活对象的内存空间

  优点:

  1. 内存使用率高
  2. 不会发生碎片化

  缺点:

  1. 整理阶段的效率不高

- **分代垃圾回收算法**

  分代回收的时候创建出来的对象首先会被放到Eden伊甸园区,随着对象在Eden区越来越多,如果Eden满了新创建的对象无法添加就会触发年轻代的GC(Minor GC  Young GC)  相当于触发了一次复制算法,将Eden中的数据放入From内存区域中,Minor GC会记录对象的年龄,初始为0 每GC一次就+1 当到15的时候就被送入老年代 (**但老年代的不一定都是满年龄的,比如伊甸园区满了 From To 也满了 这个时候再添加新对象就会被放入老年代中**) 当老年代的空间不足的时候会优先尝试 Minor GC 如果还是不足就会触发Full GC对整个堆进行垃圾回收

- **分代GC算法将堆分成年轻代和老年代的原因**

  1. 分代的设计中允许只回收新生代,如果能满足对象分配的要求就不需要对整个堆进行回收 STW时间就会减少
  2. 可以通过调整年轻代和老年代的比例来适应不同类型的应用程序,提高内存的利用率
  3. 新生代和老年代使用了不同的垃圾回收算法,新生代一般是复制算法,老年代可以选择标记清除和标记整理

### 垃圾回收器

- 年轻代 Serial

  使用复制算法  单线程串行回收年轻代的垃圾回收器

- 老年代 SerialOld

  标记整理算法  单线程串行和Serial垃圾回收器搭配使用

- 年轻代 ParNew垃圾回收器

  复制算法 本质是对Serial在多CPU下的优化,使用多线程进行垃圾回收

- 老年代 CMS

  标记清除算法 CMS关注的是系统的暂停时间 允许用户的线程和垃圾回收线程在某些步骤同时进行

- 年轻代 Parallel Scavenge

  标记整理算法 JDK8默认年轻代垃圾回收器,多线程回收,关注的是系统的吞吐量,具有自动调整对内存大小的特点

- 老年代 Parallerl Old

  标记整理算法 并发收集效率高

### G1垃圾回收器

创建的新对象会放入到Eden区中,当G1判断年轻代不足(max默认60%),无法分配对象的时候需要回收执行Young GC

标记处Eden和Survivor区域中的存活对象

根据配置的最大暂停时间选择某些区域将存活对象复制到一个新的Survivor区中(年龄+1) 清空这些区域

后续Young GC时与之前相同,只不过Survivor区中存活对象会被搬另外一个Survivor区

当某个对象的存活时间达到阈值(默认15)就会被放到老年代

部分对象如果大小超过Region的一半会直接放入老年代,这类老年代被称为Humongous区,如果对象过大横跨多个Region

多次回收之后,会出现很多老年代区,当到达阈值的时候就会触发混合回收MixedGC 回收所有年轻代和部分老年代的对象以及大对象区,采用复制算法来完成

- 混合回收   (初始标记 并发标记 最终标记 并发清理)

  G1对老年代的清理会选择存活度最低的区域来进行回收,保证回收效率最高

## jvm基础参数

- **-Xmx 和 -Xms**

  -Xmx参数设置的是的最大堆内存,计算可用内存的时候要将元空间 操作系统等其他占用内存排除

  -Xms参数用于设置初始堆大小,建议将-Xms和-Xmx一样大

- **-XX:MaxMetaspaceSize 和 -XX:MetaspaceSize**

  -XX:MaxMetaspaceSize 指的是最大元空间大小,默认值比较大

   -XX:MetaspaceSize 指的是元空间达到这个值的时候会触发FULLGC 后续什么时候再次触发FULLGC由JVM自行计算

- **-Xss 虚拟机栈大小**


`)
</script>

<template>
  <div class="article">
    <TopArticleCard :isTop="false"></TopArticleCard>
    <MarkdownViewer class="markdown" :source="mdContent" :line-numbers="true" />
    <div class="last-next">
      <RouterLink to="/article/1">{{ '< 上一篇:文章名字' }}</RouterLink>
          <RouterLink to="/article/1">{{ '下一篇:文章名字 >' }}</RouterLink>
    </div>
  </div>
</template>

<style scoped lang="scss">
.article {
  width: 100%;

  .markdown {
    background: rgba(40, 44, 52, 0.6);
    padding: 6px 25px;
    width: 100%;
    border-radius: 10px;
  }

  .last-next {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
    width: 100%;
    padding: 14px;
    background: rgba(40, 44, 52, 0.6);
    border-radius: 8px;
    color: #999;
  }
}
</style>
