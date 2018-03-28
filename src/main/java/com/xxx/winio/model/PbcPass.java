package com.xxx.winio.model;

import com.alibaba.fastjson.annotation.JSONField;

public class PbcPass {
    private long id;
    private long random;
    private String uname;

    @JSONField(name = "pass_src")
    private String passSrc;

    @JSONField(name = "pass_enc")
    private String passEnc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRandom() {
        return random;
    }

    public void setRandom(long random) {
        this.random = random;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassSrc() {
        return passSrc;
    }

    public void setPassSrc(String passSrc) {
        this.passSrc = passSrc;
    }

    public String getPassEnc() {
        return passEnc;
    }

    public void setPassEnc(String passEnc) {
        this.passEnc = passEnc;
    }

    @Override
    public String toString() {
        return "PbcPass{" +
                "id=" + id +
                ", random=" + random +
                ", uname='" + uname + '\'' +
                ", passSrc='" + passSrc + '\'' +
                ", passEnc='" + passEnc + '\'' +
                '}';
    }
}
