package chatRMI.common;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private final String m_author;
    private final String m_content;
    private Date m_date;

    public Message(String content, String author) {
        this.m_content = content;
        this.m_author = author;
    }

    public Message(String content, String author, Date date) {
        this(content, author);
        this.m_date = date;
    }

    /**
     * Used to retrieve the message's author
     */
    public String getAuthor() {
        return this.m_author;
    }

    /**
     * Used to retrieve the message's content
     */
    public String getContent() {
        return this.m_content;
    }

    /**
     * Used to retrieve the message's date
     */
    public Date getDate() {
        return this.m_date;
    }

    @Override
    public String toString() {
        return this.getAuthor() + " : " + this.getContent();
    }
}
