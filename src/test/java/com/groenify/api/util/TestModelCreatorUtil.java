package com.groenify.api.util;

import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.database.factor.answer.FactorAnswerBoolean;
import com.groenify.api.database.factor.answer.FactorAnswerMultipleChoice;
import com.groenify.api.database.price.FactorAnswerPrice;

public final class TestModelCreatorUtil {
    private static int counter = 0;

    private TestModelCreatorUtil() {
    }

    public static Company newCompany() {

        final String companyName = String.format("Company-%s", ++counter);
        return Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"" + companyName + "\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}");

    }

    public static EPoleBrand newEPoleBrand() {
        final String brandName = String.format("Brand-%s", ++counter);
        return EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"" + brandName + "\"}");
    }

    public static EPole newEPole() {
        final String epoleType = String.format("PoleType-%s", ++counter);
        return EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"" + epoleType + "\"}");
    }

    public static EPole newEPole(final EPoleBrand brandWahid) {
        final EPole ePole = newEPole();
        ePole.setBrand(brandWahid);
        return ePole;
    }

    public static CompanyEPole newCompanyEPole(final Double price) {
        return CompanyEPole.ofJsonObjStr("{\"base_price\":" + price + "}");

    }

    public static CompanyEPole newCompanyEPole(
            final Double price,
            final Company company, final EPole ePole) {

        final CompanyEPole companyEPole = newCompanyEPole(price);
        companyEPole.setCompany(company);
        companyEPole.setePole(ePole);
        return companyEPole;
    }

    public static FactorType newFactorType() {
        final String epoleType = String.format("FactorType-%s", ++counter);
        return FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"" + epoleType + "\"}");
    }

    public static Factor newFactor() {
        final String factorName = String.format("Factor-%s", ++counter);
        return Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"" + factorName + "\","
                        + "\"question\":\"Q11?\","
                        + "\"description\":\"dd\"}");
    }

    public static Factor newFactor(final FactorType type) {
        final Factor factor = newFactor();
        factor.setType(type);
        return factor;
    }

    public static FactorAnswerBoolean newFactorAnswerBoolean(
            final Boolean answer) {
        return FactorAnswerBoolean.ofJsonObjStr(
                "{\"id\":1, \"answer_boolean\":" + answer + "}");
    }

    public static FactorAnswerBoolean newFactorAnswerBoolean(
            final Boolean a, final Factor factor) {
        final FactorAnswerBoolean answer = newFactorAnswerBoolean(a);
        answer.setFactor(factor);
        return answer;

    }

    public static FactorAnswerMultipleChoice newFactorText(final String text) {
        return FactorAnswerMultipleChoice.ofJsonObjStr(
                "{\"id\":1, \"answer_multiple\":\"" + text + "\"}");
    }

    public static FactorAnswerMultipleChoice newFactorText(
            final String text, final Factor factor) {
        final FactorAnswerMultipleChoice answer = newFactorText(text);
        answer.setFactor(factor);
        return answer;
    }


    public static FactorAnswerPrice newFactorAnswerPrice(final Double price) {
        return FactorAnswerPrice.ofJsonObjSr("{\"price\":" + price + "}");
    }

    public static FactorAnswerPrice newFactorAnswerPrice(
            final Double price,
            final FactorAnswer answer,
            final CompanyEPole pole) {

        final FactorAnswerPrice answerPrice = newFactorAnswerPrice(price);
        answerPrice.setFactorAnswer(answer);
        answerPrice.setPole(pole);
        return answerPrice;
    }
}
