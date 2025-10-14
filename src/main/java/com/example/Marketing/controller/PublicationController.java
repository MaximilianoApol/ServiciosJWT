package com.example.Marketing.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.Marketing.dto.PublicationDTO;
import com.example.Marketing.model.Publication;
import com.example.Marketing.service.PublicationService;

@RestController
@RequestMapping("publications")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@Tag(name = "Publications", description = "Provides methods for managing publications")
public class PublicationController {

	@Autowired
	private PublicationService service;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Get all publications")
	@ApiResponse(responseCode = "200", description = "Found publications",
			content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Publication.class)))})
	@GetMapping
	public List<Publication> getAll() {
		return service.getAll();
	}

	@Operation(summary = "Get a publication by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publication found",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Publication.class))}),
			@ApiResponse(responseCode = "404", description = "Publication not found", content = @Content)
	})
	@GetMapping("{idPublication}")
	public ResponseEntity<Publication> getById(@PathVariable Integer idPublication) {
		Publication publication = service.getById(idPublication);
		return publication != null ? ResponseEntity.ok(publication) : ResponseEntity.notFound().build();
	}

	@Operation(summary = "Add a publication")
	@ApiResponse(responseCode = "201", description = "Publication created",
			content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PublicationDTO.class))})
	@PostMapping
	public ResponseEntity<PublicationDTO> add(@RequestBody PublicationDTO dto) {
		Publication saved = service.save(convertToEntity(dto));
		return new ResponseEntity<>(convertToDTO(saved), HttpStatus.CREATED);
	}

	// ---- Consultas Especializadas ----

	@GetMapping("high-risk-negative-influencers")
	public List<Publication> highRiskNegativeInfluencers() {
		return service.getHighRiskNegativeInfluencers();
	}

	@GetMapping("negative-comments/{campaignId}")
	public Long negativeComments(@PathVariable Integer campaignId) {
		OffsetDateTime startTime = OffsetDateTime.now().minusHours(1);
		return service.countNegativePublicationsByCampaign(campaignId, startTime);
	}

	@GetMapping("potential-viral")
	public List<Publication> potentialViral() {
		return service.getPotentialViral();
	}

	@GetMapping("cross-mentions")
	public List<Publication> crossMentions(@RequestParam String brand,
	                                       @RequestParam String competitorA,
	                                       @RequestParam String competitorB) {
		return service.getCrossMentions(brand, competitorA, competitorB);
	}

	@GetMapping("spam-low-followers")
	public List<Publication> spamLowFollowers() {
		return service.getSpamLowFollowers();
	}

	@GetMapping("support-tickets")
	public List<Publication> supportTickets() {
		return service.getSupportTickets();
	}

	@GetMapping("manual-review-needed")
	public List<Publication> manualReviewNeeded() {
		return service.getManualReviewNeeded();
	}

	private PublicationDTO convertToDTO(Publication publication) {
		return modelMapper.map(publication, PublicationDTO.class);
	}

	private Publication convertToEntity(PublicationDTO dto) {
		return modelMapper.map(dto, Publication.class);
	}
}
