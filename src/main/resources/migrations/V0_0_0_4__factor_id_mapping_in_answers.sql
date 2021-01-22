ALTER TABLE groenify.factor_answer_boolean
    ADD factor_id BIGINT(20) DEFAULT 2 NOT NULL AFTER factor_answer_id;
ALTER TABLE groenify.factor_answer_boolean
    CHANGE factor_id factor_id BIGINT(20) DEFAULT 2 NOT NULL AFTER factor_answer_id;

ALTER TABLE groenify.factor_answer
    ADD CONSTRAINT factor_answer_UN UNIQUE KEY (id, factor_id);

ALTER TABLE groenify.factor_answer_boolean
    ADD CONSTRAINT factor_answer_boolean_FK_1
        FOREIGN KEY (`factor_answer_id`, factor_id) REFERENCES
            groenify.factor_answer (`id`, `factor_id`);

ALTER TABLE groenify.factor_answer_boolean
    MODIFY COLUMN factor_id bigint(20) NOT NULL;

ALTER TABLE groenify.factor_answer_boolean
    ADD CONSTRAINT factor_answer_boolean_UN
        UNIQUE KEY (factor_answer_id, factor_id, answer_boolean);

ALTER TABLE groenify.factor_answer_multiple_choice
    ADD factor_id BIGINT(20) DEFAULT 2 NOT NULL AFTER factor_answer_id;
ALTER TABLE groenify.factor_answer_multiple_choice
    CHANGE factor_id factor_id BIGINT(20) DEFAULT 2 NOT NULL AFTER factor_answer_id;

ALTER TABLE groenify.factor_answer_multiple_choice
    ADD CONSTRAINT factor_answer_multiple_choice_FK_1
        FOREIGN KEY (`factor_answer_id`, factor_id) REFERENCES
            groenify.factor_answer (`id`, `factor_id`);

ALTER TABLE groenify.factor_answer_multiple_choice
    MODIFY COLUMN factor_id bigint(20) NOT NULL;

ALTER TABLE groenify.factor_answer_multiple_choice
    ADD lower_answer_hash varchar(512) NOT NULL;

ALTER TABLE groenify.factor_answer_multiple_choice
    ADD CONSTRAINT factor_answer_multiple_choice_UN UNIQUE KEY (factor_answer_id,
                                                                factor_id,
                                                                lower_answer_hash);
