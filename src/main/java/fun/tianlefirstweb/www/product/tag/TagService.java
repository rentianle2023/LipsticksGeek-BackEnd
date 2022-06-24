package fun.tianlefirstweb.www.product.tag;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
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

    public Tag findTagWithColorsByTitle(TagTitle title) {
        return tagRepository.findTagWithColorsByTitle(title);
    }


}
