package com.kuaimei56.cjkzy_helper.entity;

import java.io.Serializable;

/**
 * Created by CLEVO on 2016/7/22.
 */
public class Strategy implements Serializable {

    int id;
    String keyword;
    int valid;
    int mode;
    String result;
    int resultMode;
    String updateTime;
    String recorder;
    String regex;

    public Strategy(int id, String keyword, int valid, int mode, String result, int resultMode, String updateTime, String recorder, String regex) {
        this.id = id;
        this.keyword = keyword;
        this.valid = valid;
        this.mode = mode;
        this.result = result;
        this.resultMode = resultMode;
        this.updateTime = updateTime;
        this.recorder = recorder;
        this.regex = regex;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public int getResultMode() {
        return resultMode;
    }

    public void setResultMode(int resultMode) {
        this.resultMode = resultMode;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }
}
