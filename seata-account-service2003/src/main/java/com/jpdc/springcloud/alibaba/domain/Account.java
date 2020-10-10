package com.jpdc.springcloud.alibaba.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private Long userId;//用户id
    private Integer total;//总额度
    private Integer used;//已用额度
    private Integer residue;//剩余额度

}
