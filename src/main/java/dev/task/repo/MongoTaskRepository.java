package dev.task.repo;

import com.mongodb.client.*;
import com.mongodb.client.model.ReplaceOptions;

import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import dev.task.models.Task;
import dev.task.models.Status;

import java.util.*;

public class MongoTaskRepository implements TaskRepository {
    private final MongoCollection<Document> collection;

    public MongoTaskRepository(String uri, String dbName) {
        MongoClient client = MongoClients.create(uri);
        MongoDatabase database = client.getDatabase(dbName);
        this.collection = database.getCollection("tasks");
    }

        @Override
        public void save(Task task) {
            Document doc = new Document("_id", task.id.toString())
                    .append("description", task.description)
                    .append("status", task.status.toString());
                    
            collection.replaceOne(eq("_id", task.id.toString()), doc, new ReplaceOptions().upsert(true));
        }


    @Override
    public void update(Task task) {
        collection.updateOne(eq("_id", task.id.toString()),
                new Document("$set", new Document("description", task.description)
                        .append("status", task.status.toString())));
    }

    @Override
    public void delete(UUID id) {
        collection.deleteOne(eq("_id", id.toString()));
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        for (Document doc : collection.find()) {
            Task t = new Task(doc.getString("description"));
            t.id = UUID.fromString(doc.getString("_id"));
            t.status = Status.valueOf(doc.getString("status"));
            // parse createdAt if needed
            tasks.add(t);
        }
        return tasks;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        Document doc = collection.find(eq("_id", id.toString())).first();
        if (doc == null) return Optional.empty();

        Task t = new Task(doc.getString("description"));
        t.id = UUID.fromString(doc.getString("_id"));
        t.status = Status.valueOf(doc.getString("status"));

        return Optional.of(t);
    }



}
