CREATE TABLE warehouses (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    address VARCHAR(255) NOT NULL,
    UNIQUE UQ_ADDRESS_1 (address),
    PRIMARY KEY (id)
);
 
CREATE TABLE items (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    warehouse_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
 
CREATE TABLE providers (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
 
CREATE TABLE items_providers (
    item_id INT UNSIGNED NOT NULL,
    provider_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (item_id) REFERENCES items (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (provider_id) REFERENCES providers (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);