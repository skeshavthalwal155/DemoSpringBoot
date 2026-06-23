ALTER TABLE products
    ADD `description` VARCHAR(255) NULL;

ALTER TABLE products
    MODIFY `description` VARCHAR(255) NOT NULL;