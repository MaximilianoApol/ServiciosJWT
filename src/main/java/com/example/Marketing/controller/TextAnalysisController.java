package com.example.Marketing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Marketing.model.TextAnalysis;
import com.example.Marketing.service.TextAnalysisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("text-analysis")
@CrossOrigin(origins = "*")
@Tag(name = "TextAnalysis", description = "Manage text analysis and specialized queries")
public class TextAnalysisController {

	@Autowired
	private TextAnalysisService service;

	@GetMapping
	public List<TextAnalysis> getAll() {
		return service.getAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<TextAnalysis> getById(@PathVariable Integer id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<TextAnalysis> add(@RequestBody TextAnalysis analysis) {
		return new ResponseEntity<>(service.save(analysis), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	// Regla 5
	@GetMapping("alerts/potential-viral")
	public ResponseEntity<List<TextAnalysis>> potentialViralContent() {
		return new ResponseEntity<>(service.getPotentialViralContent(), HttpStatus.OK);
	}

	// Regla 7
	@GetMapping("alerts/cross-mention")
	public ResponseEntity<List<TextAnalysis>> crossMention(@RequestParam String brand,
	                                                       @RequestParam String competitorA,
	                                                       @RequestParam String competitorB) {
		return new ResponseEntity<>(service.getCrossMention(brand, competitorA, competitorB), HttpStatus.OK);
	}

	// Regla 10
	@GetMapping("alerts/support-tickets")
	public ResponseEntity<List<TextAnalysis>> supportTickets() {
		return new ResponseEntity<>(service.getSupportTickets(), HttpStatus.OK);
	}

	// Regla 11
	@GetMapping("alerts/mixed-or-question")
	public ResponseEntity<List<TextAnalysis>> mixedOrQuestionPosts() {
		return new ResponseEntity<>(service.getMixedOrQuestionPosts(), HttpStatus.OK);
	}
}
