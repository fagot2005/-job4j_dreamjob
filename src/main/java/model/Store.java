package model;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();
    Collection<Candidate> findAllCandidate();
    void save(Post post);
    Post findById(int id);

}
