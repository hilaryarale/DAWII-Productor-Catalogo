package com.prestamo.kafka.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.prestamo.entity.Pais;
import com.prestamo.kafka.config.Event;
import com.prestamo.kafka.config.EventType;
import com.prestamo.kafka.entity.PaisCreateEvent;

@Component
public class PaisEventService {

	
	@Autowired
	private KafkaTemplate<String, Event<?>> producer;
	
	@Value("${topic.customer.name:topic-pais}")
	private String topicPais;
	
	public void publish(Pais pais) {
		
		PaisCreateEvent created = new PaisCreateEvent();
		created.setData(pais);
		created.setId(UUID.randomUUID().toString());
		created.setType(EventType.CREATED);
		created.setDate(new Date());
		
		this.producer.send(topicPais, created);
	}
}