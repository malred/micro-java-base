use oauth;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `oauth_users`;
CREATE TABLE `oauth_users` (
                               `id` INT(11) NOT NULL AUTO_INCREMENT,
                               `username` CHAR(10) DEFAULT NULL,
                               `password` CHAR(100) DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
# INSERT INTO `oauth_users` VALUES (4, 'user', '123456');
INSERT INTO `oauth_users` VALUES (4, 'user', '$2a$10$8w2xo2mEdDhu3dmFOulax.c9V7oMT2c799TP12tolOV4RmbT26Ckq');
COMMIT;
SET FOREIGN_KEY_CHECKS = 1;
