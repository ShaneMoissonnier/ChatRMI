package chatRMI.common;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Message implements Serializable {

    public enum MessageType {
        APPLICATION,
        ERROR,
        USER
    }

    private final String m_author;
    private final String m_content;
    private Date m_date;
    private MessageType m_messageType;

    public Message(String content, String author) {
        this.m_content = content;
        this.m_author = author;
        this.m_messageType = MessageType.USER;
    }

    public Message(String content, String author, Date date) {
        this(content, author);
        this.m_date = date;
    }

    public Message(String content, String author, MessageType messageType) {
        this(content, author);
        this.m_messageType = messageType;
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
        String message;

        switch (this.m_messageType){
            case APPLICATION -> message = "<b style=\"color:#bce6bf;\">[ " + this.getAuthor() + " ]</b> : " + this.getContent();
            case ERROR -> message = "<b style=\"color:red;\">" + this.getAuthor() + "</b> : " + this.getContent();
            default -> message = "<b>" + this.getAuthor() + "</b> : " + this.getContent();
        }
        return message;
    }
}
