#此工程为实现根据数据库数据，定时循环启动不同job的项目
目的：学习使用springboot和springbatch
使用的技术：
    1、springboot
    2、springbatch
    3、mybatis
注意事项：
    1、多数据源
        -在application.properties中定义多个数据源参数
        -在DatabaseConfig.java中定义默认数据源、其他数据源、sqlSessionFactory、事务管理器实例，并放入spring容器中
        -在启动类ImplApplication中，给@SpringBootApplication注解加(exclude= {DataSourceAutoConfiguration.class})，去掉数据源自动加载类
        -使用时，如需使用其他数据源，可根据数据源name注入
    2、mybatis
        -在application.properits中定义.xml文件路径和模型路径
        -由于在DatabaseConfig.java中自定义了数据源。所以须在sqlSessionFactory获取时，设定mybatis的文件路径和模型路径
        -在ImplApplicathon中，添加注解@MapperScan("com.jmf.impl.*")，扫描mapper文件
        -将xxxmapper.xml放入resources的自定义目录