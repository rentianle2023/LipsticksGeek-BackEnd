package fun.tianlefirstweb.www.search.lipstickColor;

import org.springframework.stereotype.Service;

@Service
public class LipstickColorSearchService {

    private final LipstickColorSearchRepository lipstickColorSearchRepository;

    public LipstickColorSearchService(LipstickColorSearchRepository lipstickColorSearchRepository) {
        this.lipstickColorSearchRepository = lipstickColorSearchRepository;
    }

    public void save(LipstickColorSearch lipstickColorSearch){
        lipstickColorSearchRepository.save(lipstickColorSearch);
    }
}
