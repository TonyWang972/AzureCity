package org.jeecg.modules.azurecity.authentication.exception;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/6
 */
public class RepeatSubmitException extends RuntimeException {

    public RepeatSubmitException() {
        super("当前用户已经提交过实名申请！");
    }

}
