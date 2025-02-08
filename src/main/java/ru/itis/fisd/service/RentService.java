package ru.itis.fisd.service;

import ru.itis.fisd.model.Car;
import ru.itis.fisd.model.Rent;
import ru.itis.fisd.repository.RentRepository;

import java.util.List;

public class RentService {
    private final RentRepository repository;


    public RentService() {
        repository = new RentRepository();
    }

    public void insert(Rent rent) {
        repository.insert(rent);
    }

    public void delete(Car car, long id) {
        repository.delete(car, id);
    }

    public Long findById(long userId, long carId) {
        return repository.findById(userId, carId);
    }

    public List<Rent> findAll(long carId) {
        return repository.findAll(carId);
    }
}
