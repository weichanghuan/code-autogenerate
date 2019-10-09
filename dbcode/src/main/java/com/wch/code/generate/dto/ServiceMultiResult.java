package com.wch.code.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 统一列表返回对象
 */
@Data
@AllArgsConstructor
public class ServiceMultiResult<T> {
    private long total;
    private List<T> result;

    public int getResultSize(){
        if(CollectionUtils.isEmpty(this.result)){
            return 0;
        }
        return result.size();
    }
}
