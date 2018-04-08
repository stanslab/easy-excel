package com.stanslab.excel.meta;

import com.stanslab.excel.annotation.Title;

public class ExcelTitle extends ExcelStyle {
	
	private String text;
	
	public ExcelTitle() {
		
	}

	public ExcelTitle(Title title) {
		this.text = title.value();
		this.background = title.background();
		this.bold = title.bold();
		this.fontName = title.fontName();
		this.fontSize = title.fontSize();
		this.foreground = title.foreground();
		this.height = title.height();
		this.italic = title.italic();
		this.underline = title.underline();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}