package com.objnetwork;

import com.objnetwork.server.mqtt.MQTTServer;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.PersistenceContext;

@SpringBootApplication
@ServletComponentScan
@ComponentScan("com.objnetwork.server")
@EnableJpaRepositories("com.objnetwork.server.dao")
@EntityScan("com.objnetwork.server.beans")
public class ObjnetworkApplication {

    public static void main(String[] args) {
        try {
            MQTTServer.init();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        SpringApplication.run(ObjnetworkApplication.class, args);
    }
}
