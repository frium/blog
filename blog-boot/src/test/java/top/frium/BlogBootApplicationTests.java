package top.frium;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogBootApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Math.round(100000 + Math.random() * 900000));
    }

}
