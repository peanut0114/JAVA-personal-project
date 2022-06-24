package com.yedam.app.comment;
import java.sql.Date;

import com.yedam.app.common.DAO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment extends DAO{
	private int commentBnum;
	private String commentMid;
	private String commentContent;
	private Date commentDate;
	
	@Override
	public String toString() {
		return "   └ "+commentMid + " : "
				+ commentContent + " ("+commentDate+")";
	}
	
	

}
