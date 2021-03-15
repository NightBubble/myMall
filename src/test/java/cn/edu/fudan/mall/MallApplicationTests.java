package cn.edu.fudan.mall;

import cn.edu.fudan.mall.common.utils.JwtTokenUtil;
import cn.edu.fudan.mall.mbg.mapper.PmsProductMapper;
import cn.edu.fudan.mall.mbg.model.PmsProduct;
import cn.edu.fudan.mall.mbg.model.PmsProductExample;
import cn.edu.fudan.mall.service.EsProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)

@SpringBootTest
class MallApplicationTests {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EsProductService esProductService;


    @Test
    void testToken() {

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW5nIiwiY3JlYXRlZCI6MTYxNTUyNzY2MDI0OSwiZXhwIjoxNjE1NTMxMjYwfQ.33VX8DQHHSIHCFhA8AwtOC2QmiS6XpJUstStLKNJ30nIs6LJYLrqnK5mMSe1wfo-mGF8DCJ1lQM5Xc-mlwrfFQ";
        String ans = jwtTokenUtil.getUsernameFromToken(token);
    }

    @Test
    void importAll(){
        int res = esProductService.importPmsProduct();
        System.out.println(res);
    }

    @Test
    void delete(){
        int id = 2;
        esProductService.deleteById(Long.valueOf(id));
    }


}
