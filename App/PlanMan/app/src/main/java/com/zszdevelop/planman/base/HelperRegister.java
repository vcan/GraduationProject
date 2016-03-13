package com.zszdevelop.planman.base;

import com.zszdevelop.planman.bean.RegisterData;

/**
 * Created by jimmy on 16/1/3.
 */
public class HelperRegister {


    private static HelperRegister helperRegister = null;
    private RegisterData registerData;


    public static HelperRegister getInstance() {
        if (helperRegister == null) {
            helperRegister = new HelperRegister();
        }
        return helperRegister;

    }


    public RegisterData getRegisterData() {
        return registerData;
    }

    public void setRegisterData(RegisterData registerData) {
        this.registerData = registerData;
    }
}
