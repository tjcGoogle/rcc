package com.example.task;


import com.example.domain.AntUserRecommend;
import com.example.service.AntUserRecommendService;
import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * 任务
 */
@Slf4j
public class BatchTask extends RecursiveAction {

    /**
     * 临界值
     */
    private static final int THRESHOLD = 100;

    private final List<AntUserRecommend> list;

    private final AntUserRecommendService antUserRecommendService;

    public BatchTask(List<AntUserRecommend> list, AntUserRecommendService antUserRecommendService) {
        this.antUserRecommendService = antUserRecommendService;
        this.list = list;
    }

    @Override
    protected void compute() {
        boolean compute = list.size() <= THRESHOLD;
        if (compute) {
            log.info("开始插入数据：{}", list.size());
            antUserRecommendService.saveBatch(list);
        } else {
            log.info("正在整理数据,当前大小:{}....", list.size());
            List<List<AntUserRecommend>> lists = BatchTask.averageAssign(list, 2);
            // 递归
            BatchTask t1 = new BatchTask(lists.get(0), antUserRecommendService);
            BatchTask t2 = new BatchTask(lists.get(1), antUserRecommendService);
            // 拆分任务，把任务压入线程队列
            invokeAll(t1, t2);
        }
    }


    /**
     * 将一组数据平均分成n组
     *
     * @param source 要分组的数据源
     * @param n      平均分成n组
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        // 余数
        int remainder = source.size() % n;
        // 商
        int number = source.size() / n;
        // 偏移量
        int offset = 0;
        for (int i = 0; i < n; i++) {
            List<T> value;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }


}

