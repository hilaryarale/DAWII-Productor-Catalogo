package com.prestamo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamo.entity.Catalogo;
import com.prestamo.kafka.service.CatalogoEventService;
import com.prestamo.repository.CatalogoRepository;

@Service
public class CatalogoServiceImpl implements CatalogoService {

	@Autowired
	private CatalogoRepository cataRepository;
	
	private final CatalogoEventService cataEventService;	
	
	public CatalogoServiceImpl(CatalogoEventService cataEventService) {
		super();
		this.cataEventService = cataEventService;
	}
	
	
	@Override
	public List<Catalogo> findAll() {
		return cataRepository.findAll();
	}

	@Override
	public Catalogo insertaCatalogo(Catalogo cat) {
		cataEventService.publish(cat); //Publica el evento al kafka
		return cataRepository.save(cat);//Registra en la base de datos
	}

}