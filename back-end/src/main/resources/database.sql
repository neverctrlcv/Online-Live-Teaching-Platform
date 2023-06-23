create table Admin
(
    username varchar(8)  null,
    password varchar(32) null
)
    charset = utf8;

create table LogT
(
    logInfo varchar(300) null,
    time    timestamp    null
)
    charset = utf8;

create table RTCUsage
(
    rtcUsage int       null,
    useDate  timestamp null
)
    charset = utf8;

create table SysRes
(
    smsCount int null,
    rtcTime  int null
)
    charset = utf8;

create table User
(
    userId       bigint auto_increment
        primary key,
    age          int                    null,
    sex          varchar(3)             null,
    nickname     varchar(60)            null,
    signature    varchar(300)           null,
    phone        varchar(11)            null,
    password     varchar(32)            null,
    avatar       varchar(100)           null,
    jobId        varchar(20)            null,
    registerTime timestamp              null,
    status       varchar(1) default 'Y' null,
    name         varchar(20)            null,
    identity     int        default 1   null
)
    charset = utf8;

create table Classes
(
    classId    bigint auto_increment
        primary key,
    className  varchar(30)            null,
    slogan     varchar(300)           null,
    createTime timestamp              null,
    founder    bigint                 null,
    status     varchar(1) default 'N' null,
    avatar     varchar(100)           null,
    inviteCode varchar(9)             null,
    constraint Classes_ibfk_1
        foreign key (founder) references User (userId)
)
    charset = utf8;

create table ClassMember
(
    classId  bigint    null,
    userId   bigint    null,
    joinTime timestamp null,
    constraint ClassMember_ibfk_1
        foreign key (classId) references Classes (classId),
    constraint ClassMember_ibfk_2
        foreign key (userId) references User (userId)
)
    charset = utf8;

create index classId
    on ClassMember (classId);

create index userId
    on ClassMember (userId);

create table ClassT
(
    id        bigint auto_increment
        primary key,
    startTime timestamp null,
    endTime   timestamp null,
    classId   bigint    null,
    constraint ClassT_ibfk_1
        foreign key (classId) references Classes (classId)
)
    charset = utf8;

create index classId
    on ClassT (classId);

create index founder
    on Classes (founder);

create table StatusT
(
    id      bigint       null,
    logTime timestamp    null,
    logInfo varchar(300) null,
    constraint StatusT_ibfk_1
        foreign key (id) references ClassT (id)
)
    charset = utf8;

create index id
    on StatusT (id);

create table Video
(
    videoId     bigint auto_increment
        primary key,
    link        varchar(100)            null,
    playCount   bigint                 null,
    videoLength varchar(10)            null,
    uploadTime  timestamp              null,
    description varchar(600)           null,
    userId      bigint                 null,
    cover       varchar(100)           null,
    status      varchar(1) default 'N' null,
    isDelete    varchar(1) default 'N' null,
    constraint Video_ibfk_1
        foreign key (userId) references User (userId)
)
    charset = utf8;
create index userId
    on Video (userId);


create database liveTeaching;
use liveTeaching;

select * from User;
select * from SysRes;
select * from LogT;
select * from RTCUsage;

select * from Classes;
select * from RTCUsage where DATE_SUB(CURDATE(), INTERVAL 7 DAY) < date(useDate);
select rtcUsage,date_sub(useDate, interval 7 day) as 'useDate' from RTCUsage order by useDate desc ;


SELECT * FROM RTCUsage where useDate >= '2023-05-12' and useDate<= '2023-05-19';
SELECT logInfo,time FROM LogT where time between '2023-05-12' and '2023-05-19';

select userId, avatar, nickname, signature, identity, registerTime
from User
where status = 'N'
order by registerTime desc
limit 1,15;
select * from Classes;
select * from ClassT;
select * from Video;
select videoId, playCount, videoLength, uploadTime, description, userId, cover
from Video
where isDelete = 'N'
  and status = 'Y'
  and description like CONCAT('%', '物理', '%') limit 0,15;
