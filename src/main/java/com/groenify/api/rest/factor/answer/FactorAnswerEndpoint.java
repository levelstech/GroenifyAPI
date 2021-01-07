package com.groenify.api.rest.factor.answer;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.framework.annotation.FactorAnswerInPath;
import com.groenify.api.framework.annotation.FactorInPath;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerResMo;
import com.groenify.api.service.factor.answer.FactorAnswerService;
import com.groenify.api.util.LongUtil;
import com.groenify.api.util.MapperUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/factors")
public class FactorAnswerEndpoint {

    private final FactorAnswerService service;

    public FactorAnswerEndpoint(final FactorAnswerService var) {
        this.service = var;
    }

    private static FactorAnswerReqMo parseReqMoOfEnum(
            final String body,
            final FactorTypeEnum typeEnum) {
        final Class<? extends FactorAnswerReqMo> requestModelClazz =
                FactorAnswerReqMo.getClassOf(typeEnum);
        return MapperUtil.readObject(body, requestModelClazz);
    }

    private static FactorAnswerReqMo parseReqMoOfLong(
            final String body,
            final Long type) {
        final FactorTypeEnum typeEnum = FactorTypeEnum.valueOfFactorOfId(type);
        return parseReqMoOfEnum(body, typeEnum);
    }

    @GetMapping(value = "/{factorId}/answers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorAnswerResMo> getAnswerByFactorId(
            final @FactorInPath Factor factor) {
        final List<FactorAnswer> answers = service.getAllFromFactor(factor);
        return FactorAnswerResMo.mapFactorAnswerToResMoList(answers);
    }

    @PostMapping(value = "/{factorId}/answers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final FactorAnswerResMo createFactorAnswer(
            final @FactorInPath Factor factor,
            final @RequestParam("type") String type,
            final @RequestBody String bodyStr) {
        final Long typeL = LongUtil.parseOrDefault(type);
        final FactorAnswerReqMo reqMo = parseReqMoOfLong(bodyStr, typeL);
        final FactorAnswer answer = service.create(factor, reqMo);
        return answer.mapToResponseModel();
    }

    @GetMapping(value = "/answers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorAnswerResMo> getAllAnswers() {
        final List<FactorAnswer> answers = service.getAll();
        return FactorAnswerResMo.mapFactorAnswerToResMoList(answers);
    }

    @GetMapping(value = "/answers/{answerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FactorAnswerResMo getFactorAnswerById(
            final @FactorAnswerInPath FactorAnswer answer) {
        return answer.mapToResponseModel();
    }

    @PutMapping(value = "/answers/{answerId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FactorAnswerResMo updateFactorAnswerById(
            final @FactorAnswerInPath FactorAnswer answer,
            final @RequestBody String bodyStr) {
        final FactorAnswerReqMo reqMo =
                parseReqMoOfEnum(bodyStr, answer.getTypeEnum());
        final FactorAnswer newFactorAnswer = service.update(answer, reqMo);
        return newFactorAnswer.mapToResponseModel();
    }

    @DeleteMapping(value = "/answers/{answerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deleteFactorAnswerById(
            final @FactorAnswerInPath FactorAnswer answer) {
        return service.delete(answer);
    }

}
