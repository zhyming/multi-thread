package com.zhym.basic.readwritelock;

import java.util.Random;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 23:39
 */
public class WriteWorker extends Thread{

    private static final Random random = new Random(System.currentTimeMillis());

    private final SharedData data;

    private final String filler;

    private int index = 0;

    public WriteWorker(SharedData data, String filler) {
        this.data = data;
        this.filler =filler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char c = nextChar();
                data.write(c);
                Thread.sleep(random.nextInt(1_000));
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private char nextChar() {
        char c = filler.charAt(index);
        index ++;
        if (index >= filler.length()) {
            index = 0;
        }
        return c;
    }
}
