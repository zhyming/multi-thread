package com.zhym.basic.activeobject;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/22 0022 0:50
 */
public class DisplayStringRequest extends MethodRequest {

    private final String text;

    public DisplayStringRequest(Servant servant, final String text) {
        super(servant, null);
        this.text = text;
    }

    @Override
    public void execute() {
        this.servant.displayString(text);
    }
}
