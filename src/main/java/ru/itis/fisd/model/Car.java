package ru.itis.fisd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Car {
    private long id;
    private long sellerId;
    private String name;
    private int price;
    private boolean isAvailable;
    private boolean isForSale;
}
