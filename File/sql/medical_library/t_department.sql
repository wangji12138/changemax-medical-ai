/*
Navicat MySQL Data Transfer

Source Server         : SQL_WJ
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : medical_library

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-28 14:23:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `department_intro` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `department_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`department_id`,`department_name`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('1', '心血管内科', '心内科，即心血管内科，是各级医院大内科为了诊疗心血管血管疾病而设置的一个临床科室，治疗的疾病包括心绞痛、高血压、猝死、心律失常、心力衰竭、早搏、心律不齐、心肌梗死、心肌病、心肌炎、急性心肌梗死等心血管疾病。', null);
INSERT INTO `t_department` VALUES ('2', '肝胆外科', '肝胆外科主要研究肝细胞癌、肝胆管结石、肝炎后肝硬化和重型肝炎所致的急性肝功能衰竭是严重威胁国人健康的重大疾病。', null);
INSERT INTO `t_department` VALUES ('3', '泌尿外科', '泌尿外科主要治疗范围有：各种尿结石和复杂性肾结石；肾脏和膀胱肿瘤；前列腺增生和前列腺炎；睾丸附睾的炎症和肿瘤；睾丸精索鞘膜积液；各种泌尿系损伤；泌尿系先天性畸形如尿道下裂、隐睾、肾盂输尿管连接部狭窄所导致的肾积水等等。', null);
INSERT INTO `t_department` VALUES ('4', '麻醉科', '麻醉学科是一个综合性的学科，它包含多学科的知识。现在的范围更广，不单单是满足手术的要求，还参入各科室的抢救工作，妇科的无痛分娩，无痛流产等等。', null);
INSERT INTO `t_department` VALUES ('5', '一病区', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('6', '神经外科', '神经外科（Neurosurgery）是外科学中的一个分支，是在外科学以手术为主要治疗手段的基础上，应用独特的神经外科学研究方法，研究人体神经系统，如脑、脊髓和周围神经系统，以及与之相关的附属机构，如颅骨、头皮、脑血管脑膜等结构的损伤、炎症、肿瘤、畸形和某些遗传代谢障碍或功能紊乱疾病，如：癫痫、帕金森病、神经痛等疾病的病因及发病机制，并探索新的诊断、治疗、预防技术的一门高、精、尖学科。神经外科是主治由于外伤导致的脑部、脊髓等神经系统的疾病，例如脑出血出血量危及生命,车祸致脑部外伤，或脑部有肿瘤压迫需手术治疗等。', null);
INSERT INTO `t_department` VALUES ('7', '胸心外科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('8', '普外科', '普外科(Department of general surgery)是以手术为主要方法治疗肝脏、胆道、胰腺、胃肠、肛肠、血管疾病、甲状腺和乳房的肿瘤及外伤等其它疾病的临床学科，是外科系统最大的专科。普外科即普通外科，一般综合性医院外科除普外科外还有骨科、神经外科、心胸外科、泌尿外科等。有的医院甚至将普外科更细的分为颈乳科、胃肠外科、肝胆胰脾外科等，还有肛肠科、烧伤整形科、血管外科、小儿外科、移植外科、营养科等都与普外科有关系。', null);
INSERT INTO `t_department` VALUES ('9', '耳鼻喉头颈外科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('10', '口腔颌面外科', '口腔颌面外科（oral and maxillofacial surgery）是一门以外科治疗为主，研究口腔器官”（牙、牙槽骨、唇、颊、舌、腭、咽等）、面部软组织、颌面诸骨（上颌骨、下颌骨、颧骨等）、颞下颌关节、涎腺以及颈部某些相关疾病的防治为主要内容的学科。', null);
INSERT INTO `t_department` VALUES ('11', '口腔科', '口腔科，医学学科分类之一。主要口腔科疾病包括：口腔颌面部皮样、表皮颌下间隙感染、颌面部淋巴管瘤、齿状突发育畸形、上颌窦恶性肿瘤、颌骨造釉细胞瘤、慢性筛窦炎、下颌后缩、四环素牙、舌白斑等疾病。现在的技术，许多牙周病完全可以治愈。组织生物工程技术的发展如：引导组织再生技术、基因技术、种植义齿等更是为病变牙齿的再生带来令人振奋的希望。但牙周病的治疗必须是一个序列治疗。在治疗过程中制定一个详细、有效的治疗计划、医生细致精湛的治疗和病人的积极配合是治疗成功的关键。', null);
INSERT INTO `t_department` VALUES ('12', '乳腺科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('13', '妇产科', '《妇产科》是韩国SBS电视台于2010年2月3日播出的医疗剧，由李贤直、崔英勋导演，崔熙拉编剧，张瑞希、高周元、 徐智锡、宋仲基、安善英主演。该剧以一家地方医院妇产科为背景，通过描写医生、准夫妻等不同的人物面貌，讲述了现代社会的爱情和婚姻以及和怀孕相关的各种小插曲\r\n[1]', null);
INSERT INTO `t_department` VALUES ('14', '骨科', '《骨科》杂志系，是华中科技大学同济医学院附属同济医院等联合主办的骨科专业期刊，经国家新闻出版总署批准面向国内外公开发行。创刊时间为1964年。', null);
INSERT INTO `t_department` VALUES ('15', '眼科', '眼科的全称是“眼病专科”，是研究发生在视觉系统，包括眼球及与其相关联的组织有关疾病的学科。眼科一般研究玻璃体、视网膜疾病，眼视光学，青光眼和视神经病变，白内障等多种眼科疾病。', null);
INSERT INTO `t_department` VALUES ('16', '皮肤性病科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('17', '肾内科', '肾脏病是常见病和多发病，如果恶化可以发展为尿毒症，严重危害人们健康。慢性肾脏病已成为继心脑血管病、肿瘤、糖尿病之后又一种威胁人类健康的重要疾病，成为全球性公共卫生问题。', null);
INSERT INTO `t_department` VALUES ('18', '神经内科', '神经内科是关于神经方面的二级学科。不属于内科概念。主要诊治脑血管疾病（脑梗塞、脑出血）、偏头痛、脑部炎症性疾病（脑炎、脑膜炎）、脊髓炎、癫痫、痴呆、代谢病和遗传病、三叉神经痛、坐骨神经病、周围神经病及重症肌无力等。主要检查手段包括头颈部MRI，CT，ECT，PETCT，脑电图，TCD（经颅多普勒超声），肌电图，诱发电位及血流变学检查等。同时与心理科交叉进行神经衰弱、失眠等功能性疾患的诊治。', null);
INSERT INTO `t_department` VALUES ('19', '呼吸内科', '《呼吸内科》是2010年北京科学技术出版社出版的图书，作者是高占成、胡大一。该书对呼吸内科常见的各种疾病和诊疗案例进行了详细描述和剖析。', null);
INSERT INTO `t_department` VALUES ('20', '中医科', '中医科采用中药治疗各种冠心病、心律失常、脑梗塞、脑动脉硬化、顽固性头痛、急慢性肾炎、泌尿系结石、男科病、脾胃病、糖尿病以及心身疾病。运用针灸、理疗等方法治疗中风、偏瘫、风湿性关节炎、哮喘等疾病，开设针灸减肥门诊，为广大肥胖患者减轻痛苦。微电脑牵引仪治疗颈椎病、腰椎增生及腰椎间盘突出症，效果显著。', null);
INSERT INTO `t_department` VALUES ('21', '肿瘤科', '肿瘤科和内科、外科、妇产科和儿科一样，是临床医学的二级学科，分为肿瘤内科、肿瘤放射治疗科和肿瘤外科等，肿瘤内科主要从事各种良、恶性肿瘤的内科治疗；肿瘤放射治疗科主要从事肿瘤的放射线治疗；肿瘤外科提供以手术为主的综合治疗。专门的肿瘤医院的相关科室会根据不同部位再行细分，例如肿瘤内科里的胃肠肿瘤科、淋巴瘤肿瘤科；肿瘤外科有乳腺外科、头颈外科、胸外科、肿瘤妇科、腹部外科等。', null);
INSERT INTO `t_department` VALUES ('22', '风湿免疫科', '风湿免疫科是医院内科学领域中的新兴的一种学科，主要研究和治疗风湿免疫类疾病。治疗疾病包括类风湿性关节炎、系统性红斑狼疮、强直性脊柱炎、原发性干燥综合症、骨关节炎、痛风等。', null);
INSERT INTO `t_department` VALUES ('23', '消化内科', '消化内科是研究食管、胃、小肠、大肠、肝、胆及胰腺等疾病为主要内容的临床三级学科。消化内科疾病种类繁多，医学知识面广，操作复杂而精细。', null);
INSERT INTO `t_department` VALUES ('24', '血液内科', '《循证内科学丛书:血液内科》是2010年北京科学技术出版社出版的图书。全书介绍了红细胞疾病、白细胞疾病、骨髓增生异常综合征、多发性骨髓瘤等十章内容。\r\n[1-2]', null);
INSERT INTO `t_department` VALUES ('25', '内分泌内科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('26', '整形科', '整形科又称为整形外科，是医学领域的一门专修学科，主要针对皮肤，肌肉以及骨骼的先天性生长不良以及后天创伤等造成的修复治疗。', null);
INSERT INTO `t_department` VALUES ('27', '烧伤科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('28', '疼痛科', '疼痛已被现代医学列为继呼吸、脉搏、血压、体温之后的第五大生命体征。有些慢性疼痛本身还是一种疾病（如三叉神经痛、带状疱疹后遗神经痛等）。长期的局部疼痛会形成复杂的局部疼痛综合症或中枢性疼痛，使普通的疼痛变得非常剧烈和难以治疗，导致机体各系统功能失调、免疫力降低而诱发各种并发症，甚至致残或危及病人的生命。长期疼痛不仅严重影响患者的躯体、心理和社交功能，而且还影响到其家庭乃至社会。为所有疼痛患者提供治疗，是各国医疗服务的共同目标。疼痛科的医护人员治疗各种急性和慢性顽固性疼痛，为患者创造无痛轻松生活。', null);
INSERT INTO `t_department` VALUES ('29', '总院综合内科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('30', '儿外科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('31', '核医学科', '核医学科，利用核科学技术和手段对疾病进行诊断和治疗，是现代医学的主要手段之一。核医学科是医院主要医技科室之一，主要开展核医学检查项目，是辅助临床科室对疾病作出正确诊断的有效手段之一。', null);
INSERT INTO `t_department` VALUES ('32', '营养科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('33', '预防保健科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('34', '重症医学科', '重症医学科是卫生部在《医疗机构诊疗科目名录》中新增加的诊疗科目。重症医学科的主要业务范围为急危重症患者的抢救和延续性生命支持、发生多器官功能障碍患者的治疗和器官功能支持、防治多脏器功能障碍综合征。', null);
INSERT INTO `t_department` VALUES ('35', '体检科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('36', '急诊科', '急诊医学科（室）或急诊医学中心是医院中重症病人最集中、病种最多、抢救和管理任务最重的科室，是所有急诊病人入院治疗的必经之路。', null);
INSERT INTO `t_department` VALUES ('37', '针灸理疗科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('38', '功能检查科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('39', '检验科', '检验科是临床医学和基础医学之间的桥梁，包括临床化学、临床微生物学、临床免疫学、血液学、体液学以及输血学等分支学科。', null);
INSERT INTO `t_department` VALUES ('40', '病理科', '病理科是大型综合医院必不可少的科室之一，其主要任务是在医疗过程中承担病理诊断工作，包括通过活体组织检查、脱落和细针穿刺细胞学检查以及尸体剖检，为临床提供明确的病理诊断，确定疾病的性质，查明死亡原因。因为病理诊断报告不是影像学的描述，而是明确的疾病名称，临床医师主要根据病理报告决定治疗原则、估计预后以及解释临床症状和明确死亡原因。病理诊断的这种权威性决定了它在所有诊断手段中的核心作用，因此病理诊断的质量不仅对相关科室甚至对医院整体的医疗质量构成极大的影响。', null);
INSERT INTO `t_department` VALUES ('41', '放射介入科', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('42', 'CT室', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('43', '磁共振室', '暂无简介。', null);
INSERT INTO `t_department` VALUES ('44', '内镜室', '内镜室，是一种器具，主要由医院使用，用于观察患者的某些人眼直接看不到的可能病变部位。', null);
INSERT INTO `t_department` VALUES ('45', '药剂科', '药剂科业务根据医院医疗、科研和教学的需要及基本用药目录编制药品计划，查询掌握药品科技和药品市场信息，向临床提供安全有效、质优价廉的各类药品。根据医院医师处方及时准确地调配中西药品。有计划地生产普通制剂、灭菌制剂和中药制剂。开展药品检验工作，建立健全药品监督和质量检验检查制度，对外购药品和自制制剂进行全面控制。开展临床药学临床药理工作配合临床做好新药、临床试验和药品疗效评价。提出改进或淘汰药物品种意见，开展中西药新制剂新剂型药代动力学和生物利用度等科研工作。', null);
