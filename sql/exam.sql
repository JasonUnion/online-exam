/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : exam

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-04-17 15:34:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for answer_paper
-- ----------------------------
DROP TABLE IF EXISTS `answer_paper`;
CREATE TABLE `answer_paper` (
  `id` varchar(255) NOT NULL,
  `paper_name` varchar(255) DEFAULT NULL,
  `answer_time` varchar(50) DEFAULT NULL,
  `answer_user` varchar(50) DEFAULT NULL,
  `checked` varchar(20) DEFAULT NULL,
  `finished` varchar(20) DEFAULT NULL,
  `score` int(10) DEFAULT NULL COMMENT '得分',
  `type` varchar(20) DEFAULT NULL COMMENT '正式或模拟考试',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer_paper
-- ----------------------------
INSERT INTO `answer_paper` VALUES ('4e66651277cb48ef93255de2456d0a7f', '测试正式考试1', '2017-04-17 14:22:27', 'cc', 'true', 'true', '28', 'official');
INSERT INTO `answer_paper` VALUES ('8e9be10b232d44f6971e7d7e486d1a1a', '测试正式考试2', '2017-04-17 14:33:38', 'cc', 'true', 'true', '8', 'official');
INSERT INTO `answer_paper` VALUES ('b8ca2fc1ecdc4f64ae42f9d640980eda', '测试模拟考试1', '2017-04-17 14:41:08', 'cc', 'false', 'true', null, 'simulate');

-- ----------------------------
-- Table structure for answer_paper_question
-- ----------------------------
DROP TABLE IF EXISTS `answer_paper_question`;
CREATE TABLE `answer_paper_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer_paper_id` varchar(255) DEFAULT NULL,
  `answer_question_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer_paper_question
-- ----------------------------
INSERT INTO `answer_paper_question` VALUES ('94', '4e66651277cb48ef93255de2456d0a7f', 'a78dcbdb24414de6bc5ca700b455964f');
INSERT INTO `answer_paper_question` VALUES ('95', '4e66651277cb48ef93255de2456d0a7f', 'cd5423f293a4455cb9525cc53fe12659');
INSERT INTO `answer_paper_question` VALUES ('96', '4e66651277cb48ef93255de2456d0a7f', 'c97ea3fd9c574b69b0ff3b73abcea43a');
INSERT INTO `answer_paper_question` VALUES ('97', '4e66651277cb48ef93255de2456d0a7f', 'de4c192aee694d5fb16ed34791e11215');
INSERT INTO `answer_paper_question` VALUES ('98', '4e66651277cb48ef93255de2456d0a7f', 'e5719e64646043d18a8527e51ee71620');
INSERT INTO `answer_paper_question` VALUES ('99', '4e66651277cb48ef93255de2456d0a7f', 'd4283721446c482fb4c6d04f8ae853c5');
INSERT INTO `answer_paper_question` VALUES ('100', '4e66651277cb48ef93255de2456d0a7f', '568ba3eecded45ad8272474815955b2a');
INSERT INTO `answer_paper_question` VALUES ('101', '4e66651277cb48ef93255de2456d0a7f', '1f620725b6ce4bcdbe8b2f87800b5a96');
INSERT INTO `answer_paper_question` VALUES ('102', '8e9be10b232d44f6971e7d7e486d1a1a', '4f944b5aa3c14fcca4f5c5a946324ae8');
INSERT INTO `answer_paper_question` VALUES ('103', 'b8ca2fc1ecdc4f64ae42f9d640980eda', '884b68a1ae0e4255ad11ed4a7c352d1d');

-- ----------------------------
-- Table structure for answer_question
-- ----------------------------
DROP TABLE IF EXISTS `answer_question`;
CREATE TABLE `answer_question` (
  `id` varchar(255) NOT NULL,
  `title` text,
  `type` varchar(20) DEFAULT NULL,
  `option_a` text,
  `option_b` text,
  `option_c` text,
  `option_d` text,
  `content` varchar(255) DEFAULT NULL,
  `score` varchar(20) DEFAULT NULL,
  `answer` text,
  `analysis` text,
  `number` int(20) DEFAULT NULL,
  `mark_score` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer_question
-- ----------------------------
INSERT INTO `answer_question` VALUES ('1f620725b6ce4bcdbe8b2f87800b5a96', '中外历史人物评说  材料      弗罗伦斯·南丁格尔( 1820 - 1910)，出生于英国上流社会家庭。19世纪40、50 年代，伦敦郊区贫民窟频繁发生霍乱等瘟疫，人们对于“医院”“护理”这样的字眼一 向避而不谈，因为都是一些很可怕、很丢脸的事情。医院几乎就是不幸、堕落，混乱的 代名词。由于缺少必要的管理，它有时就像疯人院。\n根据材料并结合所学知识，指出南丁格尔投身护理事业的时代背景。（8分）', '简答题', null, null, null, null, null, '8', '弗罗伦斯·南丁格尔( 1820 - 1910)，出生于英国上流社会家庭。19世纪40、50 年代，伦敦郊区贫民窟频繁发生霍乱等瘟疫，人们对于“医院”“护理”这样的字眼一 向避而不谈，因为都是一些很可怕、很丢脸的事情。医院几乎就是不幸、堕落，混乱的 代名词。由于缺少必要的管理，它有时就像疯人院。 根据材料并结合所学知识，指出南丁格尔投身护理事业的时代背景。', '工业革命期间，疾病流行；医疗卫生事业和人们观念落后；战争频繁。（8分，答     出两点给6分，答出三点给8分）', '8', '8');
INSERT INTO `answer_question` VALUES ('4f944b5aa3c14fcca4f5c5a946324ae8', '中外历史人物评说 \n材料     \n弗罗伦斯·南丁格尔( 1820 - 1910)，出生于英国上流社会家庭。19世纪40、50 年代，伦敦郊区贫民窟频繁发生霍乱等瘟疫，人们对于“医院”“护理”这样的字眼一 向避而不谈，因为都是一些很可怕、很丢脸的事情。医院几乎就是不幸、堕落，混乱的 代名词。由于缺少必要的管理，它有时就像疯人院。      南丁格尔不顾家人反对毅然投身护理事业。1854年她到克里米亚野战医院工作，被 战地士兵称为“克里米亚的天使”和“提灯天使”。1860年，她创建了世界上第一所正 规护士学校，她的办学思想传到欧美及亚洲各国，护理学也成为了一门科学。1863年时， 疾病命名与分类混淆不清，各地医院各自为政。南丁格尔制定了医疗统计标准模式，被 英国各医院相继采用。1912年国际红十字大会设立国际护理界的最高荣誉奖一一南丁格 尔奖章，以表彰为护理事业中做出卓越贡献的医护人员。                              \n 一据安妮·马修森《佛罗伦萨·南丁格尔传》\n 根据材料并结合所学知识，指出南丁格尔投身护理事业的时代背景。', '简答题', null, null, null, null, null, '8', '工业革命期间，疾病流行；医疗卫生事业和人们观念落后；战争频繁。', '工业革命期间，疾病流行；医疗卫生事业和人们观念落后；战争频繁。（8分，答     出两点给6分，答出三点给8分）', '1', '8');
INSERT INTO `answer_question` VALUES ('568ba3eecded45ad8272474815955b2a', '材料      \n辽统治者耶律德光灭后晋后，入晋宫召集百官时，他“改服中国衣冠，百官起居皆 如旧制”，对群臣宣布“自今不修甲兵，不市战马，轻赋省役，天下太平矣”。并任命了 一批汉官主持汉地事务。\n根据材料概括耶律德光统治的特点。（6分）', '简答题', null, null, null, null, null, '6', '辽统治者耶律德光灭后晋后，入晋宫召集百官时，他“改服中国衣冠，百官起居皆 如旧制”，对群臣宣布“自今不修甲兵，不市战马，轻赋省役，天下太平矣”。并任命了 一批汉官主持汉地事务。 根据材料概括耶律德光统治的特点。', '学习汉文化，重用汉官；轻徭薄赋；抑制地方割据。（6分）', '7', '6');
INSERT INTO `answer_question` VALUES ('884b68a1ae0e4255ad11ed4a7c352d1d', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `answer_question` VALUES ('a78dcbdb24414de6bc5ca700b455964f', '《小雅·鹿鸣>本是西周贵族宣扬宴飨之仪的乐歌，后扩散到民间，在乡人宴会上     也可传唱。这表明西周时期', '选择题', '周人生活较为富足', '礼乐文明得到广泛认同', '乡人社会地位提高', '贵族奢靡之风波及民间', null, '4', 'optionB', null, '1', '4');
INSERT INTO `answer_question` VALUES ('c97ea3fd9c574b69b0ff3b73abcea43a', '唐前期的政治人物多为北方人，北宋时政治人物多出生于江西、福建、苏南等地。     这一变化主要反映了', '选择题', '官僚集团重视本地域人才', '南北方士人志向差异', '科举制改变人才地域分布', '政治中心转移到南方', null, '4', 'optionB', null, '3', '0');
INSERT INTO `answer_question` VALUES ('cd5423f293a4455cb9525cc53fe12659', '公元前212年，秦始皇坑杀“术士”，长子扶苏劝谏说：“远方黔首未集，诸生皆诵     法孔子，今上皆重法绳之，臣恐天下不安，唯上察之”。这反映当时', '选择题', '统一进程曲折', '地方治理不畅', '始皇灭儒崇法', '儒学影响较大', null, '4', 'optionB', null, '2', '0');
INSERT INTO `answer_question` VALUES ('d4283721446c482fb4c6d04f8ae853c5', '阅读材料，完成下列要求。 \n19世纪末20世纪初，中国也有人从历史延 续的角度、民众与英雄辩证关系的角度来解读华盛顿。清末民初，华盛顿已是中文世界 出现频率相当高的名字。在人们心目中，至少有九个不同的华盛顿形象，如开国总统、 国父形象；打了天下但不做皇帝的尧舜形象；敢于认错、不讲谎话、见义勇为、孝顺母 亲的诚实、行善、孝顺形象；也有缺点错误、也会发脾气的凡人形象。\n 根据材料并结合所学知识，指出南北战争前后美国人心目中华盛顿形象的变化及原因。', '简答题', null, null, null, null, null, '10', '19世纪末20世纪初，中国也有人从历史延 续的角度、民众与英雄辩证关系的角度来解读华盛顿。清末民初，华盛顿已是中文世界 出现频率相当高的名字。在人们心目中，至少有九个不同的华盛顿形象，如开国总统、 国父形象；打了天下但不做皇帝的尧舜形象；敢于认错、不讲谎话、见义勇为、孝顺母 亲的诚实、行善、孝顺形象；也有缺点错误、也会发脾气的凡人形象。 根据材料并结合所学知识，指出南北战争前后美国人心目中华盛顿形象的变化及原因。', '变化：圣人到凡人。（4分）    \n原因：林肯的出现促使人们以平常心来看待领袖人物；十九世纪中后期，美国民     主政治进一步发展，人们思想进一步解放。  （6分）', '6', '10');
INSERT INTO `answer_question` VALUES ('de4c192aee694d5fb16ed34791e11215', '《荆楚岁时记>云：。社日，四邻并结宗会社，宰牲牢，为屋于树下，先祭神，然后     食其胙。”据此可知，社日的民俗功能主要是', '选择题', '联谊乡邻', '颂扬盛世', '缅怀先祖', '助危济困', null, '4', 'optionB', null, '4', '0');
INSERT INTO `answer_question` VALUES ('e5719e64646043d18a8527e51ee71620', '李鸿章凭淮军实力日渐强盛。一次，他在游孔林时说道：“孔子不会打洋枪，今不     足贵也。”李鸿章这样评价孔子，其背景最可能是', '选择题', '“师夷长技”思想萌发', '“中体西用”思潮兴起', '“托古改制”思想产生', '“尊孔复古”思潮泛滥', null, '4', 'optionB', null, '5', '0');

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper` (
  `id` varchar(255) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '试卷名称',
  `created` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `start_time` varchar(255) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(255) DEFAULT NULL COMMENT '结束时间',
  `subject_name` varchar(255) DEFAULT NULL COMMENT '所属课程名称',
  `publish` varchar(255) DEFAULT NULL COMMENT '是否发布@1是，0否',
  `type` varchar(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `peoples` varchar(20) DEFAULT NULL COMMENT '参与人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paper
-- ----------------------------
INSERT INTO `paper` VALUES ('115bd22d1bf3411697c634507d0a27a7', '测试正式考试1', '2017-4-17 13:49:10', '2017-04-24 08:00', '2017-04-24 12:00', '政治', '是', 'official', '2017年广东高考适应性测试文综(历史)试题', 'http://localhost:8765/static/img/paper/5ffd8499063c4bcabc0bdf34c08fa208.png', '1');
INSERT INTO `paper` VALUES ('6b734c017c2f4f80ad3eac1fdf00b2d8', '测试模拟考试1', '2017-4-17 13:50:9', '2017-04-24 14:00', '2017-04-24 17:00', '政治', '否', 'simulate', '2016年广东高考适应性测试文综(历史)试题', 'http://localhost:8765/static/img/paper/e7f9d3117483440d84735bd7ed58d370.png', '1');
INSERT INTO `paper` VALUES ('a2d9984ca80240698d2b87a5ef036662', '测试正式考试2', '2017-4-17 14:31:20', '2017-04-24 14:00', '2017-04-24 17:00', '政治', '是', 'official', '2016届广东省高考历史模拟试卷', 'http://localhost:8765/static/img/paper/aa1aeafc76164ed3b002973cce56b04f.png', '1');
INSERT INTO `paper` VALUES ('d494206f90394f03902eb5e51e7b9991', '测试练习1', '2017-4-17 13:50:46', '2017-04-24 19:00', '2017-04-24 21:00', '政治', '否', 'practice', '2016年广东高考适应性测试文综(历史)试题', 'http://localhost:8765/static/img/paper/8573b21197ad477f9094074bc8927901.png', '0');

-- ----------------------------
-- Table structure for paper_answer_paper
-- ----------------------------
DROP TABLE IF EXISTS `paper_answer_paper`;
CREATE TABLE `paper_answer_paper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paper_id` varchar(255) DEFAULT NULL,
  `answer_paper_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paper_answer_paper
-- ----------------------------
INSERT INTO `paper_answer_paper` VALUES ('13', '115bd22d1bf3411697c634507d0a27a7', '4e66651277cb48ef93255de2456d0a7f');
INSERT INTO `paper_answer_paper` VALUES ('14', 'a2d9984ca80240698d2b87a5ef036662', '8e9be10b232d44f6971e7d7e486d1a1a');
INSERT INTO `paper_answer_paper` VALUES ('15', '6b734c017c2f4f80ad3eac1fdf00b2d8', 'b8ca2fc1ecdc4f64ae42f9d640980eda');

-- ----------------------------
-- Table structure for paper_question
-- ----------------------------
DROP TABLE IF EXISTS `paper_question`;
CREATE TABLE `paper_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paper_id` varchar(255) DEFAULT NULL,
  `question_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paper_question
-- ----------------------------
INSERT INTO `paper_question` VALUES ('46', '115bd22d1bf3411697c634507d0a27a7', 'fc41c02697004f179933aec14d6efd4c');
INSERT INTO `paper_question` VALUES ('47', '115bd22d1bf3411697c634507d0a27a7', 'c45ea2f782ab4ab39d67ef713b6c9264');
INSERT INTO `paper_question` VALUES ('48', '115bd22d1bf3411697c634507d0a27a7', 'cc7e7125df994a6ab4fef47c8f969db6');
INSERT INTO `paper_question` VALUES ('49', '115bd22d1bf3411697c634507d0a27a7', 'a2548c08191c4ffba862302121b00626');
INSERT INTO `paper_question` VALUES ('50', '115bd22d1bf3411697c634507d0a27a7', '7fb8a8ac6f16451ab416dcd2358da233');
INSERT INTO `paper_question` VALUES ('51', '115bd22d1bf3411697c634507d0a27a7', '0eee5088a03d4501ab507e20d10daff4');
INSERT INTO `paper_question` VALUES ('52', '115bd22d1bf3411697c634507d0a27a7', 'b0c6dfe2ad6b4774baad60232f1158db');
INSERT INTO `paper_question` VALUES ('53', '115bd22d1bf3411697c634507d0a27a7', 'd476c46e0e574253a95d44c53eacde73');
INSERT INTO `paper_question` VALUES ('54', '115bd22d1bf3411697c634507d0a27a7', 'e029b3872404418bb8e3ebaf7939b58b');
INSERT INTO `paper_question` VALUES ('55', '115bd22d1bf3411697c634507d0a27a7', 'be9b4fccc2fa4144a3da23fd53faa250');
INSERT INTO `paper_question` VALUES ('57', 'a2d9984ca80240698d2b87a5ef036662', '95c62e38f8ea434ab69d009231d1ae99');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` varchar(255) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `score` bigint(20) DEFAULT NULL,
  `answer` text,
  `analysis` text,
  `option_a` text,
  `option_b` text,
  `option_c` text,
  `option_d` text,
  `title` text,
  `paper_name` varchar(255) DEFAULT NULL,
  `number` int(20) DEFAULT NULL,
  `simple_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('0eee5088a03d4501ab507e20d10daff4', '简答题', null, '10', '变化：圣人到凡人。（4分）    \n原因：林肯的出现促使人们以平常心来看待领袖人物；十九世纪中后期，美国民     主政治进一步发展，人们思想进一步解放。  （6分）', '变化：圣人到凡人。（4分）    \n原因：林肯的出现促使人们以平常心来看待领袖人物；十九世纪中后期，美国民     主政治进一步发展，人们思想进一步解放。  （6分）', null, null, null, null, '阅读材料，完成下列要求。 \n19世纪末20世纪初，中国也有人从历史延 续的角度、民众与英雄辩证关系的角度来解读华盛顿。清末民初，华盛顿已是中文世界 出现频率相当高的名字。在人们心目中，至少有九个不同的华盛顿形象，如开国总统、 国父形象；打了天下但不做皇帝的尧舜形象；敢于认错、不讲谎话、见义勇为、孝顺母 亲的诚实、行善、孝顺形象；也有缺点错误、也会发脾气的凡人形象。\n 根据材料并结合所学知识，指出南北战争前后美国人心目中华盛顿形象的变化及原因。', '测试正式考试1', '6', '阅读材料，完成下列要求。 \n1...');
INSERT INTO `question` VALUES ('7fb8a8ac6f16451ab416dcd2358da233', '选择题', null, '4', 'optionC', null, '“师夷长技”思想萌发', '“中体西用”思潮兴起', '“托古改制”思想产生', '“尊孔复古”思潮泛滥', '李鸿章凭淮军实力日渐强盛。一次，他在游孔林时说道：“孔子不会打洋枪，今不     足贵也。”李鸿章这样评价孔子，其背景最可能是', '测试正式考试1', '5', '李鸿章凭淮军实力日渐强盛。一次...');
INSERT INTO `question` VALUES ('95c62e38f8ea434ab69d009231d1ae99', '简答题', null, '8', '工业革命期间，疾病流行；医疗卫生事业和人们观念落后；战争频繁。（8分，答     出两点给6分，答出三点给8分）', '工业革命期间，疾病流行；医疗卫生事业和人们观念落后；战争频繁。（8分，答     出两点给6分，答出三点给8分）', null, null, null, null, '中外历史人物评说 \n材料     \n弗罗伦斯·南丁格尔( 1820 - 1910)，出生于英国上流社会家庭。19世纪40、50 年代，伦敦郊区贫民窟频繁发生霍乱等瘟疫，人们对于“医院”“护理”这样的字眼一 向避而不谈，因为都是一些很可怕、很丢脸的事情。医院几乎就是不幸、堕落，混乱的 代名词。由于缺少必要的管理，它有时就像疯人院。      南丁格尔不顾家人反对毅然投身护理事业。1854年她到克里米亚野战医院工作，被 战地士兵称为“克里米亚的天使”和“提灯天使”。1860年，她创建了世界上第一所正 规护士学校，她的办学思想传到欧美及亚洲各国，护理学也成为了一门科学。1863年时， 疾病命名与分类混淆不清，各地医院各自为政。南丁格尔制定了医疗统计标准模式，被 英国各医院相继采用。1912年国际红十字大会设立国际护理界的最高荣誉奖一一南丁格 尔奖章，以表彰为护理事业中做出卓越贡献的医护人员。                              \n 一据安妮·马修森《佛罗伦萨·南丁格尔传》\n 根据材料并结合所学知识，指出南丁格尔投身护理事业的时代背景。', '测试正式考试2', '1', '中外历史人物评说 \n材料   ...');
INSERT INTO `question` VALUES ('a2548c08191c4ffba862302121b00626', '选择题', null, '4', 'optionC', null, '联谊乡邻', '颂扬盛世', '缅怀先祖', '助危济困', '《荆楚岁时记>云：。社日，四邻并结宗会社，宰牲牢，为屋于树下，先祭神，然后     食其胙。”据此可知，社日的民俗功能主要是', '测试正式考试1', '4', '《荆楚岁时记>云：。社日，四邻...');
INSERT INTO `question` VALUES ('b0c6dfe2ad6b4774baad60232f1158db', '简答题', null, '6', '学习汉文化，重用汉官；轻徭薄赋；抑制地方割据。（6分）', '学习汉文化，重用汉官；轻徭薄赋；抑制地方割据。（6分）', null, null, null, null, '材料      \n辽统治者耶律德光灭后晋后，入晋宫召集百官时，他“改服中国衣冠，百官起居皆 如旧制”，对群臣宣布“自今不修甲兵，不市战马，轻赋省役，天下太平矣”。并任命了 一批汉官主持汉地事务。\n根据材料概括耶律德光统治的特点。（6分）', '测试正式考试1', '7', '材料      \n辽统治者耶律...');
INSERT INTO `question` VALUES ('be9b4fccc2fa4144a3da23fd53faa250', '简答题', null, '7', '开创了现代护理事业；提高了护理事业和护士的社会地位；改善了医院的管理。(7     分，答出两点给4分，答出三点给7分)', '开创了现代护理事业；提高了护理事业和护士的社会地位；改善了医院的管理。(7     分，答出两点给4分，答出三点给7分)', null, null, null, null, '中外历史人物评说  材料      弗罗伦斯·南丁格尔( 1820 - 1910)，出生于英国上流社会家庭。19世纪40、50 年代，伦敦郊区贫民窟频繁发生霍乱等瘟疫，人们对于“医院”“护理”这样的字眼一 向避而不谈，因为都是一些很可怕、很丢脸的事情。医院几乎就是不幸、堕落，混乱的 代名词。由于缺少必要的管理，它有时就像疯人院。\n根据材料并结合所学知识，概括南丁格尔对现代医学的贡献。（7分）', '测试正式考试1', '11', '中外历史人物评说  材料   ...');
INSERT INTO `question` VALUES ('c45ea2f782ab4ab39d67ef713b6c9264', '选择题', null, '4', 'optionA', null, '统一进程曲折', '地方治理不畅', '始皇灭儒崇法', '儒学影响较大', '公元前212年，秦始皇坑杀“术士”，长子扶苏劝谏说：“远方黔首未集，诸生皆诵     法孔子，今上皆重法绳之，臣恐天下不安，唯上察之”。这反映当时', '测试正式考试1', '2', '公元前212年，秦始皇坑杀“术...');
INSERT INTO `question` VALUES ('cc7e7125df994a6ab4fef47c8f969db6', '选择题', null, '4', 'optionC', null, '官僚集团重视本地域人才', '南北方士人志向差异', '科举制改变人才地域分布', '政治中心转移到南方', '唐前期的政治人物多为北方人，北宋时政治人物多出生于江西、福建、苏南等地。     这一变化主要反映了', '测试正式考试1', '3', '唐前期的政治人物多为北方人，北...');
INSERT INTO `question` VALUES ('d476c46e0e574253a95d44c53eacde73', '简答题', null, '8', '工业革命期间，疾病流行；医疗卫生事业和人们观念落后；战争频繁。（8分，答     出两点给6分，答出三点给8分）', '工业革命期间，疾病流行；医疗卫生事业和人们观念落后；战争频繁。（8分，答     出两点给6分，答出三点给8分）', null, null, null, null, '中外历史人物评说  材料      弗罗伦斯·南丁格尔( 1820 - 1910)，出生于英国上流社会家庭。19世纪40、50 年代，伦敦郊区贫民窟频繁发生霍乱等瘟疫，人们对于“医院”“护理”这样的字眼一 向避而不谈，因为都是一些很可怕、很丢脸的事情。医院几乎就是不幸、堕落，混乱的 代名词。由于缺少必要的管理，它有时就像疯人院。\n根据材料并结合所学知识，指出南丁格尔投身护理事业的时代背景。（8分）', '测试正式考试1', '8', '中外历史人物评说  材料   ...');
INSERT INTO `question` VALUES ('e029b3872404418bb8e3ebaf7939b58b', '简答题', null, '7', '开创了现代护理事业；提高了护理事业和护士的社会地位；改善了医院的管理。(7     分，答出两点给4分，答出三点给7分)', '开创了现代护理事业；提高了护理事业和护士的社会地位；改善了医院的管理。(7     分，答出两点给4分，答出三点给7分)', null, null, null, null, '中外历史人物评说  材料      弗罗伦斯·南丁格尔( 1820 - 1910)，出生于英国上流社会家庭。19世纪40、50 年代，伦敦郊区贫民窟频繁发生霍乱等瘟疫，人们对于“医院”“护理”这样的字眼一 向避而不谈，因为都是一些很可怕、很丢脸的事情。医院几乎就是不幸、堕落，混乱的 代名词。由于缺少必要的管理，它有时就像疯人院。\n根据材料并结合所学知识，概括南丁格尔对现代医学的贡献。（7分）', '测试正式考试1', '10', '中外历史人物评说  材料   ...');
INSERT INTO `question` VALUES ('fc41c02697004f179933aec14d6efd4c', '选择题', null, '4', 'optionB', null, '周人生活较为富足', '礼乐文明得到广泛认同', '乡人社会地位提高', '贵族奢靡之风波及民间', '《小雅·鹿鸣>本是西周贵族宣扬宴飨之仪的乐歌，后扩散到民间，在乡人宴会上     也可传唱。这表明西周时期', '测试正式考试1', '1', '《小雅·鹿鸣>本是西周贵族宣扬...');

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` varchar(255) NOT NULL,
  `name` varchar(20) NOT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', '历史', '1');
INSERT INTO `subject` VALUES ('2', '地理', '2');
INSERT INTO `subject` VALUES ('3', '政治', '3');

-- ----------------------------
-- Table structure for subject_paper
-- ----------------------------
DROP TABLE IF EXISTS `subject_paper`;
CREATE TABLE `subject_paper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject_id` varchar(255) DEFAULT NULL,
  `paper_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject_paper
-- ----------------------------
INSERT INTO `subject_paper` VALUES ('35', '3', '115bd22d1bf3411697c634507d0a27a7');
INSERT INTO `subject_paper` VALUES ('36', '3', '6b734c017c2f4f80ad3eac1fdf00b2d8');
INSERT INTO `subject_paper` VALUES ('37', '3', 'd494206f90394f03902eb5e51e7b9991');
INSERT INTO `subject_paper` VALUES ('38', '3', 'a2d9984ca80240698d2b87a5ef036662');

-- ----------------------------
-- Table structure for wrong_question
-- ----------------------------
DROP TABLE IF EXISTS `wrong_question`;
CREATE TABLE `wrong_question` (
  `id` varchar(255) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `score` bigint(20) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `analysis` varchar(255) DEFAULT NULL,
  `option_a` varchar(255) DEFAULT NULL,
  `option_b` varchar(255) DEFAULT NULL,
  `option_c` varchar(255) DEFAULT NULL,
  `option_d` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `paper_name` varchar(255) DEFAULT NULL,
  `number` varchar(20) DEFAULT NULL,
  `simple_title` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `right_answer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wrong_question
-- ----------------------------
