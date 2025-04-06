package top.frium;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@MapperScan("top.frium.mapper")
@EnableTransactionManagement
public class BlogBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBootApplication.class, args);
        System.out.println("启动!");
    }

}
