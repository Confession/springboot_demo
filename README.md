## springboot_demo

## author cjl

```sql
create table user
(
    ID           int auto_increment
    primary key,
    ACCOUNT_ID   varchar(100) null,
    NAME         varchar(50)  null,
    TOKEN        char(36)     null,
    GMT_CREATE   bigint       null,
    GMT_MODIFIED bigint       null
);
create table question
(
    id            int auto_increment
        primary key,
    title         varchar(50)   null,
    description   text          null,
    gmt_create    bigint        null,
    gmt_modified  bigint        null,
    comment_count int default 0 null,
    view_count    int default 0 null,
    like_count    int default 0 null,
    tag           varchar(256)  null,
    creator       int           null
);


```

