package com.dapi;

import java.util.ArrayList;
import java.util.List;

public class LexicalLevel<A, B> {
    private String levelName;
    private List<Object> content;
    public boolean isEnding;
    
    public LexicalLevel(String name, List<Object> contains) {
    	super();
    	this.levelName = name;
    	this.content = contains;
    	this.isEnding = false;
    }

    public int hashCode() {
    	int hashFirst = levelName != null ? levelName.hashCode() : 0;
    	int hashSecond = content != null ? content.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    @SuppressWarnings("unchecked")
	public boolean equals(Object other) {
    	if (other instanceof LexicalLevel) {
    		LexicalLevel<String,List<String>> otherPair = (LexicalLevel<String,List<String>>) other;
    		return 
    		((  this.levelName == otherPair.levelName ||
    			( this.levelName != null && otherPair.levelName != null &&
    			  this.levelName.equals(otherPair.levelName))) &&
    		 (	this.content == otherPair.content ||
    			( this.content != null && otherPair.content != null &&
    			  this.content.equals(otherPair.content))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + levelName + ", " + content.toString() + ")"; 
    }

    public String getLevelName() {
    	return levelName;
    }

    public void setLevelName(String name) {
    	this.levelName = name;
    }

    public List<Object> getContent() {
    	return this.content;
    }

    public void setSecond(List<Object> contains) {
    	this.content = contains;
    }
    
    public Object addContent(Object child) {
    	this.content.add(child);
    	return child;
    }

	@SuppressWarnings("unchecked")
	public List<Object> search(String name) {
		
		if (this.isEnding) return null;
		for (int i = 0; i < this.content.size(); i++) {
			LexicalLevel<String, List<Object>> child = (LexicalLevel<String, List<Object>>) this.content.get(i);
			if (child.levelName.equals(name)){
				return child.getAllLeaves();
			}
			else {
				if ( child.search(name) != null) return child.search(name);
			}
		}
		return null;
	}

	private List<Object> getAllLeaves() {
		ArrayList<Object> result = new ArrayList<Object>();
		
		if (this.isEnding) {
			return this.content;
		}
		else {
			for (int i = 0; i < this.content.size(); i++) {
				@SuppressWarnings("unchecked")
				LexicalLevel<String, List<Object>> child = (LexicalLevel<String, List<Object>>) this.content.get(i);
				result.addAll(child.getAllLeaves());
			}

			return result;
		}
	}
}

