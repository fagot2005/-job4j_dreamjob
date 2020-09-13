package model;

import java.time.LocalDateTime;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job", "Easy task", LocalDateTime.now()));
        for (Post ps : store.findAllPosts()
             ) {
            System.out.println(ps.getId() + ps.getName() + ps.getDescription() + ps.getCreated());
        }
    }
}
