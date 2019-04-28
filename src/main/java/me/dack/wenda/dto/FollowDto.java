package me.dack.wenda.dto;

import java.util.List;

import me.dack.wenda.model.User;

public class FollowDto {

	private List<User> users;
	private long followCount;
	public FollowDto(List<User> users,long followCount){
		this.users = users;
		this.followCount = followCount;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public long getFollowCount() {
		return followCount;
	}
	public void setFollowCount(long followCount) {
		this.followCount = followCount;
	}
}