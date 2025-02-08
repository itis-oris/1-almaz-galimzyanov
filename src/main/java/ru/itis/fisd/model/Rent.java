package ru.itis.fisd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class Rent {
    private Long id;
    private Long customerId;
    private Long carId;
    private Date dateStart;
    private Date dateEnd;
}
