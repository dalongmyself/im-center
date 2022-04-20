package com.im.api.Result;

import com.im.common.bean.Result;
import lombok.Data;

/**
 * @author dalong
 * @date 2022/4/20 18:47
 */
@Data
public class PushResult extends Result {

    private int code;

    private String message;
}
