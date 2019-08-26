CREATE TABLE product (
    `id` int(11) UNSIGNED NOT NULL,
    `name` varchar(255)  NOT NULL
);

ALTER TABLE `product`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `product`
    MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
COMMIT;
