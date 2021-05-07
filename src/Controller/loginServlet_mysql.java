/*
做不出不要急，先学一会 也许做着做着就找到方法了

后台：1.获取前台的参数(request.getParameter("表单元素的name！！属性值"))
2.参数非空判断（get提交方式还没检查呢）
不满足条件就return!!!不然还往下走！！
如果不为空 用户名and密码一起去数据库里查（JDBC）对不对,返回user对象
    1.mysql的驱动包拷贝到项目的WEB-INF的lib目录下
    2.BDUtil工具类（连接数据库的方法/关闭资源的方法）
        db.properties配置文件(放置在项目源资源src下)
            驱动名/数据库地址/数据库账号/数据库密码
        得到数据库连接：
            1.加载配置文件到输入流中
            2.得到Properties配置对象(new)
            3.将输入流对象通过load方法，加载到Properties对象中
            4.通过配置对象getProperty()得到配置文件中的值
            5.加载驱动
            6.得到数据库连接，DriverManager.getConnection(地址，账户，密码)
        关闭资源：
            ResultSet/PrepareStatement/Connection
            判断对象是否为空，不为空则关闭资源
    3.通过用户名和密码去数据库中查询用户对象
            1.得到数据库连接（调用DBUtils方法）
            2.定义sql语句
            3.预编译sql语句
            4.设置参数，下标从1开始
            5.执行查询，返回resultSet结果集
            6.判断并分析结果集，如果存在，获取数据，设置到指定对象中（user对象）
            7.关闭资源
            8.返回user对象
如果用user对象不为空，登录成功;否则输出错误原因
*/
package Controller;

public class loginServlet_mysql {
}
