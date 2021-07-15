package com.exadel.sandbox.team5.service;

import com.amazonaws.services.sns.model.PublishResult;
import com.exadel.sandbox.team5.dto.DiscountDto;
import com.exadel.sandbox.team5.util.Message;

public interface SnsService {

    PublishResult sendToAllUsers(Message message);

    void sendToSubscribers(DiscountDto discountDto);
}
