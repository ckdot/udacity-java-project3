CREATE TABLE review (
   `id` int(11) UNSIGNED NOT NULL,
   `text` varchar(255) NOT NULL,
   `product_id` int(11) UNSIGNED NOT NULL
);

ALTER TABLE review
    ADD PRIMARY KEY (`id`);

ALTER TABLE review
    MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE review
    ADD CONSTRAINT `product_constraint` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;