package com.groenify.api.rest.question;

import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.service.factor.FactorService;
import com.groenify.api.rest.question.__model.QuestionResMo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/application/questions")
public class QuestionEndpoint {

    private final FactorService factorService;

    public QuestionEndpoint(final FactorService service) {
        this.factorService = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<QuestionResMo> getPriceByPole() {
        final List<Factor> factors = factorService.getAll();
        return QuestionResMo.ofList(factors);
    }
}
