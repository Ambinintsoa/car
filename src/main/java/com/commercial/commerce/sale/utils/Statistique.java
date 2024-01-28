package com.commercial.commerce.sale.utils;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Statistique {
    private String label;
    private long count;
}
