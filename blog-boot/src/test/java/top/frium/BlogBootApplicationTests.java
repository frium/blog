package top.frium;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class BlogBootApplicationTests {

    @Test
    void contextLoads() throws NoSuchAlgorithmException, InvalidKeyException {
    }

}
