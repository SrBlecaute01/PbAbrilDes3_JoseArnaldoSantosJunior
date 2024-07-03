package uol.compass.payments.service;

import uol.compass.payments.dto.request.PointsRequest;

public interface MessagingService {

    void sendPointsMessage(PointsRequest request);

}