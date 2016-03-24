/**
 * 
 */
package com.neu.msd.jpa.entities;

import java.util.Map;

/**
 * @author Harsh
 *
 */
public class AdminActivityAnswer {

	private Activity activity;
	private Map<Answer, Boolean> answers;
	
	public AdminActivityAnswer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * @return the answers
	 */
	public Map<Answer, Boolean> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(Map<Answer, Boolean> answers) {
		this.answers = answers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activity == null) ? 0 : activity.hashCode());
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminActivityAnswer other = (AdminActivityAnswer) obj;
		if (activity == null) {
			if (other.activity != null)
				return false;
		} else if (!activity.equals(other.activity))
			return false;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdminActivityAnswer [activity=" + activity + ", answers=" + answers + "]";
	}
}