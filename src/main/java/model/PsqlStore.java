package model;

import org.apache.commons.dbcp2.BasicDataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store{

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore()  {
        Properties cfg = new Properties();
        try (final InputStream resurseAsStream = PsqlStore.class.getClassLoader().getResourceAsStream("db.properties")) {
            cfg.load(resurseAsStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name"), it.getString("description"), it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return null;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from post p where p.id = ?;")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Post p = new Post(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getTimestamp("created").toLocalDateTime());
                    p.setId(rs.getInt("id"));
                    post = p;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement pr = cn.prepareStatement("INSERT INTO post (name, description, created) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            pr.setString(1, post.getName());
            pr.setString(2, post.getDescription());
            pr.setTimestamp(3, Timestamp.valueOf((post.getCreated())));
            pr.execute();
            try (ResultSet id = pr.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    private Post update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement pr = cn.prepareStatement("UPDATE post SET name = ?, description = ?, created = ? WHERE id = ?", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            pr.setString(1, post.getName());
            pr.setString(2, post.getDescription());
            pr.setTimestamp(3, Timestamp.valueOf((post.getCreated())));
            pr.setInt(4, post.getId());
            pr.executeUpdate();
//            try (ResultSet id = pr.getGeneratedKeys()) {
//                if (id.next()) {
//                    post.setId(id.getInt(1));
//                }
//            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }
}
