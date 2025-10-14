package com.example.Marketing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Marketing.model.TextAnalysis;
import com.example.Marketing.repository.TextAnalysisRepository;

@Service
@Transactional
public class TextAnalysisService {

	@Autowired
	private TextAnalysisRepository repository;

	public List<TextAnalysis> getAll() {
		return repository.findAll();
	}

	public TextAnalysis getById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	public TextAnalysis save(TextAnalysis analysis) {
		return repository.save(analysis);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	// Consultas especializadas
	public List<TextAnalysis> getPotentialViralContent() {
		return repository.findPotentialViralContent();
	}

	public List<TextAnalysis> getCrossMention(String brand, String competitorA, String competitorB) {
		return repository.findCrossMention(brand, competitorA, competitorB);
	}

	public List<TextAnalysis> getSupportTickets() {
		return repository.findSupportTickets("ayuda", "no funciona", "problema", "dónde está mi pedido");
	}

	public List<TextAnalysis> getMixedOrQuestionPosts() {
		return repository.findMixedOrQuestionPosts("?");
	}
}
