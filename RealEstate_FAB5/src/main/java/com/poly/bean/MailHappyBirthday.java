package com.poly.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailHappyBirthday {
	String from;
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String[] attachments;
	String fullName;
	String old;
	
	public MailHappyBirthday(String to, String subject, String fullName, String old) {
		this.from = "Real Estate <fab5@gmail.com.vn>";
		this.to = to;
		this.subject = subject;
		this.fullName = fullName;
		this.old = old;
	}
}
