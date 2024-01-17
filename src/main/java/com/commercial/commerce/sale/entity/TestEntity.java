package com.commercial.commerce.sale.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "test")
public class TestEntity {
    @Id
    private String id;

    private String name;

}
