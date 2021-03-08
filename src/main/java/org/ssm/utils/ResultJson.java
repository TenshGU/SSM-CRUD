package org.ssm.utils;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Tensh
 * @createDate: 2021/2/28
 */
public class ResultJson {
    /**
     * 100，代表请求成功
     * 200，代表请求失败
     */
    private int code;

    /**
     * 请求返回的提示信息
     */
    private String msg;

    /**
     * 用于存放结果信息
     */
    private Map<String, Object> infoMap = new HashMap<>();

    public ResultJson() {
    }

    public ResultJson(int code, String msg, Map<String, Object> infoMap) {
        this.code = code;
        this.msg = msg;
        this.infoMap = infoMap;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map<String, Object> infoMap) {
        this.infoMap = infoMap;
    }

    /**
     *
     * @return 成功信息
     */
    public static ResultJson success() {
        ResultJson resultJson = new ResultJson();
        resultJson.setCode(100);
        resultJson.setMsg("处理成功");
        return resultJson;
    }

    /**
     *
     * @return 失败信息
     */
    public static ResultJson fail() {
        ResultJson resultJson = new ResultJson();
        resultJson.setCode(200);
        resultJson.setMsg("处理失败");
        return resultJson;
    }

    /**
     * 链式追加结果信息
     * @param key
     * @param value
     * @return
     */
    public ResultJson addInfo(String key,Object value) {
        this.infoMap.put(key, value);
        return this;
    }
}
