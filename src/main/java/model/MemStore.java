package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {
    private static final MemStore INST = new MemStore();
    private static AtomicInteger POST_ID = new AtomicInteger(4);
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(4);

    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Map<Integer, Candidate> candidate = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Easy tasks", LocalDateTime.of(2020, 5, 12, 15, 22)));
        posts.put(2, new Post(2, "Midle Java Job", "Midle tasks", LocalDateTime.of(2020, 4, 6, 12, 14)));
        posts.put(3, new Post(3, "Senior Java Job", "Hard tasks", LocalDateTime.of(2020, 3, 14, 22, 44)));
        candidate.put(1, new Candidate(1, "Ivanov Ivan"));
        candidate.put(2, new Candidate(2, "Petrov Petro"));
        candidate.put(3, new Candidate(3, "Sidorov Sidor"));

    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPost() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidate.values();
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findByPostId(int id) {
        return posts.get(id);
    }

    public void saveCandidate(Candidate candidat) {
        if (candidat.getId() == 0) {
            candidat.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidate.put(candidat.getId(), candidat);
    }

    public Candidate findByCandidateId(int id) {
        return candidate.get(id);
    }
}
