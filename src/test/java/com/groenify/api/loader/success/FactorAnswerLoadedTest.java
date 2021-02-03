package com.groenify.api.loader.success;

import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.factor.answer.FactorAnswerBoolean;
import com.groenify.api.database.model.factor.answer.FactorAnswerDoubleNumber;
import com.groenify.api.database.model.factor.answer.FactorAnswerMultipleChoice;
import com.groenify.api.database.model.factor.answer.FactorAnswerNumber;
import com.groenify.api.database.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.framework.classes.Pair;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class FactorAnswerLoadedTest extends FactorPriceAnswerLoaderSuccessTest {

    @Autowired
    private FactorAnswerRepository repository;

    @Test
    @Override
    public void testAll() {

        Assertions.assertThat(repository.findAll()).hasSize(9);
        final List<FactorAnswer> answers = Lists.newArrayList(repository.findAll());
        assertionsFactorAnswerOne(answers.get(0));
        assertionsFactorAnswerTwo(answers.get(1));
        assertionsFactorAnswerThree(answers.get(2));
        assertionsFactorAnswerFour(answers.get(3));
        assertionsFactorAnswerFive(answers.get(4));
        assertionsFactorAnswerSix(answers.get(5));
        assertionsFactorAnswerSeven(answers.get(6));
        assertionsFactorAnswerEight(answers.get(7));
        assertionsFactorAnswerNine(answers.get(8));
    }

    static void assertionsFactorAnswerOne(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerMultipleChoice.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo("Laadpaal");
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("Laadpaal");
        FactorLoadedTest.factorOneAssertions(answer.getOwnFactor());


    }

    static void assertionsFactorAnswerTwo(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerMultipleChoice.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo("Wand");
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("Wand");
        FactorLoadedTest.factorOneAssertions(answer.getOwnFactor());
    }


    static void assertionsFactorAnswerThree(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerBoolean.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo(true);
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("true");
        FactorLoadedTest.factorTwoAssertions(answer.getOwnFactor());
    }

    static void assertionsFactorAnswerFour(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerBoolean.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo(false);
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("false");
        FactorLoadedTest.factorTwoAssertions(answer.getOwnFactor());
    }

    static void assertionsFactorAnswerFive(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerNumber.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo(Pair.of(5d, 5d));
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("[5.0,5.0]");
        FactorLoadedTest.factorThreeAssertions(answer.getOwnFactor());
    }

    static void assertionsFactorAnswerSix(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerNumber.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo(Pair.of(8d, 8d));
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("[8.0,8.0]");
        FactorLoadedTest.factorThreeAssertions(answer.getOwnFactor());
    }

    static void assertionsFactorAnswerSeven(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerDoubleNumber.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo(Pair.of(Pair.of(0d, 10d), Pair.of(0d, 0d)));
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("[[0.0,10.0],[0.0,0.0]]");
        FactorLoadedTest.factorFourAssertions(answer.getOwnFactor());
    }

    static void assertionsFactorAnswerEight(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerDoubleNumber.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo(Pair.of(Pair.of(10d, 12d), Pair.of(0d, 0d)));
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("[[10.0,12.0],[0.0,0.0]]");
        FactorLoadedTest.factorFourAssertions(answer.getOwnFactor());
    }

    static void assertionsFactorAnswerNine(final FactorAnswer answer) {
        Assertions.assertThat(answer.getClass()).
                isEqualTo(FactorAnswerDoubleNumber.class);
        Assertions.assertThat(answer.getAnswer()).
                isEqualTo(Pair.of(Pair.of(12d, 15d), Pair.of(0d, 0d)));
        Assertions.assertThat(answer.getStringedAnswer()).
                isEqualTo("[[12.0,15.0],[0.0,0.0]]");
        FactorLoadedTest.factorFourAssertions(answer.getOwnFactor());
    }

}