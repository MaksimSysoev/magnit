package ru.magnit.app.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magnit.app.OperationWithXml;
import ru.magnit.app.model.Test;
import ru.magnit.app.repository.FieldRepository;

import javax.xml.parsers.ParserConfigurationException;

@RestController
@RequestMapping("/fields")
public class FieldControl {

    private final FieldRepository fields;
    private OperationWithXml wxf;

    public FieldControl(final FieldRepository fields) {
        this.fields = fields;
    }

    @GetMapping("/{id}")
    public void create(@PathVariable int id) throws Exception {

        this.fields.deleteAll();

        for (int i = 0; i < id; i++) {
            Test test = new Test();
            test.setField(i);
            this.fields.save(test);
        }

        wxf = new OperationWithXml(id);
        wxf.createXml();

    }
}
