package com.example.pensionat.Models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import java.time.LocalDate;

//TODO ändra om koden om det behövs
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomOpend.class, name = "dog"),
        @JsonSubTypes.Type(value = RoomClosed.class, name = "dog"),
        @JsonSubTypes.Type(value = RoomCleaningStarted.class, name = "dog"),
        @JsonSubTypes.Type(value = RoomCleaningFinished.class, name = "dog"),
})public class EventBase {
    int RoomNo;
    public LocalDate TimeStamp;

}
