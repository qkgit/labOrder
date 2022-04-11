package com.bdu.laborder.exception;

/**
 * @Author Qi
 * @data 2021/2/5 10:14
 */
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public BaseException() {
        super();
        this.errorMsg = "操作异常！请联系管理员";
    }

    public BaseException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface.getCode());
        this.errorCode = errorInfoInterface.getCode();
        this.errorMsg = errorInfoInterface.getMsg();
    }

    public BaseException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getCode(), cause);
        this.errorCode = errorInfoInterface.getCode();
        this.errorMsg = errorInfoInterface.getMsg();
    }

    public BaseException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BaseException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BaseException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

    /**
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
