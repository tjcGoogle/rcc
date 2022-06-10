package com.example.recbug;

import com.example.domain.AntUserRecommend;
import com.example.service.AntUserRecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class RecBugApplicationTests {

    @Autowired
    private AntUserRecommendService antUserRecommendService;

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        String filePath = "/Users/taojiacheng/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/01a15c721f56633785c3afd51a7568c7/Message/MessageTemp/b0a1dcc019346f478b5b150f788d9f68/File/ant_user_recommend_1.txt";
        antUserRecommendService.importBigData(filePath);
    }

}
