package com.aleiye.consumerkafka.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by aric on 2016/10/25.
 */
public class Utils {
    public static List<Integer> getPartitions(String topic) {
        List<Integer> list = Lists.newArrayList();
        String path = String.format("/brokers/topics/%s/partitions", topic);
        try {
            if (CuratorUtils.getCurator().checkExists().forPath(path) != null) {
                List<String> childrenList = CuratorUtils.getCurator().getChildren().forPath(path);
                for (String ele : childrenList) {
                    list.add(Integer.parseInt(ele));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
