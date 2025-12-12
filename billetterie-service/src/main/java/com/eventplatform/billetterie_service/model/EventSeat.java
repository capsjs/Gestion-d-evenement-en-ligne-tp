package com.eventplatform.billetterie_service.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class EventSeat {

    @Id
    private UUID eventId;

    private int totalSeats;
    private int remainingSeats;

}
