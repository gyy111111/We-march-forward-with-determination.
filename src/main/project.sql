

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class1table
-- ----------------------------
DROP TABLE IF EXISTS `class1table`;
CREATE TABLE `class1table`  (
  `class_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '这个是课程的代号',
  `class_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程的中文名称',
  `class_week` int UNSIGNED NOT NULL COMMENT '这个是课程在每周中的时间',
  `class_time` int UNSIGNED NOT NULL COMMENT '这个是课程在每天中的区间数1-5',
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of class1table
-- ----------------------------
INSERT INTO `class1table` VALUES (1, 'Linux操作系统', 1, 2);
INSERT INTO `class1table` VALUES (2, 'Java编程', 1, 3);
INSERT INTO `class1table` VALUES (3, '中国近代史纲要', 2, 1);
INSERT INTO `class1table` VALUES (4, '体育', 2, 2);
INSERT INTO `class1table` VALUES (5, '高等数学', 2, 3);
INSERT INTO `class1table` VALUES (6, '离散数学', 3, 1);
INSERT INTO `class1table` VALUES (7, '大学英语', 3, 2);
INSERT INTO `class1table` VALUES (8, '高等数学', 4, 1);
INSERT INTO `class1table` VALUES (9, '离散数学', 4, 3);
INSERT INTO `class1table` VALUES (10, 'Java编程', 5, 1);

-- ----------------------------
-- Table structure for class2table
-- ----------------------------
DROP TABLE IF EXISTS `class2table`;
CREATE TABLE `class2table`  (
  `class_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程代号',
  `class_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程的中文名称',
  `class_week` int UNSIGNED NOT NULL COMMENT '在每周中的时间',
  `class_time` int UNSIGNED NOT NULL COMMENT '课程在每天中的区间',
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of class2table
-- ----------------------------
INSERT INTO `class2table` VALUES (1, 'Linux操作系统', 1, 2);
INSERT INTO `class2table` VALUES (2, 'Java编程', 1, 3);
INSERT INTO `class2table` VALUES (3, '中国近代史纲要', 2, 1);
INSERT INTO `class2table` VALUES (4, '体育', 2, 2);
INSERT INTO `class2table` VALUES (5, '高等数学', 2, 3);
INSERT INTO `class2table` VALUES (6, '离散数学', 3, 1);
INSERT INTO `class2table` VALUES (7, '大学英语', 3, 2);
INSERT INTO `class2table` VALUES (8, '高等数学', 4, 1);
INSERT INTO `class2table` VALUES (9, '离散数学', 4, 3);
INSERT INTO `class2table` VALUES (10, 'Java编程', 5, 1);

-- ----------------------------
-- Table structure for masterall
-- ----------------------------
DROP TABLE IF EXISTS `masterall`;
CREATE TABLE `masterall`  (
  `work_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `passwords` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `login_code` int NOT NULL DEFAULT 0 COMMENT '表示当前用户的登录状态,防止多次登录',
  PRIMARY KEY (`work_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of masterall
-- ----------------------------
INSERT INTO `masterall` VALUES (1, 'admin', '12345678', 0);
INSERT INTO `masterall` VALUES (2, '王老师', '12345678', 0);
INSERT INTO `masterall` VALUES (3, '李老师', '12345678', 0);
INSERT INTO `masterall` VALUES (4, '赵老师', '12345678', 0);
INSERT INTO `masterall` VALUES (5, '高老师', '12345678', 0);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `school_id` bigint UNSIGNED NOT NULL COMMENT '学号信息',
  `student_name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生名称',
  `class_message` int UNSIGNED NOT NULL COMMENT '班级信息',
  `class_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程的名称',
  `class_time` int UNSIGNED NOT NULL COMMENT '这是每天的第几节课',
  `class_id` int UNSIGNED NOT NULL COMMENT '课程的ID',
  `code_type` int UNSIGNED NOT NULL DEFAULT 1 COMMENT '1:正常考勤 0:旷课 2:迟到 3:早退 4:请假',
  `class_week` int UNSIGNED NOT NULL COMMENT '在星期几',
  `date` date NOT NULL COMMENT '这个是对于每个学生的考勤信息的时间日期',
  INDEX `message_fk_studentid`(`school_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (23030211004, '小岳', 1, '离散数学', 1, 6, 2, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211012, '小海', 1, '离散数学', 1, 6, 3, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, '离散数学', 1, 6, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211014, '小林', 1, '离散数学', 1, 6, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, '离散数学', 1, 6, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211012, '小海', 1, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211014, '小林', 1, '大学英语', 2, 7, 0, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212012, '小张', 2, '离散数学', 1, 6, 0, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212013, '小含', 2, '离散数学', 1, 6, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212014, '小程', 2, '离散数学', 1, 6, 2, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, '离散数学', 1, 6, 3, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212016, '小高', 2, '离散数学', 1, 6, 3, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, '离散数学', 1, 6, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212030, '小明', 2, '离散数学', 1, 6, 4, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212012, '小张', 2, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212013, '小含', 2, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212014, '小程', 2, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212016, '小高', 2, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030212030, '小明', 2, '大学英语', 2, 7, 1, 3, '2024-05-29');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211012, '小海', 1, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211014, '小林', 1, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, '离散数学', 3, 9, 0, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211012, '小海', 1, '离散数学', 3, 9, 2, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, '离散数学', 3, 9, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211014, '小林', 1, '离散数学', 3, 9, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, '离散数学', 3, 9, 4, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212012, '小张', 2, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212013, '小含', 2, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212014, '小程', 2, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212016, '小高', 2, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212030, '小明', 2, '高等数学', 1, 8, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212012, '小张', 2, '离散数学', 3, 9, 2, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212013, '小含', 2, '离散数学', 3, 9, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212014, '小程', 2, '离散数学', 3, 9, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, '离散数学', 3, 9, 3, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212016, '小高', 2, '离散数学', 3, 9, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, '离散数学', 3, 9, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030212030, '小明', 2, '离散数学', 3, 9, 1, 4, '2024-05-30');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, 'Java编程', 1, 10, 1, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030211012, '小海', 1, 'Java编程', 1, 10, 0, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, 'Java编程', 1, 10, 0, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030211014, '小林', 1, 'Java编程', 1, 10, 1, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, 'Java编程', 1, 10, 4, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030212012, '小张', 2, 'Java编程', 1, 10, 1, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030212013, '小含', 2, 'Java编程', 1, 10, 1, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030212014, '小程', 2, 'Java编程', 1, 10, 2, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, 'Java编程', 1, 10, 1, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030212016, '小高', 2, 'Java编程', 1, 10, 3, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, 'Java编程', 1, 10, 3, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030212030, '小明', 2, 'Java编程', 1, 10, 1, 5, '2024-05-31');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, 'Linux操作系统', 2, 1, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211012, '小海', 1, 'Linux操作系统', 2, 1, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, 'Linux操作系统', 2, 1, 3, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211014, '小林', 1, 'Linux操作系统', 2, 1, 2, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, 'Linux操作系统', 2, 1, 3, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, 'Java编程', 3, 2, 2, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211012, '小海', 1, 'Java编程', 3, 2, 2, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211014, '小林', 1, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212008, '马佳怡', 2, 'Linux操作系统', 2, 1, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212012, '小张', 2, 'Linux操作系统', 2, 1, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212013, '小含', 2, 'Linux操作系统', 2, 1, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212014, '小程', 2, 'Linux操作系统', 2, 1, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, 'Linux操作系统', 2, 1, 2, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212016, '小高', 2, 'Linux操作系统', 2, 1, 4, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, 'Linux操作系统', 2, 1, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212033, '董家欣', 2, 'Linux操作系统', 2, 1, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212008, '马佳怡', 2, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212012, '小张', 2, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212013, '小含', 2, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212014, '小程', 2, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212016, '小高', 2, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, 'Java编程', 3, 2, 1, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030212033, '董家欣', 2, 'Java编程', 3, 2, 4, 1, '2024-06-03');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, '中国近代史纲要', 1, 3, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211012, '小海', 1, '中国近代史纲要', 1, 3, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, '中国近代史纲要', 1, 3, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211014, '小林', 1, '中国近代史纲要', 1, 3, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, '中国近代史纲要', 1, 3, 2, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, '体育', 2, 4, 4, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211012, '小海', 1, '体育', 2, 4, 0, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, '体育', 2, 4, 2, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211014, '小林', 1, '体育', 2, 4, 2, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, '体育', 2, 4, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211004, '小岳', 1, '高等数学', 3, 5, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211012, '小海', 1, '高等数学', 3, 5, 4, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211013, '小蒋', 1, '高等数学', 3, 5, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211014, '小林', 1, '高等数学', 3, 5, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030211016, '小谭', 1, '高等数学', 3, 5, 2, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212008, '小马', 2, '中国近代史纲要', 1, 3, 4, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212012, '小张', 2, '中国近代史纲要', 1, 3, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212013, '小含', 2, '中国近代史纲要', 1, 3, 0, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212014, '小程', 2, '中国近代史纲要', 1, 3, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, '中国近代史纲要', 1, 3, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212016, '小高', 2, '中国近代史纲要', 1, 3, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, '中国近代史纲要', 1, 3, 0, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212033, '小董', 2, '中国近代史纲要', 1, 3, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212008, '小马', 2, '体育', 2, 4, 2, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212012, '小张', 2, '体育', 2, 4, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212013, '小含', 2, '体育', 2, 4, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212014, '小程', 2, '体育', 2, 4, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, '体育', 2, 4, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212016, '小高', 2, '体育', 2, 4, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, '体育', 2, 4, 4, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212033, '小董', 2, '体育', 2, 4, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212008, '小马', 2, '高等数学', 3, 5, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212012, '小张', 2, '高等数学', 3, 5, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212013, '小含', 2, '高等数学', 3, 5, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212014, '小程', 2, '高等数学', 3, 5, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212015, '小秦', 2, '高等数学', 3, 5, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212016, '小高', 2, '高等数学', 3, 5, 1, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212017, '小阳', 2, '高等数学', 3, 5, 3, 2, '2024-06-04');
INSERT INTO `message` VALUES (23030212033, '小董', 2, '高等数学', 3, 5, 4, 2, '2024-06-04');

-- ----------------------------
-- Table structure for studentall
-- ----------------------------
DROP TABLE IF EXISTS `studentall`;
CREATE TABLE `studentall`  (
  `school_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `gender` tinyint UNSIGNED NOT NULL DEFAULT (1) COMMENT '性别默认为1男',
  `age` tinyint UNSIGNED NOT NULL COMMENT '256以内正整数',
  `classMessage` tinyint UNSIGNED NOT NULL,
  `passwords` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '12345678',
  `login_code` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '表示当前用户的登录状态,防止多次登录;',
  PRIMARY KEY (`school_id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23030212041 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of studentall
-- ----------------------------
INSERT INTO `studentall` VALUES (23030211004, '小岳', 0, 19, 1, '12345678', 0);
INSERT INTO `studentall` VALUES (23030211012, '小海', 0, 17, 1, '12345678', 0);
INSERT INTO `studentall` VALUES (23030211013, '小蒋', 1, 19, 1, '12345678', 0);
INSERT INTO `studentall` VALUES (23030211014, '小林', 0, 17, 1, '12345678', 0);
INSERT INTO `studentall` VALUES (23030211016, '小谭', 0, 19, 1, '12345678', 0);
INSERT INTO `studentall` VALUES (23030212008, '小马', 0, 19, 2, '12345678', 0);
INSERT INTO `studentall` VALUES (23030212012, '小张', 1, 20, 2, '12345678', 0);
INSERT INTO `studentall` VALUES (23030212013, '小含', 0, 18, 2, '12345678', 0);
INSERT INTO `studentall` VALUES (23030212014, '小程', 0, 18, 2, '12345678', 0);
INSERT INTO `studentall` VALUES (23030212015, '小秦', 0, 19, 2, '12345678', 0);
INSERT INTO `studentall` VALUES (23030212016, '小高', 1, 19, 2, '12345678', 0);
INSERT INTO `studentall` VALUES (23030212017, '小阳', 1, 19, 2, '12345678', 0);
INSERT INTO `studentall` VALUES (23030212033, '小董', 0, 18, 2, '12345678', 0);

SET FOREIGN_KEY_CHECKS = 1;
