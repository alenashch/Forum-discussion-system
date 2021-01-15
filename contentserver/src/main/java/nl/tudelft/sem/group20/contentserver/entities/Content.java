package nl.tudelft.sem.group20.contentserver.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import nl.tudelft.sem.group20.contentserver.serialization.LocalDateTimeDeserializer;
import nl.tudelft.sem.group20.contentserver.serialization.LocalDateTimeSerializer;

@MappedSuperclass
public abstract class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String body;      //Main question or statement of thread

    private String creatorName;  //name of thread creator

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdTime; //when was it creates

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private transient LocalDateTime editedTime; //when was it creates


    /**
     * Empty constructor for all inheriting classes
     * of Content.
     */
    public Content() {

    }

    /**
     * Non-empty constructor for all inheriting classes
     * of Content.
     *
     * @param id id
     * @param body body message
     * @param creatorName name of creator
     * @param createdTime created time
     */
    public Content(long id, String body, String creatorName, LocalDateTime createdTime) {
        this.id = id;
        this.body = body;
        this.creatorName = creatorName;
        this.createdTime = createdTime;
        this.editedTime = createdTime;
    }

    /**
     * Non-empty constructor for all inheriting classes
     * of Content.
     *
     * @param body body message
     * @param creatorName name of creator
     * @param createdTime created time
     */
    public Content(String body, String creatorName, LocalDateTime createdTime) {
        this.body = body;
        this.creatorName = creatorName;
        this.createdTime = createdTime;
        this.editedTime  = createdTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getEditedTime() {
        return editedTime;
    }

    public void setEditedTime(LocalDateTime editedTime) {
        this.editedTime = editedTime;
    }

    @Override
    public String toString() {
        return "Content{"
                + "id=" + id
                + ", body='" + body + '\''
                + ", creatorName='" + creatorName + '\''
                + ", createdTime=" + createdTime
                + ", editedTime=" + editedTime
                + '}';
    }
}
