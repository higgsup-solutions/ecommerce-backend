SET foreign_key_checks = 0;

ALTER TABLE `cart` ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `cart` ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `product` ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);
ALTER TABLE `product` ADD CONSTRAINT `product_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`);
ALTER TABLE `review` ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `user` ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
ALTER TABLE `user_token` ADD CONSTRAINT `user_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `order` ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `order` (`id`);
ALTER TABLE `transaction` ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`xshop_order_id`) REFERENCES `order` (`id`);
ALTER TABLE `order_detail` ADD CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `order_address` ADD CONSTRAINT `order_address_ibfk_1` FOREIGN KEY (`order_address`) REFERENCES `order` (`id`);

SET foreign_key_checks = 1;

ALTER TABLE product ADD FULLTEXT(name,short_desc, full_desc);