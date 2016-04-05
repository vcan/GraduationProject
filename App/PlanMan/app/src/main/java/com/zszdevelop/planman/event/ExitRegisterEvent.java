package com.zszdevelop.planman.event;

/**
 * Created by ShengZhong on 2016/1/12.
 */
public class ExitRegisterEvent {
    private boolean isExit;

    public ExitRegisterEvent(boolean isExit) {
        this.isExit = isExit;
    }

    public boolean isExit() {
        return isExit;
    }
}
