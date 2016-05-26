package com.rip.roomies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.logging.Logger;

/**
 * This class defines a task that a user needs to complete as part of housework.
 */
public abstract class Task<TLog extends TaskLog, T extends Task<TLog, T>> implements Parcelable {
	private static final Logger log = Logger.getLogger(Task.class.getName());

	private int id;
	private String name;
	private String description;
	private int groupId;
	private User assignee;
	private User[] users;

	//------- CONSTRUCTORS -------//

	/**
	 * This is the remove, find, or complete constructor since only the id field is necessary.
	 * @param id The id of the task to remove/find/complete
	 */
	protected Task(int id) {
		this.id = id;
	}

	/**
	 * This is the create constructor for if a new task needs to be created, hence
	 * the lack of an id field. All fields except description must exist.
	 * @param name The name of the task to be created
	 * @param description The description of the task to be created. May be null
	 * @param groupId The id of the group this is being added to
	 * @param users The list of users to whom this applies
	 */
	protected Task(String name, String description, int groupId, User[] users) {
		this.name = name;
		this.description = description;
		this.groupId = groupId;
		this.users = users;
	}

	/**
	 * This is the update constructor for a task in case the item needs to be updated,
	 * hence the exclusion of groupId (which is non-modifiable).
	 * @param id The id of the task
	 * @param name The name of the task
	 * @param description The description of the task
	 * @param users The list of users to whom this applies
	 */
	protected Task(int id, String name, String description, User[] users) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.users = users;
	}

	/**
	 * Full constructor for getting a return object from the SQL classes.
	 * @param id The id of the task
	 * @param name The name of the task
	 * @param description The description of the task
	 * @param groupId The id of the group this task belongs to
	 * @param users The list of users to whom this applies
	 */
	protected Task(int id, String name, String description, int groupId, User assignee, User[] users) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.groupId = groupId;
		this.assignee = assignee;
		this.users = users;
	}

	//------- DATABASE METHODS -------//

	/**
	 * Creates a task using the current object.
	 * @return The task created
	 */
	protected abstract T create();

	/**
	 * Modifies this task using the current object.
	 * @return The task modified
	 */
	protected abstract T modify();

	/**
	 * Removes this task using the current object.
	 * @return The removed task
	 */
	protected abstract T remove();

	/**
	 * Completes the task, moves the rotation, then creates a log of the completion.
	 * @return The task log created
	 */
	protected abstract TLog complete();

	/**
	 * Gets the users that are associated with this task
	 * @return The users
	 */
	protected abstract T getRotation();

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

	public User getAssignee() {
		return assignee;
	}

	public User[] getUsers() {
		return users;
	}

	//------- PARCELABLE METHODS -------//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeInt(groupId);
		assignee.writeToParcel(dest, flags);
		dest.writeInt(users.length);
		for (User u : users) {
			u.writeToParcel(dest, flags);
		}
	}

	/**
	 * Creates a duty object from a parcel.
	 * @param in The parcel to read
	 */
	protected Task(Parcel in) {
		id = in.readInt();
		name = in.readString();
		description = in.readString();
		groupId = in.readInt();
		assignee = new User(in);

		int length = in.readInt();
		User[] temp = new User[length];
		for (int i = 0; i < length; ++i) {
			temp[i] = new User(in);
		}

		users = temp;
	}
}
