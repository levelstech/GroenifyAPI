ALTER TABLE groenify.factor_answer_boolean
    DROP FOREIGN KEY factor_answer_boolean_FK_1;
ALTER TABLE groenify.factor_answer_boolean
    ADD CONSTRAINT factor_answer_boolean_FK_1
        FOREIGN KEY (`factor_answer_id`, factor_id) REFERENCES
            groenify.factor_answer (`id`, `factor_id`)
            ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE groenify.factor_answer_multiple_choice
    DROP FOREIGN KEY factor_answer_multiple_choice_FK_1;
ALTER TABLE groenify.factor_answer_multiple_choice
    ADD CONSTRAINT factor_answer_multiple_choice_FK_1
        FOREIGN KEY (`factor_answer_id`, factor_id) REFERENCES
            groenify.factor_answer (`id`, `factor_id`)
            ON DELETE CASCADE ON UPDATE CASCADE;