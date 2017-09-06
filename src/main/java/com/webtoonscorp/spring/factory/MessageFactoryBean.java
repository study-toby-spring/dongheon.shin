package com.webtoonscorp.spring.factory;

import com.webtoonscorp.spring.domain.Message;
import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean<Message> {

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public Message getObject() throws Exception {
        return Message.newMessage(text);
    }

    public Class<?> getObjectType() {
        return Message.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
