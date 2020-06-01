package com.nyaxs.shypicture.exception;

import lombok.Data;

/**
 * @author nyaxs
 * @version V1.0
 * @Package com.nyaxs.shypicture.biz
 * @date 2020/6/1 15:07
 */
@Data
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeEnum code;
    private String remark;

    public BizException(CodeEnum code) {
        super(code.getMessage());
        this.code = code;
    }

    public BizException withRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
