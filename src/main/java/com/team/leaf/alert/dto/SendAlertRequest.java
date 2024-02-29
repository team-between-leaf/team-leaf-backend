package com.team.leaf.alert.dto;

import lombok.Getter;

@Getter
public class SendAlertRequest {

    AlertType alertType;

    String message;

}
