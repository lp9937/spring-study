package common.api;

public enum ResponseCode implements IResponseCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    ACCESS_DENIED(403, "没有相关权限");

    ResponseCode(long code, String message){
        this.code=code;
        this.message=message;
    }
    private long code;
    private String message;
    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
