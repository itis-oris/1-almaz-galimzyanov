package ru.itis.fisd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Purchase {
    private Long id;
    private Long customerId;
    private Long carId;
    private String paymentMethod;
}
