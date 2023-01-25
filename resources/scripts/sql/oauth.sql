use oauth;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
                                        `client_id` VARCHAR(48) NOT NULL,
                                        `resource_ids` VARCHAR(256) DEFAULT NULL,
                                        `client_secret` VARCHAR(256) DEFAULT NULL,
                                        `scope` VARCHAR(256) DEFAULT NULL,
                                        `authorized_grant_types` VARCHAR(256) DEFAULT NULL,
                                        `web_server_redirect_uri` VARCHAR(256) DEFAULT NULL,
                                        `authorities` VARCHAR(256) DEFAULT NULL,
                                        `access_token_validity` INT(11) DEFAULT NULL,
                                        `refresh_token_validity` INT(11) DEFAULT NULL,
                                        `additional_information` VARCHAR(4096) DEFAULT NULL,
                                        `autoapprove` VARCHAR(256) DEFAULT NULL,
                                        PRIMARY KEY (`client_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('client_id123','provider,consumer', 'abcxyz', 'all', 'password,refresh_token',NULL, NULL, 7200, 259200, NULL, NULL);
COMMIT;
SET FOREIGN_KEY_CHECKS = 1;
