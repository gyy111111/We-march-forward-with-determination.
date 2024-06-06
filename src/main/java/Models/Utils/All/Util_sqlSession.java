package Models.Utils.All;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Util_sqlSession {
    //编写一个工具类:用于获取SQLSession会话对象:
    public static SqlSession getSQLSession(){
        //在resources目录下的配置文件;

        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("MyBatisConfigration.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//通过sql工厂获取,用于会话的sqlSession对象:
        return sqlSessionFactory.openSession();
    }
}
