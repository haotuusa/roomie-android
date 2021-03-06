package com.rip.roomies.models;

import java.sql.Date;

/**
 * This object represents a log of a completed task.
 */
public abstract class TaskLog {
	private int id;
	private String name;
	private String description;
	private int groupId;
	private Date completion;
	private int taskId;
	private User assignee;

	//------- CONSTRUCTORS -------//

	/**
	 * Creates a complete task log object.
	 * @param id The id of the task log
	 * @param name The name of the task
	 * @param description The description of the task
	 * @param groupId The id of the group this belongs to
	 * @param completion The date this task was completed
	 * @param taskId The id of the task
	 * @param assignee The id of the user who completed the task
	 */
	protected TaskLog(int id, String name, String description, int groupId,
	               Date completion, int taskId, User assignee) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.groupId = groupId;
		this.completion = completion;
		this.taskId = taskId;
		this.assignee = assignee;
	}

	//------- OBJECT METHODS -------//

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getGroupId() {
		return groupId;
	}

	public Date getCompletion() {
		return completion;
	}

	public int getTaskId() {
		return taskId;
	}

	public User getAssignee() {
		return assignee;
	}
}
