package com.groenify.api.rest.price;

import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.price.FactorAnswerPrice;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.framework.annotation.CompanyEPoleInPath;
import com.groenify.api.framework.annotation.FactorAnswerInPath;
import com.groenify.api.framework.annotation.FactorAnswerPriceInPath;
import com.groenify.api.framework.annotation.FactorInPath;
import com.groenify.api.rest.price.__model.FactorAnswerPriceReqMo;
import com.groenify.api.rest.price.__model.FactorAnswerPriceResMo;
import com.groenify.api.service.price.FactorAnswerPriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/company_epoles")
public class FactorAnswerPriceEndpoint {

    private final FactorAnswerPriceService service;

    public FactorAnswerPriceEndpoint(
            final FactorAnswerPriceService var) {
        this.service = var;
    }

    @GetMapping(value = "/{companyEPoleId}/factors/answers/prices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorAnswerPriceResMo> getPriceByPole(
            final @CompanyEPoleInPath CompanyEPole companyEPole) {
        final List<FactorAnswerPrice> list = service.getAllFromCompanyEPole(companyEPole);
        return FactorAnswerPriceResMo.mapCompanyEPoleToResMoList(list);
    }

    @GetMapping(value = "/{companyEPoleId}/factors/{factorId}/answers/prices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorAnswerPriceResMo> getPriceByPoleAndFactor(
            final @CompanyEPoleInPath CompanyEPole companyEPole,
            final @FactorInPath Factor factor) {
        final List<FactorAnswerPrice> list =
                service.getAllFromCompanyEPoleAndFactor(companyEPole, factor);
        return FactorAnswerPriceResMo.mapCompanyEPoleToResMoList(list);
    }

    @GetMapping(value = "/{companyEPoleId}/factors/answers/{answerId}/prices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorAnswerPriceResMo> getPriceByPoleAndAnswer(
            final @CompanyEPoleInPath CompanyEPole companyEPole,
            final @FactorAnswerInPath FactorAnswer factorAnswer) {
        final List<FactorAnswerPrice> list =
                service.getAllFromCompanyEPoleAndFactorAnswer(companyEPole, factorAnswer);
        return FactorAnswerPriceResMo.mapCompanyEPoleToResMoList(list);
    }

    @PostMapping(value = "/{companyEPoleId}/factors/answers/{answerId}/prices",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final FactorAnswerPriceResMo createPrice(
            final @CompanyEPoleInPath CompanyEPole companyEPole,
            final @FactorAnswerInPath FactorAnswer factorAnswer,
            final @Valid @RequestBody FactorAnswerPriceReqMo body) {
        final FactorAnswerPrice newPrice =
                service.create(companyEPole, factorAnswer, body);
        return FactorAnswerPriceResMo.mapCompanyEPoleToResMo(newPrice);
    }

    @GetMapping(value = "/factors/answers/prices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FactorAnswerPriceResMo> getAllPrices() {
        final List<FactorAnswerPrice> list = service.getAll();
        return FactorAnswerPriceResMo.mapCompanyEPoleToResMoList(list);
    }

    @GetMapping(value = "/factors/answers/prices/{priceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FactorAnswerPriceResMo getPriceById(
            final @FactorAnswerPriceInPath FactorAnswerPrice price) {
        return FactorAnswerPriceResMo.mapCompanyEPoleToResMo(price);
    }

    @PutMapping(value = "/factors/answers/prices/{priceId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FactorAnswerPriceResMo updatePrice(
            final @FactorAnswerPriceInPath FactorAnswerPrice price,
            final @RequestBody FactorAnswerPriceReqMo body) {
        final FactorAnswerPrice newPrice = service.update(price, body);
        return FactorAnswerPriceResMo.mapCompanyEPoleToResMo(newPrice);
    }

    @DeleteMapping(value = "/factors/answers/prices/{priceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final Boolean deletePrice(
            final @FactorAnswerPriceInPath FactorAnswerPrice price) {
        return service.delete(price);
    }

}
