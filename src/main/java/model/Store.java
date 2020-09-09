package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final Store INST = new Store();

    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Map<Integer, Candidate> candidate = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "Easy tasks", LocalDate.of(2020, 5, 12)));
        posts.put(2, new Post(2, "Midle Java Job", "Midle tasks", LocalDate.of(2020, 4, 6)));
        posts.put(3, new Post(3, "Senior Java Job", "Hard tasks", LocalDate.of(2020, 3, 14)));
        candidate.put(1, new Candidate(1, "Ivanov Ivan"));
        candidate.put(2, new Candidate(2, "Petrov Petro"));
        candidate.put(3, new Candidate(3, "Sidorov Sidor"));

            }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPost() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidate.values();
    }
}
