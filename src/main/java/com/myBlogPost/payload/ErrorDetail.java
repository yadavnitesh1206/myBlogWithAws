package com.myBlogPost.payload;


import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorDetail{
private Date timeStamp;
private String message;
private String details;

    public ErrorDetail(Date timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getDetails() {
        return details;
    }

}
