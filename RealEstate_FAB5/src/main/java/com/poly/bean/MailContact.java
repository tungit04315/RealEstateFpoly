package com.poly.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailContact {
	String from;
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
	String[] attachments;
	String fullNameTo;
	String fullNameFrom;
	String phone;

	public MailContact(String to, String subject, String body, String fullNameTo, String fullNameFrom, String phone) {
		this.from = "Real Estate <fab5@gmail.com.vn>";
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.fullNameTo = fullNameTo;
		this.fullNameFrom = fullNameFrom;
		this.phone = phone;
	}
}
