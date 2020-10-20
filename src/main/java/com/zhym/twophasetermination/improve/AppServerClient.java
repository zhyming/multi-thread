package com.zhym.twophasetermination.improve;

import java.io.IOException;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/21 0021 0:24
 */
public class AppServerClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        AppServer server = new AppServer(12345);
        server.start();

        Thread.sleep(30_000);
        server.shutdown();
    }
}
