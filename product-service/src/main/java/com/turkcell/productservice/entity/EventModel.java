package com.turkcell.productservice.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="events")
public class EventModel {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, updatable = false)
    private OffsetDateTime timestamp;
    @Column(nullable = false, updatable = false)
    private UUID aggregateId;
    @Column(nullable = false, updatable = false)
    private String eventType; // ProductCreated,ProductPriceChanged

    @Lob // Large Object -> CLOB,BLOB
    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String eventData; // JSON


    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OffsetDateTime timestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UUID aggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String eventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String eventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }
}
