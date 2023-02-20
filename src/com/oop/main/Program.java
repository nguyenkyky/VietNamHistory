package com.oop.main;

import com.oop.repository.Repository;
import com.oop.repository.impl.ThoiKyRepositoryImpl;

public class Program {
    private Repository[] repositories = {
            ThoiKyRepositoryImpl.getInstance()
    };

    public Program() {
        for (Repository repo : repositories) {
            repo.loadData();
        }
    }
}
