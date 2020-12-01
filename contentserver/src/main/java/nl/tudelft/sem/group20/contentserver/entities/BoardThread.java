package nl.tudelft.sem.group20.contentserver.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "thread")
public class BoardThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String threadTitle;    //title of thread

    private String statement;      //Main question or statement of thread

    private String threadCreator;  //name of thread creator

    //private LocalDateTime created; //when was it creates

    private boolean locked;        //locked thread or not

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    /*public LocalDateTime getCreated() {
        return created;
    }*/

    /*public void setCreated(LocalDateTime created) {
        this.created = created;
    }*/

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "Thread{"
                + "id=" + id
                + ", threadTitle='" + threadTitle + '\''
                + ", statement='" + statement + '\''
                + ", threadCreator='" + threadCreator + '\''
                //+ ", created=" + created
                + ", locked=" + locked
                + '}';
    }

}