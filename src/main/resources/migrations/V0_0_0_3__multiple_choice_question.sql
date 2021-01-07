CREATE TABLE `groenify`.`factor_answer_multiple_choice`
(
    `factor_answer_id` bigint(20) NOT NULL,
    `answer_multiple`  mediumtext COLLATE utf8_bin NOT NULL,
    PRIMARY KEY (`factor_answer_id`),
    CONSTRAINT `factor_answer_multiple_choice_FK` FOREIGN KEY (`factor_answer_id`)
        REFERENCES `groenify`.`factor_answer` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_bin;
