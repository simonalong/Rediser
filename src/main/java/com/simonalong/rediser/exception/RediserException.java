package com.simonalong.rediser.exception;

/**
 * @author shizi
 * @since 2020/7/5 6:16 PM
 */
public class RediserException extends RuntimeException {

    public RediserException() {
        super();
    }

    public RediserException(String msg, Throwable throwable){
        super(msg, throwable);
    }

    public RediserException(String msg){
        super(msg);
    }

    public RediserException(Throwable e){
        super(e);
    }
}
