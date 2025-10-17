package com.example.Marketing.service;

import java.util.List;

import com.example.Marketing.dto.MultimediaResourceRequest;
import com.example.Marketing.dto.MultimediaResourceResponse;

public interface MultimediaResourceService {

	List<MultimediaResourceResponse> findAll();

	List<MultimediaResourceResponse> findAll(int page, int pageSize);

	MultimediaResourceResponse findById(Integer resourceId);

	MultimediaResourceResponse create(MultimediaResourceRequest request);

	MultimediaResourceResponse update(Integer resourceId, MultimediaResourceRequest request);

	void delete(Integer resourceId);

	List<MultimediaResourceResponse> findByPublicationId(Integer publicationId);

	List<MultimediaResourceResponse> findByResourceType(String type);

	List<MultimediaResourceResponse> findWithoutVisualAnalysis();

	List<MultimediaResourceResponse> findWithVisualAnalysis();
}
