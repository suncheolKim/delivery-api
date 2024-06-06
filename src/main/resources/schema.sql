DROP TABLE IF EXISTS `food`;
create table `food`
(
    id               bigint auto_increment primary key,
    name             varchar(255) not null
);

DROP TABLE IF EXISTS `parcel`;
create table `parcel`
(
    id               bigint auto_increment primary key,
    description      varchar(255) not null
);

DROP TABLE IF EXISTS `delivery`;
CREATE TABLE delivery (
    tracking_number VARCHAR(255) PRIMARY KEY,
    status VARCHAR(255) NOT NULL,
    item_type VARCHAR(50) NOT NULL,
    item_id BIGINT NOT NULL,
    name varchar(255) not null
);
