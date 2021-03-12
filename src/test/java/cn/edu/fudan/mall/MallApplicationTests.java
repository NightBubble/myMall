package cn.edu.fudan.mall;

import cn.edu.fudan.mall.common.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MallApplicationTests {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void testToken() {

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW5nIiwiY3JlYXRlZCI6MTYxNTUyNzY2MDI0OSwiZXhwIjoxNjE1NTMxMjYwfQ.33VX8DQHHSIHCFhA8AwtOC2QmiS6XpJUstStLKNJ30nIs6LJYLrqnK5mMSe1wfo-mGF8DCJ1lQM5Xc-mlwrfFQ";
        String ans = jwtTokenUtil.getUsernameFromToken(token);
    }

}
