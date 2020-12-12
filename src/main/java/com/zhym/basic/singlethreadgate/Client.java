package com.zhym.basic.singlethreadgate;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 22:31
 */
public class Client {

    public static void main(String[] args) {
        Gate gate = new Gate();

        User BB = new User("BB", "Beijing", gate);
        User SH = new User("SS", "Shanghai", gate);
        User GZ = new User("GG", "Guangzhou", gate);

        BB.start();
        SH.start();
        GZ.start();
    }
}
