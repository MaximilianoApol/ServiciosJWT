package com.example.Marketing.service;

import java.util.List;
import com.example.Marketing.dto.CampaignRequest;
import com.example.Marketing.dto.CampaignResponse;
import com.example.Marketing.model.Campaign;

public interface CampaignService {
	List<CampaignResponse> findAll();
	List<CampaignResponse> findAll(int page, int pageSize);
	CampaignResponse findById(Integer id);
	CampaignResponse create(CampaignRequest request);
	CampaignResponse update(Integer id, CampaignRequest request);
	void delete(Integer id);

	List<Campaign> findByNameNative(String name);

	List<Campaign> findByNameJPQL(String name);
}
