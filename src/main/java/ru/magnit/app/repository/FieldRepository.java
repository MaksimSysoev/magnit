package ru.magnit.app.repository;

import org.springframework.data.repository.CrudRepository;
import ru.magnit.app.model.Test;

public interface FieldRepository extends CrudRepository<Test, Integer> {
}
