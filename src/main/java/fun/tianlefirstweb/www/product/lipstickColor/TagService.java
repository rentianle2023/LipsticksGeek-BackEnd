package fun.tianlefirstweb.www.product.lipstickColor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public void saveTag(Tag tag){
        tagRepository.save(tag);
    }
}
