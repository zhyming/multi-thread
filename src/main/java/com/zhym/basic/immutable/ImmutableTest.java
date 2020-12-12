package com.zhym.basic.immutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/15 0015 22:48
 */
public class ImmutableTest {

    private final int age;

    private final String name;

    private final List<String> list;

    public ImmutableTest(int age, String name) {
        this.age = age;
        this.name = name;
        list = new ArrayList<>();
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public List<String> getList() {
        //返回一个不可变对象
        return Collections.unmodifiableList(list);
    }
}
