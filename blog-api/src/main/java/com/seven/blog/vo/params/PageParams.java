package com.seven.blog.vo.params;

import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/4 15:06
 * @Description
 */
@Data
public class PageParams {
    private int page=1;
    private int pageSize=10;
}
