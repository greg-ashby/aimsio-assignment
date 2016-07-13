package com.gregashby.aimsio.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.charts.model.style.SolidColor;

public class Colours {

	private static List<String> colours = new ArrayList<String>();

	static {
		colours.add("Blue");
		colours.add("Red");
		colours.add("Green");
		colours.add("Orange");
		colours.add("Yellow");
		colours.add("Black");
		colours.add("Purple");
		colours.add("Brown");
		colours.add("Grey");
		colours.add("Pink");
	}

	public static List<String> getColours() {
		return colours;
	}

	public static SolidColor getClass(String colour) {
		colour = colour == null ? "" : colour;
		switch (colour) {
		case "Blue":
			return SolidColor.BLUE;
		case "Red":
			return SolidColor.RED;
		case "Green":
			return SolidColor.GREEN;
		case "Orange":
			return SolidColor.ORANGE;
		case "Yellow":
			return SolidColor.YELLOW;
		case "Black":
			return SolidColor.BLACK;
		case "Purple":
			return SolidColor.PURPLE;
		case "Grey":
			return SolidColor.GREY;
		case "Brown":
			return SolidColor.BROWN;
		case "Pink":
			return SolidColor.PINK;
		default:
			return SolidColor.BEIGE;
		}
	}

}
