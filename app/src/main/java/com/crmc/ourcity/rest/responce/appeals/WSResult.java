package com.crmc.ourcity.rest.responce.appeals;

import java.util.List;

/**
 * Created by podo on 04.09.15.
 */
public class WSResult {
    public int ResultCode;
    public String ResultMessage;
    public int Status;
    List<ResultObject> ResultObjects;
    public String ReturnStatus;
    public String ReturnValue;

    public List<ResultObject> getResultObjects() {
        return ResultObjects;
    }

}
