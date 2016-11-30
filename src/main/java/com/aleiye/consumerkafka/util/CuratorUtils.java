package com.aleiye.consumerkafka.util;

import com.aleiye.zkclient.standard.CuratorFactory;
import org.apache.curator.framework.CuratorFramework;

/**
 * Created by aric on 2016/10/25.
 */
public class CuratorUtils {

    private static volatile CuratorFramework curator = null;


    public static CuratorFramework getCurator() {
        if (curator == null) {
            synchronized (CuratorUtils.class) {
                if (curator == null) {
                    CuratorFramework client = CuratorFactory.createFramework(ConfigUtils.ZK_URL);
                    client.start();
                    try {
                        client.blockUntilConnected();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    curator = client;
                }
            }
        }
        return curator;
    }
    public static void close(){
        if(curator != null)
            curator.close();
    }
}
