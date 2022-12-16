package org.example.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.model.database.PostgresConnectionProvider;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            connection.prepareStatement("""
                    create table if not exists users(
                        id bigserial primary key,
                        permission int default 1,
                        username varchar(16) unique,
                        password bigint,
                        email varchar(30) unique
                    );""").execute();
            connection.prepareStatement("""
                    create table if not exists posts(
                        id bigserial primary key,
                        owner_id bigint,
                        postName varchar(40),
                        post varchar
                    );""").execute();
            connection.prepareStatement("""
                    create table if not exists follower_list(
                        id bigserial primary key,
                        id_of_follower bigint,
                        id_of_author bigint,
                        unique (id_of_follower, id_of_author)
                    );""").execute();
            connection.prepareStatement("""
                    create table if not exists likes(
                        id_of_user bigint not null ,
                        id_of_pos bigint not null,
                        unique (id_of_pos,id_of_user)
                    );
                    """).execute();
            connection.prepareStatement("""
                    create table if not exists messages(
                        id bigserial primary key,
                        name_of_author varchar(16),
                        name_of_receiver varchar(16),
                        message_text varchar
                    );
                    """).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ignored) {
            }
        }
        ServletContextListener.super.contextDestroyed(sce);
    }
}
