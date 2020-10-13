package com.example.orderservice3.notifications.serviceImpl;


import com.example.orderservice3.model.NotificationResponse;
import com.example.orderservice3.service.IMessageSend;
import com.example.orderservice3.service.IProductCostCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class MessageConsumerImpl implements IMessageSend {

    @Autowired
    IProductCostCalculator itemCostCalculator;

    NotificationResponse notificationResponse;

    @JmsListener(destination = "simple-jms-queue")
    public void listener(String[] items){

        double totalCost = itemCostCalculator.calculateItemCost(items);

        notificationResponse = new NotificationResponse( totalCost, "Total Cost ");

        Date deliveryDate = new Date();
        deliveryDate = addDays(deliveryDate, 2);

        System.out.println("Message Received: "+notificationResponse + "Estimated Delivery is ::"+ deliveryDate);

    }

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
