package com.example.Marketing.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.Marketing.dto.CampaignRequest;
import com.example.Marketing.dto.CampaignResponse;
import com.example.Marketing.model.Campaign;
import com.example.Marketing.repository.CampaignRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CampaignServiceImpl implements CampaignService {

	@Autowired
	private CampaignRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CampaignResponse> findAll() {
		return repository.findAll()
				.stream()
				.map(c -> modelMapper.map(c, CampaignResponse.class))
				.toList();
	}

	@Override
	public List<CampaignResponse> findAll(int page, int pageSize) {
		PageRequest pageReq = PageRequest.of(page, pageSize);
		return repository.findAll(pageReq)
				.getContent()
				.stream()
				.map(c -> modelMapper.map(c, CampaignResponse.class))
				.toList();
	}

	@Override
	public CampaignResponse findById(Integer id) {
		Campaign campaign = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Campaign not found: " + id));
		return modelMapper.map(campaign, CampaignResponse.class);
	}

	@Override
	public CampaignResponse create(CampaignRequest request) {
		Campaign campaign = modelMapper.map(request, Campaign.class);
		Campaign saved = repository.save(campaign);
		return modelMapper.map(saved, CampaignResponse.class);
	}

	@Override
	public CampaignResponse update(Integer id, CampaignRequest request) {
		Campaign existing = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Campaign not found: " + id));
		modelMapper.map(request, existing); // copia campos desde request
		Campaign saved = repository.save(existing);
		return modelMapper.map(saved, CampaignResponse.class);
	}

	@Override
	public void delete(Integer id) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException("Campaign not found: " + id);
		}
		repository.deleteById(id);
	}

	// --- Consultas especializadas ---
	@Override
	public List<Campaign> findByNameNative(String name) {
		return repository.findByNameNative(name);
	}

	@Override
	public List<Campaign> findByNameJPQL(String name) {
		return repository.findByNameJPQL(name);
	}
}
