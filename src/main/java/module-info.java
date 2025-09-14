import org.jspecify.annotations.NullMarked;

@NullMarked
module maskun.aimanagedsrs {
    requires com.fasterxml.uuid;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires org.jspecify;
    requires reactor.core;
    requires spring.ai.client.chat;
    requires spring.ai.model;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.data.commons;
    requires spring.web;
    requires spring.webmvc;
    requires validation.api;
    requires maskun.aimanagedsrs;
}