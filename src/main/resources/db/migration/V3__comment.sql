CREATE TABLE comment (
    `id` int(11) UNSIGNED NOT NULL,
    `text` varchar(255) NOT NULL,
    `review_id` int(11) UNSIGNED NOT NULL
);

ALTER TABLE comment
    ADD PRIMARY KEY (`id`);

ALTER TABLE comment
    MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE comment
    ADD CONSTRAINT `review_constraint` FOREIGN KEY (`review_id`) REFERENCES `review` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;