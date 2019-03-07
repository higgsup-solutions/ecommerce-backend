package com.higgsup.xshop.dto;

import com.higgsup.xshop.common.ListOrderType;
import lombok.Data;

@Data
public class OrderCriteriaDTO {

    public Integer userId;

    public ListOrderType orderListingType;
}
