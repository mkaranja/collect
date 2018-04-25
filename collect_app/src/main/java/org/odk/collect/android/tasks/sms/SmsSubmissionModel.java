package org.odk.collect.android.tasks.sms;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

public class SmsSubmissionModel {
    private String instanceId;
    private List<Message> messages;
    private Date dateAdded;

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Returns the next message to be processed. If there's no other message
     * that simply means all messages have been sent.
     * @return next message to be sent.
     */
    public Message getNextUnsentMessage() {
        return Observable.fromIterable(messages)
                .filter(message -> !message.isSent())
                .sorted((message, otherMessage) -> message.getPart() - otherMessage.getPart())
                .firstElement()
                .blockingGet();
    }
}