package com.onestep.business_management.Service.OriginService;

import com.onestep.business_management.DTO.OriginRequest;
import com.onestep.business_management.DTO.OriginResponse;
import com.onestep.business_management.Entity.Origin;
import com.onestep.business_management.Repository.OriginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OriginService {

    @Autowired
    private OriginRepository originRepository;

    public OriginResponse createOrigin(OriginRequest originRequest) {
        Origin origin = OriginMapper.INSTANCE.toEntity(originRequest);
        Origin savedOrigin = originRepository.save(origin);
        return OriginMapper.INSTANCE.toResponse(savedOrigin);
    }

    public List<OriginResponse> getAllOrigins() {
        return originRepository.findAll().stream()
                .map(OriginMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public OriginResponse getOriginById(Integer originId) {
        return originRepository.findById(originId)
                .map(OriginMapper.INSTANCE::toResponse)
                .orElse(null);
    }

    public OriginResponse updateOrigin(Integer originId, OriginRequest originRequest) {
        Origin origin = originRepository.findById(originId).orElse(null);
        if (origin != null) {
            origin.setOriginName(originRequest.getOriginName());
            Origin updatedOrigin = originRepository.save(origin);
            return OriginMapper.INSTANCE.toResponse(updatedOrigin);
        }
        return null;
    }

    public boolean deleteOrigin(Integer originId) {
        if (originRepository.existsById(originId)) {
            originRepository.deleteById(originId);
            return true;
        }
        return false;
    }
}
