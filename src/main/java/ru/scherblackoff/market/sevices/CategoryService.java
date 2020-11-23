package ru.scherblackoff.market.sevices;

import ru.scherblackoff.market.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAll();

    Category get(Long id);
}
