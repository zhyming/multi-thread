package com.zhym.balking;

import com.zhym.readwritelock.WriteWorker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/19 0019 23:35
 */
public class BalkingData {

    private final String fileName;

    private String content;

    private boolean changed;

    public BalkingData(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.changed = false;
    }

    public synchronized void change(String newContent) {
        this.content = newContent;
        this.changed = true;
    }

    public synchronized void save() {
        if (!changed) {
            return;
        }

        doSave();
        this.changed = false;
    }

    private void doSave() {
        System.out.println(Thread.currentThread().getName() + " calls do save, content = " + this.content);
        try(Writer writer = new FileWriter(fileName, true)) {
            writer.write(content);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
