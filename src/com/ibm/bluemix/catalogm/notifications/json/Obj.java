package com.ibm.bluemix.catalogm.notifications.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

@JsonIgnoreProperties({"creation","url","alerts","_conflicts","id"})
public class Obj {
	 private String _rev;

	    //private Creation creation;

	    private String category;

	    private String text;

	    private String title;

	    private String archived;

	    private String _id;

	    private String subCategory;

	    private LastUpdate lastUpdate;

	    private EventTime eventTime;

	    private String type;

	    private RegionsAffected[] regionsAffected;

	    public String get_rev ()
	    {
	        return _rev;
	    }

	    public void set_rev (String _rev)
	    {
	        this._rev = _rev;
	    }

//	    public Creation getCreation ()
//	    {
//	        return creation;
//	    }
//
//	    public void setCreation (Creation creation)
//	    {
//	        this.creation = creation;
//	    }

	    public String getCategory ()
	    {
	        return category;
	    }

	    public void setCategory (String category)
	    {
	        this.category = category;
	    }

	    public String getText ()
	    {
	        return text;
	    }

	    public void setText (String text)
	    {
	        this.text = text;
	    }

	    public String getTitle ()
	    {
	        return title;
	    }

	    public void setTitle (String title)
	    {
	        this.title = title;
	    }

	    public String getArchived ()
	    {
	        return archived;
	    }

	    public void setArchived (String archived)
	    {
	        this.archived = archived;
	    }

	    public String get_id ()
	    {
	        return _id;
	    }

	    public void set_id (String _id)
	    {
	        this._id = _id;
	    }

	    public String getSubCategory ()
	    {
	        return subCategory;
	    }

	    public void setSubCategory (String subCategory)
	    {
	        this.subCategory = subCategory;
	    }

	    public LastUpdate getLastUpdate ()
	    {
	        return lastUpdate;
	    }

	    public void setLastUpdate (LastUpdate lastUpdate)
	    {
	        this.lastUpdate = lastUpdate;
	    }

	    public EventTime getEventTime ()
	    {
	        return eventTime;
	    }

	    public void setEventTime (EventTime eventTime)
	    {
	        this.eventTime = eventTime;
	    }

	    public String getType ()
	    {
	        return type;
	    }

	    public void setType (String type)
	    {
	        this.type = type;
	    }

	    public RegionsAffected[] getRegionsAffected ()
	    {
	        return regionsAffected;
	    }

	    public void setRegionsAffected (RegionsAffected[] regionsAffected)
	    {
	        this.regionsAffected = regionsAffected;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [_rev = "+_rev+", creation = , category = "+category+", text = "+text+", title = "+title+", archived = "+archived+", _id = "+_id+", subCategory = "+subCategory+", lastUpdate = , eventTime = "+eventTime+", type = "+type+", regionsAffected = "+regionsAffected+"]";
	    }
}
