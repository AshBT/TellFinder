

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `roxy_ui`
--
CREATE SCHEMA IF NOT EXISTS `roxy_ui`;
USE `roxy_ui`;

CREATE TABLE `sources` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Auto incremented row identifier, unique to table.',
  `name` varchar(32) NOT NULL COMMENT 'Name of the site being scraped',
  `scrapedelay` smallint(6) unsigned NOT NULL DEFAULT '10' COMMENT 'Delay in seconds between the scraper touching the website to avoid being banned.',
  `ad-errorurl` varchar(64) DEFAULT NULL COMMENT 'If this is in the URL then it''s an error page and should not be imported. Sometimes there is more then one, also check attributes table.',
  `ad-requiredattribute` varchar(32) NOT NULL DEFAULT 'text' COMMENT 'If the page does not have this attribute then it''s an error and should not be imported. Sometimes there is more then one, also check attributes table.',
  `ad-revisionfield` varchar(32) NOT NULL DEFAULT 'url' COMMENT 'Use this field to determine if it''s a new revision of a prior ad. Usually URL. Sometimes there is more then one, also check attributes table.',
  `modtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp of the most recent change to the site.',
  PRIMARY KEY (`id`),
  KEY `name` (`name`(4)),
  KEY `modtime` (`modtime`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=ascii;

INSERT INTO `sources`
  ( `id`,     `name`,                `scrapedelay`,     `ad-errorurl`,  `ad-requiredattribute`,         `ad-revisionfield`) VALUES
  ( 1,        'backpage',            10,                NULL,                   'text',                 'url');


-- --------------------------------------------------------

--
-- Table structure for table `ads`
--

CREATE TABLE `ads` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Auto incremented row identifier, unique to table.',
  `first_id` int(10) unsigned DEFAULT NULL COMMENT 'ID in this table of the first time this ad was seen.',
  `sources_id` int(10) unsigned NOT NULL COMMENT 'ID of the source the ad came from.',
  `incoming_id` int(11) unsigned NOT NULL COMMENT 'ID of the raw HTML in the incoming table.',
  `url` varchar(2083) CHARACTER SET utf8 NOT NULL COMMENT 'Web address of posting.',
  `title` varchar(1024) CHARACTER SET utf8mb4 NOT NULL COMMENT 'User populated title, usually from the HTML title tag.',
  `text` mediumtext CHARACTER SET utf8mb4 COMMENT 'User populated text of post. Included in, but not the entirety of HTML body.',
  `type` varchar(16) DEFAULT NULL COMMENT 'Type of entity advertised. Allowed values are: person, location, organization.',
  `sid` varchar(64) DEFAULT NULL COMMENT 'Site defined identification number of ad. Not necessarily unique.',
  `region` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Advertising region of post. Defined per website.',
  `city` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT 'City advertised in post.',
  `state` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Advertised state or province.',
  `country` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Advertised country.',
  `phone` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Phone number listed in post.',
  `age` varchar(10) DEFAULT NULL COMMENT 'Age of person indicated in post.',
  `website` varchar(2048) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'Website of entity advertised in post.',
  `email` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'Email address listed in post.',
  `gender` varchar(20) DEFAULT NULL COMMENT 'Listed gender. Suggested values are: female, male, trans.',
  `service` varchar(16) DEFAULT NULL COMMENT 'Services offered. Example values are: Escorts, Massage, BDSM, GFE',
  `posttime` datetime DEFAULT NULL COMMENT 'Site populated timestamp of when post was created.',
  `importtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when post was imported into database.',
  `modtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp of the most recent modification.',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`importtime`),
  KEY `First ID` (`first_id`),
  KEY `type` (`type`(3)),
  KEY `URL` (`url`(128)),
  KEY `region` (`region`(5)),
  KEY `city` (`city`(6)),
  KEY `state` (`state`(3)),
  KEY `country` (`country`(4)),
  KEY `phone` (`phone`),
  KEY `age` (`age`),
  KEY `email` (`email`(20)),
  KEY `service` (`service`(1)),
  KEY `sid` (`sid`(4)),
  KEY `incomingandsite` (`sources_id`,`incoming_id`),
  KEY `first_idandid` (`first_id`,`id`),
  KEY `posttimeandsites` (`posttime`,`sources_id`),
  FULLTEXT KEY `fulltext` (`title`,`text`,`email`,`website`)
) ENGINE=InnoDB AUTO_INCREMENT=32619772 DEFAULT CHARSET=ascii;


--
-- Dumping data for table `ads`
--
INSERT INTO `ads`
(`id`,  `first_id`, `sources_id`, `incoming_id`,  `url`,                             `title`,   `text`,  `type`,`sid`,   `region`, `city`,       `state`,  `country`,  `phone`,      `age`,  `website`,                    `email`,                      `gender`,     `service`,  `posttime`,             `importtime`,            `modtime`)              VALUES
('1',   NULL,       '1',          '1',            'http://www.backpage.com/url1',    'Title 1', 'Text 1', NULL, '1001',  'AL',     'Birmingham',  NULL,     'us',       '5551112222', '34',   'http://website1.sample',     'email1@sample.com',          'Female',     NULL,       '2013-11-25 00:00:00',  '2014-04-02 20:59:24',  '2014-04-23 21:42:58'),
('2',   NULL,       '1',          '2',            'http://www.backpage.com/url2',    'Title 2', 'Text 2', NULL, '1002',  'AL',     'Birmingham',  NULL,     'us',       '5551113333', '37',   'http://website3.sample',     'email2@sample.com',          'Female',     NULL,       '2013-11-26 00:00:00',  '2014-04-02 20:59:24',  '2014-04-23 21:42:58'),
('3',   NULL,       '1',          '3',            'http://www.backpage.com/url3',    'Title 3', 'Text 3', NULL, '1003',  'AL',     'Mobile',  NULL,         'us',       '5551114444', '29',   'http://website2.sample',     'email2@sample.com',          'Female',     NULL,       '2013-11-27 00:00:00',  '2014-04-02 20:59:24',  '2014-04-23 21:42:58'),
('4',   NULL,       '1',          '4',            'http://www.backpage.com/url2',    'Title 4', 'Text 4', NULL, '1004',  'AL',     'Mobile',  NULL,         'us',       '5551112222', '34',   'http://website2.sample',     'email1@sample.com',          'Female',     NULL,       '2013-11-28 00:00:00',  '2014-04-02 20:59:24',  '2014-04-23 21:42:58'),
('5',   NULL,       '1',          '5',            'http://www.backpage.com/url3',    'Title 5', 'Text 5', NULL, '1005',  'AL',     'Birmingham',  NULL,     'us',       '5551111111', '34',   'http://website1.sample',     'email1@sample.com',          'Female',     NULL,       '2013-11-29 00:00:00',  '2014-04-02 20:59:24',  '2014-04-23 21:42:58');

CREATE TABLE `ads_attributes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID of the attribute entry',
  `ads_id` int(11) unsigned NOT NULL COMMENT 'Parent ID for this attribute.',
  `attribute` varchar(32) NOT NULL COMMENT 'Attribute name (Age, location, etc.)',
  `value` varchar(2500) CHARACTER SET utf8 NOT NULL COMMENT 'Value of the attribute.',
  `extracted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'If no the value was from the structure of the website. If yes we used an algorithm on the text to get the value and it may be less accurate.',
  `extractedraw` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Raw text of the data if extracted.',
  `modtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp of the most recent modification.',
  PRIMARY KEY (`id`),
  KEY `ads_id` (`ads_id`),
  KEY `attribute` (`attribute`(4)),
  KEY `extracted` (`extracted`),
  KEY `value` (`value`(16)),
  KEY `modtime` (`modtime`)
) ENGINE=InnoDB AUTO_INCREMENT=590710400 DEFAULT CHARSET=ascii;

INSERT INTO `ads_attributes`
  (`id`,  `ads_id`, `attribute`,  `value`,                  `extracted`,  `extractedraw`,   `modtime`) VALUES
  ('1',   '1',      'phone',      '5551112222',             '0',          NULL,             '2014-09-05 20:21:35'),
  ('2',   '1',      'email',      'email1@sample.com',      '0',          NULL,             '2014-09-05 20:21:35'),
  ('3',   '1',      'website',    'http://website1.sample', '0',          NULL,             '2014-09-05 20:21:35'),


  ('4',   '2',      'phone',      '5551113333',             '0',          NULL,             '2014-09-05 20:21:35'),
  ('5',   '2',      'email',      'email2@sample.com',      '0',          NULL,             '2014-09-05 20:21:35'),
  ('6',   '2',      'website',    'http://website3.sample', '0',          NULL,             '2014-09-05 20:21:35'),


  ('7',   '3',      'phone',      '5551114444',             '0',          NULL,             '2014-09-05 20:21:35'),
  ('8',   '3',      'email',      'email2@sample.com',      '0',          NULL,             '2014-09-05 20:21:35'),
  ('9',   '3',      'website',    'http://website2.sample', '0',          NULL,             '2014-09-05 20:21:35'),

  ('10',   '4',      'phone',      '5551112222',             '0',          NULL,             '2014-09-05 20:21:35'),
  ('11',   '4',      'email',      'email1@sample.com',      '0',          NULL,             '2014-09-05 20:21:35'),
  ('12',   '4',      'website',    'http://website2.sample', '0',          NULL,             '2014-09-05 20:21:35'),

  ('13',   '5',      'phone',      '5551111111',             '0',          NULL,             '2014-09-05 20:21:35'),
  ('14',   '5',      'email',      'email1@sample.com',      '0',          NULL,             '2014-09-05 20:21:35'),
  ('15',   '5',      'website',    'http://website1.sample', '0',          NULL,             '2014-09-05 20:21:35');

--
-- Images not supported for demo data, but including schema
--
CREATE TABLE `images` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Auto incremented row identifier, unique to table.',
  `sources_id` int(11) unsigned NOT NULL COMMENT 'ID of the source the ad came from.',
  `ads_id` int(11) unsigned DEFAULT NULL COMMENT 'ID of the ad in the ads table that these images are from.',
  `url` varchar(2083) CHARACTER SET utf8mb4 NOT NULL COMMENT 'URL of the source image.',
  `location` varchar(128) DEFAULT NULL COMMENT 'Location of our cached copy of the image.',
  `importtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when image was imported into database.',
  `modtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp of the most recent modification.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `url_unique` (`url`(191)),
  KEY `timestamp` (`importtime`),
  KEY `url` (`url`(128)),
  KEY `ads_id` (`ads_id`),
  KEY `location` (`location`(64)),
  KEY `sources_id` (`sources_id`),
  KEY `modtime` (`modtime`)
) ENGINE=InnoDB AUTO_INCREMENT=103570504 DEFAULT CHARSET=ascii;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


CREATE SCHEMA IF NOT EXISTS `memex_uncharted`;
USE `memex_uncharted`;


CREATE TABLE `locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region` varchar(128) DEFAULT NULL,
  `city` varchar(128) DEFAULT NULL,
  `state` varchar(64) DEFAULT NULL,
  `country` varchar(64) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `label` text,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `ft` (`label`)
) ENGINE=InnoDB AUTO_INCREMENT=8239 DEFAULT CHARSET=utf8;

INSERT INTO `locations`
(`id`,`region`,     `city`, `state`,  `country`,  `address`,  `longitude`,  `latitude`, `label`) VALUES
('1', 'birmingham', NULL,   NULL,     NULL,       NULL,       '-86.8025',   '33.5207',  'Birmingham, AL, USA'),
('2', 'mobile',     NULL,   NULL,     NULL,       NULL,       '-88.0431',   '30.6944',  'Mobile, AL, USA');

--
-- Keywords are not supported in demo data, but they can be used to classify ads
--
CREATE TABLE `ads_keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ads_id` int(11) NOT NULL COMMENT 'FK Ads.id',
  `keyword` varchar(45) NOT NULL COMMENT 'The keyword string',
  `classifier` varchar(45) NOT NULL COMMENT 'The type of classifier, ie/ young, risky, underage, etc',
  `count` int(11) NOT NULL COMMENT 'Frequency of keyword occurance in ad',
  PRIMARY KEY (`id`),
  KEY `ads_idx` (`ads_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12044943 DEFAULT CHARSET=latin1;

CREATE TABLE `keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(45) NOT NULL,
  `classifier` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;


--
-- Image processing is not currently supported in the sample data, but we create the schemas for reference anyway
CREATE TABLE `ads_imagebins` (
  `ads_id` int(11) unsigned DEFAULT NULL,
  `bin` int(11) DEFAULT NULL,
  KEY `ads_id` (`ads_id`),
  KEY `bin` (`bin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `clusters_imagebins` (
  `clusterid` int(10) NOT NULL,
  `bin` int(11) DEFAULT NULL,
  `count` bigint(21) NOT NULL DEFAULT '0',
  KEY `bin` (`bin`),
  KEY `clusterid` (`clusterid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `ads_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `images_id` int(11) unsigned NOT NULL,
  `ads_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ia` (`images_id`,`ads_id`),
  KEY `ai` (`ads_id`,`images_id`),
  KEY `images_id` (`images_id`),
  KEY `ads_id` (`ads_id`)
) ENGINE=InnoDB AUTO_INCREMENT=257155544 DEFAULT CHARSET=latin1;

CREATE TABLE `imagehash_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hash` varchar(128) NOT NULL DEFAULT '',
  `histogram` varchar(128) DEFAULT NULL,
  `bin` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sha1` (`hash`),
  KEY `bin` (`bin`)
) ENGINE=InnoDB AUTO_INCREMENT=19335413 DEFAULT CHARSET=utf8;

CREATE TABLE `images_hash` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `images_id` int(11) unsigned NOT NULL,
  `hash` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `images_id` (`images_id`),
  KEY `sha1` (`hash`)
) ENGINE=InnoDB AUTO_INCREMENT=57217080 DEFAULT CHARSET=utf8;

CREATE TABLE `images_attributes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `images_id` int(11) DEFAULT NULL,
  `value` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Searching by location from a tip requires this table to be populated
--
CREATE TABLE `world_cities` (
  `country` varchar(2) DEFAULT NULL,
  `city` varchar(250) DEFAULT NULL,
  `accentcity` varchar(256) DEFAULT NULL,
  `region` varchar(256) DEFAULT NULL,
  `population` int(12) DEFAULT NULL,
  `lat` float(10,6) DEFAULT NULL,
  `lon` float(10,6) DEFAULT NULL,
  `cityalpha` varchar(250) DEFAULT NULL,
  KEY `city_idx` (`city`),
  KEY `cityalpha_index` (`cityalpha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





--
-- Insert data into these tables for phone/email/websites that will be ignored and removed by the preprocessing code
--
CREATE TABLE `bad_emails` (
  `value` varchar(2048) NOT NULL,
  PRIMARY KEY (`value`(128))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `bad_phones` (
  `value` varchar(16) NOT NULL,
  PRIMARY KEY (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bad_websites` (
  `value` varchar(2048) NOT NULL,
  PRIMARY KEY (`value`(16))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ads_price_phones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ads_id` int(11) NOT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `raw` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ads_id` (`ads_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41020 DEFAULT CHARSET=latin1;





