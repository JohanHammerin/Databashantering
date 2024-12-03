CREATE TABLE Person
(
    person_id  INT          NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(255) NOT NULL,
    gender     CHAR(1)      NOT NULL,
    dob        DATE,
    income     DOUBLE,
    PRIMARY KEY (person_id)
);

CREATE TABLE House
(
    house_id    INT          NOT NULL AUTO_INCREMENT,
    address     VARCHAR(255) NOT NULL,
    city        VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    price       DOUBLE,
    person_id   INT,
    FOREIGN KEY (person_id) REFERENCES Person (person_id),
    PRIMARY KEY (house_id)
);