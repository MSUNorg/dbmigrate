SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
	`login` VARCHAR(50) NOT NULL DEFAULT '',
	`password` VARCHAR(50) NULL DEFAULT NULL,
	`lastactive` DATETIME NULL DEFAULT NULL,
	`access_level` INT(11) NULL DEFAULT NULL,
	`ip` VARCHAR(20) NOT NULL DEFAULT '',
	`host` VARCHAR(255) NOT NULL DEFAULT '',
	`banned` INT(11) UNSIGNED NOT NULL DEFAULT '0',
	`character_slot` INT(2) UNSIGNED NOT NULL DEFAULT '0',
	`DPassword` VARCHAR(6) NOT NULL DEFAULT '-1',
	`acer` INT(11) NOT NULL DEFAULT '0',
	`expiry` INT(11) NOT NULL DEFAULT '0',
	`ask` VARCHAR(255) NULL DEFAULT NULL,
	`ans` VARCHAR(128) NULL DEFAULT NULL,
	`lastupdate` DATE NULL DEFAULT NULL,
	`created` DATETIME NULL DEFAULT NULL,
	`aid` VARCHAR(50) NOT NULL DEFAULT '0',
	`sessionkey` INT(10) NULL DEFAULT '0',
	PRIMARY KEY (`login`)
)
COMMENT='MyISAM free: 3072 kB' COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `characters`;
CREATE TABLE `characters` (
	`account_name` VARCHAR(13) NOT NULL DEFAULT '0',
	`objid` INT(11) UNSIGNED NOT NULL DEFAULT '0',
	`char_name` VARCHAR(45) NOT NULL DEFAULT '',
	`level` INT(11) UNSIGNED NOT NULL DEFAULT '0',
	`HighLevel` INT(11) UNSIGNED NOT NULL DEFAULT '0',
	`AccessLevel` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`Exp` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`MaxHp` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`CurHp` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`MaxMp` INT(10) NOT NULL DEFAULT '0',
	`CurMp` INT(10) NOT NULL DEFAULT '0',
	`Ac` INT(10) NOT NULL DEFAULT '0',
	`Str` INT(3) NOT NULL DEFAULT '0',
	`Con` INT(3) NOT NULL DEFAULT '0',
	`Dex` INT(3) NOT NULL DEFAULT '0',
	`Cha` INT(3) NOT NULL DEFAULT '0',
	`Intel` INT(3) NOT NULL DEFAULT '0',
	`Wis` INT(3) NOT NULL DEFAULT '0',
	`Status` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`Class` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`Sex` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`Type` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`Heading` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`LocX` INT(11) UNSIGNED NOT NULL DEFAULT '0',
	`LocY` INT(11) UNSIGNED NOT NULL DEFAULT '0',
	`MapID` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`Food` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`Lawful` INT(10) NOT NULL DEFAULT '0',
	`Title` VARCHAR(35) NOT NULL DEFAULT '',
	`ClanID` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`Clanname` VARCHAR(45) NOT NULL DEFAULT '',
	`ClanRank` INT(3) NOT NULL DEFAULT '0',
	`BonusStatus` INT(10) NOT NULL DEFAULT '0',
	`ElixirStatus` INT(10) NOT NULL DEFAULT '0',
	`ElfAttr` INT(10) NOT NULL DEFAULT '0',
	`PKcount` INT(10) NOT NULL DEFAULT '0',
	`PkCountForElf` INT(10) NOT NULL DEFAULT '0',
	`ExpRes` INT(10) NOT NULL DEFAULT '0',
	`PartnerID` INT(10) NOT NULL DEFAULT '0',
	`HomeTownID` INT(10) NOT NULL DEFAULT '0',
	`Contribution` INT(10) NOT NULL DEFAULT '0',
	`SpecialContribution` INT(10) NOT NULL DEFAULT '0',
	`Pay` INT(10) NOT NULL DEFAULT '0',
	`HellTime` INT(10) NOT NULL DEFAULT '0',
	`Banned` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0',
	`Karma` INT(10) NOT NULL DEFAULT '0',
	`LastPk` DATETIME NULL DEFAULT NULL,
	`LastPkForElf` DATETIME NULL DEFAULT NULL,
	`DeleteTime` DATETIME NULL DEFAULT NULL,
	`OriginalStr` INT(3) NOT NULL DEFAULT '0',
	`OriginalCon` INT(3) NOT NULL DEFAULT '0',
	`OriginalDex` INT(3) NOT NULL DEFAULT '0',
	`OriginalCha` INT(3) NOT NULL DEFAULT '0',
	`OriginalInt` INT(3) NOT NULL DEFAULT '0',
	`OriginalWis` INT(3) NOT NULL DEFAULT '0',
	`OriginalHp` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`OriginalMp` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`ElixirStatsReward` INT(10) NOT NULL DEFAULT '0',
	`Yuanbao` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`expShare` SMALLINT(3) NOT NULL DEFAULT '0',
	`lastOutTime` DATETIME NULL DEFAULT NULL,
	`vip` INT(2) NOT NULL DEFAULT '0',
	`wealtime` VARCHAR(10) NULL DEFAULT NULL,
	`vip_errand_id` INT(10) NOT NULL DEFAULT '0',
	`vip_errandparty_id` INT(10) NOT NULL DEFAULT '0',
	`vip_errand_count` INT(10) NOT NULL DEFAULT '0',
	`vip_errandparty_count` INT(10) NOT NULL DEFAULT '0',
	`vip_errand_time` VARCHAR(10) NULL DEFAULT NULL,
	`playertitle` SMALLINT(2) NOT NULL DEFAULT '0',
	`HpMpCount` SMALLINT(3) NOT NULL DEFAULT '0',
	`TurnLife` SMALLINT(2) UNSIGNED NOT NULL DEFAULT '0',
	`passpro` SMALLINT(1) UNSIGNED NOT NULL DEFAULT '0',
	`birthday` DATETIME NULL DEFAULT NULL,
	`daily_tasks` DATETIME NULL DEFAULT NULL,
	`gerardTime` INT(10) NULL DEFAULT '0',
	`status_reset` INT(3) NOT NULL DEFAULT '0',
	`conclude_id` INT(11) NOT NULL DEFAULT '0',
	`punishment_time` DATETIME NULL DEFAULT NULL,
	`giranPrisonTime` INT(10) NOT NULL DEFAULT '0',
	`ivoryTowerTime` INT(10) NOT NULL DEFAULT '0',
	`DragonValleyprisonTime` INT(10) NOT NULL DEFAULT '0',
	`ring_count` INT(2) NOT NULL DEFAULT '0',
	`clanRemark` VARCHAR(61) NOT NULL DEFAULT '',
	`join_clan_time` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`objid`),
	INDEX `key_id` (`account_name`, `char_name`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_config`;
CREATE TABLE `character_config` (
	`object_id` INT(10) NOT NULL DEFAULT '0',
	`length` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`data` BLOB NULL,
	`data1` BLOB NULL,
	PRIMARY KEY (`object_id`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_elf_warehouse`;
CREATE TABLE `character_elf_warehouse` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`account_name` VARCHAR(13) NULL DEFAULT NULL,
	`item_id` INT(11) NULL DEFAULT NULL,
	`item_name` VARCHAR(255) NULL DEFAULT NULL,
	`count` BIGINT(20) NULL DEFAULT NULL,
	`is_equipped` INT(11) NULL DEFAULT NULL,
	`enchantlvl` INT(11) NULL DEFAULT NULL,
	`is_id` INT(11) NULL DEFAULT NULL,
	`durability` INT(11) NULL DEFAULT NULL,
	`charge_count` INT(11) NULL DEFAULT NULL,
	`produce_time` DATETIME NULL DEFAULT NULL,
	`last_used` DATETIME NULL DEFAULT NULL,
	`bless` INT(11) NULL DEFAULT NULL,
	`attr_enchant_kind` INT(11) NULL DEFAULT NULL,
	`attr_enchant_level` INT(11) NULL DEFAULT NULL,
	`firemr` INT(11) NULL DEFAULT '0',
	`watermr` INT(11) NULL DEFAULT '0',
	`earthmr` INT(11) NULL DEFAULT '0',
	`windmr` INT(11) NULL DEFAULT '0',
	`addsp` INT(11) NULL DEFAULT '0',
	`addhp` INT(11) NULL DEFAULT '0',
	`addmp` INT(11) NULL DEFAULT '0',
	`hpr` INT(11) NULL DEFAULT '0',
	`mpr` INT(11) NULL DEFAULT '0',
	`remaining_time` BIGINT(20) NULL DEFAULT NULL,
	`armor_up_id` INT(11) NOT NULL DEFAULT '0',
	`armor_up_id2` INT(11) NOT NULL DEFAULT '0',
	`armor_up_endtime` DATETIME NULL DEFAULT NULL,
	`proctect_id` INT(11) NOT NULL DEFAULT '0',
	`field1` INT(5) NOT NULL DEFAULT '0',
	`field1_index` INT(5) NOT NULL DEFAULT '0',
	`field2` INT(5) NOT NULL DEFAULT '0',
	`field2_index` INT(5) NOT NULL DEFAULT '0',
	`field3` INT(5) NOT NULL DEFAULT '0',
	`field3_index` INT(5) NOT NULL DEFAULT '0',
	`field4` INT(5) NOT NULL DEFAULT '0',
	`field4_index` INT(5) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	INDEX `key_id` (`account_name`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM AUTO_INCREMENT=406012548;

DROP TABLE IF EXISTS `character_items`;
CREATE TABLE `character_items` (
	`id` INT(11) NOT NULL DEFAULT '0',
	`item_id` INT(11) NULL DEFAULT NULL,
	`char_id` INT(11) NULL DEFAULT NULL,
	`item_name` VARCHAR(255) NULL DEFAULT NULL,
	`count` BIGINT(20) NULL DEFAULT NULL,
	`is_equipped` INT(11) NULL DEFAULT NULL,
	`enchantlvl` INT(11) NULL DEFAULT NULL,
	`is_id` INT(11) NULL DEFAULT NULL,
	`durability` INT(11) NULL DEFAULT NULL,
	`charge_count` INT(11) NULL DEFAULT NULL,
	`produce_time` DATETIME NULL DEFAULT NULL,
	`last_used` DATETIME NULL DEFAULT NULL,
	`bless` INT(11) UNSIGNED NULL DEFAULT '1',
	`attr_enchant_kind` INT(11) NULL DEFAULT '0',
	`attr_enchant_level` INT(11) NULL DEFAULT '0',
	`firemr` INT(11) NULL DEFAULT '0',
	`watermr` INT(11) NULL DEFAULT '0',
	`earthmr` INT(11) NULL DEFAULT '0',
	`windmr` INT(11) NULL DEFAULT '0',
	`addsp` INT(11) NULL DEFAULT '0',
	`addhp` INT(11) NULL DEFAULT '0',
	`addmp` INT(11) NULL DEFAULT '0',
	`hpr` INT(11) NULL DEFAULT '0',
	`mpr` INT(11) NULL DEFAULT '0',
	`gamNo` INT(11) NULL DEFAULT '0',
	`gamNpcId` INT(11) NULL DEFAULT '0',
	`key_message_id` INT(11) UNSIGNED NULL DEFAULT '0',
	`remaining_time` BIGINT(20) NULL DEFAULT NULL,
	`armor_up_id` INT(11) NOT NULL DEFAULT '0',
	`armor_up_id2` INT(11) NOT NULL DEFAULT '0',
	`armor_up_endtime` DATETIME NULL DEFAULT NULL,
	`proctect_id` INT(11) NOT NULL DEFAULT '0',
	`field1` INT(5) NOT NULL DEFAULT '0',
	`field1_index` INT(5) NOT NULL DEFAULT '0',
	`field2` INT(5) NOT NULL DEFAULT '0',
	`field2_index` INT(5) NOT NULL DEFAULT '0',
	`field3` INT(5) NOT NULL DEFAULT '0',
	`field3_index` INT(5) NOT NULL DEFAULT '0',
	`field4` INT(5) NOT NULL DEFAULT '0',
	`field4_index` INT(5) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	INDEX `key_id` (`char_id`)
)
COLLATE='utf8_general_ci'  ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_itemupdate`;
CREATE TABLE `character_itemupdate` (
	`item_id` INT(10) NOT NULL,
	`count` INT(10) NOT NULL,
	`attack` INT(10) NOT NULL,
	`property` INT(10) NOT NULL,
	`critical` INT(10) NOT NULL,
	`hp` INT(10) NOT NULL,
	`magic` INT(10) NOT NULL,
	`add_dmg` INT(10) NOT NULL,
	`add_dmgmodifier` INT(10) NOT NULL,
	`add_hitmodifier` INT(10) NOT NULL,
	`add_str` INT(10) NOT NULL,
	`add_dex` INT(10) NOT NULL,
	`add_con` INT(10) NOT NULL,
	`add_cha` INT(10) NOT NULL,
	`add_int` INT(10) NOT NULL,
	`add_wis` INT(10) NOT NULL,
	`add_churinga` INT(10) NOT NULL,
	`mp` INT(10) NOT NULL,
	`fire` INT(10) NOT NULL,
	`wind` INT(10) NOT NULL,
	`earth` INT(10) NOT NULL,
	`water` INT(10) NOT NULL,
	`mr` INT(10) NOT NULL,
	`hong` INT(10) NOT NULL,
	`huang` INT(10) NOT NULL,
	`lan` INT(10) NOT NULL,
	`lv` INT(10) NOT NULL,
	`hei` INT(10) NOT NULL,
	`bai` INT(10) NOT NULL,
	`shen` INT(10) NOT NULL,
	`magichit` INT(5) NOT NULL,
	`gemstone_count` INT(10) NOT NULL DEFAULT '0',
	`add_sp` INT(10) NOT NULL DEFAULT '0',
	`add_dmgreduction` INT(10) NOT NULL DEFAULT '0',
	`add_hpr` INT(10) NOT NULL DEFAULT '0',
	`add_mpr` INT(10) NOT NULL DEFAULT '0',
	`name_color` VARCHAR(10) NULL DEFAULT NULL,
	`evolvType` INT(10) NOT NULL DEFAULT '0',
	`crystal_magic_id` INT(10) NOT NULL DEFAULT '0',
	`regist_stun` INT(2) NOT NULL DEFAULT '0',
	`regist_stone` INT(2) NOT NULL DEFAULT '0',
	`regist_sleep` INT(2) NOT NULL DEFAULT '0',
	`regist_freeze` INT(2) NOT NULL DEFAULT '0',
	`regist_sustain` INT(2) NOT NULL DEFAULT '0',
	`regist_blind` INT(2) NOT NULL DEFAULT '0',
	`bow_hit_modifier` INT(2) NOT NULL DEFAULT '0',
	`bow_dmg_modifier` INT(2) NOT NULL DEFAULT '0',
	`ac` INT(2) NOT NULL DEFAULT '0',
	`add_magic_damReduction` INT(5) NOT NULL DEFAULT '0',
	`add_stunlvl` INT(2) NOT NULL DEFAULT '0',
	PRIMARY KEY (`item_id`)
)
COLLATE='utf8_general_ci'  ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_lottery_warehouse`;
CREATE TABLE `character_lottery_warehouse` (
	`order_id` INT(10) NOT NULL AUTO_INCREMENT,
	`char_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`item_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`item_name` VARCHAR(50) NULL DEFAULT NULL,
	`item_count` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`enchantlvl` INT(10) NOT NULL DEFAULT '0',
	`time` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`order_id`, `char_id`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM ROW_FORMAT=DYNAMIC AUTO_INCREMENT=4989;

DROP TABLE IF EXISTS `character_maptime`;
CREATE TABLE `character_maptime` (
	`char_id` INT(10) UNSIGNED NOT NULL,
	`map_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`time_second` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`same_type` INT(10) NOT NULL DEFAULT '0',
	`add_max_time` INT(10) NOT NULL DEFAULT '0',
	`date` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`char_id`, `map_id`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM ROW_FORMAT=FIXED;

DROP TABLE IF EXISTS `character_passivespells`;
CREATE TABLE `character_passivespells` (
	`char_obj_id` INT(10) NOT NULL DEFAULT '0',
	`skill_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`skill_name` VARCHAR(45) NOT NULL DEFAULT '',
	PRIMARY KEY (`char_obj_id`, `skill_id`)
)
COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB' COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_quests`;
CREATE TABLE `character_quests` (
	`char_id` INT(10) UNSIGNED NOT NULL,
	`quest_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`quest_step` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	PRIMARY KEY (`char_id`, `quest_id`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_shop_consumption`;
CREATE TABLE `character_shop_consumption` (
	`account_name` VARCHAR(50) NULL DEFAULT NULL,
	`char_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`char_name` VARCHAR(50) NULL DEFAULT NULL,
	`consumption_count` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	PRIMARY KEY (`char_id`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_shop_restrict`;
CREATE TABLE `character_shop_restrict` (
	`account_name` VARCHAR(50) NOT NULL DEFAULT '',
	`char_id` INT(10) UNSIGNED NOT NULL,
	`itemid` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`count` INT(10) NOT NULL DEFAULT '0',
	`date` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`account_name`, `char_id`, `itemid`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_shop_warehouse`;
CREATE TABLE `character_shop_warehouse` (
	`order_id` INT(10) NOT NULL AUTO_INCREMENT,
	`char_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`item_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`item_name` VARCHAR(50) NULL DEFAULT NULL,
	`item_count` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`enchantlvl` INT(10) NOT NULL DEFAULT '0',
	`time` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`order_id`, `char_id`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM AUTO_INCREMENT=5145;

DROP TABLE IF EXISTS `character_skills`;
CREATE TABLE `character_skills` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`char_obj_id` INT(10) NOT NULL DEFAULT '0',
	`skill_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`skill_name` VARCHAR(45) NOT NULL DEFAULT '',
	`is_active` INT(10) NULL DEFAULT NULL,
	`activetimeleft` INT(10) NULL DEFAULT NULL,
	PRIMARY KEY (`char_obj_id`, `skill_id`),
	INDEX `key_id` (`id`)
)
COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB' COLLATE='utf8_general_ci' ENGINE=MyISAM AUTO_INCREMENT=4498835;

DROP TABLE IF EXISTS `character_teleport`;
CREATE TABLE `character_teleport` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`char_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`name` VARCHAR(45) NOT NULL DEFAULT '',
	`locx` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`locy` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`mapid` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	INDEX `key_id` (`char_id`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM AUTO_INCREMENT=405260316;

DROP TABLE IF EXISTS `character_vip_time`;
CREATE TABLE `character_vip_time` (
	`account` VARCHAR(50) NOT NULL DEFAULT '0',
	`vip_level` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`start_time` DATETIME NULL DEFAULT NULL,
	`end_time` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`account`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `character_warehouse`;
CREATE TABLE `character_warehouse` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`account_name` VARCHAR(13) NULL DEFAULT NULL,
	`item_id` INT(11) NULL DEFAULT NULL,
	`item_name` VARCHAR(255) NULL DEFAULT NULL,
	`count` BIGINT(20) NULL DEFAULT NULL,
	`is_equipped` INT(11) NULL DEFAULT NULL,
	`enchantlvl` INT(11) NULL DEFAULT NULL,
	`is_id` INT(11) NULL DEFAULT NULL,
	`durability` INT(11) NULL DEFAULT NULL,
	`charge_count` INT(11) NULL DEFAULT NULL,
	`produce_time` DATETIME NULL DEFAULT NULL,
	`last_used` DATETIME NULL DEFAULT NULL,
	`bless` INT(11) UNSIGNED NULL DEFAULT '1',
	`attr_enchant_kind` INT(11) NULL DEFAULT '0',
	`attr_enchant_level` INT(11) NULL DEFAULT '0',
	`firemr` INT(11) NULL DEFAULT '0',
	`watermr` INT(11) NULL DEFAULT '0',
	`earthmr` INT(11) NULL DEFAULT '0',
	`windmr` INT(11) NULL DEFAULT '0',
	`addsp` INT(11) NULL DEFAULT '0',
	`addhp` INT(11) NULL DEFAULT '0',
	`addmp` INT(11) NULL DEFAULT '0',
	`hpr` INT(11) NULL DEFAULT '0',
	`mpr` INT(11) NULL DEFAULT '0',
	`remaining_time` BIGINT(20) NULL DEFAULT NULL,
	`armor_up_id` INT(11) NOT NULL DEFAULT '0',
	`armor_up_id2` INT(11) NOT NULL DEFAULT '0',
	`armor_up_endtime` DATETIME NULL DEFAULT NULL,
	`proctect_id` INT(11) NOT NULL DEFAULT '0',
	`field1` INT(5) NOT NULL DEFAULT '0',
	`field1_index` INT(5) NOT NULL DEFAULT '0',
	`field2` INT(5) NOT NULL DEFAULT '0',
	`field2_index` INT(5) NOT NULL DEFAULT '0',
	`field3` INT(5) NOT NULL DEFAULT '0',
	`field3_index` INT(5) NOT NULL DEFAULT '0',
	`field4` INT(5) NOT NULL DEFAULT '0',
	`field4_index` INT(5) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	INDEX `key_id` (`account_name`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM AUTO_INCREMENT=400013304;

DROP TABLE IF EXISTS `pets`;
CREATE TABLE `pets` (
	`item_obj_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`objid` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`npcid` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`name` VARCHAR(45) NOT NULL DEFAULT '',
	`lvl` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`hp` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`mp` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`exp` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`lawful` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`food` INT(10) NOT NULL DEFAULT '0',
	`foodtime` INT(10) NOT NULL DEFAULT '1200',
	PRIMARY KEY (`item_obj_id`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM;

DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile` (
	`login` VARCHAR(50) NOT NULL,
	`area` VARCHAR(100) NULL DEFAULT NULL,
	`email` VARCHAR(60) NULL DEFAULT NULL,
	`birthday` DATE NULL DEFAULT NULL,
	`safe` VARCHAR(36) NULL DEFAULT NULL,
	PRIMARY KEY (`login`)
)
COLLATE='utf8_general_ci' ENGINE=MyISAM;


