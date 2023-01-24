import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author malguy-wang sir
 * @create ---
 */
public class password {
    PasswordEncoder encoder;

    @Before
    public void before() {
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    public void test() {
        String hash = encoder.encode("123456");
        System.out.println(hash);
        System.out.println(encoder.matches("123456",hash));
        String hash1 = encoder.encode("abcxyz");
        System.out.println(hash1);
    }
}
