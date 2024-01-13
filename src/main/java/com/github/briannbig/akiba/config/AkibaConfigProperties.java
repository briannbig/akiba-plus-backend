package com.github.briannbig.akiba.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AkibaConfigProperties(String defaultAdminUsername, String defaultAdminTelephone, String defaultAdminEmail,
                                    String defaultAdminPassword, String defaultPaymentMethod) {

}
