package com.example.service;

import com.example.domain.AntUserRecommend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.ExecutionException;

public interface AntUserRecommendService extends IService<AntUserRecommend> {


    void importBigData(String filePath) throws ExecutionException, InterruptedException;
}
