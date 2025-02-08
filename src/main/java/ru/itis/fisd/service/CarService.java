package ru.itis.fisd.service;

import ru.itis.fisd.model.Car;
import ru.itis.fisd.repository.CarRepository;

import java.util.List;

public class CarService {
    private final CarRepository repository;

    public CarService() {
        repository = new CarRepository();
    }

    public List<Car> findAll(int limit, int offset, Long id) {
        return repository.findAll(limit, offset, id);
    }

    public List<Car> findByUser(int limit, int offset, Long id) {
        return repository.findByUser(limit, offset, id);
    }

    public Car findById(Long id) {
        return repository.findById(id);
    }

    public List<Car> findBySeller(int limit, int offset, Long id) {
        return repository.findBySeller(limit, offset, id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void save(Car car) {
        repository.insert(car);
    }

    public void update(Car car) {
        repository.update(car);
    }

    public int count() {
        return repository.count();
    }

    public int countBySeller(long id) {
        return repository.countBySeller(id);
    }

    public int countByUser(long id) {
        return repository.countByUser(id);
    }
}
