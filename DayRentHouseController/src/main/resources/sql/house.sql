SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `t_house`;
CREATE TABLE `t_house` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `houseTitleImg` varchar(255) DEFAULT NULL,
  `describe` varchar(255) DEFAULT NULL,
  `houseImgs` varchar(255) DEFAULT NULL,
  `price` int(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `orderQuantity` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;