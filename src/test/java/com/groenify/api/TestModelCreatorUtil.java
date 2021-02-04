package com.groenify.api;

import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorType;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.factor.answer.FactorAnswerBoolean;
import com.groenify.api.database.model.factor.answer.FactorAnswerDoubleNumber;
import com.groenify.api.database.model.factor.answer.FactorAnswerMultipleChoice;
import com.groenify.api.database.model.factor.answer.FactorAnswerNumber;
import com.groenify.api.database.model.price.FactorAnswerPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

public final class TestModelCreatorUtil {
    private static int counter = 0;
    private static final Logger L =
            LoggerFactory.getLogger(TestModelCreatorUtil.class);

    private TestModelCreatorUtil() {
    }

    public static Company newCompany() {
        final String companyName = String.format("Company-%s", ++counter);
        return Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"" + companyName + "\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}");

    }

    public static Company newCompany(final ModelCreator creator) {
        return creator.storeNew(newCompany());
    }


    public static EPoleBrand newEPoleBrand() {
        final String brandName = String.format("Brand-%s", ++counter);
        return EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"" + brandName + "\"}");
    }

    public static EPoleBrand newEPoleBrand(final ModelCreator creator) {
        return creator.storeNew(newEPoleBrand());
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

    public static EPole newEPole(final ModelCreator creator) {
        final EPoleBrand brand = newEPoleBrand(creator);
        final EPole ePole = newEPole(brand);
        return creator.storeNew(ePole);
    }

    public static CompanyEPole newCompanyEPole(final Double price) {
        return CompanyEPole.ofJsonObjStr("{\"base_price\":" + price + "}");
    }

    public static CompanyEPole newCompanyEPole(
            final Double price,
            final Company company, final EPole ePole) {

        final CompanyEPole companyEPole = newCompanyEPole(price);
        companyEPole.setCompany(company);
        companyEPole.setEPole(ePole);
        return companyEPole;
    }

    public static CompanyEPole newCompanyEPole(
            final Double price, final ModelCreator creator) {
        final Company company = newCompany(creator);
        final EPole ePole = newEPole(creator);

        final CompanyEPole companyEPole =
                newCompanyEPole(price, company, ePole);
        return creator.storeNew(companyEPole);
    }

    public static FactorType newBooleanFactorType() {
        return FactorTypeEnum.BOOLEAN_QUESTION.getMappedTo();
    }

    public static FactorType newFactorTypeFromEnum(final FactorTypeEnum type) {
        return type.getMappedTo();
    }

    public static FactorType newFactorType() {
        final String epoleType = String.format("FactorType-%s", ++counter);
        return FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"" + epoleType + "\"}");
    }

    public static FactorType newFactorType(final ModelCreator creator) {
        return creator.storeNew(newFactorType());
    }

    public static Factor newFactor() {
        final String factorName = String.format("Factor-%s", ++counter);
        return Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"" + factorName + "\","
                        + "\"question\":\"Q11?\","
                        + "\"required\":false,"
                        + "\"description\":\"dd\"}");
    }

    public static Factor newFactor(final FactorType type) {
        final Factor factor = newFactor();
        factor.setType(type);
        return factor;
    }

    public static Factor newFactor(
            final ModelCreator creator,
            final FactorTypeEnum typeEnum) {
        final FactorType type = newFactorTypeFromEnum(typeEnum);
        final Factor factor = newFactor(type);
        return creator.storeNew(factor);
    }

    public static FactorAnswerBoolean newFactorAnswerBoolean(
            final Boolean answer) {
        return FactorAnswerBoolean.ofJsonObjStr(
                "{\"id\":1, \"answer_boolean\":" + answer + "}");
    }

    public static FactorAnswerBoolean newFactorAnswerBoolean(
            final Boolean a, final Factor factor) {
        final FactorAnswerBoolean answer = newFactorAnswerBoolean(a);
        answer.setOwnFactor(factor);
        answer.setType(factor.getType());
        return answer;
    }

    public static FactorAnswerBoolean newFactorAnswerBoolean(
            final Boolean answerBoolean, final ModelCreator creator) {
        final Factor factor = newFactor(
                creator, FactorTypeEnum.BOOLEAN_QUESTION);
        final FactorAnswerBoolean answer =
                newFactorAnswerBoolean(answerBoolean, factor);
        return creator.storeNew(answer);
    }

    public static FactorAnswerMultipleChoice newFactorAnswerText(final String text) {
        return FactorAnswerMultipleChoice.ofJsonObjStr(
                "{\"id\":1, \"answer_multiple\":\"" + text + "\"}");
    }

    public static FactorAnswerMultipleChoice newFactorAnswerText(
            final String text, final Factor factor) {
        final FactorAnswerMultipleChoice answer = newFactorAnswerText(text);
        answer.setOwnFactor(factor);
        return answer;
    }

    public static FactorAnswerMultipleChoice newFactorAnswerText(
            final String text, final ModelCreator creator) {
        final Factor factor = newFactor(
                creator, FactorTypeEnum.MULTIPLE_CHOICE);
        final FactorAnswerMultipleChoice answer = newFactorAnswerText(text, factor);
        return creator.storeNew(answer);
    }


    public static FactorAnswerNumber newFactorAnswerNumber(
            final Double min, final Double max) {
        return FactorAnswerNumber.ofJsonObjStr(
                "{\"id\":1, \"min_number\":" + min + ","
                        + " \"max_number\":" + max + "}");
    }

    public static FactorAnswerNumber newFactorAnswerNumber(
            final Double min, final Double max, final Factor factor) {
        final FactorAnswerNumber answer = newFactorAnswerNumber(min, max);
        answer.setOwnFactor(factor);
        return answer;
    }

    public static FactorAnswerNumber newFactorAnswerNumber(
            final Double min, final Double max, final ModelCreator creator) {
        final Factor factor = newFactor(creator, FactorTypeEnum.NUMBER);
        final FactorAnswerNumber answer =
                newFactorAnswerNumber(min, max, factor);
        return creator.storeNew(answer);
    }

    public static FactorAnswerDoubleNumber newFactorAnswerDoubleNumber(
            final Double minA, final Double maxA,
            final Double minB, final Double maxB) {
        return FactorAnswerDoubleNumber.ofJsonObjStr(
                "{\"id\":1, \"min_number_a\":" + minA + ","
                        + " \"max_number_a\":" + maxA + ","
                        + " \"min_number_b\":" + minB + ","
                        + " \"max_number_b\":" + maxB + "}");
    }

    public static FactorAnswerDoubleNumber newFactorAnswerDoubleNumber(
            final Double minA, final Double maxA,
            final Double minB, final Double maxB, final Factor factor) {
        final FactorAnswerDoubleNumber answer =
                newFactorAnswerDoubleNumber(minA, maxA, minB, maxB);
        answer.setOwnFactor(factor);
        return answer;
    }

    public static FactorAnswerDoubleNumber newFactorAnswerDoubleNumber(
            final Double minA, final Double maxA,
            final Double minB, final Double maxB, final ModelCreator creator) {
        final Factor factor = newFactor(creator, FactorTypeEnum.DOUBLE_NUMBER);
        final FactorAnswerDoubleNumber answer =
                newFactorAnswerDoubleNumber(minA, maxA, minB, maxB, factor);
        return creator.storeNew(answer);
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

    public static FactorAnswerPrice newFactorAnswerPrice(
            final Double price,
            final Double polePrice,
            final FactorAnswer answer,
            final ModelCreator creator) {
        final CompanyEPole pole = newCompanyEPole(polePrice, creator);

        final FactorAnswerPrice answerPrice =
                newFactorAnswerPrice(price, answer, pole);
        return creator.storeNew(answerPrice);
    }
}
