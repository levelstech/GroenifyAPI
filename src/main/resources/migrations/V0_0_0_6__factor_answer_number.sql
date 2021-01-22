CREATE TABLE `groenify`.`factor_answer_number`
(
    `factor_answer_id` BIGINT NOT NULL,
    `factor_answer_factor_id`        BIGINT NOT NULL,
    `min_number`       INT    NOT NULL,
    `max_number`       INT    NOT NULL,
    CONSTRAINT factor_answer_number_PK PRIMARY KEY (`factor_answer_id`),
    CONSTRAINT factor_answer_number_UN UNIQUE KEY (`max_number`,`min_number`,`factor_answer_factor_id`,`factor_answer_id`),
    CONSTRAINT factor_answer_number_FK FOREIGN KEY (`factor_answer_id`)
        REFERENCES `groenify`.`factor_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
    DEFAULT CHARSET=utf8
    COLLATE=utf8_bin;

ALTER TABLE `groenify`.`factor_answer_number`
    ADD CONSTRAINT factor_answer_number_FK_1
        FOREIGN KEY (`factor_answer_id`, factor_answer_factor_id) REFERENCES
            `groenify`.`factor_answer` (`id`, `factor_id`)
            ON DELETE CASCADE ON UPDATE CASCADE;