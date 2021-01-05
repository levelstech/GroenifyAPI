package com.groenify.api.rest.factor.answer;


import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.framework.annotation.FactorInPath;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerResMo;
import com.groenify.api.service.factor.answer.FactorAnswerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/factors")
public class FactorAnswerEndpoint {

    private final FactorAnswerService service;

    public FactorAnswerEndpoint(final FactorAnswerService var) {
        this.service = var;
    }

    @GetMapping(value = "/answers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorAnswerResMo> getAllFactors() {
        final List<FactorAnswer> answers = service.getAll();
        return FactorAnswerResMo.mapFactorAnswerToResMoList(answers);
    }

    @GetMapping(value = "/{factorId}/answers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorAnswerResMo> getFactorById(
            final @FactorInPath Factor factor) {
        final List<FactorAnswer> answers = service.getAllFromFactor(factor);
        return FactorAnswerResMo.mapFactorAnswerToResMoList(answers);
    }

}
