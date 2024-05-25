package com.raposo.experiment;

import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.service.DendroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DendroTests {

    @Autowired
    private DendroService dendroService;

    // Matheus
    @Test
    public void cadastrarDendroTest() {
        var dendro = new Dendro(1L, "Dendro-teste", 0);
        var response = dendroService.cadastrarDendro(dendro);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(response.get().getId(), 1L);
    }

}
