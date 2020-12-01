package nl.tudelft.sem.group20.contentserver.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.tudelft.sem.group20.contentserver.serialization.LocalDateTimeDeserializer;
import nl.tudelft.sem.group20.contentserver.serialization.LocalDateTimeSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "thread")
public class BoardThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String threadTitle;    //title of thread

    private String statement;      //Main question or statement of thread

    private String threadCreator;  //name of thread creator

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created; //when was it creates

    private boolean locked;        //locked thread or not

    /**
     * Empty constructor of Board Thread
     *
     */

    public BoardThread() {

    }

    /**
     * Non-empty constructor of BoardThread
     *
     * @param id id of item
     * @param threadTitle title of thread
     * @param statement general statment of thread
     * @param threadCreator person who created thread
     * @param locked locked or not
     */
    public BoardThread(Long id, String threadTitle, String statement, String threadCreator, LocalDateTime created,
                       boolean locked) {
        this.id = id;
        this.threadTitle = threadTitle;
        this.statement = statement;
        this.threadCreator = threadCreator;
        this.created = created;
        this.locked = locked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String name) {
        this.threadTitle = name;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getThreadCreator() {
        return threadCreator;
    }

    public void setThreadCreator(String threadCreator) {
        this.threadCreator = threadCreator;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardThread that = (BoardThread) o;
        return id.equals(that.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Thread{"
                + "id=" + id
                + ", threadTitle='" + threadTitle + '\''
                + ", statement='" + statement + '\''
                + ", threadCreator='" + threadCreator + '\''
                + ", created=" + created
                + ", locked=" + locked
                + '}';
    }

}