package ru.itis.fisd.service;

import ru.itis.fisd.model.Purchase;
import ru.itis.fisd.repository.PurchaseRepository;

public class PurchaseService {
    private final PurchaseRepository repository;


    public PurchaseService() {
        repository = new PurchaseRepository();
    }

    public void insert(Purchase purchase) {
        repository.insert(purchase);
    }

    public Long findById(long userId, long carId) {
        return repository.findById(userId, carId);
    }
}
