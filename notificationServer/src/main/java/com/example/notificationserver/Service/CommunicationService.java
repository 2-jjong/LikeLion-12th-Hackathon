package com.example.notificationserver.Service;

import java.net.URISyntaxException;

public interface CommunicationService {
    public String fetchAndSavePaymentInfo(String email) throws URISyntaxException;
}
