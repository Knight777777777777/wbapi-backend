# 数据库初始化
-- 创建库
create database if not exists wbapi;

-- 切换库
use wbapi;
-- 接口信息
create table if not exists wbapi.`interface_info`
(
    `id`             bigint                             not null auto_increment comment '主键' primary key,
    `name`           varchar(256)                       not null comment '接口名称',
    `description`    varchar(256)                       null comment '接口描述',
    `url`            varchar(512)                       not null comment '接口地址',
    `requestHeader`  text                               null comment '请求头',
    `responseHeader` text                               null comment '用户名',
    `requestParams`  varchar(256)                       null comment '请求参数',
    `status`         int      default 0                 not null comment '状态',
    `method`         varchar(256)                       not null comment '请求类型',
    `userId`         bigint                             not null comment '创建人',
    `create_time`    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_deleted`     tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

-- auto-generated definition
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    AccessKey    varchar(512)                           not null comment '用户调用标识',
    SecretKey    varchar(512)                           not null comment '密钥'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user (unionId);

-- 用户调用接口关系表
create table if not exists wbapi.`user_interface_info`
(
    `id`              bigint                             not null auto_increment comment '主键' primary key,
    `userId`          bigint                             not null comment '调用用户 id',
    `interfaceInfoId` bigint                             not null comment '接口 id',
    `totalNum`        int      default 0                 not null comment '总调用次数',
    `leftNum`         int      default 0                 not null comment '剩余调用次数',
    `status`          int      default 0                 not null comment '0-正常，1-禁用',
    `createTime`      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';


insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('ajw', 'x6Y', 'www.samuel-lebsack.info', 'GZMXC', '1umaM', 'Kt', 0, 'LX', 305434698);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('79h', 'g052', 'www.kyoko-luettgen.com', 'vEWM', '7byu', '2O5TB', 0, 'v5uNQ', 1156);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('nziR', 'Ygi', 'www.india-schiller.name', 'HGJ', 'rRKb1', 'qG', 0, 'Hbq', 1761);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('zc2o', '7kWz', 'www.ariana-abshire.org', 'VrIW', 've', 'SPn1A', 0, 'tE', 22462);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('2ypN', 'gPDf9', 'www.lashay-leuschke.biz', 'rw', 'ChOh', '6x', 0, 'fjLT', 858491963);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('slTn', '8JH19', 'www.rodger-pfannerstill.info', 'OotR', 'l7', 'urDzq', 0, 'RFo', 484);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('tc2el', '5Ax', 'www.annabelle-friesen.co', 'ud9R', '0HT', 'Hd', 0, 'l3JO', 354007);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('iyNK', '3vbdR', 'www.shantel-moore.biz', 'zsJn', 'Veks', 'VBZyg', 0, 'pe6', 7128699);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('dQxc', 'c3WD', 'www.thurman-hegmann.co', 'Ik', 'AD', '3iMW', 0, 'ncv', 4210927);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('Zkjj', 'gb2WS', 'www.jacque-gerlach.info', 'eW', 'JuV0b', 'hOzG', 0, 'Gkt6', 2283963948);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('yT4m', 'zWo', 'www.casey-carroll.net', 'mTO4S', '27', 'vn9Bl', 0, 'BTN', 94163);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('WXe36', 'xs0E', 'www.mark-crooks.co', 'q4k9T', 'r3h', 'Hj', 0, 't0', 127218741);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('TDh', 'pzu', 'www.helene-stroman.co', '6f', 'yL', '3JzPl', 0, 'gP', 566229697);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('4WPy', 'LeaK', 'www.candance-beatty.org', 'mwo2H', 'mgB', 'JpqI', 0, 'pBj', 72751719);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('hhJ', 'i3nG', 'www.maryalice-keebler.info', 'NeVKa', 'hRv', 'Iw', 0, '8Y', 7221);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('GeOEr', 'X2', 'www.samella-mayert.info', 'Ar', 'pPSv', '5bY1s', 0, 'Muj', 20513923);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('Xv2', '7B', 'www.tim-stark.name', '0w', 'uQEI', 'KKLkA', 0, 'xBDtS', 648);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('DAaz', 'ESHUx', 'www.ardella-pfeffer.org', 'zhp', 'h1da', 'GF', 0, 'XVt5', 94843873);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('bO', 'jvm9', 'www.stacy-lynch.net', '8fasn', '7l', 'Tv', 0, '58eUf', 443520);
insert into wbapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`,
                                    `status`, `method`, `userId`)
values ('qGH', 'yPKk', 'www.mao-yost.name', 'TA', 'kRjR', 'kJtV', 0, '3Zbo', 9358294);