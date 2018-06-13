package com.jdriven.kafkaworkshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SensorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorListener.class);

    @KafkaListener(topics = TopicNames.RECEIVED_SENSOR_DATA)
    public void listen(final SensorData sensorData) {
        LOGGER.info("Received data: [{}]", sensorData);
    }
}
