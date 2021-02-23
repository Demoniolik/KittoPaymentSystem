-- -----------------------------------------------------
-- Table `mydb`.`user_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_type`;

CREATE TABLE IF NOT EXISTS `user_type`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);


-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;

CREATE TABLE IF NOT EXISTS `user`
(
    `id`           INT          NOT NULL AUTO_INCREMENT,
    `first_name`   VARCHAR(45)  NOT NULL,
    `second_name`  VARCHAR(45)  NOT NULL,
    `login`        VARCHAR(45)  NOT NULL UNIQUE,
    `password`     VARCHAR(100) NOT NULL,
    `blocked`      TINYINT      NULL DEFAULT NULL,
    `user_type_id` INT          NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_user_user_type1`
        FOREIGN KEY (`user_type_id`)
            REFERENCES `user_type` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table `mydb`.`credit_card`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `credit_card`;

CREATE TABLE IF NOT EXISTS `credit_card`
(
    `id`            INT             NOT NULL AUTO_INCREMENT,
    `number`        DECIMAL         NULL     DEFAULT NULL UNIQUE,
    `name`          VARCHAR(45)     NOT NULL,
    `money_on_card` DOUBLE UNSIGNED NOT NULL DEFAULT 0,
    `blocked`       TINYINT         NOT NULL DEFAULT 0,
    `cvc`           INT(3) UNSIGNED NOT NULL DEFAULT 0,
    `user_id`       INT             NOT NULL,
    PRIMARY KEY (`id`, `user_id`),
    CONSTRAINT `fk_credit_card_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


DROP TABLE IF EXISTS `category`;

CREATE TABLE IF NOT EXISTS `category`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `mydb`.`payment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `payment`;

CREATE TABLE IF NOT EXISTS `payment`
(
    `id`                         INT                       NOT NULL AUTO_INCREMENT,
    `money`                      DOUBLE UNSIGNED           NOT NULL DEFAULT 0,
    `description`                VARCHAR(50)               NOT NULL,
    `status`                     ENUM ('prepared', 'sent') NOT NULL,
    `date`                       VARCHAR(40)               NOT NULL,
    `credit_card_id_source`      INT                       NOT NULL,
    `credit_card_id_destination` INT                       NOT NULL,
    `category_id`                INT                       NOT NULL,
    PRIMARY KEY (`id`, `credit_card_id_source`, `credit_card_id_destination`),
    INDEX `fk_payment_credit_card1` (`credit_card_id_source` ASC) VISIBLE,
    INDEX `fk_payment_credit_card2` (`credit_card_id_destination` ASC) VISIBLE,
    INDEX `fk_payment_category1_idx` (`category_id` ASC) VISIBLE,
    CONSTRAINT `fk_payment_credit_card1`
        FOREIGN KEY (`credit_card_id_source`)
            REFERENCES `credit_card` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_payment_credit_card2`
        FOREIGN KEY (`credit_card_id_destination`)
            REFERENCES `credit_card` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_payment_category1`
        FOREIGN KEY (`category_id`)
            REFERENCES `category` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);



-- -----------------------------------------------------
-- Table `mydb`.`request_blocked`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `request_blocked`;

CREATE TABLE IF NOT EXISTS `request_blocked`
(
    `id`             INT          NOT NULL AUTO_INCREMENT,
    `description`    VARCHAR(100) NULL,
    `credit_card_id` INT          NOT NULL,
    `status`         TINYINT      NOT NULL,
    PRIMARY KEY (`id`, `credit_card_id`),
    CONSTRAINT `fk_request_blocked_credit_card1`
        FOREIGN KEY (`credit_card_id`)
            REFERENCES `credit_card` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);



