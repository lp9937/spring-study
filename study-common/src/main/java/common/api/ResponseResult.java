package common.api;

/**
 * 响应结果
 */
public class ResponseResult<T> {
    //状态码
    private long code;
    //提示信息
    private String message;
    //数据
    private T data;

    protected ResponseResult(){}

    protected ResponseResult(long code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    /**
     * 成功返回结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data){
        ResponseCode code=ResponseCode.SUCCESS;
        return new ResponseResult<T>(code.getCode(), code.getMessage(),data);
    }

    /**
     * 失败返回结果
     * @param errorCode
     * @param <T>
     * @return
     */
    public static<T> ResponseResult<T> fail(IResponseCode errorCode){
        return new ResponseResult<T>(errorCode.getCode(),errorCode.getMessage(),null);
    }

    /**
     * 失败返回结果
     * @param <T>
     * @return
     */
    public static<T> ResponseResult<T> fail(){
        return fail(ResponseCode.FAILED);
    }

    /**
     * 拒绝访问
     * @param data
     * @param <T>
     * @return
     */
    public static<T> ResponseResult<T> accessDenied(T data){
        ResponseCode code=ResponseCode.ACCESS_DENIED;
        return new ResponseResult(code.getCode(),code.getMessage(),data);
    }

    /**
     * 未登录或登陆过期
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> unAuthorized(T data){
        ResponseCode code= ResponseCode.UNAUTHORIZED;
        return new ResponseResult(code.getCode(),code.getMessage(),data);
    }

    public static<T> ResponseResult<T> validateFailed(){
        ResponseCode code= ResponseCode.VALIDATE_FAILED;
        return fail(code);
    }
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
