package ru.magnit.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int field;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }
}
