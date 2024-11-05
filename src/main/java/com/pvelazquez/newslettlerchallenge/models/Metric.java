package com.pvelazquez.newslettlerchallenge.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metric {
    private long mailsSent;
    private int mailsScheduled;
    private long mailsCancelled;
    private long totalRecipients;
    private long recipientsSubscribed;
    private long recipientsUnsubscribed;

    public Metric(Metric metricNewsletter, Metric metricRecipient){
        this.mailsSent = metricNewsletter.getMailsSent();
        this.mailsScheduled = metricNewsletter.getMailsScheduled();
        this.mailsCancelled = metricNewsletter.getMailsCancelled();
        this.totalRecipients = metricRecipient.getTotalRecipients();
        this.recipientsSubscribed = metricRecipient.getRecipientsSubscribed();
        this.recipientsUnsubscribed = metricRecipient.getRecipientsUnsubscribed();
    }
}
