package cn.edu.fudan.mall.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成MBG代码
 */
public class Generator {
    public static void main(String[] args) throws Exception{
//        MBG执行过程中的警告信息
        List<String> warnings = new ArrayList<String>();
//        生成代码覆盖源代码
        boolean overwrite = true;
//        读取MBG配置文件
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");   //读取的是resource下的xml文件
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        //执行生成代码
        myBatisGenerator.generate(null);
        //输出警告信息
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
