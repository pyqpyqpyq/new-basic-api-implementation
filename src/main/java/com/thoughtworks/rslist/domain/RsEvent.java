package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private int userId;


    public String getEventName() {
        return eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    @JsonIgnore
    public int getUserId() {
        return userId;
    }
    @JsonProperty
    public void setUserId(int userId){
        this.userId = userId;
    }

}
