package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
public class RsEvent implements Serializable {
    @NotNull
    private String eventName;
    @NotNull
    private String keyWord;
    @NotNull
    private User user;


    public String getEventName() {
        return eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }
}
