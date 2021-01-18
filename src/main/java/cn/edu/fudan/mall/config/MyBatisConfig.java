package cn.edu.fudan.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan("cn.edu.fudan.mall.mbg.mapper")
public class MyBatisConfig {
}
