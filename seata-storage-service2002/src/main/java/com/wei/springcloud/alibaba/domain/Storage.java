package com.wei.springcloud.alibaba.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage {

    private Integer id;
    private Integer productId;
    private int total;
    private int used;
    private int residue;
}
