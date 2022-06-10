package com.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.AntUserRecommend;
import com.example.mapper.AntUserRecommendMapper;
import com.example.service.AntUserRecommendService;
import com.example.task.BatchTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author taojiacheng
 */
@Service
@Slf4j
public class AntUserRecommendServiceImpl extends ServiceImpl<AntUserRecommendMapper, AntUserRecommend> implements AntUserRecommendService {

    private static final int _10_MB = 10 * 1024 * 1024;

    @Autowired
    @Lazy
    private AntUserRecommendService antUserRecommendService;


    @Override
    public void importBigData(String filePath) throws ExecutionException, InterruptedException {
        log.info("开始解析json文件");
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            // 10M缓存
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8), _10_MB);
            while (in.ready()) {
                ForkJoinPool forkJoinPool = new ForkJoinPool(10);
                try {
                    String json = in.readLine();
                    List<AntUserRecommend> list = JSONObject.parseArray(json, AntUserRecommend.class);
                    BatchTask batchTask = new BatchTask(list, antUserRecommendService);
                    ForkJoinTask<Void> submit = forkJoinPool.submit(batchTask);
                    submit.get();
                } finally {
                    forkJoinPool.shutdown();
                }
            }
            in.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
