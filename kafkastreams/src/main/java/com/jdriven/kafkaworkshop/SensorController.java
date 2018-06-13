package com.jdriven.kafkaworkshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SensorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorController.class);
    private final KafkaTemplate<String, SensorData> kafkaTemplate;

    @Autowired
    public SensorController(final KafkaTemplate<String, SensorData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/sensor")
    public String greetingForm(Model model) {
        model.addAttribute("sensorData", new SensorData());
        return "sensor";
    }

    @PostMapping("/sensor")
    public String sensorSubmit(@ModelAttribute SensorData sensorData) {
        LOGGER.info("Submitting data: [{}]", sensorData);
        kafkaTemplate.send(TopicNames.RECEIVED_SENSOR_DATA, sensorData.getId(), sensorData)
                .addCallback(
                        result -> LOGGER.info("Data submitted: [{}]", result.getProducerRecord()),
                        e -> LOGGER.error("Error submitting data: [{}]", e.getMessage(), e));

        return "sensor";
    }

}
